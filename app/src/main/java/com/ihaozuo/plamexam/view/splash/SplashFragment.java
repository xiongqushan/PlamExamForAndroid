package com.ihaozuo.plamexam.view.splash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    private View rootView;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.splash_frag, container, false);

        }
        return rootView;
    }

}
