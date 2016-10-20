package com.ihaozuo.plamexam.view.mine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoFragment extends BaseFragment {


    @Bind(R.id.CPhoto)
    SimpleDraweeView CPhoto;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.tvUserPhone)
    TextView tvUserPhone;
    private View rootView;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.user_info_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.user_info));
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvUserName.setText(UserManager.getInstance().getUserInfo().RealName);
        tvUserPhone.setText(UserManager.getInstance().getUserInfo().Mobile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.userPhoto, R.id.layoutUserAuth})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.layoutUserAuth:
                Toast.makeText(getActivity(), "功能暂未开放", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
