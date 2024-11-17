package ru.froleod.FinanceTrackerApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.froleod.FinanceTrackerApp.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO транзакции
 */
@Data
@Schema(description = "DTO транзакции")
public class TransactionDTO {

    @Schema(description = "Идентификатор транзакции", required = true)
    private Long id;

    @Schema(description = "Идентификатор бюджета, к которому относится транзакция")
    private Long budgetId;

    @Schema(description = "Идентификатор финансовой цели, к которой относится транзакция")
    private Long financialGoalId;

    @Schema(description = "Сумма транзакции", required = true)
    private BigDecimal amount;

    @Schema(description = "Дата и время транзакции", required = true)
    private LocalDateTime transactionDate;

    @Schema(description = "Тип транзакции (например, доход или расход)", required = true)
    private TransactionType type;

    @Schema(description = "Описание транзакции")
    private String description;
}
