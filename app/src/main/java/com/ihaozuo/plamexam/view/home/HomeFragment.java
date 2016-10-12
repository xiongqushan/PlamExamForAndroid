package com.ihaozuo.plamexam.view.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

public class HomeFragment extends AbstractView implements HomeContract.IHomeView {


    private View rootView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setPresenter(HomeContract.IHomePresenter presenter) {

    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.home_frag, container, false);
            setCustomerTitle(rootView, "首页");


        }
        return rootView;
    }


}
