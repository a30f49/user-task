package com.jfeat.am.module.taskQueue.api.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueUserRelationService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueUserRelationMapper;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/taskQueue/userQueueRelation")
public class AppTaskQueueUserRelationEndpoint {

    @Resource
    TaskQueueUserRelationService taskQueueUserRelationService;

    @Resource
    TaskQueueUserRelationMapper taskQueueUserRelationMapper;

    @PostMapping("/userQueue")
    public Tip addUserQueue(@RequestBody TaskQueueUserRelation entity){
        return SuccessTip.create(taskQueueUserRelationService.addUserQueue(entity));
    }

    @DeleteMapping("/userQueue")
    public Tip deleteUserQueue(@RequestBody TaskQueueUserRelation entity){
        QueryWrapper<TaskQueueUserRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskQueueUserRelation.QUEUE_ID,entity.getQueueId()).eq(TaskQueueUserRelation.USER_ID,entity.getUserId());
        return SuccessTip.create(taskQueueUserRelationMapper.delete(queryWrapper));
    }
}
