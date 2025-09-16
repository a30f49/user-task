package com.jfeat.am.module.team.services.crud.service;

import com.jfeat.am.module.team.services.domain.model.AllTeamIncludeStaff;
import com.jfeat.am.module.team.services.domain.model.TeamAndStaffModel;
import com.jfeat.am.module.team.services.domain.model.TeamModel;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.plus.CRUDServiceOnly;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 团队 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */

public interface TeamService  extends CRUDServiceOnly<Team> {

    // 重构 新建 Team Service
    @Transactional
    Integer createTeamIncludeStaff(TeamAndStaffModel model);

    // 重构 修改 Team Service
    @Transactional
    Integer updateTeamIncludeStaff(long id,TeamAndStaffModel model);

    /**
     *  删除 该 Team的所有成员，包括 Leader  提供 给 更新 Team的staff 以及 Leader  的时候使用
     * */
    Integer deleteALlStaff(long id);

    // 查找Team   返回 Team 中所有成员的信息。
    TeamModel teamBuilding(long id);


    /*void deleteTeam(long id);*/

    /**
     *      删除某个Team  ，当 Team下面有记录的时候，不允许删除
     * */
    public Integer deleteTeam(long id,Integer version);


    /**
     *  所有的团队以及团队下面的所有的员工
     * */
    List<AllTeamIncludeStaff> allTeamAndStaff();

    /**
     *  登录用户作为Leader的所有的Team下面的所有的员工
     * */
    List<AllTeamIncludeStaff> allTeamLeaderAndStaff(long staffId);
}
