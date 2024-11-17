package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.BankAccountRepository;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/bank-account")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountRepository repository;
    private final UserRepository userRepository;

    public BankAccountController(BankAccountService bankAccountService, BankAccountRepository repository, UserRepository userRepository) {
        this.bankAccountService = bankAccountService;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showBankAccountPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
        User user = userRepository.findById(1L).get();
        BankAccount account = repository.findByUserId(1L);
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("accountNumber", account.getAccountNumber());
        // Здесь можно добавить данные банковского счета для отображения
        model.addAttribute("balance", "$5000"); // Пример, нужно заменить на реальные данные
        return "bank-account"; // Это указывает на файл bank_account.html в resources/templates
    }

    @GetMapping("/create-budget")
    public String showCreateBudgetPage() {
        return "create-budget"; // Указывает на страницу для создания бюджета
    }

    @PostMapping("/create-budget")
    public String createBudget(@RequestParam BigDecimal amount, @RequestParam String month) {
        // Логика для создания бюджета
        return "redirect:/bank-account"; // После создания бюджета перенаправляем обратно на страницу счета
    }

//    @GetMapping("/transactions")
//    public String showTransactionsPage(Model model) {
//        User user = userRepository.findById(1L).get();
//        BankAccount account = repository.findByUserId(1L);
//        return "transactions"; // Страница для просмотра транзакций
//    }
}

