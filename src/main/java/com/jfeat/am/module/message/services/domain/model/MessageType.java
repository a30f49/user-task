package com.jfeat.am.module.message.services.domain.model;

public enum  MessageType {
    TASK("TASK","待办事项"),
    TASKFOLLOWER("TASKFOLLOWER","代办事项跟进"),
    SALESOPPORTUNITY("SALESOPPORTUNITY","销售机会再联系");
    private String type;
    private String desc;
    MessageType(String type,String desc){
        this.type=type;
        this.desc=desc;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
