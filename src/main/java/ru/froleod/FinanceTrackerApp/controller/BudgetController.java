package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;
import ru.froleod.FinanceTrackerApp.service.BudgetService;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BankAccountService bankAccountService;

    // Получение всех бюджетов
    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    // Получение бюджета по ID
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Создание нового бюджета
    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Budget newBudget, @RequestParam Long bankAccountId) {
        return bankAccountService.getBankAccountById(bankAccountId)
                .map(bankAccount -> {
                    newBudget.setBankAccount(bankAccount);
                    return ResponseEntity.ok(budgetService.createBudget(newBudget, bankAccountId));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Обновление существующего бюджета
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget updatedBudget) {
        return budgetService.getBudgetById(id)
                .map(existingBudget -> {
                    existingBudget.setName(updatedBudget.getName());
                    existingBudget.setAmount(updatedBudget.getAmount());
                    existingBudget.setMonth(updatedBudget.getMonth());
//                    existingBudget.setStartDate(updatedBudget.getStartDate());
//                    existingBudget.setEndDate(updatedBudget.getEndDate());
                    existingBudget.setStatus(updatedBudget.getStatus());
                    return ResponseEntity.ok(budgetService.updateBudget(id, existingBudget));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удаление бюджета
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok().build();
    }

    // Получение всех бюджетов для определенного банковского счета
    @GetMapping("/account/{bankAccountId}")
    public ResponseEntity<List<Budget>> getBudgetsByBankAccountId(@PathVariable Long bankAccountId) {
        List<Budget> budgets = budgetService.getBudgetsByBankAccountId(bankAccountId);
        return ResponseEntity.ok(budgets);
    }
}