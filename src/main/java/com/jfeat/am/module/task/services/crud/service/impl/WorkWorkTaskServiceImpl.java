package com.jfeat.am.module.task.services.crud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.jfeat.am.module.attachments.services.domain.service.AttachmentsService;
import com.jfeat.am.module.message.services.domain.model.MessageType;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.task.api.patch.TaskStatus;
import com.jfeat.am.module.task.services.crud.service.TaskReferenceService;
import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.dao.QueryWorkTaskDao;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.model.TaskReferenceModel;
import com.jfeat.am.module.task.services.domain.model.TaskUpdateModel;
import com.jfeat.am.module.task.services.persistence.dao.TaskFollowerMapper;
import com.jfeat.am.module.task.services.persistence.dao.WorkTaskMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskReferenceMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskUpdateMapper;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.BusinessException;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.persistence.dao.TeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.core.util.DateTimeKit;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import com.jfeat.crud.plus.model.IdVersion;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 事件 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Service
public class WorkWorkTaskServiceImpl extends CRUDServiceOnlyImpl<WorkTask> implements WorkTaskService {

    @Resource
    private WorkTaskMapper workTaskMapper;
    @Resource
    private MessageService messageService;
    @Resource
    QueryWorkTaskDao queryWorkTaskDao;
    @Resource
    TaskUpdateMapper taskUpdateMapper;
    @Resource
    TaskFollowerMapper taskFollowerMapper;
    @Resource
    TaskReferenceMapper taskReferenceMapper;
    @Resource
    TeamMapper teamMapper;
    @Resource
    TaskReferenceService taskReferenceService;
    @Resource
    private AttachmentsService attachmentsService;

    @Resource
    UserAccountMapper userAccountMapper;

    @Override
    protected BaseMapper<WorkTask> getMasterMapper() {
        return workTaskMapper;
    }

    protected boolean checkDateValidation(WorkTask entity) {

        int ok = 0;
        if (entity.getNoticeTime() != null) {
            if (DateTimeKit.diff(entity.getStartTime(), entity.getNoticeTime(), DateTimeKit.SECOND_MS) > 0) {
                ok++;
            }

            if (DateTimeKit.diff(entity.getNoticeTime(), entity.getDeadline(), DateTimeKit.SECOND_MS) >= 0) {
                ok++;
            }
            return ok == 2;
        } else {
            if (DateTimeKit.diff(entity.getStartTime(), entity.getDeadline(), DateTimeKit.SECOND_MS) > 0) {
                ok++;
            }
            return ok == 1;
        }
    }

    @Override
    @Transactional
    public Long createMasterByStaff(WorkTaskModel entity, UserAccount user, boolean owner) {
        //int affect = 1;
        if (!checkDateValidation(entity)) {
            throw new BusinessException(BusinessCode.PowerBusinessBadRequest, "开始时间、提醒时间或结束时间不符合");
        }
        entity.setCreateByEndUserId(user.getId());
       // entity.setCreateByStaffId(staff.getId());
        entity.setCreateTime(new Date());
        //
        if (owner) {
            entity.setOwnerByEndUserId(user.getId());
            //entity.setOwnerByStaffId(staff.getId());
        }
        entity.setStatus(TaskStatus.Undefined.toString());
        entity.setCreateByEndUserId(user.getId());
        //entity.setCreateByStaffId(staff.getId());

        workTaskMapper.insert(entity);
        //设置附件
        if(!CollectionUtils.isEmpty(entity.getAttachments())){
            attachmentsService.saveAttachments(entity.getAttachments(), WorkTask.tableName,entity.getId());
        }

        if(entity.getNoticeTime()!=null){
            Message message = new Message();
            message.setTitle("代办事项提醒");
            message.setDesc("您有一个代办事项未处理,编号为"+entity.getTaskNumber());
            message.setMessageType(MessageType.TASK.getType());
            message.setReferenceId(entity.getId());
            message.setNoticeDate(entity.getNoticeTime());
            message.setNoticeStaffId(entity.getOwnerByStaffId());
            message.setNoticeEndUserId(entity.getOwnerByEndUserId());
            messageService.createMessage(message);
        }


        if (entity.getReferences() != null) {
            taskReferenceMapper.delete(new QueryWrapper<TaskReference>().eq("taskId", entity.getId()));
            for (TaskReference reference : entity.getReferences()) {
                reference.setTaskId(entity.getId());
                taskReferenceService.addReference(entity.getId(), reference);
                //affect++;
            }
        }

        if (entity.getFollowers() != null && entity.getFollowers().size() > 0) {
            taskFollowerMapper.delete(new QueryWrapper<TaskFollower>().eq("task_id", entity.getId()));
            for (TaskFollower follower : entity.getFollowers()) {
                if (follower.getStaffId() != null) {
                    follower.setStaffId(follower.getStaffId());
                    if(entity.getNoticeTime()!=null){
                        Message message = new Message();
                        message.setTitle("代办事项提醒");
                        message.setDesc("您有一个代办事项未处理,编号为"+entity.getTaskNumber());
                        message.setMessageType(MessageType.TASKFOLLOWER.getType());
                        message.setReferenceId(entity.getId());
                        message.setNoticeDate(entity.getNoticeTime());
                        message.setNoticeStaffId(follower.getStaffId());
                        message.setNoticeEndUserId(entity.getOwnerByEndUserId());
                        messageService.createMessage(message);
                    }
                }
                follower.setTaskId(entity.getId());
                taskFollowerMapper.insert(follower);
                //affect++;
            }
        }

        return entity.getId();
    }


    /**
     * 更新 事件
     */
    @Override
    public Integer updateTask(long taskId, WorkTaskModel entity, UserAccount user) {
        int affect = 1;
//        new Task().setId(taskId).setVersion(entity.getVersion());
        QueryWrapper<WorkTask> taskQueryWrapper = new QueryWrapper<>();
        taskQueryWrapper.eq("id",taskId);
        taskQueryWrapper.eq("version",entity.getVersion());
        WorkTask originWorkTask = workTaskMapper.selectOne(taskQueryWrapper);
        if(null == originWorkTask){
            throw new BusinessException(BusinessCode.PowerDateVersionError);
        }
        if (/*!(ShiroKit.hasPermission(TaskPermission.TASK_EDIT)) ||*/ !(originWorkTask.getOwnerByEndUserId().equals(user.getId())) && !(originWorkTask.getCreateByEndUserId().equals(user.getId()))) {
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
        if (!checkDateValidation(entity)) {
            throw new BusinessException(BusinessCode.PowerBusinessBadRequest, "开始时间、提醒时间或结束时间不符合");
        }
        entity.setId(taskId);
        workTaskMapper.updateById(CRUD.castObject(entity, WorkTask.class));
        //更新消息
        Message message = new Message();
        message.setTitle("代办事项提醒");
        message.setDesc("您有一个代办事项未处理,编号为"+ originWorkTask.getTaskNumber());
        message.setNoticeStaffId(originWorkTask.getOwnerByStaffId());
        message.setMessageType(MessageType.TASK.getType());
        message.setReferenceId(taskId);
        if(null != entity.getNoticeTime()){
            message.setNoticeDate(entity.getNoticeTime());
            message.setNoticeEndUserId(originWorkTask.getOwnerByEndUserId());
            messageService.updateMessage(message);
        }else if(originWorkTask.getOwnerByEndUserId()!=null){
            message.setNoticeEndUserId(originWorkTask.getOwnerByEndUserId());
            messageService.deleteMessage(message);
        }

        if(null != entity.getDeletion()){
            List<IdVersion> references = entity.getDeletion().getReferences();
            List<IdVersion> updateRecords = entity.getDeletion().getUpdateRecords();
            List<IdVersion> followers = entity.getDeletion().getFollowers();
            if(!CollectionUtils.isEmpty(references)){
                for (IdVersion idVersion : references){
                    taskReferenceMapper.delete(new QueryWrapper<TaskReference>().eq("id",idVersion.getId()).eq("version",idVersion.getVersion()));
                }
            }
            if(!CollectionUtils.isEmpty(updateRecords)){
                for (IdVersion idVersion : updateRecords){
                    taskUpdateMapper.delete(new QueryWrapper<TaskUpdate>().eq("id",idVersion.getId()).eq("version",idVersion.getVersion()));
                }
            }
            if(!CollectionUtils.isEmpty(followers)){
                for (IdVersion idVersion : followers){
                    TaskFollower taskFollower = taskFollowerMapper.selectById(idVersion.getId());
                    //删除消息
                    Message message2 = new Message();
                    message2.setNoticeStaffId(taskFollower.getStaffId());
                    message2.setMessageType(MessageType.TASKFOLLOWER.getType());
                    message2.setReferenceId(taskId);
                    messageService.deleteMessage(message2);

                    taskFollowerMapper.delete(new QueryWrapper<TaskFollower>().eq("id",idVersion.getId()).eq("version",idVersion.getVersion()));
                }
            }
        }

        if (entity.getReferences() != null) {
            for (TaskReference reference : entity.getReferences()) {
                if(reference.getId() !=null && reference.getId()>0){
                    taskReferenceService.updateMaster(reference);
                }else {
                    reference.setTaskId(taskId);
                    taskReferenceMapper.insert(reference);
                }
                affect++;
            }
        }

        if (entity.getFollowers() != null) {

            for (TaskFollower follower : entity.getFollowers()) {
                follower.setTaskId(taskId);
                follower.setTeamId(follower.getTeamId());

                if(follower.getId()!=null && follower.getId()>0){
                    taskFollowerMapper.updateById(follower);
                }else{
                    taskFollowerMapper.insert(follower);

                    if (follower.getStaffId() != null) {
                        follower.setStaffId(follower.getStaffId());
                        //新增消息
                        if(null != entity.getNoticeTime()){
                            Message message2 = new Message();
                            message2.setTitle("代办事项提醒");
                            message2.setDesc("您有一个代办事项未处理,编号为"+ originWorkTask.getTaskNumber());
                            message2.setNoticeStaffId(follower.getStaffId());
                            message2.setMessageType(MessageType.TASKFOLLOWER.getType());
                            message2.setReferenceId(taskId);
                            message2.setNoticeDate(entity.getNoticeTime());
                            messageService.createMessage(message2);
                        }
                    }
                }
                affect++;
            }
        }

        return affect;
    }


    @Override
    public List<WorkTask> tasksFromCommunicationRecord(long id) {
        return workTaskMapper.selectList(new QueryWrapper<WorkTask>().eq(
                "communicationRecordId", id)
        );
    }

    /**
     * include TaskUpdate
     */
    @Override
    @Transactional
    public WorkTaskModel taskIncludeUpdate(long id, Long userId) {
        // 找出当前的 Task 事件
        WorkTask workTask = workTaskMapper.selectById(id);
        if (workTask == null) {
            throw new BusinessException(BusinessCode.PowerBusinessFileNotFound);
        }

        // if(task.getNoticeTime()!=null && task.getNoticeTime().getTime()<=System.currentTimeMillis() && task.getOwnerByStaffId().equals(staffId)){
         if(workTask.getNoticeTime()!=null && workTask.getNoticeTime().getTime()<=System.currentTimeMillis() && workTask.getOwnerByEndUserId().equals(userId)){
            messageService.deleteMessage(new Message().setMessageType(MessageType.TASK.getType()).setReferenceId(id).setNoticeStaffId(workTask.getOwnerByStaffId()));
        }
        JSONObject taskObj = JSON.parseObject(JSONObject.toJSONString(workTask));
       // Staff ownerStaff = staffMapper.selectById(task.getOwnerByStaffId());
        UserAccount oriUser = userAccountMapper.selectById(workTask.getOwnerByEndUserId());
        taskObj.put("ownerStaffName", (oriUser == null) ? "" :oriUser.getName());
        List<TaskUpdateModel> updateRecords = new ArrayList<>();
        List<TaskUpdate> updateRecord = taskUpdateMapper.selectList
                (new QueryWrapper<TaskUpdate>().eq("task_id", id));
        for (TaskUpdate update : updateRecord) {
            // 遍历更新信息表 找到该更新对应的 Staff  的信息
            JSONObject updateObj = JSON.parseObject(JSONObject.toJSONString(update));
            UserAccount user = userAccountMapper.selectById(update.getEndUserId());
            updateObj.put("userId", user.getId());
            updateObj.put("loginName", user.getAccount());
            updateObj.put("staffName", user.getName());
            TaskUpdateModel model = JSON.parseObject(JSON.toJSONString(updateObj), TaskUpdateModel.class);
            updateRecords.add(model);
        }

        List<NameModel> followers = new ArrayList<>();
        //  找到跟进人
        List<TaskFollower> taskFollower = taskFollowerMapper.selectList
                (new QueryWrapper<TaskFollower>().eq("task_id", id));

        if (taskFollower != null && taskFollower.size() > 0) {
            List<String> containStaffName = new ArrayList<>();
            for (TaskFollower follower : taskFollower) {
                if(workTask.getNoticeTime()!=null && workTask.getNoticeTime().getTime()<=System.currentTimeMillis() && follower.getEndUserId().equals(userId)){
                    messageService.deleteMessage(new Message().setMessageType(MessageType.TASKFOLLOWER.getType()).setReferenceId(id).setNoticeStaffId(follower.getStaffId()));
                }
                NameModel record = new NameModel();
                Team team = (follower.getTeamId() == null) ? null : (teamMapper.selectById(follower.getTeamId()));
                //Staff staff = (follower.getStaffId() == null) ? null : (staffMapper.selectById(follower.getStaffId()));
                UserAccount userAccount = (follower.getEndUserId() == null) ? null : userAccountMapper.selectById(follower.getEndUserId());
//                record.setStaffId(staff == null ? null : staff.getId());
//                record.setTeamId(follower.getTeamId());
//                record.setStaffName(staff == null ? null : new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
//                record.setTeamName((team == null) ? null : team.getTeamName());
//                containStaffName.add(staff == null ? null : new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
                  record.setEndUserId(userAccount == null ? null : userAccount.getId());
                 // record.setTeamId(follower.getTeamId());
                  record.setUserName(userAccount == null ? null : userAccount.getName());
                //  record.setTeamName((team == null) ? null : team.getTeamName());
                  containStaffName.add(userAccount == null ? null : userAccount.getName());
                followers.add(record);
            }
            taskObj.put("containStaffName", containStaffName);  // 关联人员
        } else {

        }
        // 参考事件，可以存在，可以不存在， 存在的时候显示记录
        List<TaskReference> references = taskReferenceMapper.selectList
                (new QueryWrapper<TaskReference>().eq("task_id", id));
        if (references != null && references.size() > 0) {
            List<TaskReferenceModel> models = new ArrayList<>();
            for (TaskReference reference : references) {
                JSONObject referenceObj = JSON.parseObject(JSONObject.toJSONString(reference));
                TaskReference taskReference = taskReferenceMapper.selectById(reference.getReferenceId());
                referenceObj.put("taskReference", taskReference);
                TaskReferenceModel model = JSON.parseObject(JSON.toJSONString(referenceObj), TaskReferenceModel.class);
                models.add(model);
            }
        } else {

        }
        // 查找出创建人的信息   通过  ID  去查找出创建者   getCreateByStaffId
        //Staff staff = staffMapper.selectById(task.getCreateByStaffId());
        UserAccount userAccount = userAccountMapper.selectById(workTask.getCreateByEndUserId());
        taskObj.put("createByStaffName",userAccount == null ? null : userAccount.getName());
        taskObj.put("updateRecords", updateRecords);  //跟进记录
        taskObj.put("references", references);      //事件参考
        taskObj.put("followerRecords", followers);  // 跟进人员
        taskObj.put("followers", taskFollower);
        WorkTaskModel model = JSON.parseObject(JSON.toJSONString(taskObj), WorkTaskModel.class);
        // 跟进人员格式转换
        model.fixCopyFollowerRecordsToFollowerList();
        //设置附件
        model.setAttachments(attachmentsService.getAttachments(WorkTask.tableName,id));
        return model;
    }
    //
//    public ContainStaff showContain(long teamId, List<Long> ids) {
//        Team team = teamMapper.selectById(teamId);
//        JSONObject teamObj = JSON.parseObject(JSON.toJSONString(team));
//        List<NameModel> staffs = new ArrayList<>();
//        for (Long id : ids) {
//            NameModel model = new NameModel();
//            //Staff staff = staffMapper.selectById(id);
//            UserAccount userAccount = userAccountMapper.selectById(id);
//            model.setStaffName(userAccount.getName());
//            model.setStaffId(id);
//            staffs.add(model);
//        }
//        teamObj.put("staffs", staffs);
//        ContainStaff model = JSON.parseObject(JSON.toJSONString(teamObj), ContainStaff.class);
//        return model;
//    }

    @Override
    @Transactional
    public Integer deleteMaster(long id,Long userId,Integer version) {
        int effect=0;
        WorkTask workTask = workTaskMapper.selectById(id);
//        if( task.getOwnerByStaffId().longValue()==staffId){
        if( workTask.getOwnerByEndUserId().longValue()==userId || workTask.getCreateByEndUserId().longValue() == userId ){
            String status = workTask.getStatus();
            if(status==null || status.equals("Open") || status.equals("Draft") || status.equals("Closed") || status.equals("Undefined")){
                effect+=super.deleteMaster(id,version);
                //删除附件
                attachmentsService.deleteAttachments(WorkTask.tableName,id);
                //删除跟进消息
                List<TaskFollower> taskFollowerList = taskFollowerMapper.selectList(new QueryWrapper<TaskFollower>().eq("task_id", id));
                for(TaskFollower follower : taskFollowerList){
                    Message message2 = new Message();
                    message2.setNoticeEndUserId(follower.getEndUserId());
                    message2.setMessageType(MessageType.TASKFOLLOWER.getType());
                    message2.setReferenceId(id);
                    messageService.deleteMessage(message2);
                }
                effect+=taskFollowerMapper.delete(new QueryWrapper<TaskFollower>().eq("task_id",id));
                effect+=taskReferenceMapper.delete(new QueryWrapper<TaskReference>().eq("task_id",id));
                effect+=taskUpdateMapper.delete(new QueryWrapper<TaskUpdate>().eq("task_id",id));
                //删除消息
                Message message = new Message();
                message.setMessageType(MessageType.TASK.getType())
                        .setReferenceId(id).setNoticeEndUserId(workTask.getOwnerByEndUserId());
                messageService.deleteMessage(message);
                return effect;
            }else{
                throw new BusinessException(5000,status+"状态不允许删除");
            }
        }else{
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
        /*if (queryTaskDao.checkTaskReferenceDependency(id) > 0)
            throw new BusinessException(BusinessCode.PowerBusinessDeleteAssociatedOne, "删除失败,task" + id + "被依赖(checkTaskReferenceDependency)");
        if (queryTaskDao.checkTaskFollowerDependency(id) > 0)
            throw new BusinessException(BusinessCode.PowerBusinessDeleteAssociatedOne, "删除失败,task" + id + "被依赖(checkTaskFollowerDependency)");
        if (queryTaskDao.checkTaskUpdateDependency(id) > 0)
            throw new BusinessException(BusinessCode.PowerBusinessDeleteAssociatedOne, "删除失败,task" + id + "被依赖(checkTaskUpdateDependency)");*/
    }

}

