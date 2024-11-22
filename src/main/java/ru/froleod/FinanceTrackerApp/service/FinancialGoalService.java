package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.model.enums.GoalStatus;
import ru.froleod.FinanceTrackerApp.model.enums.TransactionType;
import ru.froleod.FinanceTrackerApp.repo.FinancialGoalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialGoalService {

    @Autowired
    private FinancialGoalRepository financialGoalRepository;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;

    public List<FinancialGoal> getAllFinancialGoals() {
        return financialGoalRepository.findAll();
    }

    public void createFinancialGoal(
            Long bankAccountId,
            String name,
            BigDecimal targetAmount,
            String startDate,
            String endDate,
            String status) {

        FinancialGoal financialGoal = new FinancialGoal();

        financialGoal.setName(name);
        financialGoal.setTargetAmount(targetAmount);

        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("BankAccount not found"));
        financialGoal.setBankAccount(bankAccount);

        // Установка дат
        financialGoal.setStartDate(startDate == null || startDate.isEmpty() ? LocalDate.now() : LocalDate.parse(startDate));
        if (endDate != null && !endDate.isEmpty()) {
            financialGoal.setEndDate(LocalDate.parse(endDate));
        }

        GoalStatus goalStatus = GoalStatus.valueOf(status.toUpperCase());
        financialGoal.setStatus(goalStatus);

        financialGoalRepository.save(financialGoal);
    }

    public void updateFinancialGoalForm(Long id,
                                          String name,
                                          BigDecimal targetAmount,
                                          String startDate,
                                          String endDate,
                                          String status) {

        FinancialGoal financialGoal = financialGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));
        financialGoal.setName(name);
        financialGoal.setTargetAmount(targetAmount);
        if (startDate != null && !startDate.isEmpty()) {
            financialGoal.setStartDate(LocalDate.parse(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            financialGoal.setEndDate(LocalDate.parse(endDate));

        }
        GoalStatus goalStatus = GoalStatus.valueOf(status.toUpperCase());
        financialGoal.setStatus(goalStatus);
        financialGoalRepository.save(financialGoal);
    }

    public void createTransactionForGoal(
            Long goalId,
            String name,
            BigDecimal amount,
            String transactionDate,
            String type) {

        FinancialGoal financialGoal = financialGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));

        Transaction transaction = new Transaction();
        transaction.setName(name);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.valueOf(type.toUpperCase()));
        transaction.setFinancialGoal(financialGoal);

        LocalDate date = (transactionDate == null || transactionDate.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(transactionDate);
        transaction.setTransactionDate(date);

        BankAccount bankAccount = financialGoal.getBankAccount();

        // Изменяем баланс счёта на основе типа транзакции
        if (transaction.getType() == TransactionType.EXPENSE) { // исходящая - накопления из копилки достаются
            bankAccount.setBalance(bankAccount.getBalance().add(amount));
        } else if (transaction.getType() == TransactionType.INCOME) { // входящая для фин цели - значит со своего счета перечисляем в копилку
            if (bankAccount.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient funds for this transaction.");
            }
            bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
        }

        transactionService.saveTransaction(transaction);
        bankAccountService.updateBankAccount(bankAccount);
    }

    public Optional<FinancialGoal> getFinancialGoalById(Long id) {
        return financialGoalRepository.findById(id);
    }

    public void deleteFinancialGoal(Long id) {
        financialGoalRepository.deleteById(id);
    }
}
