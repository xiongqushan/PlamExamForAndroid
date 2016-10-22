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
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportGetFragment extends AbstractView implements ReportContract.IReportGetView {

    ReportContract.IReportGetPresenter mPresenter;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.et_Name)
    EditText etName;
    private View rootView;

    public ReportGetFragment() {
        // Required empty public constructor
    }


    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static ReportGetFragment newInstance() {
        return new ReportGetFragment();
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
        String realName = UserManager.getInstance().getUserInfo().RealName;
        if (StringUtil.isNotEmpty(realName)) {
            etName.setText(realName);
        }
//        phone.setEnabled(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.cancelRequest();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_login:
                String name = etName.getText().toString();
                if (StringUtil.isEmpty(name)) {
                    etName.requestFocus();
                    etName.setFocusableInTouchMode(true);
                    etName.setError("不能为空");
                    return;
                }
                mPresenter.getReport(UserManager.getInstance().getUserInfo().Mobile, name);
                break;
        }
    }

    @Override
    public void setPresenter(ReportContract.IReportGetPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showReportList() {
        Intent intent = new Intent(ReportListFragment.FILTER_REFRESH_REPORTLIST);
        getActivity().sendBroadcast(intent);
        getActivity().finish();
    }
}