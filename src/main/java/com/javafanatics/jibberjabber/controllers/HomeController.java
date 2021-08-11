package com.javafanatics.jibberjabber.controllers;

import com.javafanatics.jibberjabber.jibber.Jibber;
import com.javafanatics.jibberjabber.jibber.JibberService;
import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private UserService userService;
    private JibberService jibberService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJibberService(JibberService JIbberService) {
        this.jibberService = JIbberService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal, HttpServletRequest request) {
        User user = userService.getUserByHandle(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("jibber", new Jibber());
        model.addAttribute("jibbers", jibberService.getFeedForUser(user));
        return "home";
    }


    @GetMapping("/{handle:^(?!user$|home$).*}")
    public String userFeed(@PathVariable String handle, Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("handle",handle);
        List<Jibber> jibbers = jibberService.findByHandle(handle);
        if (jibbers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found");
        }
        model.addAttribute("jibbers", jibbers);
        if (principal != null ) {
             model.addAttribute("user",userService.getUserByHandle(principal.getName()));
        }
        return "profile";
    }

}
