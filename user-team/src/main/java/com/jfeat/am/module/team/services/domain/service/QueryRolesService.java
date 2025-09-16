package com.jfeat.am.module.team.services.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.team.services.persistence.model.Roles;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryRolesService {
    List<Roles> findRolePage(Page<Roles> page, Roles roles);
}