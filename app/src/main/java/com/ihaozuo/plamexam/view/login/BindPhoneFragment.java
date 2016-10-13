package com.ihaozuo.plamexam.view.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindPhoneFragment extends BaseFragment {


    private View rootView;

    public BindPhoneFragment() {
        // Required empty public constructor
    }

    public static BindPhoneFragment newInstance() {
        return new BindPhoneFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.bind_phone_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.bind_phone), getString(R.string.androidColorE));
        return rootView;
    }

}
