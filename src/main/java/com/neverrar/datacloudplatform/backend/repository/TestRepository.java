package com.neverrar.datacloudplatform.backend.repository;

import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.Tester;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TestRepository extends CrudRepository<Test, Integer> {

      @Modifying
      @Query(value = "delete from test where task_id=?1", nativeQuery = true)
      void deleteByTaskId(Integer taskId);

      @Modifying
      @Query(value = "delete from test where tester_id=?1", nativeQuery = true)
      void deleteByTesterId(Integer testerId);

      Set<Test> findAllByTesterAndTask(Tester tester, Task task);
}