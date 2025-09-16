package com.jfeat.am.module.taskQueue.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueMapper;
import com.jfeat.am.module.taskQueue.services.gen.crud.service.CRUDTaskQueueService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDTaskQueueService
 * @author Code generator
 * @since 2022-11-09
 */

@Service
public class CRUDTaskQueueServiceImpl  extends CRUDServiceOnlyImpl<TaskQueue> implements CRUDTaskQueueService {





        @Resource
        protected TaskQueueMapper taskQueueMapper;

        @Override
        protected BaseMapper<TaskQueue> getMasterMapper() {
                return taskQueueMapper;
        }







}


