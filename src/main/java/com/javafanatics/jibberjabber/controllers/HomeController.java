package com.javafanatics.jibberjabber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHome() {
        return "home";
    }

    @GetMapping("/register")
    public String name(@RequestParam(name="name", required=false, defaultValue=" ") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }

    @PostMapping("/home")
    public String signin (@RequestParam(name="name", required=false, defaultValue=" ") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }

}