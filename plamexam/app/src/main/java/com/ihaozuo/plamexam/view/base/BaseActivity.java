package com.ihaozuo.plamexam.view.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.ioc.AppComponent;
import haozuo.com.healthdoctor.util.SystemBarTintUtil;
import haozuo.com.healthdoctor.view.consult.ConsultDetailActivity;

//import com.umeng.analytics.MobclickAgent;

/**
 * Created by xiongwei1 on 2016/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTranslucentStatus();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.to_right_in, R.anim.to_right_out);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintUtil tintManager = new SystemBarTintUtil(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(resource);//状态栏无背景
    }

    protected void setCustomerTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.txt_TitleBar_title);
        textView.setText(title);
        findViewById(R.id.btn_go_back).setOnClickListener(finishActivity);
    }

    protected void setTitleWithSearch(String title) {
        TextView textView = (TextView) findViewById(R.id.txt_TitleBar_title);
        ImageView btn_search = (ImageView) findViewById(R.id.btn_search);
        textView.setText(title);
        findViewById(R.id.btn_go_back).setOnClickListener(finishActivity);
        btn_search.setOnClickListener(showSearchbar);
        btn_search.setVisibility(View.VISIBLE);
    }

    protected void setTitleWithConsult(String title, final int CustomID) {
        TextView textView = (TextView) findViewById(R.id.txt_TitleBar_title);
        textView.setText(title);
        findViewById(R.id.btn_go_back).setOnClickListener(finishActivity);
        findViewById(R.id.btn_search).setVisibility(View.GONE);
        findViewById(R.id.tv_Consult).setVisibility(View.VISIBLE);
        findViewById(R.id.tv_Consult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ConsultDetailActivity.class);
                intent.putExtra(ConsultDetailActivity.EXTRA_CONSULT_ITEM, CustomID);
                startActivity(intent);
            }
        });
    }

    protected void setSearchBar() {
        findViewById(R.id.txt_TitleBar_title).setVisibility(View.GONE);
        findViewById(R.id.btn_search).setVisibility(View.GONE);
        findViewById(R.id.et_TitleBar_search).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_go_back).setOnClickListener(finishActivity);
    }

    protected AppComponent getAppComponent() {
        return ((HZApplication) getApplication()).getAppComponent();
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    View.OnClickListener finishActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener showSearchbar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            findViewById(R.id.txt_TitleBar_title).setVisibility(View.GONE);
            findViewById(R.id.btn_search).setVisibility(View.GONE);
            findViewById(R.id.et_TitleBar_search).setVisibility(View.VISIBLE);
        }
    };

}