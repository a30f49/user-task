package com.jfeat.am.module.task.services.crud.service;

import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.crud.plus.CRUDServiceOnly;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;

import java.util.List;


/**
 * <p>
 * 事件 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */

public interface WorkTaskService extends CRUDServiceOnly<WorkTask> {

    ///
    Long createMasterByStaff(WorkTaskModel entity, UserAccount user, boolean owner);

    /**
     *  更新 事件
     * */

    Integer updateTask(long taskId, WorkTaskModel entity, UserAccount user);


    List<WorkTask> tasksFromCommunicationRecord(long id);


    /**
     *   事件 详情
     * */
    WorkTaskModel taskIncludeUpdate(long id, Long userId);

    Integer deleteMaster(long id,Long userId,Integer version);
    
}
