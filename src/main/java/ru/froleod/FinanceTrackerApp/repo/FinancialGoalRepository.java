package ru.froleod.FinanceTrackerApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;

@Repository
public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {

}
