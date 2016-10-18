package com.ihaozuo.plamexam.bean;

import com.ihaozuo.plamexam.util.DateUtil;

/**
 * Created by hzguest3 on 2016/10/17.
 */
public class ConsultDetailBean {
    public String AccountId;
    public int DoctorId;
    public int Type;
    public int SourceType;
    public String Content;
    public String AppendInfo;
    private String Date;

    public String getDate(){
        return DateUtil.TimeFormatByWeek(Date, "yyyy-MM-dd HH:mm");
    }
}
