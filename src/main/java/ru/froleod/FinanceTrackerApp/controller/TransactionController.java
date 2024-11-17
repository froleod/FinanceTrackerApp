package ru.froleod.FinanceTrackerApp.controller;

//@Controller
//@RequestMapping("/transactions")
//public class TransactionController {
//
//    private final TransactionService transactionService;
//
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    // Страница просмотра транзакций для конкретного бюджета
////    @GetMapping("/{budgetId}")
////    public String showTransactionsPage(@PathVariable Long budgetId, Model model) {
////        model.addAttribute("transactions", transactionService.getTransactionsByBudget(budgetId));
////        model.addAttribute("budgetId", budgetId);
////        return "transactions";  // Указывает на transactions.html
////    }
//
//    // Страница добавления новой транзакции для бюджета
//    @GetMapping("/{budgetId}/new")
//    public String showAddTransactionPage(@PathVariable String budgetId, Model model) {
//        model.addAttribute("budgetId", budgetId);
//        return "add-transaction";  // Указывает на add_transaction.html
//    }
//
//    @PostMapping("/{budgetId}/new")
//    public String addTransaction(@PathVariable Long budgetId,
//                                 @RequestParam BigDecimal amount,
//                                 @RequestParam String description,
//                                 @RequestParam TransactionType type) {
//        transactionService.addTransaction(budgetId, amount, description, type);  // Добавляем транзакцию
//        return "redirect:/transactions/" + budgetId;  // Перенаправление на страницу транзакций для бюджета
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.froleod.FinanceTrackerApp.service.BudgetService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetService budgetService; // Assuming it exists

//    @PostMapping
//    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction, @RequestParam Long budgetId) throws Exception {
//        Budget budget = budgetService.getBudgetById(budgetId)
//                .orElseThrow(() -> new Exception("Budget not found"));
//        transaction.setBudget(budget);
//        return ResponseEntity.ok(transactionService.saveTransaction(transaction));
//    }

    // Other endpoints...
}
