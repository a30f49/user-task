package com.jfeat.am.module.attachments.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.attachments.services.domain.dao.QueryAttachmentsDao;
import com.jfeat.am.module.attachments.services.domain.service.AttachmentsService;
import com.jfeat.am.module.attachments.services.gen.crud.service.impl.CRUDAttachmentsServiceImpl;
import com.jfeat.am.module.attachments.services.gen.persistence.dao.AttachmentsMapper;
import com.jfeat.am.module.attachments.services.gen.persistence.model.Attachments;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("attachmentsService")
public class AttachmentsServiceImpl extends CRUDAttachmentsServiceImpl implements AttachmentsService {
    @Resource
    private AttachmentsMapper attachmentsMapper;
    @Resource
    private QueryAttachmentsDao queryAttachmentsDao;

    /*@Override
    public Integer addOrUpdateAttachmentReference(List<Attachments> attachments,String tableName,Long referenceId) {
        int affect = 0;
        if(StringUtils.isEmpty(tableName) || referenceId == null ){
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        }
        //移除附件之前的引用
        queryAttachmentsDao.updateReference(tableName,referenceId);
        //更新
        if(!CollectionUtils.isEmpty(attachments)){
            for(Attachments attachment : attachments){
                attachment.setTableName(tableName);
                attachment.setReferenceId(referenceId);
                affect += attachmentsMapper.update(attachment,new EntityWrapper<Attachments>().eq("url",attachment.getUrl()));
            }
        }
        return affect;
    }*/

    @Override
    public List<Attachments> getAttachments(String tableName, Long referenceId) {
        if(StringUtils.isEmpty(tableName) || null == referenceId || referenceId < 0){
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        }
        return attachmentsMapper.selectList(new QueryWrapper<Attachments>().eq("table_name", tableName).eq("reference_id", referenceId));
    }

    @Override
    public Integer saveAttachments(List<Attachments> attachments, String tableName, Long referenceId) {
        if(CollectionUtils.isEmpty(attachments) || StringUtils.isEmpty(tableName) || null == referenceId || referenceId < 0){
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        }
        //保存前删除
        attachmentsMapper.delete(new QueryWrapper<Attachments>().eq("table_name", tableName).eq("reference_id", referenceId));
        for(Attachments attachment : attachments){
            attachment.setTableName(tableName);
            attachment.setReferenceId(referenceId);
        }
        return this.bulkAppendMasterList(attachments);
    }

    @Override
    public Integer deleteAttachments(String tableName, Long referenceId) {
        if(StringUtils.isEmpty(tableName) || null == referenceId || referenceId < 0){
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        }
        //queryAttachmentsDao.updateReference(tableName,referenceId);
        return attachmentsMapper.delete(new QueryWrapper<Attachments>().eq("table_name", tableName).eq("reference_id", referenceId));
    }

}
