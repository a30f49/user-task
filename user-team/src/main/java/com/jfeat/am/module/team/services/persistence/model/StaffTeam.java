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
@TableName("staff_team")
public class StaffTeam extends Model<StaffTeam> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 团队Id
     */
	private Long teamId;
    /**
     * 员工ID
     */
	private Long staffId;
    /**
     * 领导者(0-N 1-Y)
     */
	private Integer isLeader;
    /**
     * 组织(部门)ID
     */
	@TableField("org_id")
	private Long orgId;


	public Long getId() {
		return id;
	}

	public StaffTeam setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTeamId() {
		return teamId;
	}

	public StaffTeam setTeamId(Long teamId) {
		this.teamId = teamId;
		return this;
	}

	public Long getStaffId() {
		return staffId;
	}

	public StaffTeam setStaffId(Long staffId) {
		this.staffId = staffId;
		return this;
	}

	public Integer getIsLeader() {
		return isLeader;
	}

	public StaffTeam setIsLeader(Integer isLeader) {
		this.isLeader = isLeader;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public StaffTeam setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String TEAMID = "teamId";

	public static final String STAFFID = "staffId";

	public static final String ISLEADER = "isLeader";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "StaffTeam{" +
			"id=" + id +
			", teamId=" + teamId +
			", staffId=" + staffId +
			", isLeader=" + isLeader +
			", orgId=" + orgId +
			"}";
	}
}
