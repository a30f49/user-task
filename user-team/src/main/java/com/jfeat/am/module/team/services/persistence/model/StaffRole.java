package com.jfeat.am.module.team.services.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;


import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-11-26
 */
@TableName("staff_role")
public class StaffRole extends Model<StaffRole> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 角色ID
     */
	private Long roleId;
    /**
     * 员工ID
     */
	private Long staffId;
    /**
     * 组织(部门)ID
     */
	@TableField("org_id")
	private Long orgId;


	public Long getId() {
		return id;
	}

	public StaffRole setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getRoleId() {
		return roleId;
	}

	public StaffRole setRoleId(Long roleId) {
		this.roleId = roleId;
		return this;
	}

	public Long getStaffId() {
		return staffId;
	}

	public StaffRole setStaffId(Long staffId) {
		this.staffId = staffId;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public StaffRole setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String ROLEID = "roleId";

	public static final String STAFFID = "staffId";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "StaffRole{" +
			"id=" + id +
			", roleId=" + roleId +
			", staffId=" + staffId +
			", orgId=" + orgId +
			"}";
	}
}
