package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Статус цели
 */
@Getter
@RequiredArgsConstructor
public enum GoalStatus {

    ACTIVE("Активный"),

    COMPLETED("Завершен");

    private final String name;
}