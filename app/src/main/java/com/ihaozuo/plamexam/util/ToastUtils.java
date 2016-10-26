package com.ihaozuo.plamexam.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    protected static Toast toastNoCancel = null;

    public static void showToastNoCancel(Context context, String s) {
        if (toastNoCancel == null) {
            toastNoCancel = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toastNoCancel.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > 2000) {// Toast.LENGTH_SHORT
                    toastNoCancel.show();
                }
            } else {
                oldMsg = s;
                toastNoCancel.setText(s);
                toastNoCancel.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(Context context, String text) {
        // Toast toast = Toast.makeText(this, "toast", Toast.LENGTH_LONG);
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        // View view =
        // LayoutInflater.from(context).inflate(R.layout.custom_toast,
        // null);
        // TextView tvText = (TextView) view.findViewById(R.id.textView1);
        // toast.setBackground();
        // toast.setView(view);
        toast.show();
    }

}
