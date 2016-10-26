package com.ihaozuo.plamexam.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseActivity;
import com.ihaozuo.plamexam.view.consult.ConsultLoadingFragment;
import com.ihaozuo.plamexam.view.home.HomeFragment;
import com.ihaozuo.plamexam.view.login.LoginActivity;
import com.ihaozuo.plamexam.view.mine.MineFragment;
import com.umeng.socialize.UMShareAPI;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static final String FINISH_ACTIVITY = "FINISHACTIVITY";
    public static final String REMOVE_UNREAD_MARK = "REMOVE_UNREAD_MARK";
    public static final String SHOW_UNREAD_MARK = "SHOW_UNREAD_MARK";

    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    public static String LOCAL_CLASS_NAME;
    @Bind(R.id.unreadMarkTab)
    ImageView unreadMarkTab;
    private FragmentManager supportFragmentManager;
    private boolean isExit;

    public void setCurrentTab(int page) {
        mTabHost.setCurrentTab(page);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LOCAL_CLASS_NAME = getLocalClassName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);
        ButterKnife.bind(this);

        supportFragmentManager = getSupportFragmentManager();
        mTabHost.setup(this, supportFragmentManager, R.id.realtabcontent);
        addTab(getString(R.string.tab_home), R.drawable.tabhost_home, HomeFragment.class);
//        addTab(getString(R.string.tab_consult), R.drawable.tabhost_consult, NewsListFragment.class);
        addTab(getString(R.string.tab_consult), R.drawable.tabhost_consult, ConsultLoadingFragment.class);
        addTab(getString(R.string.tab_mine), R.drawable.tabhost_mine, MineFragment.class);

        registerCustomReceiver(new String[]{
                REMOVE_UNREAD_MARK,
                FINISH_ACTIVITY,
                SHOW_UNREAD_MARK});
    }

    private void addTab(String tabName, int drawable, Class fragment) {
        View tabView = getLayoutInflater().inflate(R.layout.item_tab_layout,
                null);
        TextView tabLabel = (TextView) tabView.findViewById(R.id.tv_tab_label);
        tabLabel.setText(tabName);
        setTextDrawable(this, drawable, tabLabel);
        mTabHost.addTab(mTabHost.newTabSpec(tabName).setIndicator(tabView), fragment, null);
    }

    public static void setTextDrawable(Context context, int drawableRes,
                                       TextView tvName) {
        Drawable drawableTop = context.getResources().getDrawable(drawableRes);
        // 必须设置图片大小，否则不显示
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
                drawableTop.getMinimumHeight());
        tvName.setCompoundDrawables(null, drawableTop, null, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                System.exit(0);
                return true;
            } else {
                isExit = true;
                Toast.makeText(this, getString(R.string.system_exit), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2500);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        switch (filterAction){
            case MainActivity.REMOVE_UNREAD_MARK:
                if (unreadMarkTab.getVisibility() == View.VISIBLE){
                    unreadMarkTab.setVisibility(View.INVISIBLE);
                }
                break;

            case MainActivity.FINISH_ACTIVITY:
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                break;

            case MainActivity.SHOW_UNREAD_MARK:
                if (unreadMarkTab.getVisibility() == View.INVISIBLE){
                    unreadMarkTab.setVisibility(View.VISIBLE);
                }
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("result","onActivityResult");
    }
}


