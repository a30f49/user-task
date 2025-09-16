package com.jfeat.am.module.team.api.patch;

import com.jfeat.am.module.team.services.persistence.model.Staff;

/**
 * Created by vincenthuang on 05/03/2018.
 */
public class StaffNameModel extends Staff {

    private String staffName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }


}
