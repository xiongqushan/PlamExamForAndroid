package com.ihaozuo.plamexam.bean;

import com.ihaozuo.plamexam.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hzguest3 on 2016/10/17.
 */
public class ConsultDetailBean implements Serializable {
    public String AccountId;
    public String DoctorId;
    public int Type;
    public int SourceType;
    public String Content;
    public String AppendInfo;
    private String Date;

    public String getDate(){
        return DateUtil.TimeFormatByWeek(Date,"yyyy-MM-dd HH:mm:ss");
    }

    public void setDate(){
        this.Date =DateUtil.date2Str(new Date(), "yyyy-MM-dd'T'HH:mm:ss");;

    }
}
