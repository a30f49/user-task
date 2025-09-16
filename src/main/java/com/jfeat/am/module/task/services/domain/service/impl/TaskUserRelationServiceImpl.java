package com.jfeat.am.module.task.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.service.QueryWorkTaskService;
import com.jfeat.am.module.task.services.domain.service.TaskUserRelationService;
import com.jfeat.am.module.task.services.gen.crud.service.impl.CRUDTaskUserRelationServiceImpl;
import com.jfeat.am.module.task.services.gen.persistence.dao.TaskUserRelationMapper;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("taskUserRelationService")
public class TaskUserRelationServiceImpl extends CRUDTaskUserRelationServiceImpl implements TaskUserRelationService {


    @Resource
    TaskUserRelationMapper taskQueueUserRelation;

    @Resource
    QueryWorkTaskService queryWorkTaskService;

    @Override
    protected String entityName() {
        return "TaskUserRelation";
    }


    @Override
    public Integer addTaskUser(TaskUserRelation entity) {
        if (entity==null || entity.getUserId()==null || entity.getTaskId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"参数不能为空");
        }
        QueryWrapper<TaskUserRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskUserRelation.USER_ID,entity.getUserId()).eq(TaskUserRelation.TASK_ID,entity.getTaskId());
        TaskUserRelation userRelation = taskQueueUserRelation.selectOne(queryWrapper);
        if (userRelation==null){
            return taskQueueUserRelation.insert(entity);
        }else {
            return 0;
        }
    }

    @Override
    public Integer addTaskUser(Long userId, TaskUserRelation entity) {

        if (entity==null || entity.getUserId()==null || entity.getTaskId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"参数不能为空");
        }

        WorkTaskModel record = new WorkTaskModel();

        record.setUserId(userId);
        record.setId(entity.getTaskId());
        List<WorkTask> workTasks = queryWorkTaskService.queryUserWorkTaskList(null, record, null, null, null, null, null);

        if (workTasks==null||workTasks.size()<=0){
            throw new BusinessException(BusinessCode.NoPermission,"没有该task权限");
        }

        return addTaskUser(entity);
    }
}
