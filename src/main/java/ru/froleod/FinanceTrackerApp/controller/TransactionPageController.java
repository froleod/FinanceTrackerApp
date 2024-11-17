package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.model.enums.TransactionType;
import ru.froleod.FinanceTrackerApp.repo.BudgetRepository;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionPageController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/create")
    public String createTransactionForm(Model model) {
        model.addAttribute("budgets", budgetRepository.findAll()); // Предоставляем доступные бюджеты
        model.addAttribute("transaction", new Transaction());
        return "create-transaction"; // create-transaction.html
    }


    @PostMapping
    public String createTransaction(@RequestParam Long budgetId,
                                    @RequestParam String name,
                                    @RequestParam BigDecimal amount,
                                    @RequestParam String type,
                                    @RequestParam String transactionDate,
                                    @RequestParam String description) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        BankAccount bankAccount = budget.getBankAccount();

        Transaction transaction = new Transaction();
        transaction.setName(name);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.valueOf(type.toUpperCase()));
        transaction.setBudget(budget);

        // Устанавливаем дату транзакции текущей, если не указана
        LocalDate date = (transactionDate == null || transactionDate.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(transactionDate);
        transaction.setTransactionDate(date);

        transaction.setDescription(description);

        if (transaction.getType() == TransactionType.INCOME) {
            bankAccount.setBalance(bankAccount.getBalance().add(amount));
        } else if (transaction.getType() == TransactionType.EXPENSE) {
            if (amount.compareTo(budget.getAmount()) > 0) {
                throw new IllegalArgumentException("Transaction amount exceeds budget available.");
            }
            budget.setAmount(budget.getAmount().subtract(amount));
            bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
        }

        transactionService.saveTransaction(transaction);
        bankAccountService.updateBankAccount(bankAccount);
        budgetRepository.save(budget);

        return "redirect:/transactions";
    }

    @GetMapping
    public String viewTransactions(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

}