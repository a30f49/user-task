package com.jfeat.am.module.team.services.patch;

import com.jfeat.am.module.team.services.domain.model.StaffRoleModel;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */
@Deprecated
public interface RolePatchService {
    //判断某个Staff是否在某个Roles
    void isStaffInRole(long roleId, long staffId);

    // 判断某个Roles中是否有 Staff  如果存在 Staff 无法执行删除操作
    void judgeWhetherStaffInRoles(long id);
    //    批量添加Staff到某个ROLE
    Integer batchAddStaffToRole(long roleId, List<Long> ids);

    //    批量删除某个ROLE下的Staff
    Integer batchDeleteStaffToRole(long roleId, List<Long> ids);

    // 我的所有角色
    List<StaffRoleModel> allMyRoles(long staffId);
}
