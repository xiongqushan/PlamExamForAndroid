package com.ihaozuo.plamexam.framework;

/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class SysConfig {
    public static final String CURRENT_BASE_VERSION = "V1";
    public static final String[] BASE_API = new String[]{"http://hc.ihaozhuo.com:90", "HZ_API_V2", "1!2@3#4$5%6^"};//pro
    //public static final String[] BASE_API = new String[]{"http://hz75thbd2:803", "HZ_API_V2", "1!2@3#4$5%6^"};//xw
    //public static final String[] BASE_API = new String[]{"http://hz75thbd2:19949", "HZ_API_V2", "1!2@3#4$5%6^"};//xwdebugg
    //public static final String[] BASE_API = new String[]{"http://hzswvajgs01:91", "HZ_API_V2", "1!2@3#4$5%6^"};//xwdebugg


    public static final String CONTROLLER_PRE_API_USER = "api/" + CURRENT_BASE_VERSION + "_User/";

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 40;

}
