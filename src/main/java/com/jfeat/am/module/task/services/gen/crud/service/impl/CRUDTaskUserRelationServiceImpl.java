package com.jfeat.am.module.task.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.task.services.gen.persistence.model.TaskUserRelation;
import com.jfeat.am.module.task.services.gen.persistence.dao.TaskUserRelationMapper;
import com.jfeat.am.module.task.services.gen.crud.service.CRUDTaskUserRelationService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDTaskUserRelationService
 * @author Code generator
 * @since 2022-11-17
 */

@Service
public class CRUDTaskUserRelationServiceImpl  extends CRUDServiceOnlyImpl<TaskUserRelation> implements CRUDTaskUserRelationService {





        @Resource
        protected TaskUserRelationMapper taskUserRelationMapper;

        @Override
        protected BaseMapper<TaskUserRelation> getMasterMapper() {
                return taskUserRelationMapper;
        }







}


