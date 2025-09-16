package com.jfeat.am.module.team.services.patch;

import com.jfeat.am.module.team.services.domain.model.AllTeamIncludeStaff;
import com.jfeat.am.module.team.services.domain.model.StaffTeamModel;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public interface TeamPatchService {


    /**
    *    判断某个 Staff 在某个 Team 中是否是该 Team 的 Leader 通过 staffId 以及 TeamId 去查找
    * */
    boolean isStaffTeamLeader(long teamId, long staffId);

    /*//判断某个Team是否有Leader
    public void  isHasTeamLeader(long teamId);*/

    // 判断某个团队是否有 Staff  如果存在 Staff 无法执行操作

    /**
    *
    * */
    void judgeWhetherStaffInTeam(long id);

    /**
    *  //  指派某个Staff为TeamLeader
    * */
    StaffTeam setStaffToTeamLeader(long teamId, Long staffId);

    /**
    *  // 批量添加Staff到某个team
    * */
    Integer batchAddStaffToTeam(long teamId, List<Long> ids);

    /**
    *  // 某个Team批量删除Staff
    * */
    Integer batchDeleteStaffToTeam(Long teamId, List<Long> ids);

    /**
    *  // 判断某个Staff是否在某个Team
    * */
    Boolean isStaffInTeam(long teamId, long staffId);

    /**
    *  // 判断某个Staff是否在某个Team
    * */
    Integer deleteStaffInTeam(long teamId,long staffId);

    /**
    *   我的所有团队
    * */
    List<StaffTeamModel> allMyTeam(long staffId);

}

