package ru.froleod.FinanceTrackerApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.froleod.FinanceTrackerApp.model.base.NamedBaseEntity;
import ru.froleod.FinanceTrackerApp.model.enums.BudgetStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private BankAccount bankAccounts;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BudgetStatus status;
}