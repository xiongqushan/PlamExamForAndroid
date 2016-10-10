package com.ihaozuo.plamexam.view.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.framework.HZApp;


/**
 * Created by xiongwei1 on 2016/8/1.
 */
public class BaseFragment extends Fragment {
    public static final String BROADFILTER_CONSULT_REPLAY = "BROADFILTER_CONSULT_REPLAY";
    public static final String BROADFILTER_CUSTOM_DELETEGROUP = "BROADFILTER_CUSTOM_DELETEGROUP";

    BroadcastReceiver receiver;

    /**
     * 沉浸式状态栏
     */
    protected void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
    protected void initState(View actionBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        actionBar.setPadding(0,getStatusBarHeight(getActivity()),0,0);
    }

    //沉浸式Nav
    private void navViewToTop(DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawerLayout.setFitsSystemWindows(true);
            drawerLayout.setClipToPadding(false);
        }
    }

    protected int getStatusBarHeight(Activity activity){
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        return r.top;
    }

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
            HZApp.shareApplication().getRefWatcher().watch(this);
        }
    }
}
