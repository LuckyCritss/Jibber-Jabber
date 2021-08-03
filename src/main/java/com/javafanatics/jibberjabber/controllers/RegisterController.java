package com.javafanatics.jibberjabber.controllers;

import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String add(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping
    public String processForm(@Valid @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.save(user);
        } catch (UserService.PasswordMisMatchException e) {
            bindingResult.rejectValue("password","password-mismatch",e.getMessage());
            return "register";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            if (e.getMessage().contains("handle_unique")) {
                bindingResult.rejectValue("handle","user.handle-unique",e.getMessage());
            }
            return "register";
        }
        authWithAuthManager(request, user.getHandle(),user.getCheckPassWord());
        return "redirect:/home";
    }

    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}