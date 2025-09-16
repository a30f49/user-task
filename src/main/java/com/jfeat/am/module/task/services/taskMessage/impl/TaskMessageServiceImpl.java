package com.jfeat.am.module.task.services.taskMessage.impl;

import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.taskMessage.TaskMessageService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.dao.TaskQueueMapper;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.module.kafka.services.domain.model.KafkaEmailProperties;
import com.jfeat.module.kafka.services.domain.service.KafkaEmailProductService;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskMessageServiceImpl implements TaskMessageService {

    @Resource
    WorkTaskService workTaskService;

    @Resource
    KafkaEmailProductService kafkaEmailProductService;

    // will not use kafka to send email, will do n8n
    // @Resource
    // KafkaSendEmailServices kafkaSendEmailServices;

    @Resource
    TaskQueueMapper taskQueueMapper;


    @Override
    public void sendTaskMessageToKafkaEmail(WorkTaskModel entity) {
        KafkaEmailProperties kafkaEmailProperties = new KafkaEmailProperties();
        kafkaEmailProperties.setContent(entity.getDesc());
        kafkaEmailProperties.setSubject(entity.getTaskName());
        kafkaEmailProperties.setToAddress(entity.getToEmailAddress());
        kafkaEmailProperties.setToAddressList(entity.getToEmailAddressList());
        kafkaEmailProductService.sendTextEmail(kafkaEmailProperties);
    }

    @Override
    public void queueControlTaskMessage(WorkTaskModel entity, boolean isKafka) {
        if (entity.getQueueId() == null || entity.getQueueId() <= 0) {
            return;
        }
        TaskQueue taskQueue = taskQueueMapper.selectById(entity.getQueueId());
        if (taskQueue.getEmailOperation()) {

            if (isKafka) {
                sendTaskMessageToKafkaEmail(entity);
            } else {
                sendTaskMessageToEmail(entity);
            }

        }
    }


    @Override
    public void sendTaskMessageToEmail(WorkTaskModel entity) {
        throw new BusinessException(BusinessCode.NotImplement, "未实现发送邮件，将由n8n代替.");
        // KafkaMailSenderProperties mailSenderInfo = new KafkaMailSenderProperties();
        // String subject = entity.getTaskName();
        // String content = entity.getDesc();
        // String toAddress = entity.getToEmailAddress();
        // List<String> toAddressList = entity.getToEmailAddressList();
        // mailSenderInfo.setTos(toAddressList);
        // mailSenderInfo.setContent(content);
        // mailSenderInfo.setToAddress(toAddress);
        // mailSenderInfo.setSubject(subject);
        // kafkaSendEmailServices.sendTextEmail(mailSenderInfo);
    }


    @Override
    public void createTaskMessageToEmail(WorkTaskModel entity, UserAccount user, boolean owner, boolean isKafka) {
        Long taskId = workTaskService.createMasterByStaff(entity, user, owner);
        if (taskId != null) {
            if (isKafka) {
                sendTaskMessageToKafkaEmail(entity);
            } else {
                sendTaskMessageToEmail(entity);
            }
        }
    }

    @Override
    public void createQueueControlTaskMessageToEmail(WorkTaskModel entity, UserAccount user, boolean owner, boolean isKafka) {
        Long taskId = workTaskService.createMasterByStaff(entity, user, owner);
        if (taskId != null) {
            queueControlTaskMessage(entity, isKafka);
        }
    }
}
