package com.ihaozuo.plamexam.view.mine;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;
import com.ihaozuo.plamexam.view.mine.settings.SysSetActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @Bind(R.id.img_user)
    SimpleDraweeView imgUser;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    //    @Bind(R.id.layoutUserInfo)
//    LinearLayout layoutUserInfo;
//    @Bind(R.id.layoutSysSet)
//    LinearLayout layoutSysSet;
//    @Bind(R.id.layoutReportEx)
//    LinearLayout layoutReportEx;
    private View rootView;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mine_frag, container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }

    private void initView() {
        tvUserName.setText("艾欧尼亚");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.layoutUserInfo, R.id.layoutSysSet, R.id.layoutReportEx})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.layoutUserInfo:
                getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.layoutSysSet:
                getActivity().startActivity(new Intent(getActivity(), SysSetActivity.class));
                break;
            case R.id.layoutReportEx:
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
