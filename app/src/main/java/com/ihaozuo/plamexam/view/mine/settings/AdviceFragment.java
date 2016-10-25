package com.ihaozuo.plamexam.view.mine.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.common.dialog.ConfirmDialog;
import com.ihaozuo.plamexam.contract.AdviceContract;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * by zy  2016.08.30
 */
public class AdviceFragment extends AbstractView implements AdviceContract.IAdviceView {


    private AdviceContract.IAdvicePresenter mPresenter;
    private View rootView;

    private UserBean mUserBean;

    @Bind(R.id.tv_addReport)
    TextView tvAddReport;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_qq)
    EditText etQQ;

    @OnClick({R.id.btn_commit_advice,R.id.tv_addReport})
    void commit() {
        String content = etContent.getText().toString();
        boolean validInput = isValidInput(content, etPhone.getText().toString(), etQQ.getText().toString());
        if (!validInput) return;
        mPresenter.addFeedback(content);
    }


    public AdviceFragment() {
        // Required empty public constructor
    }

    public static AdviceFragment newInstance() {
        return new AdviceFragment();
    }

    ;

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.advice_frag, container, false);
        mUserBean = UserManager.getInstance().getUserInfo();
        ButterKnife.bind(this, rootView);

        setCustomerTitle(rootView, getString(R.string.advice_feedback));
        tvAddReport.setText("发送");
        tvAddReport.setVisibility(View.VISIBLE);
        etPhone.setText(mUserBean.Mobile);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setPresenter(AdviceContract.IAdvicePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessDialog() {
        new ConfirmDialog(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();

            }
        }).setContentText("感谢您的反馈！").show();

    }

    private boolean isValidInput(String content, String phone, String qq) {
        if (StringUtil.isTrimEmpty(content)) {
            etContent.setError("意见内容不能为空");
            etContent.requestFocus();
            return false;
        }
//        if (!StringUtil.isTrimEmpty(phone) && !HZUtils.checkMobileNumber(phone)) {
//            etPhone.setError("请输入正确的手机号码");
//            etPhone.requestFocus();
//            return false;
//        }
//        if (!StringUtil.isTrimEmpty(qq) && !HZUtils.checkCharacter(qq)) {
//            etQQ.setError("请输入正确QQ号码");
//            etQQ.requestFocus();
//            return false;
//        }
        return true;
    }
}
