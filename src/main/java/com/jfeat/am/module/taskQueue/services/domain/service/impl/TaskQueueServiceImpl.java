package com.jfeat.am.module.taskQueue.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.task.services.persistence.dao.WorkTaskMapper;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueService;
import com.jfeat.am.module.taskQueue.services.gen.crud.service.impl.CRUDTaskQueueServiceImpl;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueMapper;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("taskQueueService")
public class TaskQueueServiceImpl extends CRUDTaskQueueServiceImpl implements TaskQueueService {

    @Resource
    TaskQueueMapper taskQueueMapper;

    @Resource
    WorkTaskMapper workTaskMapper;

    @Resource
    TaskQueueService taskQueueService;

    @Override
    protected String entityName() {
        return "TaskQueue";
    }


    @Override
    public List<WorkTask> getWorkTaskByQueueId(Long id) {
        QueryWrapper<WorkTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WorkTask.QUEUE_ID,id);
        return workTaskMapper.selectList(queryWrapper);
    }


    @Override
    @Transactional
    public int deleteWorkTaskByQueueId(Long id) {
        QueryWrapper<WorkTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WorkTask.QUEUE_ID,id);
        return workTaskMapper.delete(queryWrapper);
    }



    @Override
    public TaskQueue getTaskQueueByName(String name) {
        QueryWrapper<TaskQueue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskQueue.NAME,name);
        TaskQueue taskQueue = taskQueueMapper.selectOne(queryWrapper);
        if (taskQueue==null){
            taskQueue = new TaskQueue();
            taskQueue.setName(name);
            taskQueueService.createMaster(taskQueue);
        }
        return taskQueue;
    }
}
