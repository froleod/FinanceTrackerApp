package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.model.enums.GoalStatus;
import ru.froleod.FinanceTrackerApp.model.enums.TransactionType;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;
import ru.froleod.FinanceTrackerApp.service.FinancialGoalService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/financial-goals")
public class FinancialGoalPageController {

    @Autowired
    private FinancialGoalService financialGoalService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public String viewFinancialGoals(Model model) {
        List<FinancialGoal> financialGoals = financialGoalService.getAllFinancialGoals();
        model.addAttribute("financialGoals", financialGoals);
        return "financial-goals";
    }

    @GetMapping("/create")
    public String createFinancialGoalForm(Model model) {
        model.addAttribute("financialGoal", new FinancialGoal());
        return "create-financial-goal";
    }

    @PostMapping
    public String createFinancialGoal(
            @RequestParam Long bankAccountId,
            @RequestParam String name,
            @RequestParam BigDecimal targetAmount,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String status) {

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

        financialGoalService.saveFinancialGoal(financialGoal);
        return "redirect:/financial-goals";
    }


    @GetMapping("/{id}/transactions")
    public String viewTransactionsForGoal(@PathVariable Long id, Model model) {
        FinancialGoal financialGoal = financialGoalService.getFinancialGoalById(id)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));

        List<Transaction> transactions = transactionService.findTransactionsByFinancialGoalId(id);

        // Расчет накопленной суммы
        BigDecimal accumulated = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Расчет прогресса (процент)
        BigDecimal targetAmount = financialGoal.getTargetAmount();
        BigDecimal progressPercentage = accumulated.divide(targetAmount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

        model.addAttribute("financialGoal", financialGoal);
        model.addAttribute("transactions", transactions);
        model.addAttribute("accumulated", accumulated);
        model.addAttribute("progressPercentage", progressPercentage);

        return "goal-transactions";
    }
    @GetMapping("/edit/{id}")
    public String editFinancialGoalForm(@PathVariable Long id, Model model) {
        FinancialGoal financialGoal = financialGoalService.getFinancialGoalById(id)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));
        model.addAttribute("financialGoal", financialGoal);
        return "edit-financial-goal";
    }

    @GetMapping("/{goalId}/create-transaction")
    public String createTransactionForGoalForm(@PathVariable Long goalId, Model model) {
        FinancialGoal financialGoal = financialGoalService.getFinancialGoalById(goalId)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));

        model.addAttribute("financialGoal", financialGoal);
        model.addAttribute("transaction", new Transaction());
        return "create-financial-goal-transaction";
    }


    @PostMapping("/{goalId}/transactions")
    public String createTransactionForGoal(
            @PathVariable Long goalId,
            @RequestParam String name,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String transactionDate,
            @RequestParam String type) {

        FinancialGoal financialGoal = financialGoalService.getFinancialGoalById(goalId)
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

        return "redirect:/financial-goals/" + goalId + "/transactions";
    }

}
