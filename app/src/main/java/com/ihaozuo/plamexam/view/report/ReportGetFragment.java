package com.ihaozuo.plamexam.view.report;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.home.HomeFragment;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportGetFragment extends AbstractView implements ReportContract.IReportGetView {

    ReportContract.IReportGetPresenter mPresenter;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.et_Name)
    EditText etName;
    @Bind(R.id.btn_login)
    Button btnLogin;
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
        rootView = inflater.inflate(R.layout.report_add_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.get_report));
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
//        phone.setEnabled(false);
        //      phone.setText(UserManager.getInstance().getUserInfo().Mobile);
        phone.setText("13651646955");
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    btnLogin.setEnabled(false);
                } else {
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                MobclickAgent.onEvent(getActivity(), "getReports");
                String name = etName.getText().toString();
                if (name.length() < 2 || name.length() > 15) {
                    etName.requestFocus();
                    etName.setFocusableInTouchMode(true);
                    etName.setError("请重新输入");
                    return;
                }
                String tele = phone.getText().toString();
                mPresenter.getReport(tele, name);
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

    @Override
    public void updateHomeBanner() {
        Intent intent = new Intent(HomeFragment.FILTER_UPDATEBANNER_HOME);
        getActivity().sendBroadcast(intent);
    }

}
