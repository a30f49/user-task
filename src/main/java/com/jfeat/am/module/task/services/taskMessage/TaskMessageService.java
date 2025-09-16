package com.jfeat.am.module.task.services.taskMessage;

import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;

public interface TaskMessageService {

    void sendTaskMessageToKafkaEmail(WorkTaskModel entity);

    void queueControlTaskMessage(WorkTaskModel entity,boolean isKafka);

    void sendTaskMessageToEmail(WorkTaskModel entity);

    void createTaskMessageToEmail(WorkTaskModel entity, UserAccount user,boolean owner,boolean isKafka);

    void createQueueControlTaskMessageToEmail(WorkTaskModel entity, UserAccount user,boolean owner,boolean isKafka);
}
