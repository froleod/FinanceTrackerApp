package ru.froleod.FinanceTrackerApp.model.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@MappedSuperclass
public class NamedBaseEntity extends BaseEntity {

    @Column(name="name", nullable = false)
    private String name;
}
