package com.jfeat.am.module.message.services.gen.crud.service.impl;
            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.message.services.gen.persistence.dao.MessageMapper;
import com.jfeat.am.module.message.services.gen.crud.service.CRUDMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDMessageService
 * @author Code Generator
 * @since 2019-04-15
 */

@Service
public class CRUDMessageServiceImpl  extends CRUDServiceOnlyImpl<Message> implements CRUDMessageService {





        @Resource
        protected MessageMapper messageMapper;

        @Override
        protected BaseMapper<Message> getMasterMapper() {
                return messageMapper;
        }







}


