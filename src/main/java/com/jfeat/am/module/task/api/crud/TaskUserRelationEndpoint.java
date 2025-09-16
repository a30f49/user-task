
package com.jfeat.am.module.task.api.crud;


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
import com.jfeat.am.module.task.services.domain.dao.QueryTaskUserRelationDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.task.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.task.services.domain.service.*;
import com.jfeat.am.module.task.services.domain.model.TaskUserRelationRecord;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;

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
 * @since 2022-11-17
 */
@RestController
@Api("TaskUserRelation")
@RequestMapping("/api/crud/task/taskUserRelation/taskUserRelations")
public class TaskUserRelationEndpoint {

    @Resource
    TaskUserRelationService taskUserRelationService;

    @Resource
    QueryTaskUserRelationDao queryTaskUserRelationDao;


    @BusinessLog(name = "TaskUserRelation", value = "create TaskUserRelation")
    @Permission(TaskUserRelationPermission.TASKUSERRELATION_NEW)
    @PostMapping
    @ApiOperation(value = "新建 TaskUserRelation", response = TaskUserRelation.class)
    public Tip createTaskUserRelation(@RequestBody TaskUserRelation entity) {
        Integer affected = 0;
        try {
            affected = taskUserRelationService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(TaskUserRelationPermission.TASKUSERRELATION_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 TaskUserRelation", response = TaskUserRelation.class)
    public Tip getTaskUserRelation(@PathVariable Long id) {
        return SuccessTip.create(taskUserRelationService.queryMasterModel(queryTaskUserRelationDao, id));
    }

    @BusinessLog(name = "TaskUserRelation", value = "update TaskUserRelation")
    @Permission(TaskUserRelationPermission.TASKUSERRELATION_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 TaskUserRelation", response = TaskUserRelation.class)
    public Tip updateTaskUserRelation(@PathVariable Long id, @RequestBody TaskUserRelation entity) {
        entity.setId(id);
        return SuccessTip.create(taskUserRelationService.updateMaster(entity));
    }

    @BusinessLog(name = "TaskUserRelation", value = "delete TaskUserRelation")
    @Permission(TaskUserRelationPermission.TASKUSERRELATION_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 TaskUserRelation")
    public Tip deleteTaskUserRelation(@PathVariable Long id) {
        return SuccessTip.create(taskUserRelationService.deleteMaster(id));
    }

    @Permission(TaskUserRelationPermission.TASKUSERRELATION_VIEW)
    @ApiOperation(value = "TaskUserRelation 列表信息", response = TaskUserRelationRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "taskId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryTaskUserRelationPage(Page<TaskUserRelationRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         // for tag feature query
                                         @RequestParam(name = "tag", required = false) String tag,
                                         // end tag
                                         @RequestParam(name = "search", required = false) String search,

                                         @RequestParam(name = "userId", required = false) Long userId,

                                         @RequestParam(name = "taskId", required = false) Long taskId,

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

        TaskUserRelationRecord record = new TaskUserRelationRecord();
        record.setUserId(userId);
        record.setTaskId(taskId);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<TaskUserRelationRecord> taskUserRelationPage = queryTaskUserRelationDao.findTaskUserRelationPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(taskUserRelationPage);

        return SuccessTip.create(page);
    }
}

