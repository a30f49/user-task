package com.jfeat.am.module.task.services.domain.dao;

import com.jfeat.am.module.task.services.domain.model.TaskUserRelationRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;
import com.jfeat.am.module.task.services.gen.crud.model.TaskUserRelationModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-11-17
 */
public interface QueryTaskUserRelationDao extends QueryMasterDao<TaskUserRelation> {
   /*
    * Query entity list by page
    */
    List<TaskUserRelationRecord> findTaskUserRelationPage(Page<TaskUserRelationRecord> page, @Param("record") TaskUserRelationRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    TaskUserRelationModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<TaskUserRelationModel> queryMasterModelList(@Param("masterId") Object masterId);
}