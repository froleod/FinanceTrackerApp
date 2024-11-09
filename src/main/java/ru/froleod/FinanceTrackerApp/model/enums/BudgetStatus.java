package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Статус бюджета
 */
@Getter
@RequiredArgsConstructor
public enum BudgetStatus {

    ACTIVE("Активный"),

    COMPLETED("Завершен");

    private final String name;
}
