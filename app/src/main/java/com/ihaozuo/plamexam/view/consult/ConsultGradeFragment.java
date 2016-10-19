package com.ihaozuo.plamexam.view.consult;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.contract.ConsultGradeContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;

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
            ImageLoadUtils.getInstance(mContext).display(mDoctorInfo.ImageSrc, imgHead);
        }
        tvName.setText(mDoctorInfo.RealName);
        tvSpeciality.setText(mDoctorInfo.Speciality);
    }

    ;

    @Override
    public void setPresenter(ConsultGradeContract.IConsultGradePresenter presenter) {
        mIConsultPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_commit_grade)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit_grade:
                int score = RatingBar.getNumStars();
                String content = etContent.getText().toString();
                mIConsultPresenter.sendGrade(score, content);
        }
    }

    @Override
    public void finishView() {
        getActivity().finish();
    }
}
