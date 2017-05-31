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
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private AuthorityService authorityService;

    private boolean confirmed = true;

    private boolean passCorrecte = true;

    private boolean fromRate = false;

    private Requete maRequete;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, Principal principal) throws UnsupportedEncodingException {
        String role = getRole(principal.getName());
        System.out.println("Role INDEX : " +  role);
        model.addAttribute("role", role);
        if(role.equals("RESPONSABLE")){
            Communaute communaute = communauteService.getCommunauteByResponsable(principal.getName());
            List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(communaute.getId());
            model.addAttribute("nbDemandes", adhesions.size());
            model.addAttribute("isUser", "false");
        }
        else {
            if(role.equals("MEMBRE")){
                User user = userService.findUserByUsername(principal.getName());
                Communaute communaute = communauteService.getCommunauteById(user.getIdCommunaute());
                model.addAttribute("communaute", communaute.getNom());
                model.addAttribute("isUser", "false");
            }
            else {
                model.addAttribute("isUser", "true");
            }
        }
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
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
    public String creer(Principal principal, Model model){
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        return "creer";
    }

    @RequestMapping(value = "/creerCommunaute", method = RequestMethod.POST)
    public String creerCommunaute(Communaute communaute, Principal principal){
        Communaute communaute1 = communauteService.getCommunauteByResponsable(principal.getName());
        //System.out.println(communaute1.getId() + communaute1.getNom() + communaute1.getResponsable());
        if(communaute1==null) {
            communaute.setResponsableID(principal.getName());
            Communaute communaute2 = communauteService.getCommunauteByNom(communaute.getNom());
            if(communaute2==null) {
                communauteService.save(communaute);
                Authority authority = new Authority(principal.getName(), "RESPONSABLE");
                System.out.println(authority.getAuthority());
                authorityService.save(authority);
                User user = userService.findUserByUsername(communaute.getResponsable());
                user.setIdCommunaute(communaute.getId());
                userService.save(user);
                for (Authority a : authorityService.selectAll()) {
                    System.out.println(a.getAuthority());
                }
            }
        }
        else{
            System.out.println("A DEJA UNE COMMUNAUTE");
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/gerer", method = RequestMethod.GET)
    public String gerer(Model model, Principal principal){
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        if(role.equals("RESPONSABLE")){
            Communaute communaute = communauteService.getCommunauteByResponsable(principal.getName());
            List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(communaute.getId());
            model.addAttribute("nbDemandes", adhesions.size());
        }
        Communaute communaute=communauteService.getCommunauteByResponsable(principal.getName());
        List<User> users=userService.findUserByidCommunaute(communaute.getId());
        model.addAttribute("resultatsusers", users);
        model.addAttribute("resultats", communaute);
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        return "gerer";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Requete requete, Principal principal, Model model){
        requete.setUsername(principal.getName());
        requete.setQuery(requete.getQuery().toLowerCase());
        rechercheService.saveRequete(requete);
        maRequete = requete;
        List<Result> res = new ArrayList<>();//rechercheService.getResultsElastic(requete.getQuery());
        System.out.println("RES SIZE : " + res.size());
        if(res.size() == 0){
            res = rechercheService.getResultsMongo(requete.getQuery(),userService.findUserByUsername(requete.getUsername()).getIdCommunaute());
            System.out.println(res.size());
        }
        Crawler obj = new Crawler();
        List<Result> results = obj.getDataFromGoogle(requete.getQuery().replace(" ", "+"));
        for(Result temp : results){
            temp.setRequete(requete.getId());
        }
        res.addAll(results);
        model.addAttribute("resultats", res);
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        if(role.equals("RESPONSABLE")){
            Communaute communaute = communauteService.getCommunauteByResponsable(principal.getName());
            List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(communaute.getId());
            model.addAttribute("nbDemandes", adhesions.size());
        }
        return "index";
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public String rate(Result result, Principal principal, Model model){
        result.setTitle(result.getTitle().toLowerCase());
        rechercheService.saveResultat(result);
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        return "redirect:/search?query=" + maRequete.getQuery();
    }

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    public ModelAndView click(Result result, Principal principal, Model model){
        result.setTitle(result.getTitle().toLowerCase());
        rechercheService.saveResultat(result);
        return new ModelAndView("redirect:" + result.getUri());
    }

    @RequestMapping(value = "/rejoindre/{id}")
    public String adherer(@PathVariable("id") String id, Principal principal){
        adhesionService.save(new Adhesion(id, principal.getName()));
        return "redirect:/rejoindre";
    }

    @RequestMapping(value = "/rejoindre")
    public String rejoindre(Principal principal, Model model){
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        List<Communaute> communautes = new ArrayList<>();
        
        User user = userService.findUserByUsername(principal.getName());
        if(user.getIdCommunaute()==null) {
            Adhesion adhesion = adhesionService.findAdhesionByUser(principal.getName());
            if (adhesion != null) {
                communautes.add(communauteService.getCommunauteById(adhesion.getIdCommunaute()));
                model.addAttribute("resultats2", communautes);
            } else {
                communautes = communauteService.selectAll();
                model.addAttribute("resultats", communautes);
            }
        }
        model.addAttribute("user",user);
        return "rejoindre";
    }

    @RequestMapping(value = "/deleteAdhesion/{id}")
    public String retirerAdhesion(@PathVariable("id") String id, Principal principal){
        String idAdhesion = adhesionService.findAdhesionByUser(principal.getName()).getId();
        adhesionService.delete(idAdhesion);
        return "redirect:/rejoindre";
    }

    @RequestMapping(value = "/gererDemandes")
    public String gererDemandes(Principal principal, Model model){
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        String idCommunaute = communauteService.getCommunauteByResponsable(principal.getName()).getId();
        List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(idCommunaute);
        model.addAttribute("demandes", adhesions);
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
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
        Authority authority = new Authority(user.getUsername(),"MEMBRE");
        authorityService.save(authority);
        return "redirect:/gererDemandes";
    }


    @RequestMapping(value = "/desapprouver/{id}")
    public String desapprouverDamande(@PathVariable("id") String id, Principal principal){
        Adhesion adhesion = adhesionService.findAdhesionByID(id);
        adhesionService.delete(adhesion.getId());
        return "redirect:/gererDemandes";
    }


    @RequestMapping(value = "/deleteOrUpdateCommunaute")
    public String deleteOrUpdateCommunaute(Principal principal, Model model){
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        Communaute communaute=communauteService.getCommunauteByResponsable(principal.getName());
        model.addAttribute("communaute",communaute);
        List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(communaute.getId());
        model.addAttribute("nbDemandes", adhesions.size());
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        return "deleteOrUpdateCommunaute";
    }



    //
    @RequestMapping(value = "/deleteCommunaute/{id}")
    public String deleteCommunaute(@PathVariable("id") String id, Principal principal){

      List<User> users= userService.findUserByidCommunaute(id);
        for (User user: users) {
            user.setIdCommunaute(null);
            userService.save(user);
            Authority authority=new Authority();
            authority.setAuthority("USER");
            authority.setUsername(user.getUsername());
            authorityService.save(authority);
        }

        communauteService.delete(id);
        Authority authority=new Authority();
        authority.setAuthority("USER");
        authority.setUsername(principal.getName());
        authorityService.save(authority);


        return "redirect:/";
    }

    @RequestMapping(value = "/UpdateCommunaute", method = RequestMethod.POST)
    public String UpdateCommunaute(Communaute communaute, Principal principal){
        communaute.setResponsableID(principal.getName());
        Communaute communaute2 = communauteService.getCommunauteByNom(communaute.getNom());
        if(communaute2==null) {
            communauteService.save(communaute);
        }
        return "redirect:/gerer";
    }
    //
    @RequestMapping(value = "/Update", method = RequestMethod.POST)
    public String Update(){
        return  "redirect:/deleteOrUpdateCommunaute";
    }

    public String getRole(String username){
        Authority authority = authorityService.getAuthorityByUsername(username);
        return authority.getAuthority();
    }

    @RequestMapping(value = "/compte", method = RequestMethod.GET)
    public String compte(Principal principal, Model model){
        String role = getRole(principal.getName());
        model.addAttribute("role", role);
        if(role.equals("RESPONSABLE")){
            Communaute communaute = communauteService.getCommunauteByResponsable(principal.getName());
            List<Adhesion> adhesions = adhesionService.findAdhesionByCommunaute(communaute.getId());
            model.addAttribute("nbDemandes", adhesions.size());
        }
        else {
            if(role.equals("MEMBRE")){
                User user = userService.findUserByUsername(principal.getName());
                Communaute communaute = communauteService.getCommunauteById(user.getIdCommunaute());
                model.addAttribute("communaute", communaute.getNom());
            }
        }
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        if(passCorrecte)
            model.addAttribute("pinc","true");
        if(confirmed)
            model.addAttribute("pni","true");

        return  "compte";
    }


    @RequestMapping(value = "/UpdatePassword", method = RequestMethod.POST)
    public String UpdatePassword(@RequestParam("newPassword") String password, @RequestParam("confPassword") String confirmation, Model model, Principal principal){
        User user = userService.findUserByUsername(principal.getName());
        if(!password.equals(confirmation)){
                confirmed = false;
                model.addAttribute("pni","false");
                System.out.println("NON IDENTIQUES");
            }
            else {
                confirmed = true;
                user.setEnable(1);
                user.setPassword(password);
                userService.save(user);
            }


        //userService.save(user);
        return "redirect:/compte";
    }

    @RequestMapping(value = "/UpdateCompte", method = RequestMethod.POST)
    public String UpdateCompte(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom, Model model, Principal principal){
        User user = userService.findUserByUsername(principal.getName());
        user.setNom(nom);
        user.setPrenom(prenom);
        userService.save(user);

        //userService.save(user);
        return "redirect:/compte";
    }

    @RequestMapping(value = "/deleteUserFromCommunaute/{id}")
    public String deleteUserFromCommunaute(@PathVariable("id") String id, Principal principal){
        //
        User user = userService.findUserByUsername(id);
        user.setIdCommunaute(null);
        userService.save(user);
        Authority authority=new Authority();
        authority.setAuthority("USER");
        authority.setUsername(user.getUsername());
        authorityService.save(authority);
        return "redirect:/gerer";
    }

    @RequestMapping(value = "/QuiteCommunaute")
    public String QuiteCommunaute(Principal principal){
        //
        User user = userService.findUserByUsername(principal.getName());
        user.setIdCommunaute(null);
        userService.save(user);
        Authority authority=new Authority();
        authority.setAuthority("USER");
        authority.setUsername(user.getUsername());
        authorityService.save(authority);
        return "redirect:/";
    }
}
