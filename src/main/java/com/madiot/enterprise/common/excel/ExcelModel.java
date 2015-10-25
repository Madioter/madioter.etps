package com.madiot.enterprise.common.excel;


import com.madiot.enterprise.common.exception.ErrorMessage;

import java.util.Map;

/**
 * Created by yi.wang1 on 2014/12/17.
 */
public abstract class ExcelModel {
    public abstract void setValues(Map<Integer, String> values);

    //异常信息
    private ErrorMessage errorMessage;

    //是否进行过验证
    private boolean isValidate = false;

    public abstract String[] getTitles();

    //获取错误信息
    public String getErrorMessage() {
        if(!this.isValidate){
            this.validate();
        }
        return errorMessage.toString();
    }

    //判断当前对象是否存在错误
    public boolean haveError(){
        if(!this.isValidate){
            this.validate();
        }
        return errorMessage.haveError();
    }

    //当前对象自验证
    public void validate() {
        errorMessage = new ErrorMessage();
        errorMessage.validate(this);
        this.isValidate = true;
    }
}
