package com.ihaozuo.plamexam.bean;

import java.util.List;

/**
 * Created by zhangzhongyao on 2016/10/20.
 */
public class ReportAddBean {

    /**
     * CheckUnitCode : sample string 1
     * DepartName : sample string 2
     * Reports : [{"CheckUnitCode":"sample string 1","WorkNo":"sample string 2","CustomerName":"sample string 3","ReportName":"sample string 4","ReportDate":"sample string 5"},{"CheckUnitCode":"sample string 1","WorkNo":"sample string 2","CustomerName":"sample string 3","ReportName":"sample string 4","ReportDate":"sample string 5"},{"CheckUnitCode":"sample string 1","WorkNo":"sample string 2","CustomerName":"sample string 3","ReportName":"sample string 4","ReportDate":"sample string 5"}]
     */

    public String CheckUnitCode;
    public String DepartName;
    public List<ReportItemBean> Reports;

}
