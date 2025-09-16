package com.jfeat.am.module.message.services.domain.service;

import com.jfeat.am.module.message.services.gen.crud.service.CRUDMessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.observer.WebsocketObserverInterface;

/**
 * Created by vincent on 2017/10/19.
 */
public interface MessageService extends CRUDMessageService, WebsocketObserverInterface {

    /**
     * 新增message
     * @param message
     * @return
     */
    Integer createMessage(Message message);

    /**
     * 更新message
     * @param message
     * @return
     */
    Integer updateMessage(Message message);

    /**
     * 删除message
     * @param message
     * @return
     */
    Integer deleteMessage(Message message);

//    Long getStaffIdByUserId(Long userId);

//    Long getUserIdByStaffId(Long staffId);
}