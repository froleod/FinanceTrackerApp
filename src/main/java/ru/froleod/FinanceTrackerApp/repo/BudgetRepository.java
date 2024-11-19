package ru.froleod.FinanceTrackerApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.froleod.FinanceTrackerApp.model.Budget;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByBankAccountId(Long accountId);

    List<Budget> findByBankAccountUserId(Long userId);

    List<Budget> findByBankAccountUserUsername(String username);

}