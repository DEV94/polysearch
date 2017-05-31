package com.polytech.web;

import com.polytech.business.RechercheService;
import com.polytech.business.UserService;
import com.polytech.models.Requete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by E.Marouane on 30/05/2017.
 */
@RestController
public class RestfulController {

    @Autowired
    private RechercheService rechercheService;

    @Autowired
    private UserService userService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/autocomplite")
    public Set<String> autocomplite(Principal principal) {
        String idCommunaute = userService.findUserByUsername(principal.getName()).getIdCommunaute();
        Set<String> list = new HashSet<>();
        for (Requete requete: rechercheService.getRequeteMongo(idCommunaute)
             ) {
            list.add(requete.getQuery());
        }
        return list;
    }
}
