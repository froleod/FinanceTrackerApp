package ru.froleod.FinanceTrackerApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO банковского аккаунта
 */
@Data
@Schema(description = "DTO банковского аккаунта")
public class BankAccountDTO {

    @Schema(description = "Номер аккаунта", required = true)
    private String accountNumber;

    @Schema(description = "Баланс аккаунта", required = true)
    private BigDecimal balance;

    @Schema(description = "Пользователь", required = true)
    private UserDTO user;

    @Schema(description = "Транзакции")
    private List<TransactionDTO> transactions;

    @Schema(description = "Бюджет")
    private Budget budget; //TODO: связь?

    @Schema(description = "Финансовые цели")
    private List<FinancialGoal> financialGoals;
}
