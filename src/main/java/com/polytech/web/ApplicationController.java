package com.polytech.web;

import com.polytech.business.SignInService;
import com.polytech.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by dev on 3/15/17.
 */
@Controller
public class ApplicationController {
    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
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
}
