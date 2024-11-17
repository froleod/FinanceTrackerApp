package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home"; // Это направляет на home.html
    }
}
