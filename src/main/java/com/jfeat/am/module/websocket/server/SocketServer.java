package com.jfeat.am.module.websocket.server;

import com.jfeat.am.module.observer.WebsocketObserverInterface;
import com.jfeat.am.module.websocket.setting.MyEndpointConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/api/pub/socketServer/{userId}",configurator= MyEndpointConfigure.class)
@Service
public class SocketServer {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private static Map<Long, Session> sessionPool = new ConcurrentHashMap<Long, Session>();
	private static Map<String,Long> sessionIds = new ConcurrentHashMap<String,Long>();

	/**
	 * ws://192.168.2.101:8080/api/pub/socketServer/1
	 * 用户连接时触发
	 * @param session
	 * @param userId
	 */
	@OnOpen
	public void open(Session session, @PathParam(value="userId")Long userId){
		sessionPool.put(userId, session);
		sessionIds.put(session.getId(),userId);
		//根据接口类型返回相应的所有bean
		Map<String, WebsocketObserverInterface> map =webApplicationContext.getBeansOfType(WebsocketObserverInterface.class);
		for(Map.Entry<String, WebsocketObserverInterface> entry : map.entrySet()){
			entry.getValue().openUpdate(userId);
		}
	}

	/**
	 * 收到信息时触发
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message){
		System.out.println(message);
	}

	/**
	 * 连接关闭触发
	 */
	@OnClose
	public void onClose(Session session){
		Long userId = sessionIds.get(session.getId());
		//根据接口类型返回相应的所有bean
		Map<String, WebsocketObserverInterface> map =webApplicationContext.getBeansOfType(WebsocketObserverInterface.class);
		for(Map.Entry<String, WebsocketObserverInterface> entry : map.entrySet()){
			entry.getValue().onCloseUpdate(userId);
		}
		sessionPool.remove(userId);
		sessionIds.remove(session.getId());
	}

	/**
	 * 发生错误时触发
	 * @param session
	 * @param error
	 */
    @OnError
    public void onError(Session session, Throwable error) {
		Long userId = sessionIds.get(session.getId());
		//根据接口类型返回相应的所有bean
		Map<String, WebsocketObserverInterface> map =webApplicationContext.getBeansOfType(WebsocketObserverInterface.class);
		for(Map.Entry<String, WebsocketObserverInterface> entry : map.entrySet()){
			entry.getValue().onErrorUpdate(userId);
		}
		sessionPool.remove(userId);
		sessionIds.remove(session.getId());

    	error.printStackTrace();
    }

	/**
	 *发送消息的方法
	 * @param message
	 * @param userId
	 */
	public static void sendMessage(String message,Long userId){
		Session s = sessionPool.get(userId);
		if(s!=null){
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
