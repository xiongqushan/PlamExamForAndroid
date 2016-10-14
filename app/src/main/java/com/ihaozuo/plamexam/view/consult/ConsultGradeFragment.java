package com.ihaozuo.plamexam.view.consult;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultGradeFragment extends AbstractView {


    private View rootView;

    public ConsultGradeFragment() {
        // Required empty public constructor
    }

    public static ConsultGradeFragment newInstance() {
        return new ConsultGradeFragment();
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.consult_grade_frag, container, false);
        setCustomerTitle(rootView,getString(R.string.consult_grade));
        return rootView;
    }

}
