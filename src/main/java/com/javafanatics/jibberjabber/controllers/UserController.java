package com.javafanatics.jibberjabber.controllers;

import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
public class UserController {

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

    @GetMapping("/signup")
    public String add(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String processForm(@Valid @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        try {
            userService.save(user);
        } catch (UserService.PasswordException e) {
            bindingResult.rejectValue("passWord","user.password",e.getMessage());
            return "signup";
        } catch (UserService.PasswordMisMatchException e) {
            bindingResult.rejectValue("passWord","password-mismatch",e.getMessage());
            return "signup";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            if (e.getMessage().contains("handle_unique")) {
                bindingResult.rejectValue("handle","user.handle-unique",e.getMessage());
            }
            return "signup";
        }
        authWithAuthManager(request, user.getHandle(),user.getCheckPassWord());
        return "redirect:/home";
    }

    @GetMapping("/users")
    public String users(Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("user",
                userService .getUserByHandle(principal.getName()));
        model.addAttribute("usersIFollow",
                userService.getUsersIFollow(principal.getName()));
        model.addAttribute("usersToFollow",
                userService.getUsersToFollow(principal.getName(), (Set<User>) model.getAttribute("usersIFollow")));
        return "users";
    }

    @PostMapping("/user/follow")
    public String follow(@RequestParam(name = "handle") String follow, Principal principal) {
        userService.follow(principal.getName(),follow);
        return "redirect:/users";
    }

    @PostMapping("/user/unfollow")
    public String unFollow(@RequestParam(name = "handle") String follow, Principal principal) {
        userService.unFollow(principal.getName(),follow);
        return "redirect:/users";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }






}
