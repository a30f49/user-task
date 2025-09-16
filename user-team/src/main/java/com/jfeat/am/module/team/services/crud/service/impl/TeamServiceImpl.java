package com.jfeat.am.module.team.services.crud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.PowerBusinessException;
import com.jfeat.am.module.team.services.crud.service.StaffTeamService;
import com.jfeat.am.module.team.services.crud.service.TeamService;
import com.jfeat.am.module.team.services.domain.dao.QueryTeamDao;
import com.jfeat.am.module.team.services.domain.model.*;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffTeamMapper;
import com.jfeat.am.module.team.services.persistence.dao.TeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 团队 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Service
public class TeamServiceImpl extends CRUDServiceOnlyImpl<Team> implements TeamService {

    @Resource
    private TeamMapper teamMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    StaffTeamMapper staffTeamMapper;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    QueryTeamDao queryTeamDao;

    @Override
    protected BaseMapper<Team> getMasterMapper() {
        return teamMapper;
    }

    // 重构 新建 Team Service

    @Transactional
    public Integer createTeamIncludeStaff(TeamAndStaffModel model){
        try {
            teamMapper.insert(model);
        }catch (Exception e){
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNameRepetione);
        }
        // 插入 Leader 指派多个个Staff为TeamLeader
        if (model.getLeaders()!=null){
            staffTeamService.setStaffToTeamLeader(model.getId(),model.getLeaders());
        }else{
            // do nothings
        }
        // 插入 staff 批量添加 Staff 到指定的 Team 中
        if (model.getStaffs()!=null){
            staffTeamService.addStaffToTeam(model.getId(),model.getStaffs());
        }else{
            // do nothings
        }
        return 1;
    }

    // 重构 修改 Team Service

    @Transactional
    public Integer updateTeamIncludeStaff(long id,TeamAndStaffModel model){
        Team originTeam = teamMapper.selectById(id);
        model.setId(id);
        if (((originTeam.getTeamName()).compareTo(model.getTeamName()) != 0 )) {
            try {
                teamMapper.updateById(CRUD.castObject(model,Team.class));
            }catch (Exception e){
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNameRepetione);
            }
        }
        teamMapper.updateById(CRUD.castObject(model,Team.class));
        // 抹去 之前的数据
        staffTeamMapper.delete(new QueryWrapper<StaffTeam>().eq("teamId",id));

        // 插入 staff 批量添加 Staff 到指定的 Team 中
        if (model.getStaffs()!=null){
            staffTeamService.addStaffToTeam(id,model.getStaffs());
        }else{
            // do nothings
        }
        // 插入 Leader 指派多个个Staff为TeamLeader
        if (model.getLeaders()!=null){
            staffTeamService.setStaffToTeamLeader(id,model.getLeaders());
        }else{
            // do nothings
        }
        return 1;
    }

    /**
    *  删除 该 Team的所有成员，包括 Leader  提供 给 更新 Team的staff 以及 Leader  的时候使用
    *  1. 删除 某个 Team 下面的 所有的 staff  包括 Leader
    *  2. 该方法仅仅是提供给 修改Team信息的时候 先抹去该Team的所有的信息 在执行插入的操作
    * */
    @Override
    public Integer deleteALlStaff(long id){
        return staffTeamMapper.delete(new QueryWrapper<StaffTeam>().eq("teamId",id));
    }


    /**
     *      删除某个Team  ，当 Team下面有记录的时候，不允许删除
     * */
    @Override
    public Integer deleteTeam(long id,Integer version){
        Integer staffTeams = staffTeamMapper.selectCount(new QueryWrapper<StaffTeam>().eq("teamId",id));
        Integer taskFollowers = queryTeamDao.checkTeamWithFollow(id);
        if (staffTeams == 0 && taskFollowers == 0){
            return this.deleteMaster(id,version);
        }
        else{
            if(staffTeams!=0){
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne,"team被staffTeams关联");
            }
            if(taskFollowers!=0){
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne,"team被taskFollowers关联");
            }
            throw new RuntimeException("系统异常");
        }
    }

    @Override
    public TeamModel teamBuilding(long id) {
        Team team = teamMapper.selectById(id);
        JSONObject teamObj = JSONObject.parseObject(JSONObject.toJSONString(team));

        List<StaffTeam> staffTeams = staffTeamMapper.selectList(new QueryWrapper<StaffTeam>().eq("teamId", team.getId()));
        if (staffTeams == null || staffTeams.size() == 0) {
            TeamModel model = JSONObject.parseObject(JSONObject.toJSONString(teamObj), TeamModel.class);
            return model;
        } else {
            List<NameModel> teamStaffs = new ArrayList<>();
            List<NameModel> teamLeaders = new ArrayList<>();
            for (StaffTeam staffTeam : staffTeams) {

                // 确定是 Staff  而不是 Leader 加入到 员工数组
                if (staffTeam.getIsLeader().compareTo(1) != 0) {
                    NameModel nameModel = new NameModel();
                    Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                    if (staff != null) {
                        nameModel.setStaffId(staffTeam.getStaffId());
                        nameModel.setTeamId(id);
                        nameModel.setTeamName(team.getTeamName());
                        nameModel.setStaffName(new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
                        teamStaffs.add(nameModel);
                    }
                } else {
                    NameModel nameModel = new NameModel();
                    Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                    if (staff != null) {
                        nameModel.setStaffId(staffTeam.getStaffId());
                        nameModel.setTeamId(id);
                        nameModel.setTeamName(team.getTeamName());
                        nameModel.setStaffName(new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
                        teamLeaders.add(nameModel);
                    }

                }

            }
            teamObj.put("teamStaffs", teamStaffs);
            teamObj.put("teamLeaders", teamLeaders);
            TeamModel model = JSONObject.parseObject(JSONObject.toJSONString(teamObj), TeamModel.class);
            return model;
        }
    }


    /**
     *  所有的团队以及团队下面的所有的员工
     * */
    @Override
    public List<AllTeamIncludeStaff> allTeamAndStaff(){
        List<AllTeamIncludeStaff> models = new ArrayList<>();
        List<Team> teams = teamMapper.selectList(new QueryWrapper<Team>());
        for (Team team : teams) {
            AllTeamIncludeStaff model = new AllTeamIncludeStaff();
            model.setTeamName(team.getTeamName());
            model.setTeamId(team.getId());
            List<StaffTeam> staffTeams = staffTeamMapper.selectList(new QueryWrapper<StaffTeam>().eq("teamId",team.getId()));
            List<NameModel> staffs = new ArrayList<>();
            for(StaffTeam staffTeam : staffTeams){
                Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                if(staff.getIsValid()==1){
                    NameModel nameModel = new NameModel();
                    nameModel.setStaffName(new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
                    nameModel.setStaffId(staff.getId());
                    staffs.add(nameModel);
                }
            }
            model.setStaffs(staffs);
            models.add(model);
        }
        return models;
    }

    /**
     *  登录用户作为Leader的所有的Team下面的所有的员工
     * */
    @Override
    public List<AllTeamIncludeStaff> allTeamLeaderAndStaff(long staffId){
        List<AllTeamIncludeStaff> models = new ArrayList<>();
        List<Team> teams = teamMapper.selectList(new QueryWrapper<Team>());
        for (Team team : teams) {
            if(!staffTeamService.checkIsTeamLeader(team.getId(),staffId)){

            }else{
                AllTeamIncludeStaff model = new AllTeamIncludeStaff();
                model.setTeamName(team.getTeamName());
                model.setTeamId(team.getId());
                List<StaffTeam> staffTeams = staffTeamMapper.selectList(new QueryWrapper<StaffTeam>().eq(StaffTeam.TEAMID,team.getId()));
                List<NameModel> staffs = new ArrayList<>();
                for(StaffTeam staffTeam : staffTeams){
                    Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                    if(null !=staff.getIsValid() && staff.getIsValid() ==1){
                        NameModel nameModel = new NameModel();
                        nameModel.setStaffName(staff==null?null:new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
                        nameModel.setStaffId(staff==null?null:staff.getId());
                        staffs.add(nameModel);
                    }
                }
                model.setStaffs(staffs);
                models.add(model);
            }
        }
        return models;
    }
}