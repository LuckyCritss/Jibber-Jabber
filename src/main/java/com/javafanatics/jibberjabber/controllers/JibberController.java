package com.javafanatics.jibberjabber.controllers;

import com.javafanatics.jibberjabber.jibber.JibberService;
import com.javafanatics.jibberjabber.jibber.Jibbers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JibberController {


    private JibberService jibberService;

    @Autowired
    public void setJibberService(JibberService jibberService){
        this.jibberService = jibberService;
    }

    @GetMapping("/Jibbers")
    public String showAllJibber (Model model){
        model.addAttribute("Jibbers", jibberService.getAll());
        return "jibbers";
    }

    public String createJibberTweet (Model model){
        model.addAttribute("Jibbers", new Jibbers());
        return "/jibbers/edit";
    }
}