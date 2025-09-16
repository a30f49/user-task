package com.jfeat.am.module.team.services.domain.model;
import com.jfeat.am.module.team.services.persistence.model.Roles;
import com.jfeat.am.module.team.services.persistence.model.Staff;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public class RolesModel extends Roles {
    List<Staff> staffs;

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }
}
