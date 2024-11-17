package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.repo.FinancialGoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialGoalService {

    @Autowired
    private FinancialGoalRepository financialGoalRepository;

    public List<FinancialGoal> getAllFinancialGoals() {
        return financialGoalRepository.findAll();
    }

    public Optional<FinancialGoal> getFinancialGoalById(Long id) {
        return financialGoalRepository.findById(id);
    }

    public FinancialGoal saveFinancialGoal(FinancialGoal financialGoal) {
        return financialGoalRepository.save(financialGoal);
    }

    public void deleteFinancialGoal(Long id) {
        financialGoalRepository.deleteById(id);
    }
}
