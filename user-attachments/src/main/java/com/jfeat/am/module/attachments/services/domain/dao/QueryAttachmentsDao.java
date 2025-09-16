package com.jfeat.am.module.attachments.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.attachments.services.gen.persistence.model.Attachments;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Code Generator on 2019-04-08
 */
public interface QueryAttachmentsDao extends BaseMapper<Attachments> {
    //List<AttachmentsRecord> findAttachmentsPage(Page<AttachmentsRecord> page, @Param("record") AttachmentsRecord record, @Param("search") String search, @Param("orderBy") String orderBy);

    @Update("UPDATE attachments SET attachments.table_name=NULL,attachments.reference_id=NULL WHERE attachments.table_name=#{tableName} AND attachments.reference_id=#{referenceId};")
    Integer updateReference(@Param("tableName") String tableName,@Param("referenceId") Long referenceId);
}