package ru.froleod.FinanceTrackerApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.froleod.FinanceTrackerApp.model.base.BaseEntity;

/**
 * Сущность пользователя
 */
@Entity
@Getter
@Setter
@Table(name = "users")
@ToString
public class User extends BaseEntity {

    private static final int STRING_LENGTH = 64;

    /**
     * Банковский аккаунт
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private BankAccount bankAccount;

    /**
     * Имя
     */
    @Column(name = "first_name", length = STRING_LENGTH, nullable = false)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", length = STRING_LENGTH, nullable = false)
    private String lastName;

    /**
     * Отчество (опционально)
     */
    @Column(name = "middle_name", length = STRING_LENGTH)
    private String middleName;

    /**
     * Пароль
     */
    @Column(name = "password", nullable = false)
    private String password;

}
