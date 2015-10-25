package com.madiot.enterprise.model;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class ResponseVo {

    private boolean success = false;

    private String message;

    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
