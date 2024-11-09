package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Тип транзакции
 */
@Getter
@RequiredArgsConstructor
public enum TransactionType {

    INCOME("Входящая"),

    EXPENSE("Исходящая");

    private final String name;
}