package com.ihaozuo.plamexam.listener;

/**
 * Created by xiongwei1 on 16/4/20.
 */
public interface OnHandlerResultListener<T> {
    void handlerResultSuccess(T resultData);

    void handlerResultError(T resultData);
}
