package com.jfeat.am.module.task.services.domain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.dao.QueryWorkTaskDao;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.service.QueryWorkTaskService;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service
public class QueryWorkWorkTaskServiceImpl implements QueryWorkTaskService {

    @Resource
    QueryWorkTaskDao queryWorkTaskDao;
    @Resource
    WorkTaskService workTaskService;

    @Override
    public List<WorkTask> findTaskPage(Page<WorkTask> page,
                                       Long ownerByStaffId,
                                       Long ownerByEndUserId,
                                       String taskName,
                                       String status) {
        return queryWorkTaskDao.findTaskPage(page, ownerByStaffId,ownerByEndUserId,taskName,status);
    }


    @Override
    public List<WorkTask> searchTaskOwnerByTeam(Page<WorkTaskModel> page,
                                                Long staffId,
                                                String status,
                                                String orderBy) {
        return queryWorkTaskDao.searchTaskOwnerByTeam(page,staffId ,status, orderBy);

    }

    @Override
    public List<WorkTask> queryUserWorkTaskList(Page<WorkTask> page, WorkTaskModel record, String tag, String search, String orderBy, Date startTime, Date endTime) {
        return queryWorkTaskDao.queryUserWorkTaskList(page,record,tag,search,orderBy,startTime,endTime);
    }

    /*@Override
    public List<Task> findNewTask(Long staffId) {
        return queryTaskDao.findNewTask(staffId);
    }*/
}
