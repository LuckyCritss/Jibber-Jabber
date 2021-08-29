package com.javafanatics.jibberjabber.api;

import com.javafanatics.jibberjabber.jibber.Jibber;
import com.javafanatics.jibberjabber.jibber.JibberService;
import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private UserService userService;
    private JibberService jibberService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJibberService(JibberService jibberService) {
        this.jibberService = jibberService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        User users = userService.getAll();
        model.addAttribute("users", users);
        return "allusers";
    }
    @GetMapping("/users/{handle}")
    public String user(@PathVariable String handle, Model model) {
        User user = userService.getUserByHandle(handle);
        model.addAttribute("users", user);
        return "user";
    }

    @GetMapping("/users/{handle}/jibbers")
    public String userJibbers(@PathVariable String handle, Model model) {
        List<Jibber> jibbers = jibberService.findByHandle(handle);
        model.addAttribute("jibbers", jibbers);
        return "userjibbers";
    }

    @GetMapping("/jibbers")
    public String jibbers(Model model) {
        List<Jibber> jibbers = jibberService.findAll();
        model.addAttribute("jibbers",jibbers);
        return "jibbers";
    }

    @GetMapping("/jibbers/{id}")
    public String jibbersId(@PathVariable int id, Model model) {
        List<Jibber> jibbers = jibberService.findByid(id);
        model.addAttribute("jibbers",jibbers);
        return "jibberid";
    }
}
