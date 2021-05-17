package com.neverrar.datacloudplatform.backend.repository;

import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.model.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Integer> {
}
