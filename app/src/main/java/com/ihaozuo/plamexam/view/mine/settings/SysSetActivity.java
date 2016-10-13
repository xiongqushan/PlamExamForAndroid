package com.ihaozuo.plamexam.view.mine.settings;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

public class SysSetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SysSetFragment fragment = (SysSetFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = new SysSetFragment();
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }
}
