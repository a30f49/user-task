package com.jfeat.am.module.task.services.gen.persistence.model;

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
@ApiModel(value="TaskUserRelation对象", description="")
public class TaskUserRelation extends Model<TaskUserRelation> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "task id")
      private Long taskId;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public TaskUserRelation setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public TaskUserRelation setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getTaskId() {
        return taskId;
    }

      public TaskUserRelation setTaskId(Long taskId) {
          this.taskId = taskId;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public TaskUserRelation setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public TaskUserRelation setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String TASK_ID = "task_id";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "TaskUserRelation{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", taskId=" + taskId +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
