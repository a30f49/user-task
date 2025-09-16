package com.jfeat.am.module.task.api.patch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.task.services.crud.service.TaskFollowerService;
import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.crud.service.TaskUpdateService;
import com.jfeat.am.module.task.services.domain.model.TaskListModel;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.model.TeamId;
import com.jfeat.am.module.task.services.domain.service.QueryWorkTaskService;
import com.jfeat.am.module.task.services.patch.TaskPatchService;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Code Generator on 2017-11-21
 */
@Api("工作任务")
@RestController
@RequestMapping("/api/task/tasks")
public class TaskPatchEndpoint  {
    @Resource
    WorkTaskService workTaskService;
    @Resource
    QueryWorkTaskService queryWorkTaskService;
    @Resource
    TaskUpdateService taskUpdateService;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    UserAccoutService staffService;
    @Resource
    TaskFollowerService taskFollowerService;
    @Resource
    TaskPatchService taskPatchService;

    @ApiOperation(value = "批量添加 staff 去 跟进 事件  TaskFollower (重新分配)", response = BulkTaskFollowerRequest.class)
    @BusinessLog(name = "跟进事件",value = "重新分配")
    @PostMapping("/{taskId}/bulk/add")
    public Tip bulkAddStaffToTask(@PathVariable long taskId, @RequestBody BulkTaskFollowerRequest followers) {
        int affect = taskFollowerService.bulkAddUserFollowerTask(taskId, staff, followers.getItems());
        return SuccessTip.create(affect);
    }


    @ApiOperation(value = "关闭事件  Owner关闭 或者 管理员关闭", response = WorkTask.class)
    @BusinessLog(name = "事件",value = "关闭事件")
    @PutMapping("/{id}/close")
    public Tip closeTask(@PathVariable Long id) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        WorkTask workTask = workTaskService.retrieveMaster(id);
        if (((staff.getId()).compareTo(workTask.getOwnerByStaffId()) == 0)
                ) {
            workTask.setStatus(TaskStatus.Closed.toString());
            workTask.setPriority(0);
            workTask.setCloseTime(new Date());
            int affect = workTaskService.updateMaster(workTask);
            return SuccessTip.create(affect);
        }else{
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
    }

    @ApiOperation(value = "搁置事件  Owner搁置 或者 管理员搁置", response = WorkTask.class)
    @BusinessLog(name = "事件",value = "搁置事件")
    @PutMapping("/{id}/pending")
    public Tip pendingTask(@PathVariable Long id) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        WorkTask workTask = workTaskService.retrieveMaster(id);
        if (((staff.getId()).compareTo(workTask.getOwnerByStaffId()) == 0)
                ) {
            workTask.setStatus(TaskStatus.Pending.toString());
            int affect = workTaskService.updateMaster(workTask);
            return SuccessTip.create(affect);
        }else{
            throw new BusinessException(BusinessCode.PowerBusinessNoPermission);
        }
    }

    @ApiOperation(value = "查看 属于自己的事件  根据状态来查找 不使用默认是全部", response = TaskListModel.class)
    @GetMapping("/owner")
    public Tip queryTasksOwnerByUser(Page page,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(name = "status", required = false) String status,
                                     @RequestParam(name = "orderBy", required = false) String orderBy,
                                     @RequestParam(name = "sort", required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.PowerBusinessBadRequest, "sort must be ASC or DESC");
                }
            } else {
                sort = "ASC";
            }
            orderBy = orderBy + " " + sort;
        }

        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords( taskPatchService.allTaskThatOwnByMe(page, staff.getId(), status, orderBy));
        return SuccessTip.create(page);
    }

    @ApiOperation(value = "查看 自己Follower的事件  根据状态来查找 不使用默认是全部", response = TaskListModel.class)
    @GetMapping("/follower")
    public Tip queryTasksFollowerByUser(Page page,
                                        @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(name = "status", required = false) String status,
                                        @RequestParam(name = "orderBy", required = false) String orderBy,
                                        @RequestParam(name = "sort", required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.PowerBusinessBadRequest, "sort must be ASC or DESC");
                }
            } else {
                sort = "ASC";
            }
            orderBy = orderBy + " " + sort;
        }

        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(taskPatchService.allTaskFollowerByMe(page,staff.getId(),status, orderBy));
        return SuccessTip.create(page);
    }


    @ApiOperation(value = "查看 ownByTeam 的事件  根据状态来查找 不使用默认是全部", response = WorkTaskModel.class)
    @GetMapping("/team/owner")
    public Tip taskOwnByTeam(Page page,
                             @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(name = "status", required = false) String status,
                             @RequestParam(name = "orderBy", required = false) String orderBy,
                             @RequestParam(name = "sort", required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.PowerBusinessBadRequest, "sort must be ASC or DESC");
                }
            } else {
                sort = "ASC";
            }
            orderBy = orderBy + " " + sort;
        }

        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(taskPatchService.allTaskThatOwnByTeam(page,staff.getId(),status, orderBy));
        return SuccessTip.create(page);
    }

    @ApiOperation(value = "查看 Team Follower的事件  根据状态来查找 不使用默认是全部", response = WorkTaskModel.class)
    @GetMapping("/team/follower")
    public Tip queryTasksFollowerByTeam(Page/*<TaskModel>*/ page,
                                        @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(name = "status", required = false) String status,
                                        @RequestParam(name = "orderBy", required = false) String orderBy,
                                        @RequestParam(name = "sort", required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.PowerBusinessBadRequest, "sort must be ASC or DESC");
                }
            } else {
                sort = "ASC";
            }
            orderBy = orderBy + " " + sort;
        }

        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(taskPatchService.allTaskFollowerByTeam(page,staff.getId(),status, orderBy));
        return SuccessTip.create(page);
    }


    @ApiOperation(value = "分配事件  Leader分配 组员 follower 事件  单个", response = WorkTask.class)
    @BusinessLog(name = "事件",value = "分配事件")
    @PostMapping("/{id}/allot")
    public Tip allotTask(@PathVariable Long id,@RequestBody StaffId staffId) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        WorkTask workTask = workTaskService.retrieveMaster(id);
        int affect = taskPatchService.assignStaffFollowerTask(staff.getId(), workTask.getId(), staffId.getStaffId());
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "转移事件  Leader将事件转移到其他的组", response = WorkTask.class)
    @BusinessLog(name = "事件",value = "转移事件")
    @PostMapping("/{id}/transfer")
    public Tip transferTask(@PathVariable Long id,@RequestBody TeamId teamId) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        int affect = taskPatchService.transferTask(staff, id, teamId);
        return SuccessTip.create();
    }

    @ApiOperation(value = "指定新的跟进人  Leader将事件原来的跟进人转移到其他的跟进人", response = WorkTask.class)
    @BusinessLog(name = "事件",value = "指定新的跟进人")
    @PostMapping("/{id}/change")
    public Tip assignStaffOwnerTask(@PathVariable Long id,@RequestBody StaffId staffId) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        int affect = taskPatchService.assignStaffOwnerTask(staff.getId(), id, staffId.getStaffId());
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "新建事件更新",response = TaskUpdate.class)
    @BusinessLog(name = "事件更新",value = "新建事件更新")
    @PostMapping("/{id}/update")
    public Tip taskUpdate(@PathVariable Long id, @RequestBody TaskUpdate entity) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        int affect = taskPatchService.addTaskUpdate(id, staff.getId(), entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "修改事件更新记录",response = TaskUpdate.class)
    @BusinessLog(name = "事件更新",value = "修改事件更新记录")
    @PutMapping("/{taskId}/update/{id}")
    public Tip updateTaskUpdate(@PathVariable Long taskId, @PathVariable long id,@RequestBody TaskUpdate entity) {
        UserAccount user = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        int affect = taskUpdateService.updateTaskUpdate(taskId, id, staff, entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "删除更新记录",response = TaskUpdate.class)
    @BusinessLog(name = "更新记录",value = "删除更新记录")
    @DeleteMapping("/taskupdates/{id}/{version}")
    public Tip deleteTaskUpdate(@PathVariable Long id,@PathVariable Integer version) {
        int affect = taskUpdateService.deleteMaster(id,version);
        return SuccessTip.create(affect);
    }

}