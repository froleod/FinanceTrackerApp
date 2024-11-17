package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.FinancialGoalRepository;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;

import java.util.List;

@Controller
@RequestMapping("/financial-goals")
public class FinancialGoalPageController {

    @Autowired
    private FinancialGoalRepository financialGoalRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewFinancialGoals(Model model) {
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        if (user != null) {
            List<FinancialGoal> financialGoals = financialGoalRepository.findByBankAccountUserId(user.getId());
            model.addAttribute("financialGoals", financialGoals);
        }
        return "financial-goals"; // financial-goals.html.html
    }

    @GetMapping("/create")
    public String createFinancialGoalForm(Model model) {
        model.addAttribute("financialGoal", new FinancialGoal());
        return "create-financial-goal"; // Убедитесь, что create-financial-goal.html существует
    }

    @PostMapping
    public String createFinancialGoal(@ModelAttribute FinancialGoal financialGoal, @RequestParam Long bankAccountId) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("BankAccount not found with id " + bankAccountId));
        financialGoal.setBankAccount(bankAccount);
        financialGoalRepository.save(financialGoal);
        return "redirect:/financial-goals";
    }


    // ... (similar methods for create, edit, delete)
}
