package com.ihaozuo.plamexam.framework;

/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class SysConfig {
    public static final String LOCAL_OPERATION_SYSTEM = "ANDROID";
    public static final String CURRENT_BASE_VERSION = "V3";
//    public static final String[] BASE_API = new String[]{"http://hc.ihaozhuo.com:90", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//pro
//    public static final String[] BASE_API = new String[]{"http://hz3bn04d2/ZSTJ/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//gaotang debug
//    public static final String[] BASE_API = new String[]{"http://hz3bn04d2:8070/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//gaotang
//    public static final String[] BASE_API = new String[]{"http://hzdjl89j92:9955/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//liurun
//    public static final String[] BASE_API = new String[]{"http://hzdjl89j92:25007/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//liurun debug
    public static final String[] BASE_API = new String[]{"http://10.50.50.14:142/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//test

    public static final String CONTROLLER_PRE_API_USER = "api/" + CURRENT_BASE_VERSION + "/User/";
    public static final String CONTROLLER_PRE_API_HOME = "api/" + CURRENT_BASE_VERSION + "/Home/";
    public static final String CONTROLLER_PRE_API_REPORT = "api/" + CURRENT_BASE_VERSION + "/Report/";
    public static final String CONTROLLER_PRE_API_CONSULT = "api/" + CURRENT_BASE_VERSION + "/Consult/";

    public static final int CONNECT_TIMEOUT = 30;
    public static final int WRITE_TIMEOUT = 30;
    public static final int READ_TIMEOUT = 40;

    public static final String NEWS_DETAIL_URL = "http://hz3bn04d2:8060/Examination.html#/exam/";

}
