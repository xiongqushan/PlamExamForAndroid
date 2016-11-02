package com.ihaozuo.plamexam.bean;

import java.util.List;

/**
 * Created by zhangzhongyao on 2016/10/18.
 */
public class ReportDetailBean {


    public String CustomerName;
    public String CheckUnitName;
    public String CheckUnitCode;
    public String OrderName;
    public String OrderCode;
    public String Birthday;
    public String RegDate;
    public String ReportDate;
    public String Age;
    public String CommitUserName;
    public String WorkNo;
    public List<String> GroupIds;
    public String ReportDateFormat;

    public List<CheckItemsBean> CheckItems;

    public List<GeneralSummarysBean> GeneralSummarys;

    public List<GeneralAdvicesBean> GeneralAdvices;

    public static class CheckItemsBean {
        public String CheckItemName;
        public String CheckItemCode;
        public String DepartmentName;
        public String SalePrice;
        public String CheckStateID;
        public String CheckUserName;
        public String SummaryFormat;
        public boolean IsAbnormal;

        public List<CheckResultsBean> CheckResults;

        public static class CheckResultsBean {
            public String CheckIndexName;
            public String CheckIndexCode;
            public String ResultValue;
            public String AppendInfo;
            public String IsCalc;
            public String Unit;
            public String TextRef;
            public boolean IsAbandon;
            public boolean IsAbnormalForamt;
            public String ResultTypeID;
            public String ResultFlagID;
            public String LowValueRef;
            public String ValueRefFormat;
            public String HighValueRef;
            public String ShowIndex;
        }
    }

    public static class GeneralSummarysBean {
        public String SummaryName;
        public String SummaryCode;
        public String SummaryDescription;
        public String ReviewAdvice;
        public String IsPrivacy;
        public String SummaryMedicalExplanation;
        public String SummaryReasonResult;
        public String SummaryAdvice;
    }

    public static class GeneralAdvicesBean {
        public String AdviceCode;
        public String AdviceName;
        public String AdviceDescription;
        public String IsPrivacy;
        public String ShowIndex;
        public String GeneralSummarys;
    }
}
