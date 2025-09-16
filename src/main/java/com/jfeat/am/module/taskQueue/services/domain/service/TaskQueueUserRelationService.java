package com.jfeat.am.module.taskQueue.services.domain.service;

import com.jfeat.am.module.taskQueue.services.gen.crud.service.CRUDTaskQueueUserRelationService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;

/**
 * Created by vincent on 2017/10/19.
 */
public interface TaskQueueUserRelationService extends CRUDTaskQueueUserRelationService {

    Integer addUserQueue(TaskQueueUserRelation taskQueueUserRelation);
}