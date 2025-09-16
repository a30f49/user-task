package com.jfeat.am.module.attachments.services.domain.service;

import com.jfeat.am.module.attachments.services.gen.crud.service.CRUDAttachmentsService;
import com.jfeat.am.module.attachments.services.gen.persistence.model.Attachments;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface AttachmentsService extends CRUDAttachmentsService {

    /**
     * 添加或者更新附件引用
     * @param attachments
     * @param tableName
     * @param referenceId
     * @return
     */
    //Integer addOrUpdateAttachmentReference(List<Attachments> attachments,String tableName,Long referenceId);

    /**
     * 获取对应的附件
     * @param tableName
     * @param referenceId
     * @return
     */
    List<Attachments> getAttachments(String tableName,Long referenceId);

    /**
     * 保存附件,保存之前清除
     * @param attachments
     * @param tableName
     * @param referenceId
     * @return
     */
    Integer saveAttachments(List<Attachments> attachments,String tableName,Long referenceId);

    /**
     * 删除附件
     * @param tableName
     * @param referenceId
     * @return
     */
    Integer deleteAttachments(String tableName,Long referenceId);
}