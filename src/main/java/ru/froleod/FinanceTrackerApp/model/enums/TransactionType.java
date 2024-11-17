package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;

/**
 * Тип транзакции
 */
@Getter
public enum TransactionType {

    INCOME("Входящая"),

    EXPENSE("Исходящая");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}