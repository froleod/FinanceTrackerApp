package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String showLoginPage() {
        return "login"; // Это указывает на файл login.html в resources/templates
    }

//    @PostMapping
//    public String handleLogin(@RequestParam String username, @RequestParam String password) {
//        // Логика авторизации пользователя, например проверка в БД
//        // Если успешная авторизация:
//        return "redirect:/bank-account"; // Перенаправление на страницу банковского аккаунта
//    }
}

