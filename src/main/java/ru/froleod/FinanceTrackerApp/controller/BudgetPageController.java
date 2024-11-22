package ru.froleod.FinanceTrackerApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.model.enums.Months;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;
import ru.froleod.FinanceTrackerApp.service.BudgetService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetPageController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetService budgetService;

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth.getPrincipal() instanceof UserDetails) ?
                ((UserDetails) auth.getPrincipal()).getUsername() : auth.getName();
    }


    @GetMapping
    public String viewBudgets(Model model) {
        model.addAttribute("budgets", budgetService.viewBudgets());
        return "budgets/budgets";
    }

    @GetMapping("/create")
    public String createBudgetForm(Model model) {
        String username = getCurrentUsername();
        BankAccount bankAccount = bankAccountService.getBankAccountByUserUsername(username);
        model.addAttribute("bankAccountId", bankAccount.getId());
        model.addAttribute("budget", new Budget());
        model.addAttribute("months", Months.values());
        return "budgets/create-budget";
    }


    @PostMapping
    public String createBudget(@ModelAttribute Budget budget, @RequestParam Long bankAccountId, @RequestParam String month,
                               @RequestParam String name, @RequestParam BigDecimal amount) {
        budgetService.createBudget(budget, bankAccountId, month, name, amount);
        return "redirect:/budgets/budgets";
    }

    @GetMapping("/edit/{id}")
    public String editBudgetForm(@PathVariable Long id, Model model) {
        Budget budget = budgetService.getBudgetById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        model.addAttribute("budget", budget);
        model.addAttribute("months", Months.values());
        return "budgets/edit-budget";
    }

    @PostMapping("/edit/{id}")
    public String updateBudget(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam BigDecimal amount,
            @RequestParam String month,
            @RequestParam String status
    ) {
        budgetService.updateBudget(id, name, amount, month, status);
        return "redirect:/budgets/budgets";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return "redirect:/budgets/budgets";
    }

    @GetMapping("/{id}/transactions")
    public String viewBudgetTransactions(@PathVariable Long id, Model model) {
        Budget budget = budgetService.getBudgetById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        List<Transaction> transactions = transactionService.findTransactionsByBudgetId(id);
        model.addAttribute("budget", budget);
        model.addAttribute("transactions", transactions);
        return "budgets/budget-transactions";
    }

}