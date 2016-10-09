package com.ihaozuo.plamexam.bean;

/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class RestResult<T> {
    public boolean RequestSuccess;

    public boolean LogicSuccess;

    public String Message;

    public T Data;

    public RequestErrorEnum RequestErrorType;

    public String OriginErrorMessage;

    //http请求成功,逻辑处理成功
    public RestResult(T data){
        RequestSuccess=true;
        LogicSuccess=true;
        Data=data;
    }

    //http请求成功,逻辑处理失败
    public RestResult(String logicErrorMessage) {
        Message = logicErrorMessage;
        RequestSuccess = true;
    }

    //http请求失败或者处理过程exception
    public RestResult(RequestErrorEnum errorType, String originErrorMessage){
        RequestErrorType=errorType;
        OriginErrorMessage=originErrorMessage;
        Message="系统异常！";
        if(errorType==RequestErrorEnum.HttpResponseError || errorType==RequestErrorEnum.HttpException){
            Message="网络请求不稳定，请稍后重试！";
        }
    }

}
