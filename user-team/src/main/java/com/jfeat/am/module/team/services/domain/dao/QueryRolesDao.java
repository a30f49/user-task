package com.jfeat.am.module.team.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.team.services.persistence.model.Roles;


import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public interface QueryRolesDao extends BaseMapper<Roles> {
    List<Roles> findRolePage(Page<Roles> page, Roles roles);
}