package com.polytech.web;

import com.polytech.business.*;
import com.polytech.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 3/15/17.
 */
@Controller
public class ApplicationController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private CommunauteService communauteService;

    @Autowired
    private AdhesionService adhesionService;

    @Autowired
    private RechercheService rechercheService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() throws UnsupportedEncodingException {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/dosignup", method = RequestMethod.POST)
    public String dosignup(String username, String password, Principal principal){
        signInService.signIn(new User(username, new BCryptPasswordEncoder().encode(password), 1));
        return "login";
    }

    @RequestMapping(value = "/creer", method = RequestMethod.GET)
    public String creer(){
        return "creer";
    }

    @RequestMapping(value = "/creerCommunaute", method = RequestMethod.POST)
    public String creerCommunaute(Communaute communaute, Principal principal){
        communaute.setResponsableID(principal.getName());
        System.out.println(communaute.getNom());
        System.out.println(communaute.getResponsable());
        System.out.println(communaute.getDescription());
        communauteService.save(communaute);
        return "creer";
    }

    @RequestMapping(value = "/gerer", method = RequestMethod.GET)
    public String gerer(Model model){
        List<Communaute> communautes = communauteService.selectAll();
        model.addAttribute("resultats", communautes);
        return "gerer";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Requete requete, Principal principal, Model model){
        requete.setUsername(principal.getName());
        rechercheService.saveRequete(requete);
        Crawler obj = new Crawler();
        List<Result> results = obj.getDataFromGoogle(requete.getQuery().replace(" ", "+"));
        for(Result temp : results){
            temp.setRequete(requete.getId());
        }
        model.addAttribute("resultats", results);
        return "index";
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public String rate(Result result, Principal principal, Model model){
        rechercheService.saveResultat(result);
        return "index";
    }

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    public ModelAndView click(Result result, Principal principal, Model model){
        rechercheService.saveResultat(result);
        return new ModelAndView("redirect:" + result.getUri());
    }

    @RequestMapping(value = "/rejoindre/{id}")
    public String adherer(@PathVariable("id") String id, Principal principal){
        adhesionService.save(new Adhesion(id, principal.getName()));
        return "redirect:/rejoindre";
    }

    @RequestMapping(value = "/rejoindre")
    public String rejoindre(Model model, Principal principal){
        List<Communaute> communautes = new ArrayList<>();
        User user = userService.findUserByUsername(principal.getName());
        if(user.getIdCommunaute()=="") {
            Adhesion adhesion = adhesionService.findAdhesionByUser(principal.getName());
            if (adhesion != null) {
                communautes.add(communauteService.getCommunauteById(adhesion.getIdCommunaute()));
                model.addAttribute("resultats2", communautes);
            } else {
                communautes = communauteService.selectAll();
                model.addAttribute("resultats", communautes);
            }
        }
        return "rejoindre";
    }

    @RequestMapping(value = "/deleteAdhesion/{id}")
    public String retirerAdhesion(@PathVariable("id") String id, Principal principal){
        String idAdhesion = adhesionService.findAdhesionByUser(principal.getName()).getId();
        adhesionService.delete(idAdhesion);
        return "redirect:/rejoindre";
    }

    @RequestMapping(value = "/gererDemandes")
    public String gererDemandes(Model model, Principal principal){
        String idCommunaute = communauteService.getCommunauteByResponsable(principal.getName()).getId();
        List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(idCommunaute);
        model.addAttribute("demandes", adhesions);
        return "gererDemandes";
    }

    @RequestMapping(value = "/approuver/{id}")
    public String approuverDamande(@PathVariable("id") String id, Principal principal){
        Communaute communaute = communauteService.getCommunauteByResponsable(principal.getName());
        Adhesion adhesion = adhesionService.findAdhesionByID(id);
        User user = userService.findUserByUsername(adhesion.getIdUtilisateur());
        user.setIdCommunaute(communaute.getId());
        userService.save(user);
        adhesionService.delete(adhesion.getId());
        return "redirect:/gererDemandes";
    }

    @RequestMapping(value = "/desapprouver/{id}")
    public String desapprouverDamande(@PathVariable("id") String id, Principal principal){
        Adhesion adhesion = adhesionService.findAdhesionByID(id);
        adhesionService.delete(adhesion.getId());
        return "redirect:/gererDemandes";
    }

}
