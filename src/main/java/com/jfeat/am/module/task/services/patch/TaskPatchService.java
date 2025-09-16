package com.jfeat.am.module.task.services.patch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.domain.model.TeamId;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface TaskPatchService {

    /**
     * own  login user - >  Task
     */
    List<WorkTaskModel> allTaskThatOwnByMe(Page<WorkTaskModel> page, Long userId, String status, String orderBy);

    /**
     * follower  login user - >  Task
     */
    List<WorkTaskModel> allTaskFollowerByMe(Page<WorkTaskModel> page, Long userId, String status, String orderBy);

    /**
     * own  By Team - >  Task
     */
    List<WorkTaskModel> allTaskThatOwnByTeam(Page<WorkTaskModel> page, Long userId, String status, String orderBy);

    /**
     *   follower  By Team
     * */

    List<WorkTaskModel> allTaskFollowerByTeam(Page<WorkTaskModel> page, Long userId, String status, String orderBy);

    /**
     *  指派 User Follower 事件
     *
     * */
    Integer assignUserFollowerTask(Long userId,Long taskId);

    /**
     *  转移  事件
     *
     * */
    Integer transferTask(UserAccount user, Long taskId, TeamId teamId);

    /**
     *  指派 Staff Owner  事件
     *
     * */
    Integer assignUserOwnerTask(Long userId,Long taskId);

    /**
     *  添加跟进信息
     * */
    @Transactional
    Integer addTaskUpdate(Long taskId,Long userId, TaskUpdate taskUpdate);

    /**
     *  更新事件信息
     * */
    Integer updateTaskUpdate(Long id, Long userId, TaskUpdate taskUpdate);

    /**
     *  更新事件信息
     * */
    Integer deleteTaskUpdate(Long id, Long userId);

}

