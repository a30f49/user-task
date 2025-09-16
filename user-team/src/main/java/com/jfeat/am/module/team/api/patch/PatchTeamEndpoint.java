package com.jfeat.am.module.team.api.patch;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.PowerBusinessException;
import com.jfeat.am.module.team.services.crud.service.StaffService;
import com.jfeat.am.module.team.services.crud.service.StaffTeamService;
import com.jfeat.am.module.team.services.crud.service.TeamService;
import com.jfeat.am.module.team.services.domain.dao.QueryTeamDao;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.domain.service.QueryStaffService;
import com.jfeat.am.module.team.services.domain.service.QueryStaffTeamService;
import com.jfeat.am.module.team.services.domain.service.QueryTeamService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 团队 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Api(value = "Team Staff 及 Team 的 Leader")
@RestController
@RequestMapping("/api/team/teams")
public class PatchTeamEndpoint  {

    @Autowired
    QueryTeamDao queryTeamDao;

    @Resource
    StaffTeamService staffTeamService;
    @Resource
    QueryTeamService queryTeamService;

    @Resource
    StaffService staffService;
    @Resource
    TeamService teamService;

    @Resource
    QueryStaffService queryStaffService;
    @Resource
    QueryStaffTeamService queryStaffTeamService;

    @ApiOperation(value = "某个team中的所有的Staff 包括 Leader 的信息")
    @GetMapping("/{teamId}/allstaffs")
    public Tip allMyTeam(@PathVariable long teamId){
        return SuccessTip.create(queryStaffService.searchStaffInTeam(teamId));
    }

    @ApiOperation(value = "查看自己 所在的 所有的Team信息(包括不作为领导人的小组)",response = StaffTeam.class)
    @GetMapping("/owners")
    public Tip allMyTeam(){
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        return SuccessTip.create(queryStaffTeamService.getTeamsLedByStaff(staff.getId()));
    }

    @ApiOperation(value = "查看自己作为领导人的所有的Team信息",response = StaffTeam.class)
    @GetMapping("/leader")
    public Tip teamLeader() {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }
        return SuccessTip.create(teamService.allTeamLeaderAndStaff(staff.getId()));
    }

    @ApiOperation(value = "指派Leader",response = StaffTeam.class)
    @PostMapping("/{teamId}/bulk/assignLeader")
    public Tip setTeamLeader(@PathVariable long teamId, @RequestBody Ids ids){
        Integer count = queryTeamDao.checkTeamIsExist(teamId);
        if(count==0) {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessBadRequest, "team不存在");
        }
        int affect = staffTeamService.setStaffToTeamLeader(teamId, ids.getIds());
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "批量将员工放到某个Team",response = StaffTeam.class)
    @PostMapping("/{teamId}/bulk/add")
    public Tip batchAddStaffToTeam(@PathVariable long teamId, @RequestBody Ids ids){
        int affect = staffTeamService.addStaffToTeam(teamId, ids.getIds());
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "批量将员工以及Leader从某个Team移除",response = StaffTeam.class)
    @DeleteMapping("/{teamId}/clean")
    public Tip cleanALlStaff(@PathVariable long teamId){
        int affect = teamService.deleteALlStaff(teamId);
        return SuccessTip.create(affect);
    }


    @ApiOperation(value = "前端复选框API",response = StaffTeam.class)
    @GetMapping("/staff")
    public Tip queryTeams(Page<NameModel> page,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(name = "teamName", required = false) String teamName,
                          @RequestParam(name = "staffName", required = false) String staffName) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(queryStaffTeamService.searchByStaffOrTeamName(page,teamName,staffName));
        return SuccessTip.create(page);
    }


    @ApiOperation(value = "所有的 Team 以及 Team 中的成员 ",response = Team.class)
    @GetMapping("/list")
    public Tip allTeam() {
        return SuccessTip.create(teamService.allTeamAndStaff());
    }



}
