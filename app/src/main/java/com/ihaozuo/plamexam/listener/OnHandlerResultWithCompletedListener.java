package com.ihaozuo.plamexam.listener;

/**
 * Created by zhangzhongyao on 2016/10/17.
 */
public interface OnHandlerResultWithCompletedListener<T> {
    void handlerResult(T resultData);

    void onCompleted();
}
