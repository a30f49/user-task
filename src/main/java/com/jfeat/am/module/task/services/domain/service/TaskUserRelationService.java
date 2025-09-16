package com.jfeat.am.module.task.services.domain.service;

import com.jfeat.am.module.task.services.gen.crud.service.CRUDTaskUserRelationService;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;

/**
 * Created by vincent on 2017/10/19.
 */
public interface TaskUserRelationService extends CRUDTaskUserRelationService {

    Integer addTaskUser(TaskUserRelation entity);

    Integer addTaskUser(Long userId,TaskUserRelation entity);
}