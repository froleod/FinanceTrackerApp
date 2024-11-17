package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;

/**
 * Статус цели
 */
@Getter
public enum GoalStatus {

    ACTIVE("Активный"),

    COMPLETED("Завершен");

    private final String name;

    GoalStatus(String name) {
        this.name = name;
    }
}