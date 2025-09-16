package com.jfeat.am.module.taskQueue.services.gen.persistence.model;

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
 * @since 2022-11-17
 */
@ApiModel(value="TaskQueueUserRelation对象", description="")
public class TaskQueueUserRelation extends Model<TaskQueueUserRelation> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "队列id")
      private Long queueId;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public TaskQueueUserRelation setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public TaskQueueUserRelation setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getQueueId() {
        return queueId;
    }

      public TaskQueueUserRelation setQueueId(Long queueId) {
          this.queueId = queueId;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public TaskQueueUserRelation setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public TaskQueueUserRelation setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String QUEUE_ID = "queue_id";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "TaskQueueUserRelation{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", queueId=" + queueId +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
