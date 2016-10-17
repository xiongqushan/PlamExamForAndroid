package com.ihaozuo.plamexam.view.consult;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConsultFragment extends AbstractView implements ConsultContract.IConsultView {


    @Bind(R.id.layer_docInfo)
    LinearLayout layerDocInfo;
    @Bind(R.id.pull_refresh_recycler)
    PullToRefreshRecyclerView pullRefreshRecycler;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;

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
            setCustomerTitle(rootView, "健康咨询服务");
        }
        ButterKnife.bind(this, rootView);

        mRecyclerView = pullRefreshRecycler.getRefreshableView();
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //添加PULL_TO_REFRESH音效
        SoundPullEventListener<RecyclerView> soundListener = new SoundPullEventListener<RecyclerView>(mContext);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        pullRefreshRecycler.setOnPullEventListener(soundListener);

        //设置PULL_TO_REFRESH LOADING
//        pullRefreshRecycler.setHeaderLayout(new LoadingLayout(mContext));

        pullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Toast.makeText(mContext, "Pull Down!", Toast.LENGTH_SHORT).show();
                pullRefreshRecycler.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Toast.makeText(mContext, "Pull Up!", Toast.LENGTH_SHORT).show();
                pullRefreshRecycler.onRefreshComplete();
            }
        });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("我是第" + i + "个");
        }
        ConsultDetailAdapter mAdapter = new ConsultDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.refreshList(list);


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
