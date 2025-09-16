
package com.jfeat.am.module.taskQueue.api.crud;


import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.taskQueue.services.domain.dao.QueryTaskQueueDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.taskQueue.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.taskQueue.services.domain.service.*;
import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueRecord;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-11-09
 */
@RestController
@Api("TaskQueue")
@RequestMapping("/api/crud/taskQueue/taskQueue/taskQueues")
public class TaskQueueEndpoint {

    @Resource
    TaskQueueService taskQueueService;

    @Resource
    QueryTaskQueueDao queryTaskQueueDao;


    @BusinessLog(name = "TaskQueue", value = "create TaskQueue")
    @Permission(TaskQueuePermission.TASKQUEUE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 TaskQueue", response = TaskQueue.class)
    public Tip createTaskQueue(@RequestBody TaskQueue entity) {
        Integer affected = 0;
        try {
            affected = taskQueueService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(TaskQueuePermission.TASKQUEUE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 TaskQueue", response = TaskQueue.class)
    public Tip getTaskQueue(@PathVariable Long id) {
        return SuccessTip.create(taskQueueService.queryMasterModel(queryTaskQueueDao, id));
    }

    @BusinessLog(name = "TaskQueue", value = "update TaskQueue")
    @Permission(TaskQueuePermission.TASKQUEUE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 TaskQueue", response = TaskQueue.class)
    public Tip updateTaskQueue(@PathVariable Long id, @RequestBody TaskQueue entity) {
        entity.setId(id);
        return SuccessTip.create(taskQueueService.updateMaster(entity));
    }

    @BusinessLog(name = "TaskQueue", value = "delete TaskQueue")
    @Permission(TaskQueuePermission.TASKQUEUE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 TaskQueue")
    public Tip deleteTaskQueue(@PathVariable Long id) {
        return SuccessTip.create(taskQueueService.deleteMaster(id));
    }

    @Permission(TaskQueuePermission.TASKQUEUE_VIEW)
    @ApiOperation(value = "TaskQueue 列表信息", response = TaskQueueRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "title", dataType = "String"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "emailOperation", dataType = "Integer"),
            @ApiImplicitParam(name = "smsOperation", dataType = "Integer"),
            @ApiImplicitParam(name = "wechatOperation", dataType = "Integer"),
            @ApiImplicitParam(name = "pid", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryTaskQueuePage(Page<TaskQueueRecord> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  // for tag feature query
                                  @RequestParam(name = "tag", required = false) String tag,
                                  // end tag
                                  @RequestParam(name = "search", required = false) String search,

                                  @RequestParam(name = "name", required = false) String name,

                                  @RequestParam(name = "title", required = false) String title,

                                  @RequestParam(name = "orgId", required = false) Long orgId,

                                  @RequestParam(name = "emailOperation", required = false) Boolean emailOperation,

                                  @RequestParam(name = "smsOperation", required = false) Boolean smsOperation,

                                  @RequestParam(name = "wechatOperation", required = false) Boolean wechatOperation,

                                  @RequestParam(name = "pid", required = false) Long pid,

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

        TaskQueueRecord record = new TaskQueueRecord();
        record.setName(name);
        record.setTitle(title);
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setEmailOperation(emailOperation);
        record.setSmsOperation(smsOperation);
        record.setWechatOperation(wechatOperation);
        record.setPid(pid);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<TaskQueueRecord> taskQueuePage = queryTaskQueueDao.findTaskQueuePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(taskQueuePage);

        return SuccessTip.create(page);
    }
}

