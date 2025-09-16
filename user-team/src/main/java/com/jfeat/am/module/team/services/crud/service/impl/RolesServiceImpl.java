package com.jfeat.am.module.team.services.crud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.PowerBusinessException;
import com.jfeat.am.module.team.services.crud.service.RolesService;
import com.jfeat.am.module.team.services.persistence.dao.RolesMapper;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.crud.plus.impl.CRUDServiceGroupImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Service
public class RolesServiceImpl extends CRUDServiceGroupImpl<Roles> implements RolesService {

    @Resource
    private RolesMapper rolesMapper;

    @Override
    protected BaseMapper<Roles> getGroupMapper() {
        return rolesMapper;
    }

    public void  saveOrUpdateEntity(Roles entity){
        List<Roles> entities = rolesMapper.selectList(new QueryWrapper<Roles>());
        for (Roles originEntity : entities) {
            if (((entity.getRoleName()).compareTo(originEntity.getRoleName()) == 0)) {
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessCodeBase, "Name repetition, please enter other Name");
            }
        }
    }


    /*// 该角色下所有员工的信息
    public RolesModel roleIncludeStaff(long id){
        Roles role = rolesMapper.selectById(id);
        JSONObject roleObj = JSONObject.parseObject(JSONObject.toJSONString(role));
        //search all records
        List<StaffRole> roles = staffRoleMapper.selectList(new EntityWrapper<StaffRole>().eq("RoleId",id));
        List<Staff> staffs = new ArrayList<>();
        for(StaffRole staffRole : roles){
            Staff staff = staffMapper.selectById(staffRole.getStaffId());
            staffs.add(staff);
        }
        roleObj.put("staffs",staffs);
        RolesModel model = JSONObject.parseObject(JSONObject.toJSONString(roleObj),RolesModel.class);
        return model;
    }*/
}


