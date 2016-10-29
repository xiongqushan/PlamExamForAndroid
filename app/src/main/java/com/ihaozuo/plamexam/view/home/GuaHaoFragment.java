package com.ihaozuo.plamexam.view.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

public class GuaHaoFragment extends BaseFragment {


    private View rootView;

    public GuaHaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gua_hao_frag, container, false);
        setCustomerTitle(rootView, "挂号预约");
        return rootView;
    }

}
