package com.ihaozuo.plamexam.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.ConsultModule;
import com.ihaozuo.plamexam.ioc.DaggerConsultComponent;
import com.ihaozuo.plamexam.presenter.ConsultPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultDetailActivity extends BaseActivity {

    public static String LOCAL_CLASS_NAME;
    public static final String INTENT_KEY_CONSULT_FROM_REPORT = "REPORT_CONSULTDETAILACTIVITY";


    @Inject
    ConsultPresenter mConsultPresenter;
    @Inject
    ConsultContract.IConsultView mConsultView;

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOCAL_CLASS_NAME = getLocalClassName();
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.content_act);
        List<ConsultDetailBean> dataConsult = (List<ConsultDetailBean>) getIntent().getSerializableExtra(ConsultDetailActivity.INTENT_KEY_CONSULT_FROM_REPORT);
        DaggerConsultComponent.builder()
                .appComponent(HZApp.shareApplication()
                        .getAppComponent())
                .consultModule(new ConsultModule())
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ConsultDetailFragment fragment = (ConsultDetailFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (ConsultDetailFragment) mConsultView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }

}
