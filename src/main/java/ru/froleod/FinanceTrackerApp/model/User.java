package ru.froleod.FinanceTrackerApp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.froleod.FinanceTrackerApp.model.base.BaseEntity;

/**
 * Сущность пользователя
 */
@Entity
@Getter
@Setter
@Table(name = "users")
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

    @Column(name = "username", unique = true)
    private String username;

    private String roles;

    /**
     * Пароль
     */
    @Column(name = "password", nullable = false)
    private String password;

}
