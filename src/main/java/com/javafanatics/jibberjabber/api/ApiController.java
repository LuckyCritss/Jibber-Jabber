package com.javafanatics.jibberjabber.api;

import com.javafanatics.jibberjabber.jibber.JibberService;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String users() {
        userService.getAll();
        return "allusers";
    }
    @GetMapping("/users/{handle}")
    public String user(@PathVariable String handle) {
        userService.getUserByHandle(handle);
        return "user";
    }

    @GetMapping("/users/{handle}/jibbers")
    public String userJibbers(@PathVariable String handle) {
        jibberService.findByHandle(handle);
        return "userjibbers";
    }

    @GetMapping("/jibbers")
    public String jibbers() {
        jibberService.findAll();
        return "jibbers";
    }

    @GetMapping("/jibbers/{id}")
    public String jibbersId(@PathVariable int id) {
        jibberService.findByid(id);
        return "jibberid";
    }
}
