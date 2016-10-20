package com.ihaozuo.plamexam.bean;

import com.ihaozuo.plamexam.util.DateUtil;

import java.util.Date;

/**
 * Created by hzguest3 on 2016/10/17.
 */
public class ConsultDetailBean {
    public String AccountId;
    public String DoctorId;
    public int Type;
    public int SourceType;
    public String Content;
    public String AppendInfo;
    private String Date;

    public String getDate(){
        return DateUtil.TimeFormatByWeek(Date, "yyyy-MM-dd HH:mm");
    }

    public void setDate(Date date){
        this.Date = DateUtil.TimeFormatByWeek(date+"", "yyyy-MM-dd HH:mm");
    }
}
