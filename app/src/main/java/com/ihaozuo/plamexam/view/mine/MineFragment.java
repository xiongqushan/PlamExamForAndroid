package com.ihaozuo.plamexam.view.mine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

public class MineFragment extends BaseFragment {


    private View rootView;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mine_frag, container, false);
            setCustomerTitle(rootView, "我的", R.color.androidColorC);
        }
        return rootView;
    }

}
