package ru.froleod.FinanceTrackerApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.BudgetRepository;
import ru.froleod.FinanceTrackerApp.repo.TransactionRepository;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionPageController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewTransactions(Model model) {
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        if (user != null) {
            List<Transaction> transactions = transactionRepository.findByBudgetBankAccountUserId(user.getId());
            model.addAttribute("transactions", transactions);
        }
        return "transactions"; // transactions.html
    }

    @GetMapping("/create")
    public String createTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "create-transaction"; // Убедитесь, что create-transaction.html существует
    }

    @PostMapping
    public String createTransaction(@ModelAttribute Transaction transaction, @RequestParam Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found with id " + budgetId));
        transaction.setBudget(budget);
        transactionRepository.save(transaction);
        return "redirect:/transactions";
    }


    // ... (similar methods for handling creation and editing transactions)
}
