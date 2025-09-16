package com.jfeat.am.module.task.services.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryWorkTaskService {

    List<WorkTask> findTaskPage(Page<WorkTask> page,
                                Long ownerByStaffId,
                                Long ownerByEndUserId,
                                String taskName,
                                String status);


    public List<WorkTask> searchTaskOwnerByTeam(Page<WorkTaskModel> page,
                                                Long staffId,
                                                String status,
                                                String orderBy);
    /*List<Task> findNewTask(Long staffId);*/

    List<WorkTask> queryUserWorkTaskList(Page<WorkTask> page, WorkTaskModel record,
                                        String tag,
                                         String search,String orderBy,
                                         Date startTime, Date endTime);
}