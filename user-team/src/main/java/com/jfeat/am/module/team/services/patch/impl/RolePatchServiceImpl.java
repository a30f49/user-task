package com.jfeat.am.module.team.services.patch.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.team.services.domain.model.StaffRoleModel;
import com.jfeat.am.module.team.services.patch.RolePatchService;
import com.jfeat.am.module.team.services.persistence.dao.RolesMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffRoleMapper;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.am.module.team.services.persistence.model.StaffRole;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */
@Deprecated
@Service
public class RolePatchServiceImpl implements RolePatchService {

    @Resource
    StaffMapper staffMapper;
    @Resource
    StaffRoleMapper staffRoleMapper;
    @Resource
    RolesMapper rolesMapper;

    //判断某个Staff是否在某个Roles
    public void isStaffInRole(long roleId, long staffId) {
        //判断该员工是否在该组里面，如果没有，插入该员工到该组里面
        Integer isInRole = staffRoleMapper.selectCount(new QueryWrapper<StaffRole>().
                eq("roleId", roleId).eq("staffId", staffId));
        if (isInRole.compareTo(0) != 0) {
            throw new BusinessException(2010, "The Staff has been in that Roles!");
        }
    }

    // 判断某个Roles中是否有 Staff  如果存在 Staff 无法执行删除操作
    public void judgeWhetherStaffInRoles(long id){
        List<StaffRole> staffs = staffRoleMapper.selectList(new QueryWrapper<StaffRole>().eq("roleId",id));
        if(staffs.size() > 0){
            throw new BusinessException(2015,"Can not executed that operation!");
        }
    }

    //    批量添加Staff到某个ROLE
    public Integer batchAddStaffToRole(long roleId, List<Long> ids) {
        int result = 0;
        for (Long id : ids) {

            //通过TeamId 以及 StaffId 判断是否已经在该Roles内
            Integer isInRole = staffRoleMapper.selectCount(new QueryWrapper<StaffRole>().
                    eq("roleId",roleId).eq("staffId",id));
//            isStaffInRole(roleId, id);
            // 不在该组的时候，允许插入该Team
            if ((isInRole.compareTo(0)) == 0 ) {
                StaffRole role = new StaffRole();
                role.setStaffId(id);
                role.setRoleId(roleId);
                staffRoleMapper.insert(role);
                result++;
            }else {
            }
        }
        return result;
    }

    //    //    批量删除某个ROLE下的Staff
    public Integer batchDeleteStaffToRole(long roleId, List<Long> ids) {
        int result = 0;
        for (Long id : ids) {
            //通过TeamId 以及 StaffId 判断是否已经在该Roles内
            Integer isInRole = staffRoleMapper.selectCount(new QueryWrapper<StaffRole>().
                    eq("roleId", roleId).eq("staffId", id));
            if ((isInRole.compareTo(1) == 0)) {
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("staffId", id);
                staffRoleMapper.deleteByMap(map);
                result++;
            } else {
            }
        }
        return result;
    }

    // 我的所有角色
    public List<StaffRoleModel> allMyRoles(long staffId) {
        // 找出所有与我有关的角色
        List<StaffRole> staffRoles = staffRoleMapper.selectList(new QueryWrapper<StaffRole>().eq("staffId", staffId));
        List<StaffRoleModel> models = new ArrayList<StaffRoleModel>();
        for (StaffRole staffRole : staffRoles) {
            JSONObject staffRolesObj = JSON.parseObject(JSON.toJSONString(staffRole));
            Roles roles = rolesMapper.selectById(staffRole.getRoleId());
            staffRolesObj.put("roles", roles);
            StaffRoleModel model = JSONObject.parseObject(JSON.toJSONString(staffRolesObj), StaffRoleModel.class);
            models.add(model);
        }
        return models;
    }

}
