package com.jfeat.am.module.team.services.domain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffRoleDao;
import com.jfeat.am.module.team.services.domain.service.QueryStaffRoleService;
import com.jfeat.am.module.team.services.persistence.model.StaffRole;
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
public class QueryStaffRoleServiceImpl implements QueryStaffRoleService {

    @Resource
    QueryStaffRoleDao queryStaffRoleDao;

    @Override
    public List<StaffRole> findStaffRolePage(Page<StaffRole> page, StaffRole staffRole) {
        return queryStaffRoleDao.findStaffrolePage(page, staffRole);
    }
}
