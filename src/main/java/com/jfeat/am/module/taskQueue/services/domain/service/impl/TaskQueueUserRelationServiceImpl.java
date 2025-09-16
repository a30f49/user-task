package com.jfeat.am.module.taskQueue.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueUserRelationService;
import com.jfeat.am.module.taskQueue.services.gen.crud.service.impl.CRUDTaskQueueUserRelationServiceImpl;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueUserRelationMapper;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("taskQueueUserRelationService")
public class TaskQueueUserRelationServiceImpl extends CRUDTaskQueueUserRelationServiceImpl implements TaskQueueUserRelationService {

    @Resource
    TaskQueueUserRelationMapper queueUserRelationMapper;

    @Override
    protected String entityName() {
        return "TaskQueueUserRelation";
    }


    @Override
    public Integer addUserQueue(TaskQueueUserRelation taskQueueUserRelation) {
        if (taskQueueUserRelation==null || taskQueueUserRelation.getUserId()==null || taskQueueUserRelation.getQueueId()==null){
            return 0;
        }
        QueryWrapper<TaskQueueUserRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskQueueUserRelation.USER_ID,taskQueueUserRelation.getUserId()).eq(TaskQueueUserRelation.QUEUE_ID,taskQueueUserRelation.getQueueId());
        TaskQueueUserRelation userRelation = queueUserRelationMapper.selectOne(queryWrapper);
        if (userRelation==null){
            return queueUserRelationMapper.insert(taskQueueUserRelation);
        }else {
            return 0;
        }
    }
}
