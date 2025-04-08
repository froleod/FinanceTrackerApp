package ru.froleod.FinanceTrackerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Контроллер выхода из системы", description = "Операции, связанные с выходом пользователя из системы")
public class LogoutController {

    @Operation(summary = "Выход из системы", description = "Производит выход пользователя и перенаправляет на страницу входа.")
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/auth/login?logout";
    }
}