package com.jfeat.am.module.observer;

/**
 * websocket观察者接口
 *
 */
public interface WebsocketObserverInterface {
    /**
     * 用户连接时触发
     * @param userId
     */
    void openUpdate(Long userId);

    /**
     * 连接关闭触发
     * @param userId
     */
    void onCloseUpdate(Long userId);

    /**
     * 发生错误时触发
     * @param userId
     */
    void onErrorUpdate(Long userId);
}
