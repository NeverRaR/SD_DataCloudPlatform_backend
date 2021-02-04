package com.neverrar.datacloudplatform.backend.repository;

import com.neverrar.datacloudplatform.backend.model.LogEventData;
import com.neverrar.datacloudplatform.backend.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface LogEventDataRepository extends CrudRepository<LogEventData, Integer> {
}
