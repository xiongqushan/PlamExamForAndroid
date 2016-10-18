package com.ihaozuo.plamexam.framework;

/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class SysConfig {
    public static final String LOCAL_OPERATION_SYSTEM = "ANDROID";
    public static final String CURRENT_BASE_VERSION = "V3";
    //    public static final String[] BASE_API = new String[]{"http://hc.ihaozhuo.com:90", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//pro
//    public static final String[] BASE_API = new String[]{"http://hz3bn04d2/ZSTJ/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//xw
//    public static final String[] BASE_API = new String[]{"http://hz3bn04d2:8070/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//Test
    public static final String[] BASE_API = new String[]{"http://hz3bn04d2/ZSTJ/", "HZ_PME_API_V1", "1!2@3#4$5%6^"};//GT


    public static final String CONTROLLER_PRE_API_CONSULT = "api/" + CURRENT_BASE_VERSION + "/Consult/" ;
    public static final String CONTROLLER_PRE_API_USER = "api/" + CURRENT_BASE_VERSION + "/User/" ;


    public static final int CONNECT_TIMEOUT = 30;
    public static final int WRITE_TIMEOUT = 30;
    public static final int READ_TIMEOUT = 40;

}
