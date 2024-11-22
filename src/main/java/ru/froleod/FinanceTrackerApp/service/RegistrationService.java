package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.User;

import java.math.BigDecimal;

@Service
public class RegistrationService {

    @Autowired
    private AppService appService;

    public void addUser(String firstName,
                          String lastName,
                          String middleName,
                          String username,
                          String password) {
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
    }

    private String generateAccountNumber() {
        return "BA" + System.currentTimeMillis();
    }

}
