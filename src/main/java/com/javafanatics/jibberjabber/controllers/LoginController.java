package com.javafanatics.jibberjabber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Principal principal){
        if (principal != null){
            return ("login");
        }
        return ("home");
    }

}
