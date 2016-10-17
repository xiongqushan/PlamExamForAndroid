package com.ihaozuo.plamexam.view.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReportFragment extends BaseFragment {


    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.password)
    EditText password;
    private View rootView;

    public AddReportFragment() {
        // Required empty public constructor
    }

    public static AddReportFragment newInstance() {
        return new AddReportFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_report_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.get_report));
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        phone.setText("13888888888");
        phone.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.phone, R.id.password, R.id.btn_login})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.phone:
                break;
            case R.id.password:
                break;
            case R.id.btn_login:
                getActivity().sendBroadcast(new Intent(ReportListFragment.ADD_REPORT));
                getActivity().finish();
                break;
        }
    }
}
