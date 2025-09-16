package com.jfeat.am.module.team.services.crud.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.team.services.crud.service.StaffRoleService;
import com.jfeat.am.module.team.services.persistence.dao.RolesMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffRoleMapper;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffRole;
import com.jfeat.crud.plus.impl.CRUDServicePeerImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 员工角色 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Service
public class StaffRoleServiceImpl extends CRUDServicePeerImpl<Staff,Roles,StaffRole> implements StaffRoleService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private RolesMapper rolesMapper;

    @Resource
    private StaffRoleMapper staffRoleMapper;

    @Override
    protected BaseMapper<Staff> getMasterMapper() {
        return staffMapper;
    }

    @Override
    protected BaseMapper getMasterPeerMapper() {
        return rolesMapper;
    }

    @Override
    protected BaseMapper getRelationMapper() {
        return staffRoleMapper;
    }

    @Override
    protected String[] relationMatches() {
        return new String[]{StaffRole.STAFFID, StaffRole.ROLEID};
    }
}


