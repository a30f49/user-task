package com.jfeat.am.module.message.services.domain.dao;

import com.jfeat.am.module.message.services.domain.model.MessageRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import java.util.List;

/**
 * Created by Code Generator on 2019-04-15
 */
public interface QueryMessageDao extends BaseMapper<Message> {
    List<MessageRecord> findMessagePage(Page<MessageRecord> page, @Param("record") MessageRecord record, @Param("search") String search, @Param("orderBy") String orderBy);

    /**
     * 获取被提醒人大于或小于当前时间的消息
     * @param isGreat
     * @return
     */
    List<MessageRecord> findNewMessage(@Param("userId")Long userId,@Param("isGreat")int isGreat);

    /**
     * 获得被提醒人的所有消息 分页
     * @param page
     * @param userId
     * @return
     */
    List<MessageRecord> getMessagePage(Page<MessageRecord> page,@Param("userId") Long userId);
}