package com.jfeat.am.module.taskQueue.services.domain.service.impl;

import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.dao.WorkTaskMapper;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.task.services.taskMessage.TaskMessageService;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueMassageService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueMapper;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;
import com.jfeat.crud.plus.CRUD;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskQueueMassageServiceImpl implements TaskQueueMassageService {

    @Resource
    TaskQueueMapper taskQueueMapper;

    @Resource
    TaskMessageService taskMessageService;

    @Resource
    WorkTaskMapper workTaskMapper;

    @Override
    public Integer taskQueueMessageControl(WorkTaskModel task,boolean isKafka) {
        Integer affect  = 0;
        if (task==null || task.getQueueId()==null){
            return affect;
        }
        TaskQueue taskQueue = taskQueueMapper.selectById(task.getQueueId());
        if (taskQueue==null){
            return affect;
        }

//        发送email
        if (taskQueue.getEmailOperation()){
            if (isKafka){
                taskMessageService.sendTaskMessageToKafkaEmail(task);
            }else {
                taskMessageService.sendTaskMessageToEmail(task);
            }

            task.setEmailOperation(true);
        }

//        发送短信
        if (taskQueue.getSmsOperation()){
            if (isKafka){

            }else {

            }
            task.setEmailOperation(true);
        }

//        发送公众号
        if (taskQueue.getSmsOperation()){
            if (isKafka){

            }else {

            }
            task.setEmailOperation(true);
        }

        workTaskMapper.updateById(CRUD.castObject(task, WorkTask.class));

        return affect;
    }
}
