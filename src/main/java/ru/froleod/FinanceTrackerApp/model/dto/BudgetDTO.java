package ru.froleod.FinanceTrackerApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.froleod.FinanceTrackerApp.model.enums.BudgetStatus;
import ru.froleod.FinanceTrackerApp.model.enums.Months;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO бюджета
 */
@Data
@Schema(description = "DTO бюджета")
public class BudgetDTO {

    @Schema(description = "Идентификатор банковского аккаунта, к которому относится бюджет", required = true)
    private BankAccountDTO bankAccount;

    @Schema(description = "Список транзакций, относящихся к данному бюджету")
    private List<TransactionDTO> transactions;

    @Schema(description = "Месяц, на который устанавливается бюджет", required = true)
    private Months month;

    @Schema(description = "Сумма бюджета", required = true)
    private BigDecimal amount;

    @Schema(description = "Дата начала действия бюджета", required = true)
    private LocalDate startDate;

    @Schema(description = "Дата окончания действия бюджета")
    private LocalDate endDate;

    @Schema(description = "Статус бюджета", required = true)
    private BudgetStatus status;
}

