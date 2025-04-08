package ru.froleod.FinanceTrackerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.repo.BudgetRepository;
import ru.froleod.FinanceTrackerApp.service.BankAccountService;
import ru.froleod.FinanceTrackerApp.service.BudgetService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

import static ru.froleod.FinanceTrackerApp.controller.BudgetPageController.getCurrentUsername;

@Controller
@RequestMapping("/transactions")
@Tag(name = "Контроллер транзакций", description = "Операции, связанные с транзакциями")
public class TransactionPageController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BudgetService budgetService;

    @Operation(summary = "Форма создания транзакции", description = "Отображает форму для создания новой транзакции.")
    @GetMapping("/create")
    public String createTransactionForm(Model model) {
        String username = getCurrentUsername();
        model.addAttribute("budgets", budgetService.findByBankAccountUserUsername(username));
        model.addAttribute("transaction", new Transaction());
        return "transactions/create-transaction";
    }

    @Operation(summary = "Создание транзакции", description = "Создает новую транзакцию и сохраняет её в базе данных.")
    @PostMapping
    public String createTransaction(@RequestParam Long budgetId,
                                    @RequestParam String name,
                                    @RequestParam BigDecimal amount,
                                    @RequestParam String type,
                                    @RequestParam String transactionDate,
                                    @RequestParam String description) {

        transactionService.createTransaction(budgetId, name, amount, type, transactionDate, description);
        return "redirect:/transactions";
    }

    @Operation(summary = "Просмотр транзакций", description = "Отображает список транзакций пользователя.")
    @GetMapping
    public String viewTransactions(Model model) {
        String username = getCurrentUsername();
        List<Transaction> transactions = transactionService.getTransactionsByUserName(username);
        model.addAttribute("transactions", transactions);
        return "transactions/transactions";
    }

}