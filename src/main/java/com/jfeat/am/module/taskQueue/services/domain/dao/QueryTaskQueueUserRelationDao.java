package com.jfeat.am.module.taskQueue.services.domain.dao;

import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueUserRelationRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;
import com.jfeat.am.module.taskQueue.services.gen.crud.model.TaskQueueUserRelationModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-11-17
 */
public interface QueryTaskQueueUserRelationDao extends QueryMasterDao<TaskQueueUserRelation> {
   /*
    * Query entity list by page
    */
    List<TaskQueueUserRelationRecord> findTaskQueueUserRelationPage(Page<TaskQueueUserRelationRecord> page, @Param("record") TaskQueueUserRelationRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    TaskQueueUserRelationModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<TaskQueueUserRelationModel> queryMasterModelList(@Param("masterId") Object masterId);
}