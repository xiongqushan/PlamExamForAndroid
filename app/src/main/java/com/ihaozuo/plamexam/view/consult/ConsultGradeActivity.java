package com.ihaozuo.plamexam.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ConsultGradeContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.ConsultGradeModule;
import com.ihaozuo.plamexam.ioc.DaggerConsultGradeComponent;
import com.ihaozuo.plamexam.presenter.ConsultGradePresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class ConsultGradeActivity extends BaseActivity {

    @Inject
    ConsultGradePresenter mConsultGradePresenter;
    @Inject
    ConsultGradeContract.IConsultGradeView mConsultGradeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ConsultGradeFragment fragment = (ConsultGradeFragment) fragmentManager.findFragmentById(R.id.frameContent);

        DaggerConsultGradeComponent.builder()
                .appComponent(HZApp.shareApplication().getAppComponent())
                .consultGradeModule(new ConsultGradeModule())
                .build()
                .inject(this);

        if (fragment == null) {
            fragment = (ConsultGradeFragment)mConsultGradeView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }
}
