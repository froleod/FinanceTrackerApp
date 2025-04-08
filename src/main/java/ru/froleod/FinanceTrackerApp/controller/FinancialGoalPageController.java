package ru.froleod.FinanceTrackerApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.model.Transaction;
import ru.froleod.FinanceTrackerApp.model.enums.TransactionType;
import ru.froleod.FinanceTrackerApp.service.FinancialGoalService;
import ru.froleod.FinanceTrackerApp.service.TransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequestMapping("/financial-goals")
@Tag(name = "Контроллер финансовых целей", description = "Операции, связанные с управлением финансовыми целями")
public class FinancialGoalPageController {

    @Autowired
    private FinancialGoalService financialGoalService;

    @Autowired
    private TransactionService transactionService;


    @Operation(summary = "Просмотр всех финансовых целей", description = "Возвращает страницу со списком всех финансовых целей.")
    @GetMapping
    public String viewFinancialGoals(Model model) {
        List<FinancialGoal> financialGoals = financialGoalService.getAllFinancialGoals();
        model.addAttribute("financialGoals", financialGoals);
        return "financialGoals/financial-goals";
    }

    @Operation(summary = "Открытие формы для создания финансовой цели", description = "Возвращает страницу для создания новой финансовой цели.")
    @GetMapping("/create")
    public String createFinancialGoalForm(Model model) {
        model.addAttribute("financialGoal", new FinancialGoal());
        return "financialGoals/create-financial-goal";
    }

    @Operation(summary = "Создание новой финансовой цели", description = "Создает новую финансовую цель с указанными параметрами.")
    @PostMapping
    public String createFinancialGoal(
            @RequestParam Long bankAccountId,
            @RequestParam String name,
            @RequestParam BigDecimal targetAmount,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String status) {
        financialGoalService.createFinancialGoal(bankAccountId, name, targetAmount, startDate, endDate, status);
        return "redirect:/financial-goals";
    }


    @Operation(summary = "Просмотр транзакций по финансовой цели", description = "Возвращает страницу с транзакциями для указанной финансовой цели.")
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

        return "transactions/goal-transactions";
    }

    @Operation(summary = "Открытие формы для редактирования финансовой цели", description = "Возвращает страницу для редактирования финансовой цели по ID.")
    @GetMapping("/edit/{id}")
    public String editFinancialGoalForm(@PathVariable Long id, Model model) {
        FinancialGoal financialGoal = financialGoalService.getFinancialGoalById(id)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));
        model.addAttribute("financialGoal", financialGoal);
        return "financialGoals/edit-financial-goal";
    }

    @Operation(summary = "Обновление финансовой цели", description = "Обновляет финансовую цель с указанными параметрами.")
    @PostMapping("/edit/{id}")
    public String updateFinancialGoalForm(@PathVariable Long id,
                                          @RequestParam String name,
                                          @RequestParam BigDecimal targetAmount,
                                          @RequestParam String startDate,
                                          @RequestParam String endDate,
                                          @RequestParam String status) {

        financialGoalService.updateFinancialGoalForm(id, name, targetAmount, startDate, endDate, status);
        return "redirect:/financial-goals";
    }

    @Operation(summary = "Удаление финансовой цели", description = "Удаляет финансовую цель по ID.")
    @DeleteMapping("/delete/{id}")
    public String deleteFinancialGoal(@PathVariable Long id) {
        financialGoalService.deleteFinancialGoal(id);
        return "redirect:/financial-goals";
    }

    @Operation(summary = "Открытие формы для создания транзакции по финансовой цели", description = "Возвращает страницу для добавления транзакции к финансовой цели.")
    @GetMapping("/{goalId}/create-transaction")
    public String createTransactionForGoalForm(@PathVariable Long goalId, Model model) {
        FinancialGoal financialGoal = financialGoalService.getFinancialGoalById(goalId)
                .orElseThrow(() -> new RuntimeException("Financial Goal not found"));

        model.addAttribute("financialGoal", financialGoal);
        model.addAttribute("transaction", new Transaction());
        return "transactions/create-financial-goal-transaction";
    }


    @Operation(summary = "Создание транзакции по финансовой цели", description = "Добавляет новую транзакцию для указанной финансовой цели.")
    @PostMapping("/{goalId}/transactions")
    public String createTransactionForGoal(
            @PathVariable Long goalId,
            @RequestParam String name,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String transactionDate,
            @RequestParam String type) {

        financialGoalService.createTransactionForGoal(goalId, name, amount, transactionDate, type);
        return "redirect:/financial-goals/" + goalId + "/transactions";
    }

}
