package com.jfeat.am.module.attachments.services.gen.crud.service.impl;
            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.attachments.services.gen.persistence.model.Attachments;
import com.jfeat.am.module.attachments.services.gen.persistence.dao.AttachmentsMapper;
import com.jfeat.am.module.attachments.services.gen.crud.service.CRUDAttachmentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDAttachmentsService
 * @author Code Generator
 * @since 2019-04-08
 */

@Service
public class CRUDAttachmentsServiceImpl  extends CRUDServiceOnlyImpl<Attachments> implements CRUDAttachmentsService {
        @Resource
        protected AttachmentsMapper attachmentsMapper;

        @Override
        protected BaseMapper<Attachments> getMasterMapper() {
                return attachmentsMapper;
        }
}


