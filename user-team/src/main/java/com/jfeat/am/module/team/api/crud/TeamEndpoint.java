package com.jfeat.am.module.team.api.crud;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.team.services.crud.service.TeamService;
import com.jfeat.am.module.team.services.domain.model.TeamAndStaffModel;
import com.jfeat.am.module.team.services.domain.service.QueryTeamService;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "团队信息")
@RestController
@RequestMapping("/api/team/teams")
public class TeamEndpoint {

    @Resource
    TeamService teamService;

    @Resource
    QueryTeamService queryTeamService;

    @ApiOperation(value = "新建Team",response = Team.class)
    @BusinessLog(name = "Team",value = "新建Team")
    @PostMapping
    public Tip createTeam(@RequestBody TeamAndStaffModel entity) {
        int affect = teamService.createTeamIncludeStaff(entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "获取Team",response = Team.class)
    @GetMapping("/{id}")
    public Tip getTeam(@PathVariable Long id) {
        return SuccessTip.create(teamService.teamBuilding(id));
    }

    @ApiOperation(value = "修改Team的信息",response = Team.class)
    @BusinessLog(name = "Team",value = "修改Team的信息")
    @PutMapping("/{id}")
    public Tip updateTeam(@PathVariable Long id, @RequestBody TeamAndStaffModel entity) {
        int affect = teamService.updateTeamIncludeStaff(id, entity);
        return SuccessTip.create(affect);
    }


    @ApiOperation(value = "删除某个Team",response = Team.class)
    @BusinessLog(name = "Team",value = "删除某个Team")
    @DeleteMapping("/{id}/{version}")
    public Tip deleteTeam(@PathVariable Long id,@PathVariable Integer version) {
        //TODO  删除关联事件 以及其他的关联信息
        Integer result = teamService.deleteTeam(id,version);
        return SuccessTip.create(result);
    }

    @ApiOperation(value = "根据名称或者简介去查找Team",response = Team.class)
    @GetMapping
    public Tip queryTeams(Page page,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(name = "name", required = false) String name,
                          @RequestParam(name = "desc", required = false) String desc) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Team team = new Team();
        team.setTeamName(name);
        team.setTeamDesc(desc);
        page.setRecords(queryTeamService.findTeamPage(page, team));

        return SuccessTip.create(page);
    }


}
