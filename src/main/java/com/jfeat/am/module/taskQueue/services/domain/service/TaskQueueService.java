package com.jfeat.am.module.taskQueue.services.domain.service;

import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.taskQueue.services.gen.crud.service.CRUDTaskQueueService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface TaskQueueService extends CRUDTaskQueueService {

    /**
     * 通过获取队列 的代办事项
     * @param id
     * @return
     */
    List<WorkTask> getWorkTaskByQueueId(Long id);


    /**
     * 删除队列的 代办事项
     * @param id
     * @return
     */
    int deleteWorkTaskByQueueId(Long id);


    TaskQueue getTaskQueueByName(String name);

}