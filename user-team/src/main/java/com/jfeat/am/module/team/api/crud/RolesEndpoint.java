package com.jfeat.am.module.team.api.crud;

import com.jfeat.am.module.team.services.crud.service.RolesService;
import com.jfeat.am.module.team.services.crud.service.StaffRoleService;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
/**
 * <p>
 * 角色表 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Deprecated
@RestController
@RequestMapping("/api/team/roles")
public class RolesEndpoint  {

    //TODO，权限未实现，需要引入 Perm 表再继续优化

    @Resource
    RolesService rolesService;

    @Resource
    StaffRoleService staffRoleService;

    @ApiOperation(value = "获取Roles层级数据",response = Roles.class)
    @GetMapping
    public Tip getRoleList() {
        return SuccessTip.create(rolesService.toJSONObject());
    }


    @ApiOperation(value = "新建Roles",response = Roles.class)
    @PostMapping
    public Tip createRole(@RequestBody Roles entity) {
        rolesService.saveOrUpdateEntity(entity);
        return SuccessTip.create(rolesService.createGroup(entity));
    }

//    @ApiOperation(value = "查看角色权限",response = Roles.class)
//    @GetMapping("/{id}")
//    public Tip getRole(@PathVariable Long id) {
//        //return SuccessTip.create(rolesService.roleIncludeStaff(id));
//        throw new PowerBusinessException(PowerBusinessCodeBase);
//    }

    @ApiOperation(value = "修改Roles信息",response = Roles.class)
    @PutMapping("/{id}")
    public Tip updateRole(@PathVariable Long id, @RequestBody Roles entity) {
        Roles originRoles = rolesService.retrieveGroup(id);
        entity.setId(id);
        if (((originRoles.getRoleName()).compareTo(entity.getRoleName()) != 0 )) {
            rolesService.saveOrUpdateEntity(entity);
        }
        return SuccessTip.create(rolesService.updateGroup(entity));
    }

    @ApiOperation(value = "删除层级数据",response = Roles.class)
    @DeleteMapping("/{id}")
    public Tip deleteRole(@PathVariable Long id) {
        return SuccessTip.create(rolesService.deleteGroup(id));
    }
}
