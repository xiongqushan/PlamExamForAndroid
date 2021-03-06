package com.ihaozuo.plamexam.view.home;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

public class ExamActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        ExamFragment fragment = (ExamFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = new ExamFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
