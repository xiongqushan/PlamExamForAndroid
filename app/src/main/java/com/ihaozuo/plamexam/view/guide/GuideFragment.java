package com.ihaozuo.plamexam.view.guide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;
import com.ihaozuo.plamexam.view.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GuideFragment extends BaseFragment {

    @Bind(R.id.draweeView)
    SimpleDraweeView draweeView;
    private View rootView;
    private int mDrawableID;
    private boolean mShowTV;

    public GuideFragment() {
    }

    @SuppressLint("ValidFragment")
    public GuideFragment(int DrawableID, boolean showTV) {
        mDrawableID = DrawableID;
        mShowTV = showTV;
    }

    public static GuideFragment newInstance(int DrawableID, boolean showTV) {
        return new GuideFragment(DrawableID, showTV);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.guide_frag, container, false);
        }
        ButterKnife.bind(this, rootView);

        ImageLoadUtils.getInstance().display("res://com.ihaozuo.plamexam/" + mDrawableID, draweeView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.draweeView)
    public void onClick() {
        if (!mShowTV) {
            return;
        }
//        if (UserManager.getInstance().exist()) {
//            startActivity(new Intent(getActivity(), MainActivity.class));
//        } else {
        startActivity(new Intent(getActivity(), LoginActivity.class));
//        }
        getActivity().finish();
    }
}
