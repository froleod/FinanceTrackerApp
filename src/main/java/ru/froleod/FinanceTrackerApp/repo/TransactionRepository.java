package ru.froleod.FinanceTrackerApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBudget(Budget budget);

    List<Transaction> findByBudgetBankAccountUserId(long userId);

    List<Transaction> findByBudgetId(long budgetId);

    List<Transaction> findByFinancialGoalId(Long financialGoalId);
}

