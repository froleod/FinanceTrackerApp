package ru.froleod.FinanceTrackerApp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO пользователя
 */
@Data
@Schema(description = "DTO пользователя")
public class UserDTO {

    private static final int STRING_LENGTH = 64;

    /**
     * Банковский аккаунт
     */
    @Schema(description = "Банковский аккаунт", required = true)
    private BankAccountDTO bankAccount;

    /**
     * Имя
     */
    @Schema(description = "Имя", required = true, maxLength = STRING_LENGTH)
    private String firstName;

    /**
     * Фамилия
     */
    @Schema(description = "Фамилия", required = true, maxLength = STRING_LENGTH)
    private String lastName;

    /**
     * Отчество (опционально)
     */
    @Schema(description = "Отчество", maxLength = STRING_LENGTH)
    private String middleName;

    /**
     * Пароль
     */
    @Schema(description = "Пароль")
    private String password;
}
