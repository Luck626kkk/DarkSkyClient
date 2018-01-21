package com.example.luck.darkskyclient.event;

/**
 * Created by Luck on 2018/1/8.
 */

public class ErrorEvent {
    private final String errorMessage;

    public ErrorEvent(String errorMessage) {
        this.errorMessage=errorMessage;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
