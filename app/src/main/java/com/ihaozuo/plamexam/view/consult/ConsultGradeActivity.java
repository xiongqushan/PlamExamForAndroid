package com.ihaozuo.plamexam.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

public class ConsultGradeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ConsultGradeFragment fragment = (ConsultGradeFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = new ConsultGradeFragment();
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }
}
