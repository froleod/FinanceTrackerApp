package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.repo.BudgetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BankAccountService bankAccountService;

    // Создание нового бюджета
    public Budget createBudget(Budget budget, Long bankAccountId) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("BankAccount not found with id " + bankAccountId));
        budget.setBankAccount(bankAccount);
        return budgetRepository.save(budget);
    }

    // Получение бюджета по ID
    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }

    // Получение всех бюджетов
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    // Обновление существующего бюджета
    public Budget updateBudget(Long id, Budget updatedBudget) {
        return budgetRepository.findById(id)
                .map(existingBudget -> {
                    existingBudget.setName(updatedBudget.getName());
                    existingBudget.setAmount(updatedBudget.getAmount());
                    existingBudget.setMonth(updatedBudget.getMonth());
                    existingBudget.setStatus(updatedBudget.getStatus());
                    return budgetRepository.save(existingBudget);
                })
                .orElseThrow(() -> new RuntimeException("Budget not found with id " + id));
    }

    // Удаление бюджета
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    // Получение всех бюджетов для определенного банковского счета
    public List<Budget> getBudgetsByBankAccountId(Long bankAccountId) {
        return budgetRepository.findByBankAccountId(bankAccountId);
    }
}