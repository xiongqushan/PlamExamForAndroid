package com.ihaozuo.plamexam.view.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.framework.HZApp;


/**
 * Created by xiongwei1 on 2016/8/1.
 */
public class BaseFragment extends Fragment {
    private BroadcastReceiver receiver;
    View.OnClickListener finishActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    protected void setCustomerTitle(View view, String title) {
        setCustomerTitle(view, title, -1);
    }

    protected void setCustomerTitle(View view, String title, int color) {
        TextView textView = (TextView) view.findViewById(R.id.txt_actionbar_title);
        textView.setText(title);
        view.findViewById(R.id.img_actionbar_left).setOnClickListener(finishActivity);
        View actionbar = view.findViewById(R.id.actionbar);
        if (actionbar != null) {
            int statusHeight = getStatusHeight(getActivity());
            actionbar.setPadding(0, statusHeight, 0, 0);
        }
        if (color != -1) {
            actionbar.setBackgroundColor(color);
        }
    }

    /**
     * 状态栏高度算法
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass = null;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
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
