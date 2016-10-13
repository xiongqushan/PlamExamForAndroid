package com.ihaozuo.plamexam.view.mine.settings;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * by zy  2016.08.30
 */
public class AdviceFragment extends BaseFragment {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_qq)
    EditText etQQ;

    @OnClick(R.id.btn_commit_advice)
    void commit() {
        boolean validInput = isValidInput(etContent.getText().toString(), etPhone.getText().toString(), etQQ.getText().toString());
        if (!validInput) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etContent.setText("");
                Toast.makeText(getActivity(), "谢谢反馈", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    private boolean isValidInput(String content, String phone, String qq) {
        if (StringUtil.isTrimEmpty(content)) {
            etContent.setError("意见内容不能为空");
            etContent.requestFocus();
            return false;
        }
        if (!StringUtil.isTrimEmpty(phone) && !HZUtils.checkMobileNumber(phone)) {
            etPhone.setError("请输入正确的手机号码");
            etPhone.requestFocus();
            return false;
        }
        if (!StringUtil.isTrimEmpty(qq) && !HZUtils.checkCharacter(qq)) {
            etQQ.setError("请输入正确QQ号码");
            etQQ.requestFocus();
            return false;
        }
        return true;
    }

    private View rootView;

    public AdviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.advice_frag, container, false);
        ButterKnife.bind(this, rootView);
        setCustomerTitle(rootView, getString(R.string.advice_feedback));
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
