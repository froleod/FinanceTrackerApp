package ru.froleod.FinanceTrackerApp.model.enums;

import lombok.Getter;

/**
 * Месяц
 */
@Getter
public enum Months {

    JANUARY("Январь"),
    FEBRUARY("Февраль"),
    MARCH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNE("Июнь"),
    JULY("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь");

    private final String displayName;

    Months(String displayName) {
        this.displayName = displayName;
    }

}

