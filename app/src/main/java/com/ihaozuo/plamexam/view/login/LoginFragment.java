package com.ihaozuo.plamexam.view.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends AbstractView implements LoginContract.ILoginView {

    @Bind(R.id.phone)
    AutoCompleteTextView phone;
    @Bind(R.id.et_authCode)
    EditText etAuthCode;
    @Bind(R.id.btn_getAuthCode)
    Button btnGetAuthCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.TVLayer_phone)
    TextInputLayout TVLayerPhone;
    @Bind(R.id.TVLayer_authCode)
    TextInputLayout TVLayerAuthCode;
    private LoginContract.ILoginPresenter mLoginPresenter;
    private View rootView;
    private Context mContext;
    private ArrayAdapter<String> arrayAdapter;
    private TimeCount time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        rootView = inflater.inflate(R.layout.login_frag, container, false);
        ButterKnife.bind(this, rootView);
//        String[] arr = {"aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aac"};
//        arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr);
//        phone.setAdapter(arrayAdapter);

        etAuthCode.addTextChangedListener(textWatch);
        phone.addTextChangedListener(textWatch);
        btnLogin.setClickable(false);

        return rootView;
    }

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mLoginPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        mLoginPresenter = presenter;
    }


    @OnClick({R.id.btn_getAuthCode, R.id.btn_login})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_getAuthCode:
                time = new TimeCount(6000, 1000);
                time.start();
                //                startActivity(new Intent(mContext, BindPhoneActivity.class));
                //                getActivity().finish();
                break;
            case R.id.btn_login:
                String phoneNum = phone.getText().toString();
//                String authCode = etAuthCode.getText().toString();
                if (!StringUtil.isMobile(phoneNum)) {
                    TVLayerPhone.setError("请输入正确的手机号码！");
                } else {
                    TVLayerPhone.setErrorEnabled(false);
                    gotoMainPage();
                }
                break;
        }
    }

    @Override
    public void gotoMainPage() {
        startActivity(new Intent(mContext, MainActivity.class));
        getActivity().finish();
    }

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btnGetAuthCode.setText(getString(R.string.get_authcode));
            btnGetAuthCode.setClickable(true);

        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btnGetAuthCode.setClickable(false);
            btnGetAuthCode.setText(millisUntilFinished / 1000 + "S后重新获取");
        }
    }

    private TextWatcher textWatch = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (StringUtil.isEmpty(phone.getText().toString())
                    || StringUtil.isEmpty(etAuthCode.getText().toString())) {
                btnLogin.setClickable(false);
            } else {
                btnLogin.setClickable(true);
            }

        }
    };
}