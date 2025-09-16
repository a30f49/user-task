package com.jfeat.am.module.task.services.persistence.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-11-27
 */
@TableName("task")
public class WorkTask extends Model<WorkTask> {
	public static final String tableName = "task";
    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 事件类型
     */
	private String taskType;
    /**
     * 创建者ID
     */
	private Long createByStaffId;
    /**
     * 事件编号
     */
	private String taskNumber;
    /**
     * ownerId
     */
	private Long ownerByStaffId;
    /**
     * ownByTeamId
     */
	private Long ownerByTeamId;
    /**
     * 事件名称
     */
	private String taskName;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 关闭时间
     */
	private Date closeTime;
    /**
     * 状态
     */
	private String status;
    /**
     * 优先级 0/1/2 低 中 高
     */
	private Integer priority;
    /**
     * 描述
     */
	@TableField("`desc`")
	private String desc;
    /**
     * 开始时间
     */
	private Date startTime;
    /**
     * 提醒时间
     */
	private Date noticeTime;
    /**
     * 到期时间
     */
	private Date deadline;
    /**
     * 客服记录
     */
	private Long communicationRecordId;
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

	private Long ownerByEndUserId;

	private Long createByEndUserId;


	private Long queueId;


	@ApiModelProperty(value = "是否发送email")
	private Boolean emailOperation;

	@ApiModelProperty(value = "是否发送短信")
	private Boolean smsOperation;

	@ApiModelProperty(value = "是否发送公众号")
	private Boolean wechatOperation;



	public Boolean getEmailOperation() {
		return emailOperation;
	}

	public void setEmailOperation(Boolean emailOperation) {
		this.emailOperation = emailOperation;
	}

	public Boolean getSmsOperation() {
		return smsOperation;
	}

	public void setSmsOperation(Boolean smsOperation) {
		this.smsOperation = smsOperation;
	}

	public Boolean getWechatOperation() {
		return wechatOperation;
	}

	public void setWechatOperation(Boolean wechatOperation) {
		this.wechatOperation = wechatOperation;
	}

	public Long getQueueId() {
		return queueId;
	}

	public void setQueueId(Long queueId) {
		this.queueId = queueId;
	}

	public Long getOwnerByEndUserId() {
		return ownerByEndUserId;
	}

	public void setOwnerByEndUserId(Long ownerByEndUserId) {
		this.ownerByEndUserId = ownerByEndUserId;
	}

	public Long getCreateByEndUserId() {
		return createByEndUserId;
	}

	public void setCreateByEndUserId(Long createByEndUserId) {
		this.createByEndUserId = createByEndUserId;
	}

	public Long getId() {
		return id;
	}

	public WorkTask setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTaskType() {
		return taskType;
	}

	public WorkTask setTaskType(String taskType) {
		this.taskType = taskType;
		return this;
	}

	public Long getCreateByStaffId() {
		return createByStaffId;
	}

	public WorkTask setCreateByStaffId(Long createByStaffId) {
		this.createByStaffId = createByStaffId;
		return this;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public WorkTask setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
		return this;
	}

	public Long getOwnerByStaffId() {
		return ownerByStaffId;
	}

	public WorkTask setOwnerByStaffId(Long ownerByStaffId) {
		this.ownerByStaffId = ownerByStaffId;
		return this;
	}

	public Long getOwnerByTeamId() {
		return ownerByTeamId;
	}

	public WorkTask setOwnerByTeamId(Long ownerByTeamId) {
		this.ownerByTeamId = ownerByTeamId;
		return this;
	}

	public String getTaskName() {
		return taskName;
	}

	public WorkTask setTaskName(String taskName) {
		this.taskName = taskName;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public WorkTask setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public WorkTask setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public WorkTask setStatus(String status) {
		this.status = status;
		return this;
	}

	public Integer getPriority() {
		return priority;
	}

	public WorkTask setPriority(Integer priority) {
		this.priority = priority;
		return this;
	}

	public String getDesc() {
		return desc;
	}

	public WorkTask setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public Date getStartTime() {
		return startTime;
	}

	public WorkTask setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public WorkTask setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
		return this;
	}

	public Date getDeadline() {
		return deadline;
	}

	public WorkTask setDeadline(Date deadline) {
		this.deadline = deadline;
		return this;
	}

	public Long getCommunicationRecordId() {
		return communicationRecordId;
	}

	public WorkTask setCommunicationRecordId(Long communicationRecordId) {
		this.communicationRecordId = communicationRecordId;
		return this;
	}


	public Long getOrgId() {
		return orgId;
	}

	public WorkTask setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String TASKTYPE = "taskType";

	public static final String CREATEBYSTAFFID = "createByStaffId";

	public static final String TASKNUMBER = "taskNumber";

	public static final String OWNERBYSTAFFID = "ownerByStaffId";

	public static final String OWNERBYTEAMID = "ownerByTeamId";

	public static final String TASKNAME = "taskName";

	public static final String CREATETIME = "createTime";

	public static final String CLOSETIME = "closeTime";

	public static final String STATUS = "status";

	public static final String PRIORITY = "priority";

	public static final String DESC = "desc";

	public static final String STARTTIME = "startTime";

	public static final String NOTICETIME = "noticeTime";

	public static final String DEADLINE = "deadline";

	public static final String COMMUNICATIONRECORDID = "communicationRecordId";

	public static final String ATTACHMENT = "attachment";

	public static final String ORG_ID = "org_id";

	public static final String QUEUE_ID = "queue_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Task{" +
			"id=" + id +
			", taskType=" + taskType +
			", createByStaffId=" + createByStaffId +
			", taskNumber=" + taskNumber +
			", ownerByStaffId=" + ownerByStaffId +
			", ownerByTeamId=" + ownerByTeamId +
			", taskName=" + taskName +
			", createTime=" + createTime +
			", closeTime=" + closeTime +
			", status=" + status +
			", priority=" + priority +
			", desc=" + desc +
			", startTime=" + startTime +
			", noticeTime=" + noticeTime +
			", deadline=" + deadline +
			", communicationRecordId=" + communicationRecordId +
			", orgId=" + orgId +
			"}";
	}

	public Integer getVersion() {
		return version;
	}

	public WorkTask setVersion(Integer version) {
		this.version = version;
		return this;
	}
}
