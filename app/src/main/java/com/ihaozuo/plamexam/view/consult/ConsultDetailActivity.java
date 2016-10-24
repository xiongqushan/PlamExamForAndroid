package com.ihaozuo.plamexam.view.consult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.ConsultModule;
import com.ihaozuo.plamexam.ioc.DaggerConsultComponent;
import com.ihaozuo.plamexam.presenter.ConsultPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultDetailActivity extends BaseActivity {

    public static String LOCAL_CLASS_NAME;
    public static final String INTENT_KEY_CONSULT_FROM_REPORT = "REPORT_CONSULTDETAILACTIVITY";
    public static final String INTENT_KEY_CONSULT_FROM_PUSH = "PUSH_CONSULTDETAILACTIVITY";

    private ConsultModule mConsultModule;
    private List<ConsultDetailBean> mConsultDatailList;

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show();
        List<ConsultDetailBean> list = (List<ConsultDetailBean>) intent.getSerializableExtra(ConsultDetailActivity.INTENT_KEY_CONSULT_FROM_REPORT);
        mConsultPresenter.refresh(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOCAL_CLASS_NAME = getLocalClassName();
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.content_act);
        mConsultDatailList = new ArrayList<ConsultDetailBean>();
        if (null != getIntent().getSerializableExtra(ConsultDetailActivity.INTENT_KEY_CONSULT_FROM_REPORT)) {
            mConsultDatailList.addAll((List<ConsultDetailBean>) getIntent().getSerializableExtra(ConsultDetailActivity.INTENT_KEY_CONSULT_FROM_REPORT));
        }
        mConsultModule = new ConsultModule(mConsultDatailList);
        DaggerConsultComponent.builder()
                .appComponent(HZApp.shareApplication()
                .getAppComponent())
                .consultModule(mConsultModule)
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
