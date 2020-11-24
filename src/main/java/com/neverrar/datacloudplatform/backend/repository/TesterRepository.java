package com.neverrar.datacloudplatform.backend.repository;

import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Tester;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TesterRepository extends CrudRepository<Tester, Integer> {

    @Modifying
    void deleteByProject(Project project);

}