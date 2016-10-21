package com.ihaozuo.plamexam.bean;

import java.io.Serializable;

/**
 * Created by hzguest3 on 2016/10/14.
 */
public class UserBean implements Serializable{

    public int ID;
    public String AccountId;
    public String RealName;
    public String Mobile;
    public String DepartId;
    public String DepartName;
    public String OS;
    public String lastUpdateDate;

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}
