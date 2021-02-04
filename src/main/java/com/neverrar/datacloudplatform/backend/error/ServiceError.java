package com.neverrar.datacloudplatform.backend.error;

import javax.persistence.criteria.CriteriaBuilder;

public interface ServiceError {
    String getMessage();

    Integer getCode();
}