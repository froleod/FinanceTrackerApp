package ru.froleod.FinanceTrackerApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.froleod.FinanceTrackerApp.model.base.NamedBaseEntity;
import ru.froleod.FinanceTrackerApp.model.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Сущность финансовой цели
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "financial_goals")
public class FinancialGoal extends NamedBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
