package com.ihaozuo.plamexam.view.consult;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.contract.ConsultGradeContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultGradeFragment extends AbstractView implements ConsultGradeContract.IConsultGradeView {


    @Bind(R.id.img_head)
    SimpleDraweeView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_Speciality)
    TextView tvSpeciality;
    @Bind(R.id.RatingBar)
    android.widget.RatingBar RatingBar;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.CheckBoxA)
    CheckBox CheckBoxA;
    @Bind(R.id.CheckBoxB)
    CheckBox CheckBoxB;
    @Bind(R.id.CheckBoxC)
    CheckBox CheckBoxC;
    @Bind(R.id.CheckBoxD)
    CheckBox CheckBoxD;
    private View rootView;
    private ConsultGradeContract.IConsultGradePresenter mIConsultPresenter;

    private DoctorInfoBean mDoctorInfo;
    Context mContext;

    public ConsultGradeFragment() {
        // Required empty public constructor
    }

    public static ConsultGradeFragment newInstance() {
        return new ConsultGradeFragment();
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mIConsultPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        rootView = inflater.inflate(R.layout.consult_grade_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.consult_grade));
        ButterKnife.bind(this, rootView);
        setDoctorInfo();
        return rootView;
    }

    public void setDoctorInfo() {
        mDoctorInfo = getDoctorInfo();
        if (null == mDoctorInfo) {
            return;
        }
        if (null != mDoctorInfo.ImageSrc) {
            ImageLoadUtils.getInstance(HZApp.shareApplication()).display(mDoctorInfo.ImageSrc, imgHead);
        }
        tvName.setText(mDoctorInfo.RealName);
        tvSpeciality.setText(mDoctorInfo.Speciality);
        tvDescription.setText(getString(R.string.workTime));
    }


    @Override
    public void setPresenter(ConsultGradeContract.IConsultGradePresenter presenter) {
        mIConsultPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_commit_grade})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit_grade:
                String content = etContent.getText().toString();
                List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
                checkBoxes.add(CheckBoxA);
                checkBoxes.add(CheckBoxB);
                checkBoxes.add(CheckBoxC);
                checkBoxes.add(CheckBoxD);
                for (CheckBox cb: checkBoxes){
                    if (cb.isChecked()){
                        if (!StringUtil.isEmpty(content)){
                            content += ",";
                        }
                        content += cb.getText().toString();
                    }
                }
                int score = RatingBar.getNumStars();

                mIConsultPresenter.sendGrade(score, content);
                break;

        }
    }

    @Override
    public void finishView() {
        Toast.makeText(mContext,"感谢您的评价！",Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

}
