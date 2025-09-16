package com.jfeat.am.module.task.api.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.task.services.domain.service.TaskUserRelationService;
import com.jfeat.am.module.task.services.gen.persistence.dao.TaskUserRelationMapper;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/task/taskUserRelation")
public class AppTaskUserRelationEndpoint {

    @Resource
    TaskUserRelationService taskUserRelationService;

    @Resource
    TaskUserRelationMapper taskUserRelationMapper;

    @PostMapping("/moveTask")
    public Tip moveTask(@RequestBody TaskUserRelation entity){
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        return SuccessTip.create(taskUserRelationService.addTaskUser(userId,entity));
    }

    @DeleteMapping
    public Tip deleteUserTask(@RequestBody TaskUserRelation entity){
        QueryWrapper<TaskUserRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TaskUserRelation.TASK_ID,entity.getTaskId()).eq(TaskUserRelation.USER_ID,entity.getUserId());
        return SuccessTip.create(taskUserRelationMapper.delete(queryWrapper));
    }


}
