package com.jfeat.am.module.team.services.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Roles extends Model<Roles> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 角色名称
     */
	private String roleName;
    /**
     * 角色描述
     */
	private String roleDesc;
    /**
     * 父级ID
     */
	private Long pid;
    /**
     * 组织(部门)ID
     */
	@TableField("org_id")
	private Long orgId;


	public Long getId() {
		return id;
	}

	public Roles setId(Long id) {
		this.id = id;
		return this;
	}

	public String getRoleName() {
		return roleName;
	}

	public Roles setRoleName(String roleName) {
		this.roleName = roleName;
		return this;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public Roles setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
		return this;
	}

	public Long getPid() {
		return pid;
	}

	public Roles setPid(Long pid) {
		this.pid = pid;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Roles setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String ROLENAME = "roleName";

	public static final String ROLEDESC = "roleDesc";

	public static final String PID = "pid";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Roles{" +
			"id=" + id +
			", roleName=" + roleName +
			", roleDesc=" + roleDesc +
			", pid=" + pid +
			", orgId=" + orgId +
			"}";
	}
}
