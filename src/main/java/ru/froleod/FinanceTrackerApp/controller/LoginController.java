package ru.froleod.FinanceTrackerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Tag(name = "Контроллер входа в систему", description = "Операции, связанные с процессом входа в систему")
public class LoginController {

    @Operation(summary = "Страница входа", description = "Отображает страницу входа.")
    @GetMapping
    public String showLoginPage() {
        return "auth/login";
    }
}

