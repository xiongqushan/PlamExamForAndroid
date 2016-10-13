package com.ihaozuo.plamexam.view.consult;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.SystemBarTintUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConsultFragment extends AbstractView implements ConsultContract.IConsultView {


    @Bind(R.id.layer_docInfo)
    LinearLayout layerDocInfo;
    @Bind(R.id.n_scroll_view)
    NestedScrollView nScrollView;


    private View rootView;
    private Context mContext;
    private ConsultContract.IConsultPresenter mIConsultPresenter;

    public ConsultFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mIConsultPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static ConsultFragment newInstance() {
        return new ConsultFragment();
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.consult_frag, container, false);
            mContext = getContext();
//            setCustomerTitle(rootView, "健康咨询服务");
            SystemBarTintUtil tintManager = new SystemBarTintUtil(getActivity());
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.main_color_blue);
        }
        ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        mIConsultPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
