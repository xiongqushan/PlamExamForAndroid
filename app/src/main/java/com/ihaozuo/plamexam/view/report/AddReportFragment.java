package com.ihaozuo.plamexam.view.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReportFragment extends AbstractView implements ReportContract.ReportGetView {


    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.et_Name)
    EditText etName;
    private View rootView;

    public AddReportFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return null;
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
        phone.setText(UserManager.getInstance().getUserInfo().Mobile);
        phone.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.phone, R.id.et_Name, R.id.btn_login})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.phone:
                break;
            case R.id.et_Name:
                break;
            case R.id.btn_login:
                getActivity().sendBroadcast(new Intent(ReportListFragment.REFRESH_REPORTLIST));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void setPresenter(ReportContract.ReportGetPresenter presenter) {

    }
}
