package ru.froleod.FinanceTrackerApp.model.util;

import org.apache.commons.lang3.StringUtils;
import ru.froleod.FinanceTrackerApp.model.base.BaseEntity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Генератор id
 */
public class UUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(final SharedSessionContractImplementor sharedSessionContractImplementor,
                                 final Object o) throws HibernateException {
        BaseEntity baseEntity = o instanceof BaseEntity ? ((BaseEntity) o) : null;
        if (baseEntity != null && !StringUtils.isEmpty(baseEntity.getId())) {
            return baseEntity.getId();
        }

        return UUID.randomUUID().toString();
    }
}
