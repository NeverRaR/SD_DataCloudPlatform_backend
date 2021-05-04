package com.neverrar.datacloudplatform.backend.repository;

import com.neverrar.datacloudplatform.backend.model.MainData;
import com.neverrar.datacloudplatform.backend.model.Test;
import org.springframework.data.repository.CrudRepository;

public interface MainDataRepository extends CrudRepository<MainData, Integer> {
    MainData findByTest(Test test);
}
