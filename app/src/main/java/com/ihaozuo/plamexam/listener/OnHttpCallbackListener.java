package com.ihaozuo.plamexam.listener;


import com.ihaozuo.plamexam.bean.RequestErrorEnum;

/**
 * Created by xiongwei1 on 16/4/20.
 */
public interface OnHttpCallbackListener<T> {
    void onSuccess(T resultData);
    void onError(RequestErrorEnum errorType, String msg);
}
