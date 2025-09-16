package com.jfeat.am.module.message.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.message.services.domain.dao.QueryMessageDao;
import com.jfeat.am.module.message.services.domain.model.MessageRecord;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.crud.service.impl.CRUDMessageServiceImpl;
import com.jfeat.am.module.message.services.gen.persistence.dao.MessageMapper;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.websocket.server.SocketServer;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("messageService")
public class MessageServiceImpl extends CRUDMessageServiceImpl implements MessageService {
    private static Map<Long, TimerTask> taskMap = new ConcurrentHashMap<Long, TimerTask>();
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private QueryMessageDao queryMessageDao;

    @Override
    /**
     * 第一次连接时提醒并设置定时任务
     */
    public void openUpdate(Long userId) {
//        Long staffId = this.getStaffIdByUserId(userId);
        if(null == userId){
            throw new BusinessException(BusinessCode.UserNotExisted);
        }
        //查询小于等于当前时间的消息 发送
        List<MessageRecord> oldMessage = queryMessageDao.findNewMessage(userId, 0);
        SocketServer.sendMessage(JSONObject.toJSONString(oldMessage), userId);
        //查询大于当前时间的消息 设置定时任务
        List<MessageRecord> newMessage = queryMessageDao.findNewMessage(userId, 1);
        Date currentDate = new Date();
        for (MessageRecord message : newMessage) {
            List<MessageRecord> messageRecords = new ArrayList<MessageRecord>();
            messageRecords.add(message);
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            TimerTask timerTask = new TimerTask(){
                @Override
                public void run() {
                    SocketServer.sendMessage(JSONObject.toJSONString(messageRecords), userId);
                }
            };
            taskMap.put(message.getId(),timerTask);
            executorService.schedule(timerTask, message.getNoticeDate().getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    /**
     * 连接关闭时取消定时任务
     */
    public void onCloseUpdate(Long userId) {
        this.remove(userId);
    }

    @Override
    /**
     *  发生错误时取消定时任务
     */
    public void onErrorUpdate(Long userId) {
        this.remove(userId);
    }

    @Override
    public Integer createMessage(Message message) {
        Integer affect = messageMapper.insert(message);
        this.updateBycreate(message.getNoticeEndUserId(),message);
        return affect;
    }

    @Override
    public Integer updateMessage(Message message) {
        //message_type reference_id notice_date notice_staff_id
        Integer affect =0;
//        new Message().setMessageType(message.getMessageType())
//                .setReferenceId(message.getReferenceId())
//                .setNoticeStaffId(message.getNoticeStaffId());
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("message_type",message.getMessageType());
        messageQueryWrapper.eq("reference_id",message.getReferenceId());
        messageQueryWrapper.eq("notice_end_user_id",message.getNoticeEndUserId());
        Message oldMessage = messageMapper.selectOne(messageQueryWrapper);
        if(oldMessage == null){
            affect += this.createMaster(message);
        }else{
            if(message.getNoticeDate()!=null){
                oldMessage.setNoticeDate(message.getNoticeDate());
            }
            affect += this.updateMaster(oldMessage);
            this.updateByUpdate(oldMessage.getNoticeEndUserId(),oldMessage);
        }
        return affect;
    }

    @Override
    public Integer deleteMessage(Message message) {
        //message_type reference_id noticeStaffId
        Long userId = message.getNoticeEndUserId();
        Integer affect = messageMapper.delete(new QueryWrapper<Message>().eq("message_type", message.getMessageType()).eq("reference_id", message.getReferenceId()).eq("notice_end_user_id",message.getNoticeEndUserId()));
        this.remove(userId);
        return affect;
    }

    /**
     * 更新时修改定时任务
     * @param userId
     * @param message
     */
    public void updateByUpdate(Long userId, Message message){
        //移除之前的task
        if(taskMap.containsKey(message.getId())){
            taskMap.get(message.getId()).cancel();
            taskMap.remove(message.getId());
        }
        //新增task
        updateBycreate(userId,message);
    }

    /**
     * 新建时触发提醒
     *
     * @param userId
     * @param message
     */
    public void updateBycreate(Long userId, Message message) {
//        Long userId = this.getUserIdByStaffId(staffId);
        if(null == userId){
            throw new BusinessException(BusinessCode.UserNotExisted);
        }
        Date currentDate = new Date();
        if(null != message.getNoticeDate() && message.getNoticeDate().compareTo(currentDate)!=-1){
            List<Message> messageList = new ArrayList<Message>();
            messageList.add(message);
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            TimerTask timerTask = new TimerTask(){
                @Override
                public void run() {
                    SocketServer.sendMessage(JSONObject.toJSONString(messageList), userId);
                }
            };
            taskMap.put(message.getId(),timerTask);
            executorService.schedule(timerTask, message.getNoticeDate().getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);
        }
    }



    /**
     * 取消timer任务
     *
     * @param userId
     */
    public void remove(Long userId) {
      //  Long staffId = this.getStaffIdByUserId(userId);
        if(null == userId){
            throw new BusinessException(BusinessCode.UserNotExisted);
        }
        //查询该员工下有哪些未提醒的message，取消
        List<Message> noticeMessageList = messageMapper.selectList(new QueryWrapper<Message>().eq("notice_end_user_id", userId));
        for(Message message: noticeMessageList){
            if (taskMap.containsKey(message.getId())) {
                taskMap.get(message.getId()).cancel();
                taskMap.remove(message.getId());
            }
        }
    }

//    @Override
//    public Long getStaffIdByUserId(Long userId) {
//
//        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
//        staffQueryWrapper.eq("user_id",userId);
//        return staffMapper.selectOne(staffQueryWrapper).getId();
//    }
//    @Override
//    public Long getUserIdByStaffId(Long staffId) {
//        return staffMapper.selectById(staffId).getUserId();
//    }
}
