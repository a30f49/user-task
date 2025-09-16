package com.jfeat.am.module.task.services.domain.dao;

import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.taskQueue.services.domain.model.TaskQueueRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface QueryWorkTaskDao extends BaseMapper<WorkTask> {
    List<WorkTask> findTaskPage(Page<WorkTask> page,
                                @Param("ownerByStaffId")Long ownerByStaffId,
                                @Param("ownerByEndUserId") Long ownerByEndUserId,
                                @Param("taskName")String taskName,
                                @Param("status")String status);


    List<WorkTask> searchTaskOwnerByTeam(Page<WorkTaskModel> page, @Param("staffId")Long staffId, @Param("status")String status, @Param("orderBy")String orderBy);

    List<WorkTask> searchTaskFollowerByTeam(Page<WorkTaskModel> page, @Param("staffId")Long staffId, @Param("status")String status, @Param("orderBy")String orderBy);

    List<WorkTask> allTaskOwnerByMe(Page<WorkTaskModel> page, @Param("staffId")Long staffId, @Param("status")String status, @Param("orderBy")String orderBy);

    List<WorkTask> allTaskFollowerByMe(Page<WorkTaskModel> page, @Param("staffId")Long staffId, @Param("status")String status, @Param("orderBy")String orderBy);

    @Select("SELECT count(id) FROM task_reference WHERE referenceId = #{referenceId}")
    Integer checkTaskReferenceDependency(@Param("referenceId") Long referenceId);

    @Select("SELECT count(id) FROM task_follower WHERE taskId = #{taskId}")
    Integer checkTaskFollowerDependency(@Param("taskId") Long taskId);

    @Select("SELECT count(id) FROM task_update WHERE taskId = #{taskId}")
    Integer checkTaskUpdateDependency(@Param("taskId") Long taskId);

    List<WorkTask> queryUserWorkTaskList(Page<WorkTask> page, @Param("record") WorkTaskModel record,
                                         @Param("tag") String tag,
                                         @Param("search") String search, @Param("orderBy") String orderBy,
                                         @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}