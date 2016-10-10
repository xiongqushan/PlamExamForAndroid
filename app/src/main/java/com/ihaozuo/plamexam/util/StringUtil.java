package com.ihaozuo.plamexam.util;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiongwei on 16/5/17.
 */
public class StringUtil {
    public static boolean isNotEmpty(String str) {
        return (str != null) && (str.length() != 0);
    }

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotTrimEmpty(String str) {
        return (str != null) && (str.trim().length() != 0);
    }

    public static boolean isTrimEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^0?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher matcher = p.matcher(mobile);
        return matcher.find();
    }

    public static boolean isIntegerString(String integerString) {
        if (isTrimEmpty(integerString)) {
            return false;
        }
        Pattern p = Pattern.compile("^[1-9][0-9]*$");
        Matcher matcher = p.matcher(integerString);
        return matcher.find();
    }

    /**
     * 对字符串进行MD5加密
     */
    //十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                //将得到的字节数组变成字符串返回
                return byteArrayToHexString(results);
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String getUrlParam(String url,String key){
        if(!url.contains("?")){
            return null;
        }
        String paramString=url.split("[?]")[1];
        String[] keyValues=paramString.split("&");
        String param=null;
        for (String keyValue:keyValues){
            if(keyValue.contains("=") && keyValue.split("=")[0].toLowerCase().equals(key.toLowerCase())){
                param=keyValue.split("=")[1];
                break;
            }
        }
        return param;
    }
}