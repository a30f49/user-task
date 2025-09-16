
package com.jfeat.am.module.taskQueue.api.crud;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.taskQueue.api.permission.TaskQueueUserRelationPermission;
import com.jfeat.am.module.taskQueue.services.domain.dao.QueryTaskQueueUserRelationDao;
import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueUserRelationRecord;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueUserRelationService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-11-17
 */
@RestController
@Api("TaskQueueUserRelation")
@RequestMapping("/api/crud/taskQUeue/taskQueueUserRelation/taskQueueUserRelations")
public class TaskQueueUserRelationEndpoint {

    @Resource
    TaskQueueUserRelationService taskQueueUserRelationService;

    @Resource
    QueryTaskQueueUserRelationDao queryTaskQueueUserRelationDao;


    @BusinessLog(name = "TaskQueueUserRelation", value = "create TaskQueueUserRelation")
    @Permission(TaskQueueUserRelationPermission.TASKQUEUEUSERRELATION_NEW)
    @PostMapping
    @ApiOperation(value = "新建 TaskQueueUserRelation", response = TaskQueueUserRelation.class)
    public Tip createTaskQueueUserRelation(@RequestBody TaskQueueUserRelation entity) {
        Integer affected = 0;
        try {
            affected = taskQueueUserRelationService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(TaskQueueUserRelationPermission.TASKQUEUEUSERRELATION_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 TaskQueueUserRelation", response = TaskQueueUserRelation.class)
    public Tip getTaskQueueUserRelation(@PathVariable Long id) {
        return SuccessTip.create(taskQueueUserRelationService.queryMasterModel(queryTaskQueueUserRelationDao, id));
    }

    @BusinessLog(name = "TaskQueueUserRelation", value = "update TaskQueueUserRelation")
    @Permission(TaskQueueUserRelationPermission.TASKQUEUEUSERRELATION_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 TaskQueueUserRelation", response = TaskQueueUserRelation.class)
    public Tip updateTaskQueueUserRelation(@PathVariable Long id, @RequestBody TaskQueueUserRelation entity) {
        entity.setId(id);
        return SuccessTip.create(taskQueueUserRelationService.updateMaster(entity));
    }

    @BusinessLog(name = "TaskQueueUserRelation", value = "delete TaskQueueUserRelation")
    @Permission(TaskQueueUserRelationPermission.TASKQUEUEUSERRELATION_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 TaskQueueUserRelation")
    public Tip deleteTaskQueueUserRelation(@PathVariable Long id) {
        return SuccessTip.create(taskQueueUserRelationService.deleteMaster(id));
    }

    @Permission(TaskQueueUserRelationPermission.TASKQUEUEUSERRELATION_VIEW)
    @ApiOperation(value = "TaskQueueUserRelation 列表信息", response = TaskQueueUserRelationRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "queueId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryTaskQueueUserRelationPage(Page<TaskQueueUserRelationRecord> page,
                                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              // for tag feature query
                                              @RequestParam(name = "tag", required = false) String tag,
                                              // end tag
                                              @RequestParam(name = "search", required = false) String search,

                                              @RequestParam(name = "userId", required = false) Long userId,

                                              @RequestParam(name = "queueId", required = false) Long queueId,

                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(name = "createTime", required = false) Date createTime,

                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(name = "updateTime", required = false) Date updateTime,
                                              @RequestParam(name = "orderBy", required = false) String orderBy,
                                              @RequestParam(name = "sort", required = false) String sort) {

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String sortPattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(sortPattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        TaskQueueUserRelationRecord record = new TaskQueueUserRelationRecord();
        record.setUserId(userId);
        record.setQueueId(queueId);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<TaskQueueUserRelationRecord> taskQueueUserRelationPage = queryTaskQueueUserRelationDao.findTaskQueueUserRelationPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(taskQueueUserRelationPage);

        return SuccessTip.create(page);
    }
}

