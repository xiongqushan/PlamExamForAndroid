package com.ihaozuo.plamexam.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangzhongyao on 2016/8/31.
 */
public class HZUtils {
    private static final String DIR_CACHE = "PECache";

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static void setEditTextEnd(EditText mEditText) {
        CharSequence text = mEditText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    public void hideKeyboard(Context mContext) {
        View view = ((Activity) mContext).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDirCache(Context mContext) {
        String filePath;
        if (hasSdcard()) {
            filePath = Environment.getExternalStorageDirectory()
                    + File.separator + DIR_CACHE;
        } else {
            filePath = mContext.getCacheDir().getPath() + File.separator
                    + DIR_CACHE;
        }
        return filePath;
    }

    public static void deleteCache(Context mContext) {
        String filePath = getDirCache(mContext);
        delFilesFromPath(new File(filePath));
    }

    public static void delFilesFromPath(File filePath) {
        if (filePath == null) {
            return;
        }
        if (!filePath.exists()) {
            return;
        }
        File[] files = filePath.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();
            } else {
                delFilesFromPath(files[i]);
                files[i].delete();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T cloneTo(T src) throws RuntimeException {
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        T dist = null;
        try {
            out = new ObjectOutputStream(memoryBuffer);
            out.writeObject(src);
            out.flush();
            in = new ObjectInputStream(new ByteArrayInputStream(
                    memoryBuffer.toByteArray()));
            dist = (T) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null)
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            if (in != null)
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return dist;
    }

    /**
     * 验证QQ
     */
    public static boolean checkCharacter(String character) {
        boolean flag = false;
        try {
            //String check = "[a-zA-Z0-9@.-]{6,20}";
            String check = "[1-9][0-9]{5,11}";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(character);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern
                    .compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static int getCurrVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 启动软件商店，同时定位到包名对应的页面
     *
     * @param context
     * @return true 如果当前设备中存在软件商店软件，否则返回false
     */
    public static boolean turnStore(Context context) {

        Uri uri = Uri.parse("market://details?id=" +
                context.getPackageName());
//        Uri uri = Uri.parse("market://details?id=" +
//                "com.todayonhistory.toh");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        List<ResolveInfo> activities =
                context.getPackageManager().queryIntentActivities(
                        goToMarket, 0);
        if (activities != null && activities.size() > 0) {
            context.startActivity(goToMarket);
            return true;
        } else {
            return false;
        }
    }

    public static String checkEmptyStr(String object) {
        if (StringUtil.isTrimEmpty(object)) return "未知";
        return object;
    }

    public static Map initParamsMap() {
        Map<String, Object> params = new TreeMap<>();
        params.put("timespan", System.currentTimeMillis() / 1000L);

        return params;
    }


    /**
     * 创建目录
     *
     * @param context
     * @param dirName 文件夹名�?
     * @return
     */
    public static File createFileDir(Context context, String dirName) {
        String filePath;
        // 如SD卡已存在，则存储；反之存在data目录�?
        if (hasSdcard()) {
            // SD卡路�?
            filePath = Environment.getExternalStorageDirectory()
                    + File.separator + dirName;
        } else {
            filePath = context.getCacheDir().getPath() + File.separator
                    + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            boolean isCreate = destDir.mkdirs();
        }
        return destDir;
    }

}
