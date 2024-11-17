package ru.froleod.FinanceTrackerApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

    @Schema(description = "Пользователь, которому принадлежит аккаунт", required = true)
    private UserDTO user;

    @Schema(description = "Список бюджетов, связанных с аккаунтом")
    private List<BudgetDTO> budgets;

    @Schema(description = "Список финансовых целей, связанных с аккаунтом")
    private List<FinancialGoalDTO> financialGoals;
}
