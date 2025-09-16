package com.jfeat.am.module.task.api.crud;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.task.services.crud.service.SnpoolService;
import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.service.QueryWorkTaskService;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * <p>
 * 事件 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Api(value = "工作任务")
@RestController
@RequestMapping("/api/task/tasks")
public class WorkTaskEndpoint {

    @Resource
    WorkTaskService workTaskService;
    @Resource
    QueryWorkTaskService queryWorkTaskService;

    @Resource
    private SnpoolService snpoolService;

    @Resource
    private UserAccountMapper userAccountMapper;

    @ApiOperation(value = "新建事件",response = WorkTask.class)
    @BusinessLog(name = "事件",value = "新建事件")
    @PostMapping
    public Tip createTask(@Valid @RequestBody WorkTaskModel entity) {
        entity.setTaskNumber(snpoolService.getSerial("TD"));
        Long userId = JWTKit.getUserId();
        UserAccount userAccount = userAccountMapper.selectById(userId);
//        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
//        if (staff==null){
//            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
//        }
        Long affect = workTaskService.createMasterByStaff(entity, userAccount, false);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "新建 ownerByme 事件",response = WorkTask.class)
    @BusinessLog(name = "ownerByme事件",value = "新建ownerByme事件")
    @PostMapping("/create/owner")
    public Tip createTaskOwnerByMe(@Valid @RequestBody WorkTaskModel entity) {
//        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
//        if (staff==null){
//            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
//        }
        Long userId = JWTKit.getUserId();
        UserAccount userAccount = userAccountMapper.selectById(userId);

        Long affect = workTaskService.createMasterByStaff(entity, userAccount, true);
        return SuccessTip.create(affect);
    }


    @ApiOperation(value = "查询事件   包括事件的关联项",response = WorkTask.class)
    @GetMapping("/{id}")
    public Tip getTask(@PathVariable Long id) {
//        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
//        if (staff==null){
//            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
//        }
        return SuccessTip.create(workTaskService.taskIncludeUpdate(id,JWTKit.getUserId()));
    }

    @ApiOperation(value = " 修改事件",response = WorkTask.class)
    @BusinessLog(name = "事件",value = "修改事件")
    @PutMapping("/{id}")
    public Tip updateTask(@Valid @PathVariable Long id, @RequestBody WorkTaskModel entity) {
        UserAccount userAccount = userAccountMapper.selectById(JWTKit.getUserId());
//        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
//        if (staff==null){
//            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
//        }
        int affect = workTaskService.updateTask(id, entity, userAccount);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "删除事件   是否删除 关联的信息 待定",response = WorkTask.class)
    @BusinessLog(name = "事件",value = "删除事件")
    @DeleteMapping("/{id}/{version}")
    public Tip deleteTask(@PathVariable Long id,@PathVariable Integer version) {
        Long userId = JWTKit.getUserId();
        int affect = workTaskService.deleteMaster(id, userId, version);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "查询事件  根据事件的名称(taskName) 跟进员工(ownerByStaffId) 以及 状态(status) 来查找事件",response = WorkTask.class)
    @GetMapping
    public Tip queryTasks(Page page,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(name = "ownerByStaffId", required = false) Long ownerByStaffId,
                          @RequestParam(name = "ownerByEndUserId", required = false) Long ownerByEndUserId,
                          @RequestParam(name = "taskName", required = false) String taskName,
                          @RequestParam(name = "status", required = false) String status) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(queryWorkTaskService.findTaskPage(page,ownerByStaffId, ownerByEndUserId,taskName,status));
        return SuccessTip.create(page);
    }

    /*@ApiOperation(value = "查询当前登录用户的最新代办事项")
    @GetMapping("/new")
    public Tip queryNewTasks(HttpServletRequest httpServletRequest){
        Long staffId = staffUserKit.getStaffIdByUserId(JWTKit.getUserId(httpServletRequest));
        if(staffId == null )
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        return SuccessTip.create(queryTaskService.findNewTask(staffId));
    }*/
}
