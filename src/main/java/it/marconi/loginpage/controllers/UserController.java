package it.marconi.loginpage.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.loginpage.domains.RegistrationForm;

import it.marconi.loginpage.services.UserService;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView viewHomepage() {
        return new ModelAndView("homepage");
    }

    @GetMapping("/user")
    public ModelAndView handlerUserAction(@RequestParam("type") String type) {
        if(type.equals("new"))
            return new ModelAndView("registration").addObject("userForm", new RegistrationForm());
        else if(type.equals("login"))
            return new ModelAndView("login").addObject("userForm", new RegistrationForm());
            
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found :(");
    }

    @PostMapping("/user/login")
    public ModelAndView handlerLoggedUser(
        @ModelAttribute RegistrationForm userForm,
        RedirectAttributes attr
    ) {
        String username = userForm.getUsername();
        Optional<RegistrationForm> user = userService.getByUsername(username);

        if(user.isPresent())
            return new ModelAndView("redirect:/user/" + username);

        attr.addFlashAttribute("not found", true);
        return new ModelAndView("redirect:/user?type=login");
    }

    @PostMapping("/user/new")
    public ModelAndView handlerNewUser(@ModelAttribute RegistrationForm userForm) {
        userService.addUser(userForm);

        String username = userForm.getUsername();
        return new ModelAndView("redirect:/user/" + username);
    }

    @GetMapping("/user/{username}")
    public ModelAndView userDetail(@PathVariable("username") String username) {
        Optional<RegistrationForm> user = userService.getByUsername(username);

        if(user.isPresent())
            return new ModelAndView("user-detail").addObject("user", user.get());
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}