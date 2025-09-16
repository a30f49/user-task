package com.jfeat.am.module.taskQueue.services.domain.dao;

import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;
import com.jfeat.am.module.taskQueue.services.gen.crud.model.TaskQueueModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-11-09
 */
public interface QueryTaskQueueDao extends QueryMasterDao<TaskQueue> {
   /*
    * Query entity list by page
    */
    List<TaskQueueRecord> findTaskQueuePage(Page<TaskQueueRecord> page, @Param("record") TaskQueueRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    TaskQueueModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<TaskQueueModel> queryMasterModelList(@Param("masterId") Object masterId);
}