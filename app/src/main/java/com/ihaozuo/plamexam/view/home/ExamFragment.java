package com.ihaozuo.plamexam.view.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ToastUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamFragment extends BaseFragment {


    @Bind(R.id.layoutTCYY)
    LinearLayout layoutTCYY;
    @Bind(R.id.layoutSRDZ)
    LinearLayout layoutSRDZ;
    private View rootView;

    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exam_frag, container, false);
        setCustomerTitle(rootView, "体检预约");
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layoutTCYY, R.id.layoutSRDZ})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutTCYY:
            case R.id.layoutSRDZ:
                ToastUtils.showToast("敬请期待");
                break;
        }
    }
}
