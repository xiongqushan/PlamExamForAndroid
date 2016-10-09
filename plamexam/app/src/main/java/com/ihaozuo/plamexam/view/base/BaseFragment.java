package com.ihaozuo.plamexam.view.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;

import haozuo.com.healthdoctor.BuildConfig;
import haozuo.com.healthdoctor.framework.HZApplication;

/**
 * Created by xiongwei1 on 2016/8/1.
 */
public class BaseFragment extends Fragment {
    public static final String BROADFILTER_CONSULT_REPLAY = "BROADFILTER_CONSULT_REPLAY";
    public static final String BROADFILTER_CUSTOM_DELETEGROUP = "BROADFILTER_CUSTOM_DELETEGROUP";

    BroadcastReceiver receiver;

    protected void registerCustomReceiver(String activeName) {
        String[] filterActiveNames = new String[]{activeName};
        registerCustomReceiver(filterActiveNames);
    }

    protected void registerCustomReceiver(String[] filterActiveNames) {
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
        }
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String actionName = intent.getAction();
                onReceiveBroadcast(actionName, intent);
            }
        };
        IntentFilter filter = new IntentFilter();
        for (String activeName : filterActiveNames) {
            filter.addAction(activeName);
        }
        getContext().registerReceiver(receiver, filter);
    }

    protected void onReceiveBroadcast(String filterAction, Intent intent) {

    }

    protected void sendCustomBroadcast(String activeName) {
        Intent intent = new Intent(activeName);
        getContext().sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
        }
        if (BuildConfig.DEBUG) {
            HZApplication.shareApplication().getRefWatcher().watch(this);
        }
    }
}
