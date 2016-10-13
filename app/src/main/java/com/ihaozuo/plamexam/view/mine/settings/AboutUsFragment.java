package com.ihaozuo.plamexam.view.mine.settings;


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
public class AboutUsFragment extends BaseFragment {


    private View rootView;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aboutus_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.about_us));
        return rootView;
    }

}
