package com.jfeat.am.module.task.services.persistence.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-11-27
 */
@TableName("task_follower")
public class TaskFollower extends Model<TaskFollower> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 团队Id
     */
	private Long teamId;
    /**
     * 事件ID
     */
	private Long taskId;


	private Long endUserId;
    /**
     * 员工ID
     */
	private Long userId;
    /**
     * 组织(部门)ID
     */
	@TableField("org_id")
	private Long orgId;
	/**
	 * 版本控制
	 */
	@Version
	private Integer version;

	public Long getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(Long endUserId) {
		this.endUserId = endUserId;
	}

	public Long getId() {
		return id;
	}

	public TaskFollower setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTeamId() {
		return teamId;
	}

	public TaskFollower setTeamId(Long teamId) {
		this.teamId = teamId;
		return this;
	}

	public Long getTaskId() {
		return taskId;
	}

	public TaskFollower setTaskId(Long taskId) {
		this.taskId = taskId;
		return this;
	}

	public Long getUserId() {
		return userId;
	}

	public TaskFollower setUserId(Long staffId) {
		this.userId = staffId;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public TaskFollower setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TaskFollower{" +
			"id=" + id +
			", teamId=" + teamId +
			", taskId=" + taskId +
			", staffId=" + userId +
			", orgId=" + orgId +
			"}";
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
