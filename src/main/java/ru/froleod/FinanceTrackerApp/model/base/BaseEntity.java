package ru.froleod.FinanceTrackerApp.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "FINANCE_UUID")
    @GenericGenerator(name = "FINANCE_UUID", strategy = "ru.froleod.model.util.UUIDGenerator")
    @Column(name = "ID", nullable = false)
    private String id;
}