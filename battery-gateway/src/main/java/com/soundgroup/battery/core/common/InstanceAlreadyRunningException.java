package com.soundgroup.battery.core.common;

public class InstanceAlreadyRunningException extends Exception {

    public InstanceAlreadyRunningException(String msg){
        super(msg);
    }

    public InstanceAlreadyRunningException(String msg, Throwable t){
        super(msg,t);
    }
}
