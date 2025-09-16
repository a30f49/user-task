package com.jfeat.am.module.taskQueue.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueueUserRelation;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueUserRelationMapper;
import com.jfeat.am.module.taskQueue.services.gen.crud.service.CRUDTaskQueueUserRelationService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDTaskQueueUserRelationService
 * @author Code generator
 * @since 2022-11-17
 */

@Service
public class CRUDTaskQueueUserRelationServiceImpl  extends CRUDServiceOnlyImpl<TaskQueueUserRelation> implements CRUDTaskQueueUserRelationService {





        @Resource
        protected TaskQueueUserRelationMapper taskQueueUserRelationMapper;

        @Override
        protected BaseMapper<TaskQueueUserRelation> getMasterMapper() {
                return taskQueueUserRelationMapper;
        }







}


