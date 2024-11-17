package ru.froleod.FinanceTrackerApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.froleod.FinanceTrackerApp.model.base.NamedBaseEntity;
import ru.froleod.FinanceTrackerApp.model.enums.BudgetStatus;
import ru.froleod.FinanceTrackerApp.model.enums.Months;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сущность бюджета
 */
@Entity
@Getter
@Setter
@Table(name = "budgets")
@ToString
public class Budget extends NamedBaseEntity {

    /**
     * Банковский аккаунт
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "budget", orphanRemoval = true)
    private List<Transaction> transactions;

    @Column(name = "month", nullable = false)
    @Enumerated(EnumType.STRING)
    private Months month;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BudgetStatus status;
}