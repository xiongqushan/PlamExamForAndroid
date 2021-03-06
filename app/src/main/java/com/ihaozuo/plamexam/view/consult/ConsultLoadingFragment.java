package com.ihaozuo.plamexam.view.consult;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.WaveView;
import com.ihaozuo.plamexam.view.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ConsultLoadingFragment extends BaseFragment {

    @Bind(R.id.waveView)
    WaveView waveView;
    private View rootView;
    private Context mContext;


    public ConsultLoadingFragment() {
        // Required empty public constructor
    }

    public static ConsultLoadingFragment newInstance() {
        return new ConsultLoadingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.consult_loading_frag, container, false);
            mContext = getContext();
            setCustomerTitle(rootView, getString(R.string.consult_loading));
        }

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        waveView.showWave();
    }

    @Override
    public void onPause() {
        super.onPause();
        waveView.stopWave();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_goConsult,R.id.btn_consult_loading})
    public void onClick() {

        MobclickAgent.onEvent(getActivity(), "doctorConsult");
        startActivity(new Intent(mContext, ConsultDetailActivity.class));
    }
}
