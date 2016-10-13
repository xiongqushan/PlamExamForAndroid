package com.ihaozuo.plamexam.view.mine.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

public class DisclaimerFragment extends BaseFragment {


    private View rootView;

    public DisclaimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.statement_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.disclaimer));
        return rootView;
    }

}
