package com.jfeat.am.module.team.services.domain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.team.services.domain.dao.QueryRolesDao;
import com.jfeat.am.module.team.services.domain.service.QueryRolesService;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service
public class QueryRolesServiceImpl implements QueryRolesService {

    @Resource
    QueryRolesDao queryRolesDao;

    @Override
    public List<Roles> findRolePage(Page<Roles> page, Roles roles) {
        return queryRolesDao.findRolePage(page, roles);
    }
}
