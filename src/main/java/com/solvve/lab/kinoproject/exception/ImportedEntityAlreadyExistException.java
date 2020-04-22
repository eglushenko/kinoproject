package com.solvve.lab.kinoproject.exception;


import com.solvve.lab.kinoproject.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ImportedEntityAlreadyExistException extends Exception {

    private Class<? extends AbstractEntity> entityClass;
    private UUID entityId;

    public ImportedEntityAlreadyExistException(Class<? extends AbstractEntity> entityClass,
                                               UUID entityId, String message) {
        super(message);
        this.entityClass = entityClass;
        this.entityId = entityId;

    }

}
