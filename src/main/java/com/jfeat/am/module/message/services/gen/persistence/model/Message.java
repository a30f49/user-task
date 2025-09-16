package com.jfeat.am.module.message.services.gen.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2019-04-15
 */
public class Message extends Model<Message> {

    @TableField(exist = false)
    private com.alibaba.fastjson.JSONObject extra;

    public com.alibaba.fastjson.JSONObject getExtra() {
        return extra;
    }
    public void setExtra(com.alibaba.fastjson.JSONObject extra) {
        this.extra = extra;
    }


    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 消息标题
     */
	@TableField("`title`")
	private String title;
    /**
     * 消息描述
     */
	@TableField("`desc`")
	private String desc;
    /**
     * 消息类型
     */
	@TableField("message_type")
	private String messageType;
    /**
     * 关联表里的id
     */
	@TableField("reference_id")
	private Long referenceId;
    /**
     * 消息提醒时间
     */
	@TableField("notice_date")
	private Date noticeDate;
    /**
     * 被提醒人
     */
	private Long noticeStaffId;

	private Long noticeEndUserId;

	public Long getNoticeEndUserId() {
		return noticeEndUserId;
	}

	public void setNoticeEndUserId(Long noticeEndUserId) {
		this.noticeEndUserId = noticeEndUserId;
	}

	public Long getId() {
		return id;
	}

	public Message setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Message setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDesc() {
		return desc;
	}

	public Message setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public String getMessageType() {
		return messageType;
	}

	public Message setMessageType(String messageType) {
		this.messageType = messageType;
		return this;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public Message setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public Message setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
		return this;
	}

	public Long getNoticeStaffId() {
		return noticeStaffId;
	}

	public Message setNoticeStaffId(Long noticeStaffId) {
		this.noticeStaffId = noticeStaffId;
		return this;
	}

	public static final String ID = "id";

	public static final String TITLE = "title";

	public static final String DESC = "desc";

	public static final String MESSAGE_TYPE = "message_type";

	public static final String REFERENCE_ID = "reference_id";

	public static final String NOTICE_DATE = "notice_date";

	public static final String NOTICESTAFFID = "noticeStaffId";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Message{" +
			"id=" + id +
			", title=" + title +
			", desc=" + desc +
			", messageType=" + messageType +
			", referenceId=" + referenceId +
			", noticeDate=" + noticeDate +
			", noticeStaffId=" + noticeStaffId +
			"}";
	}
}
