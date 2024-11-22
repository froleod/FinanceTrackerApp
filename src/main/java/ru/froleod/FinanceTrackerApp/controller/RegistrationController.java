package ru.froleod.FinanceTrackerApp.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.froleod.FinanceTrackerApp.service.RegistrationService;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration(){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("firstName") String firstName,
                          @ModelAttribute("lastName") String lastName,
                          @ModelAttribute("middleName") String middleName,

                          @ModelAttribute("username") String username,
                          @ModelAttribute("password") String password) {

        registrationService.addUser(firstName, lastName, middleName, username, password);
        return "redirect:/auth/login";
    }

}
