package com.jfeat.am.module.team.services.crud.service;


import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.crud.plus.CRUDServiceGroup;


/**
 * <p>
 * 角色表 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */

public interface RolesService extends CRUDServiceGroup<Roles> {
    // 该角色下所有员工的信息
    //RolesModel roleIncludeStaff(long id);

    //重复的时候   返回重复错误
    void  saveOrUpdateEntity(Roles entity);
}
