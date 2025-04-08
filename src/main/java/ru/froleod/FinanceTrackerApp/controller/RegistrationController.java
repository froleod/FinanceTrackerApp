package ru.froleod.FinanceTrackerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.froleod.FinanceTrackerApp.service.RegistrationService;

@Controller
@AllArgsConstructor
@Tag(name = "Контроллер регистрации", description = "Операции, связанные с регистрацией нового пользователя")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Форма регистрации", description = "Отображает страницу регистрации.")
    @GetMapping("/registration")
    public String registration(){
        return "auth/registration";
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Обрабатывает форму регистрации и добавляет нового пользователя.")
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
