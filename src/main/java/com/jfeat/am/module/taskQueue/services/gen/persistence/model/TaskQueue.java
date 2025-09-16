package com.jfeat.am.module.taskQueue.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code generator
 * @since 2022-11-09
 */
@TableName("task_queue")
@ApiModel(value="TaskQueue对象", description="")
public class TaskQueue extends Model<TaskQueue> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "名称")
      private String name;

      @ApiModelProperty(value = "标题")
      private String title;

      @ApiModelProperty(value = "社区隔离")
      private Long orgId;

      @ApiModelProperty(value = "是否自动发送email")
      private Boolean emailOperation;

      @ApiModelProperty(value = "是否自动发送短信")
      private Boolean smsOperation;

      @ApiModelProperty(value = "是否自动发送公众号")
      private Boolean wechatOperation;

      @ApiModelProperty(value = "父id")
      private Long pid;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public TaskQueue setId(Long id) {
          this.id = id;
          return this;
      }
    
    public String getName() {
        return name;
    }

      public TaskQueue setName(String name) {
          this.name = name;
          return this;
      }
    
    public String getTitle() {
        return title;
    }

      public TaskQueue setTitle(String title) {
          this.title = title;
          return this;
      }
    
    public Long getOrgId() {
        return orgId;
    }

      public TaskQueue setOrgId(Long orgId) {
          this.orgId = orgId;
          return this;
      }

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

    public Long getPid() {
        return pid;
    }

      public TaskQueue setPid(Long pid) {
          this.pid = pid;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public TaskQueue setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public TaskQueue setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String NAME = "name";

      public static final String TITLE = "title";

      public static final String ORG_ID = "org_id";

      public static final String EMAIL_OPERATION = "email_operation";

      public static final String SMS_OPERATION = "sms_operation";

      public static final String WECHAT_OPERATION = "wechat_operation";

      public static final String PID = "pid";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "TaskQueue{" +
              "id=" + id +
                  ", name=" + name +
                  ", title=" + title +
                  ", orgId=" + orgId +
                  ", emailOperation=" + emailOperation +
                  ", smsOperation=" + smsOperation +
                  ", wechatOperation=" + wechatOperation +
                  ", pid=" + pid +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
