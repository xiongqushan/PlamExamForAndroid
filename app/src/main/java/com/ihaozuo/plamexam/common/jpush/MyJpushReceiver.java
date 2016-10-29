package com.ihaozuo.plamexam.common.jpush;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ihaozuo.plamexam.view.consult.ConsultDetailFragment;
import com.ihaozuo.plamexam.view.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangzhongyao on 2016/10/09.
 * 自定义接收器
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyJpushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyJpushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction()
                + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: "
                    + bundle.getString(JPushInterface.EXTRA_MESSAGE));

//            senBroadcastToActivity(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            // TODO 向指定的ACTIVITY发送广播
            senBroadcastToActivity(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知" + printBundle(bundle));
            // TODO 打开自定义的Activity
            if (MainActivity.isLived) {
                Intent intentPush = new Intent();
                intentPush.setClass(context, WakeActivity.class);
                intentPush.putExtras(bundle);
//            intentPush.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentPush.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentPush);
            } else {
                Intent intentPush = new Intent();
                intentPush.setClass(context, MainActivity.class);
                intentPush.putExtras(bundle);
//            intentPush.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentPush.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentPush);
            }


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {
            Log.d(TAG,
                    "[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
                            + bundle.getString(JPushInterface.EXTRA_EXTRA));

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction()
                    + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.e(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(
                            bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" + myKey + " - "
                                + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    //send msg to MainActivity
    private void senBroadcastToActivity(Context context, Bundle bundle) {
        if (ConsultDetailFragment.isLive) {
            Intent msgIntent = new Intent(ConsultDetailFragment.REFRESH_COSULTD_LIST);
            context.sendBroadcast(msgIntent);
            if (!ConsultDetailFragment.isForeground) {
                msgIntent = new Intent(MainActivity.SHOW_UNREAD_MARK);
                context.sendBroadcast(msgIntent);
            }
        } else {
            Intent msgIntent = new Intent(MainActivity.SHOW_UNREAD_MARK);
            context.sendBroadcast(msgIntent);
        }
    }

}
