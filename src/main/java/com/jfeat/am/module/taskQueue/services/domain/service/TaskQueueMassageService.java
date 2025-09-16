package com.jfeat.am.module.taskQueue.services.domain.service;

import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;

public interface TaskQueueMassageService {

    Integer taskQueueMessageControl(WorkTaskModel task,boolean isKafka);

}