package com.jfeat.am.module.task.services.crud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.task.services.crud.service.TaskUpdateService;
import com.jfeat.am.module.task.services.domain.model.TaskUpdateModel;
import com.jfeat.am.module.task.services.persistence.dao.WorkTaskMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskUpdateMapper;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.BusinessException;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 事件更新表 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Service
public class TaskUpdateServiceImpl extends CRUDServiceOnlyImpl<TaskUpdate> implements TaskUpdateService {


    @Resource
    private TaskUpdateMapper taskUpdateMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    WorkTaskMapper workTaskMapper;


    @Override
    protected BaseMapper<TaskUpdate> getMasterMapper() {
        return taskUpdateMapper;
    }

    //include Staff details
    public TaskUpdateModel showTaskUpdateIncludeStaff(long id) {
        TaskUpdate update = taskUpdateMapper.selectById(id);
        JSONObject updateObj = JSON.parseObject(JSONObject.toJSONString(update));
        Staff staff = staffMapper.selectById(update.getStaffId());
        updateObj.put("staff", staff);
        TaskUpdateModel model = JSON.parseObject(JSONObject.toJSONString(updateObj), TaskUpdateModel.class);
        return model;
    }

    // only show records belongs to login user

    public List<TaskUpdateModel> loginStaffTaskUpdateRecords(Page<TaskUpdateModel> page, long staffId,String status){
        List<TaskUpdateModel> updateRecords = new ArrayList<>();
        List<TaskUpdate> updateRecord = taskUpdateMapper.selectList(new QueryWrapper<TaskUpdate>().eq("StaffId",staffId).like("Status",status));
        for(TaskUpdate update : updateRecord){
            JSONObject updateObj = JSON.parseObject(JSONObject.toJSONString(update));
            WorkTask workTask = workTaskMapper.selectById(update.getTaskId());
            updateObj.put("task", workTask);
            TaskUpdateModel model = JSON.parseObject(JSON.toJSONString(updateObj),TaskUpdateModel.class);
            updateRecords.add(model);
        }
        return updateRecords;
    }

    // only show records belongs to Team
    public List<TaskUpdateModel> teamTaskUpdateRecords(Page<TaskUpdateModel> page, long teamId,String status){
        List<TaskUpdateModel> updateRecords = new ArrayList<>();
        // teamId 查找出属于该team的事件，status过滤事件，通过优先级来进行降序。高在前边
        List<TaskUpdate> updateRecord = taskUpdateMapper.selectList(new QueryWrapper<TaskUpdate>().eq("TeamId",teamId).like("Status",status).orderBy(false,true,"priority"));
        for(TaskUpdate update : updateRecord){
            JSONObject teamUpdateObj = JSON.parseObject(JSONObject.toJSONString(update));
            WorkTask workTask = workTaskMapper.selectById(update.getTaskId());
            teamUpdateObj.put("task", workTask);
            TaskUpdateModel model = JSON.parseObject(JSON.toJSONString(teamUpdateObj),TaskUpdateModel.class);
            updateRecords.add(model);
        }
        return updateRecords;
    }

    // rebuild  update taskUpdate
    public Integer updateTaskUpdate(long taskId,long id,Staff staff,TaskUpdate entity){
        TaskUpdate update = taskUpdateMapper.selectById(id);
        if ((update.getTaskId().compareTo(taskId)!=0)){
            throw new BusinessException(BusinessCode.PowerBusinessConnectDataError);
        }
        if (!(update.getStaffId().equals(staff.getId()))/*||!(ShiroKit.hasPermission(TaskPermission.TASK_EDIT))*/){
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
        entity.setId(id);
        entity.setUpdateTime(new Date());
        return taskUpdateMapper.updateById(entity);
    }
}


