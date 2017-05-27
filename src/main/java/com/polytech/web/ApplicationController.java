package com.polytech.web;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.polytech.business.*;
import com.polytech.models.*;
import com.polytech.repository.RequetMongoRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.*;

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
    public String gerer(Model model,Principal principal){

       // List<Communaute> communautes = communauteService.selectAll();
        //model.addAttribute("resultats", communautes);
        Communaute communaute=communauteService.getCommunauteByResponsable(principal.getName());
        model.addAttribute("resultats", communaute);
        List<User> users=userService.findUserByidCommunaute(communaute.getId());
        model.addAttribute("resultatsusers", users);
        for (User x:users)
            System.out.println(x.getUsername());

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
    public String rate(Requete requete, Principal principal, Model model){
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


    @RequestMapping(value = "/deleteOrUpdateCommunaute")
    public String deleteOrUpdateCommunaute(Model model, Principal principal){
        Communaute communaute=communauteService.getCommunauteByResponsable(principal.getName());
        model.addAttribute("communaute",communaute);
        return "deleteOrUpdateCommunaute";
    }



    //
    @RequestMapping(value = "/deleteCommunaute/{id}")
    public String deleteCommunaute(@PathVariable("id") String id, Principal principal){
        communauteService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/UpdateCommunaute", method = RequestMethod.POST)
    public String UpdateCommunaute(Communaute communaute, Principal principal){
        communaute.setResponsableID(principal.getName());
        System.out.println(communaute.getNom());
        System.out.println(communaute.getId());
        System.out.println(communaute.getDescription());
        communauteService.save(communaute);
        return "/deleteOrUpdateCommunaute";
    }
    //
    @RequestMapping(value = "/Update", method = RequestMethod.POST)
    public String Update(){
        return  "redirect:/deleteOrUpdateCommunaute";
    }

/////
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



}
