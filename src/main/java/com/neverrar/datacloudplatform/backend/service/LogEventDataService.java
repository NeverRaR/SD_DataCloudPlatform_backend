package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.repository.InteractionBehaviourDataRepository;
import com.neverrar.datacloudplatform.backend.repository.LogEventDataRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEventDataService {

    @Autowired
    private LogEventDataRepository logEventDataRepository;

    @Autowired
    private TestRepository testRepository;
}
