package ru.froleod.FinanceTrackerApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.froleod.FinanceTrackerApp.model.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO финансовой цели
 */
@Data
@Schema(description = "DTO финансовой цели")
public class FinancialGoalDTO {

    @Schema(description = "Идентификатор банковского аккаунта, к которому относится цель", required = true)
    private BankAccountDTO bankAccount;

    @Schema(description = "Список транзакций, относящихся к данной финансовой цели")
    private List<TransactionDTO> transactions;

    @Schema(description = "Целевая сумма, которую необходимо накопить", required = true)
    private BigDecimal targetAmount;

    @Schema(description = "Дата начала накопления", required = true)
    private LocalDate startDate;

    @Schema(description = "Дата окончания накопления", required = true)
    private LocalDate endDate;

    @Schema(description = "Статус финансовой цели", required = true)
    private GoalStatus status;
}

