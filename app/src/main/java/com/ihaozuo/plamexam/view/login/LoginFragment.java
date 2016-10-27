package com.ihaozuo.plamexam.view.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.main.MainActivity;
import com.ihaozuo.plamexam.view.mine.settings.DisclaimerActivity;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginFragment extends AbstractView implements LoginContract.ILoginView {

    @Bind(R.id.phone)
    AutoCompleteTextView phone;
    @Bind(R.id.et_authCode)
    EditText etAuthCode;
    @Bind(R.id.btn_getAuthCode)
    TextView btnGetAuthCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.TVLayer_phone)
    TextInputLayout TVLayerPhone;
    @Bind(R.id.TVLayer_authCode)
    TextInputLayout TVLayerAuthCode;
    @Bind(R.id.tv_warning)
    TextView tvWarning;
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
        String numb = PreferenceManager.getInstance().readLoginPhone();
        if (StringUtil.isNotEmpty(numb)) {
            phone.setText(numb);
        }
        etAuthCode.addTextChangedListener(textWatch);
        phone.addTextChangedListener(textWatch);
        btnLogin.setEnabled(false);
        tvWarning.setText(getClickableSpan());
        tvWarning.setMovementMethod(LinkMovementMethod.getInstance());

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
        String phoneNum = phone.getText().toString();
        String validCode = etAuthCode.getText().toString();
        switch (view.getId()) {
            case R.id.btn_getAuthCode:
                if (StringUtil.isMobile(phoneNum)) {
                    mLoginPresenter.getAuthCode(phoneNum);
                    time = new TimeCount(30000, 1000);
                    time.start();
                } else {
                    TVLayerPhone.setError(getString(R.string.error_input_phone));
//                    Toast.makeText(mContext,getString(R.string.error_input_phone),Toast.LENGTH_LONG).show();
                }

                //                startActivity(new Intent(mContext, BindPhoneActivity.class));
                //                getActivity().finish();
                break;
            case R.id.btn_login:
//                String authCode = etAuthCode.getText().toString();
                if (!StringUtil.isMobile(phoneNum)) {
                    TVLayerPhone.setErrorEnabled(true);
                    TVLayerPhone.setError(getString(R.string.error_input_phone));
                } else {
                    TVLayerPhone.setErrorEnabled(false);
                    mLoginPresenter.register(phoneNum, validCode);
//                    gotoMainPage();
                }
                break;
        }
    }

    @Override
    public void gotoMainPage() {
        setAlias(UserManager.getInstance().getUserInfo().AccountId);
        startActivity(new Intent(mContext, MainActivity.class));
        getActivity().finish();
    }

    private class TimeCount extends com.ihaozuo.plamexam.common.CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            if (btnGetAuthCode!=null){
                btnGetAuthCode.setText(getString(R.string.get_authcode));
                btnGetAuthCode.setClickable(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            if (getActivity()!=null){
                if (btnGetAuthCode!= null){
                    btnGetAuthCode.setClickable(false);
                    btnGetAuthCode.setText(millisUntilFinished / 1000 + getString(R.string.reget_authcode));
                }
            }else{
                Log.e(mContext+"!countdowntimer","cancel");
                cancel();
            }
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
//            if (!btnGetAuthCode.isClickable()){
//                if (StringUtil.isMobile(phone.getText().toString())){
//
//                }
//            }
            if (StringUtil.isEmpty(phone.getText().toString())
                    || StringUtil.isEmpty(etAuthCode.getText().toString())) {
                btnLogin.setEnabled(false);
            } else {
                btnLogin.setEnabled(true);
            }

        }
    };

    private SpannableString getClickableSpan() {
        View.OnClickListener GetUserContract = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "用户协议", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, DisclaimerActivity.class));
            }
        };

        SpannableString sp = new SpannableString(getString(R.string.login_statent));
//        sp.setSpan(new StyleSpan(Typeface.BOLD), 13, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new Clickable(GetUserContract), 13, 25, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.main_color_blue)), 13, 25, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return sp;
    }

    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
    }


    public void setAlias(String alias) {
        alias = alias.replaceAll("-","_");
        if (isValidTagAndAlias(alias)) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, alias));
//            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, "13818724007"));
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            Log.e(mContext+"",logs);
        }
    };
    private static final String TAG = "JPush";
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                case MSG_SET_TAGS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(HZApp.shareApplication(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    private static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|￥¥]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

}