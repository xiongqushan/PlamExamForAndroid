package com.ihaozuo.plamexam.view.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ToastUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiskFragment extends BaseFragment {


    @OnClick(R.id.img_developing)
    public void onClick() {
        ToastUtils.showToast("敬请期待");
    }

    private View rootView;

    public RiskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.risk_frag, container, false);
        setCustomerTitle(rootView, "风险评估");
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
