package com.ihaozuo.plamexam.view.consult;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

public class ConsultFragment extends BaseFragment {


    private View rootView;

    public ConsultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.consult_frag, container, false);
            setCustomerTitle(rootView, "咨询");
        }
        return rootView;
    }

}
