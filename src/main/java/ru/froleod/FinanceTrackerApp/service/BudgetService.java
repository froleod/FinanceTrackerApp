package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.enums.BudgetStatus;
import ru.froleod.FinanceTrackerApp.model.enums.Months;
import ru.froleod.FinanceTrackerApp.repo.BudgetRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static ru.froleod.FinanceTrackerApp.controller.BudgetPageController.getCurrentUsername;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BankAccountService bankAccountService;

    public void createBudget(@ModelAttribute Budget budget, @RequestParam Long bankAccountId, @RequestParam String month,
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
    }

    public List<Budget> viewBudgets() {
        return budgetRepository.findByBankAccountUserUsername(getCurrentUsername());
    }

    public void updateBudget(
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
    }

    // Получение бюджета по ID
    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    public List<Budget> findByBankAccountUserUsername(String username) {
        return budgetRepository.findByBankAccountUserUsername(username);
    }
}