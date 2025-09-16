package com.jfeat.am.module.team.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.team.services.persistence.model.StaffRole;


import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public interface QueryStaffRoleDao extends BaseMapper<StaffRole> {
    List<StaffRole> findStaffrolePage(Page<StaffRole> page, StaffRole staffRole);
}