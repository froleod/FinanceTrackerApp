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
import ru.froleod.FinanceTrackerApp.model.enums.BudgetStatus;
import ru.froleod.FinanceTrackerApp.model.enums.Months;
import ru.froleod.FinanceTrackerApp.repo.BudgetRepository;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetPageController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth.getPrincipal() instanceof UserDetails) ?
                ((UserDetails) auth.getPrincipal()).getUsername() : auth.getName();
    }


    @GetMapping
    public String viewBudgets(Model model) {
        String username = getCurrentUsername();
        List<Budget> budgets = budgetRepository.findByBankAccountUserUsername(username);
        model.addAttribute("budgets", budgets);
        return "budgets";
    }


//    @GetMapping("/create")
//    public String createBudgetForm(Model model) {
//        model.addAttribute("budget", new Budget());
//        model.addAttribute("months", Months.values());
//        return "create-budget";
//    }

    @GetMapping("/create")
    public String createBudgetForm(Model model) {
        String username = getCurrentUsername();
        BankAccount bankAccount = bankAccountService.getBankAccountByUserUsername(username);
        model.addAttribute("bankAccountId", bankAccount.getId());
        model.addAttribute("budget", new Budget());
        model.addAttribute("months", Months.values());
        return "create-budget";
    }


    @PostMapping
    public String createBudget(@ModelAttribute Budget budget, @RequestParam Long bankAccountId, @RequestParam String month,
                               @RequestParam String name, @RequestParam BigDecimal amount) {
        // Получение банковского счета через ID
        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("BankAccount not found with id " + bankAccountId));

        // Преобразование строки в enum
        Months monthEnum;
        try {
            monthEnum = Months.valueOf(month.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid month value: " + month);
        }

        budget.setMonth(monthEnum);
        budget.setBankAccount(bankAccount);
        budget.setAmount(amount);
        budget.setName(name);
        budget.setStatus(BudgetStatus.ACTIVE);
        budgetRepository.save(budget);
        return "redirect:/budgets";
    }

    @GetMapping("/edit/{id}")
    public String editBudgetForm(@PathVariable Long id, Model model) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        model.addAttribute("budget", budget);
        model.addAttribute("months", Months.values());
        return "edit-budget";
    }

    @PostMapping("/edit/{id}")
    public String updateBudget(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam BigDecimal amount,
            @RequestParam String month,
            @RequestParam String status
    ) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        // Обновляем поля
        existingBudget.setName(name);
        existingBudget.setAmount(amount);
        existingBudget.setMonth(Months.valueOf(month.toUpperCase()));
        existingBudget.setStatus(BudgetStatus.valueOf(status.toUpperCase()));

        // Сохраняем обновленный бюджет
        budgetRepository.save(existingBudget);

        return "redirect:/budgets";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetRepository.deleteById(id);
        return "redirect:/budgets";
    }

    @GetMapping("/{id}/transactions")
    public String viewBudgetTransactions(@PathVariable Long id, Model model) {
        String username = getCurrentUsername();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        List<Transaction> transactions = transactionService.findTransactionsByBudgetId(id);
        model.addAttribute("budget", budget);
        model.addAttribute("transactions", transactions);
        return "budget-transactions"; // название шаблона, который будет использоваться для отображения
    }



}