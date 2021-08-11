package com.javafanatics.jibberjabber.controllers;

import com.javafanatics.jibberjabber.jibber.Jibber;
import com.javafanatics.jibberjabber.jibber.JibberService;
import com.javafanatics.jibberjabber.notification.NotificationService;
import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/jibber")
public class JibberController {

    private UserService userService;
    private JibberService jibberService;
    private NotificationService notificationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setJibberService(JibberService jibberService) {
        this.jibberService = jibberService;
    }

    @PostMapping("/post")
    public String postJibber(@Valid @ModelAttribute Jibber jibber, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/home";
        }

        User user = userService .getUserByHandle(principal.getName());
        jibber.setUser(user);
        jibberService.save(jibber);
        notificationService.sendNotification(user);

        return "redirect:/home";
    }
}
