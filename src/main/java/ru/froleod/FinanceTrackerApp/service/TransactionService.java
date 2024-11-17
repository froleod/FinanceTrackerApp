package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.repo.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findTransactionsByBudgetId(Long budgetId) {
        return transactionRepository.findByBudgetId(budgetId);
    }

    public List<Transaction> findTransactionsByFinancialGoalId(Long financialGoalId) {
        return transactionRepository.findByFinancialGoalId(financialGoalId);
    }
}