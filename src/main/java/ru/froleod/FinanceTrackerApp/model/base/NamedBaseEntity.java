package ru.froleod.FinanceTrackerApp.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NamedBaseEntity extends BaseEntity {

    @Column(name="name", nullable = false)
    private String name;
}
