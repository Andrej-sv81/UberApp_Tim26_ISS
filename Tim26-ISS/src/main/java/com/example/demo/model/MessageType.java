package com.example.demo.model;

public enum MessageType {
    SUPPORT,RIDE,PANIC,ERROR;

    public static MessageType getType(String type){
        if(type.equalsIgnoreCase("SUPPORT")){
            return  MessageType.SUPPORT;
        } else if (type.equalsIgnoreCase("RIDE")) {
            return MessageType.RIDE;
        }else if(type.equalsIgnoreCase("PANIC")){
            return MessageType.PANIC;
        }else{
            return MessageType.ERROR;
        }
    }
}
