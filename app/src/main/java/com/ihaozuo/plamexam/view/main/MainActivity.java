package com.ihaozuo.plamexam.view.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseActivity;
import com.ihaozuo.plamexam.view.consult.ConsultFragment;
import com.ihaozuo.plamexam.view.home.HomeFragment;
import com.ihaozuo.plamexam.view.mine.MineFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    private FragmentManager supportFragmentManager;
    private boolean isExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);
        ButterKnife.bind(this);
        supportFragmentManager = getSupportFragmentManager();
        mTabHost.setup(this, supportFragmentManager, R.id.realtabcontent);
        addTab("首页", R.drawable.tabhost_home, HomeFragment.class);
        addTab("咨询", R.drawable.tabhost_consult, ConsultFragment.class);
        addTab("我的", R.drawable.tabhost_mine, MineFragment.class);
    }

    private void addTab(String tabName, int drawable, Class fragment) {
        View tabView = getLayoutInflater().inflate(R.layout.tab_item_layout,
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

}


