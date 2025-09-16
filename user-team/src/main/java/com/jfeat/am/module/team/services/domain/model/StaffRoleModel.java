package com.jfeat.am.module.team.services.domain.model;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.am.module.team.services.persistence.model.StaffRole;

/**
 * Created by Code Generator on 2017-11-20
 */
public class StaffRoleModel extends StaffRole {
    Roles roles;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
