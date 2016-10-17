package com.ihaozuo.plamexam.bean;

/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class BaseBean<T> {
    public int Code;
    public String Message;
    public T Data;

    public BaseBean(T a) {
        Data = a;
        Code = 1;
    }

    public BaseBean() {
    }
}
