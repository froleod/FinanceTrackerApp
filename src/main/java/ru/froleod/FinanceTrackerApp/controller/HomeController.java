package ru.froleod.FinanceTrackerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;

@Controller
@Tag(name = "Контроллер домашней страницы", description = "Операции, связанные с отображением домашней страницы")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Домашняя страница", description = "Отображает домашнюю страницу для аутентифицированного пользователя.")
    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "auth/home";
    }
}
