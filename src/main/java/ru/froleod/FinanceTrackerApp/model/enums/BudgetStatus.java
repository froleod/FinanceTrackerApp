package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;

/**
 * Статус бюджета
 */
@Getter
public enum BudgetStatus {

    ACTIVE("Активный"),

    COMPLETED("Завершен");

    private final String name;

    BudgetStatus(String name) {
        this.name = name;
    }
}
