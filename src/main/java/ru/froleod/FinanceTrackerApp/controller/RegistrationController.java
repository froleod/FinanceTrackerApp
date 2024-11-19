package ru.froleod.FinanceTrackerApp.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.service.AppService;

import java.math.BigDecimal;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private AppService appService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("firstName") String firstName,
                          @ModelAttribute("lastName") String lastName,
                          @ModelAttribute("middleName") String middleName,

                          @ModelAttribute("username") String username,
                          @ModelAttribute("password") String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setBankAccount(new BankAccount());
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles("ROLE_USER");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(generateAccountNumber());
        bankAccount.setBalance(BigDecimal.valueOf(100000));
        bankAccount.setUser(user);
        user.setBankAccount(bankAccount);

        // Сохраняем пользователя
        appService.addUser(user);

        return "redirect:/login";
    }

    private String generateAccountNumber() {
        return "BA" + System.currentTimeMillis();
    }
}
