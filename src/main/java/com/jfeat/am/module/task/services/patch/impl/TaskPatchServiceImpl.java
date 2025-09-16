package com.jfeat.am.module.task.services.patch.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.message.services.domain.model.MessageType;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.task.api.patch.TaskStatus;
import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.dao.QueryWorkTaskDao;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.model.TeamId;
import com.jfeat.am.module.task.services.patch.TaskPatchService;
import com.jfeat.am.module.task.services.persistence.dao.TaskFollowerMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskUpdateMapper;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.BusinessException;
import com.jfeat.am.module.team.services.crud.service.StaffTeamService;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by J4cob on 2018/1/23.
 */
@Service
public class TaskPatchServiceImpl implements TaskPatchService {
    @Resource
    QueryWorkTaskDao queryWorkTaskDao;
    @Resource
    WorkTaskService workTaskService;
    @Resource
    StaffTeamService staffTeamService;

    @Resource
    TaskFollowerMapper taskFollowerMapper;
    @Resource
    TaskUpdateMapper taskUpdateMapper;
    @Resource
    UserAccountMapper userAccountMapper;
    @Resource
    MessageService messageService;

    /**
     * own  login user - >  Task
     */
    @Override
    public List<WorkTaskModel> allTaskThatOwnByMe(Page<WorkTaskModel> page, long staffId, String status, String orderBy){
        List<WorkTaskModel>  models = new ArrayList<>();
        List<WorkTask> teamWorkTasks = queryWorkTaskDao.allTaskOwnerByMe(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamWorkTasks == null || teamWorkTasks.size() <= 0 ){

        }else{
            for (WorkTask workTask : teamWorkTasks){
                WorkTaskModel model = workTaskService.taskIncludeUpdate(workTask.getId(),staffId);

                /// fix response data format
                models.add(model);
            }

        }
        return models;
    }

    /**
     * follower  login user - >  Task
     */
    @Override
    public List<WorkTaskModel> allTaskFollowerByMe(Page<WorkTaskModel> page, long staffId, String status, String orderBy){
        List<WorkTaskModel>  models = new ArrayList<>();
        List<WorkTask> teamWorkTasks = queryWorkTaskDao.allTaskFollowerByMe(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamWorkTasks == null || teamWorkTasks.size() <= 0 ){

        }else{
            for (WorkTask workTask : teamWorkTasks){
                WorkTaskModel model = workTaskService.taskIncludeUpdate(workTask.getId(),staffId);

                /// fix response data format
                if(!models.contains(model)) {
                    models.add(model);
                }
            }

        }
        return models;
    }

    /**
     * own  By Team - >  Task
     */
    @Override
    public List<WorkTaskModel> allTaskThatOwnByTeam(Page<WorkTaskModel> page, long staffId, String status, String orderBy){
        List<WorkTaskModel>  models = new ArrayList<>();
        List<WorkTask> teamWorkTasks = queryWorkTaskDao.searchTaskOwnerByTeam(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamWorkTasks == null || teamWorkTasks.size() <= 0 ){

        }else{
            for (WorkTask workTask : teamWorkTasks){
                WorkTaskModel model = workTaskService.taskIncludeUpdate(workTask.getId(),staffId);

                /// fix response data format
                    models.add(model);

            }
        }

        return models;
    }


    /**
    *   follower  By Team
    * */

    @Override
    public List<WorkTaskModel> allTaskFollowerByTeam(Page<WorkTaskModel> page, long staffId, String status, String orderBy){
        List<WorkTaskModel>  models = new ArrayList<>();
        List<WorkTask> teamWorkTasks = queryWorkTaskDao.searchTaskFollowerByTeam(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamWorkTasks == null || teamWorkTasks.size() <= 0 ){

        }else{
            for (WorkTask workTask : teamWorkTasks){
                WorkTaskModel model = workTaskService.taskIncludeUpdate(workTask.getId(),staffId);

                /// fix response data format
                models.add(model);
            }
        }
        return models;

    }

    /**
     *  指派 或重新指派 某人 跟进 某事件
     * */
    @Override
    public Integer assignUserOwnerTask(Long taskId, Long staffId) {
        WorkTask workTask = workTaskService.retrieveMaster(taskId);
        if (workTask.getOwnerByStaffId().equals(staffId)) {
            return null;
        }
        // 如果 OwnerByStaffId 与传进来的 StaffId 不相等 且不为空的时候 设置为传入来的StaffId 并且task.status -> WIP
        else {
            if (!(workTask.getOwnerByStaffId().equals(staffId)) || workTask.getOwnerByStaffId().equals(null)) {
                workTask.setStatus(TaskStatus.WIP.toString());
                workTask.setOwnerByStaffId(staffId);
            }
            return workTaskService.updateMaster(workTask);
        }
    }

    /**
     *  指派 Staff Follower 事件
     *
     * */
    @Override
    public Integer assignStaffFollowerTask(Long userId, long taskId){
        WorkTask workTask = workTaskService.retrieveMaster(taskId);
        if(staffTeamService.checkIsTeamLeader(workTask.getOwnerByTeamId(), userId)){
            TaskFollower follower = new TaskFollower();
            follower.setTaskId(taskId);
            follower.setStaffId(userId);
            //  判断是否存在记录，存在 不重复插入
            QueryWrapper<TaskFollower> taskFollowerQueryWrapper = new QueryWrapper<>();
            taskFollowerQueryWrapper.eq("task_id",taskId);
            taskFollowerQueryWrapper.eq("user_id",userId);
            if(taskFollowerMapper.selectOne(taskFollowerQueryWrapper)==null){
                if (workTask.getStatus().compareTo(TaskStatus.WIP.toString())!=0){
                    workTask.setStatus(TaskStatus.WIP.toString());
                    workTaskService.updateMaster(workTask);
                }
                return taskFollowerMapper.insert(follower);
            }
            else{
                return null;
            }

        }else{
            throw new BusinessException(BusinessCode.NotSupport, "非组织负责人");
        }
    }

    @Override
    public Integer transferTask(UserAccount user, long taskId, TeamId teamId) {
        return null;
    }


    /**
     *  转移  事件
     *
     * */
    @Override
    public Integer transferTask(UserAccount user, long taskId, TeamId teamId){
        WorkTask workTask = workTaskService.retrieveMaster(taskId);
        Long ownerByStaffId = workTask.getOwnerByStaffId();
//        UserAccount ownerStaff = userAccountMapper.selectById(ownerByStaffId);

        if(staffTeamService.checkIsTeamLeader(workTask.getOwnerByTeamId(),user.getId())
                /*|| ShiroKit.hasPermission(TaskPermission.TASK_EDIT)*/){
            messageService.deleteMessage(new Message().setMessageType(MessageType.TASK.getType()).setReferenceId(taskId).setNoticeStaffId(ownerByStaffId));
            if(workTask.getNoticeTime()!=null){
                Message message = new Message();
                message.setTitle("代办事项提醒");
                message.setDesc("您有一个代办事项未处理,编号为"+ workTask.getTaskNumber());
                message.setNoticeStaffId(teamId.getStaffId());
                message.setMessageType(MessageType.TASK.getType());
                message.setReferenceId(taskId);
                message.setNoticeDate(workTask.getNoticeTime());
                messageService.createMessage(message);
            }
            workTask.setOwnerByTeamId(teamId.getTeamId());
            workTask.setOwnerByStaffId(teamId.getStaffId());
            return workTaskService.updateMaster(workTask);
        }else{
            throw new BusinessException(BusinessCode.NotSupport, "非组织负责人");
        }
    }


    /**
     *  指派 Staff Owner  事件
     *
     * */
    @Override
    public Integer assignStaffOwnerTask(Long userId, long taskId, long staffId){
        WorkTask workTask = workTaskService.retrieveMaster(taskId);
        if(staffTeamService.checkIsTeamLeader(workTask.getOwnerByTeamId(),userId) ){
            workTask.setOwnerByStaffId(staffId);
            return workTaskService.updateMaster(workTask);
        }else{
            throw new BusinessException(BusinessCode.PowerTeamStaffLeaderOnly);
        }
    }

    /**
    *  添加跟进信息
    * */
    @Override
    @Transactional
    public Integer addTaskUpdate(long taskId, long staffId, TaskUpdate taskUpdate){
        WorkTask workTask = workTaskService.retrieveMaster(taskId);

        //通过taskId,staffId,在task_follower中找 找到就可以跟进 判断是否是跟进人，跟进人才可以添加
        List<TaskFollower> taskFollowers = taskFollowerMapper.selectList(new QueryWrapper<TaskFollower>().eq("taskId", taskId).eq("staffId", staffId));
        if (workTask.getOwnerByStaffId().equals(staffId) || taskFollowers != null && taskFollowers.size()>0
                /*|| ShiroKit.hasPermission(TaskPermission.TASK_EDIT)*/) {
            taskUpdate.setUpdateTime(new Date());
            taskUpdate.setStaffId(staffId);
            taskUpdate.setTaskId(taskId);
            if((workTask.getStatus().toString()).compareTo(TaskStatus.Undefined.toString())==0){
                workTask.setStatus(TaskStatus.WIP.toString());
                workTaskService.updateMaster(workTask);
            }
            return taskUpdateMapper.insert(taskUpdate);
        }else {
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
    }

    /**
     *  更新事件信息
     * */
    @Override
    public Integer updateTaskUpdate(long id, long staffId, TaskUpdate taskUpdate){
        TaskUpdate update = taskUpdateMapper.selectById(id);
        if (update.getStaffId().equals(staffId)
               ) {
            taskUpdate.setUpdateTime(new Date());
            return taskUpdateMapper.updateById(taskUpdate);
        }else {
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
    }

    /**
     *  删除更新事件信息
     * */
    @Override
    public Integer deleteTaskUpdate(long id, long staffId){
        TaskUpdate update = taskUpdateMapper.selectById(id);
        if (update.getStaffId().equals(staffId)
               ) {
            return taskUpdateMapper.deleteById(id);
        }else {
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
    }


}
