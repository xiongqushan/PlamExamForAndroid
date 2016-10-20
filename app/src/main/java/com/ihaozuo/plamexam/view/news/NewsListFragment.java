package com.ihaozuo.plamexam.view.news;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsListFragment extends AbstractView {

    @Bind(R.id.NewsList)
    ListView mListView;
    private View rootView;
    private Context mContext;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            rootView = inflater.inflate(R.layout.news_list_frag, container, false);
            setCustomerTitle(rootView, getString(R.string.daily_news));
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }

    private void initView() {
        NewsListAdapter adapter = new NewsListAdapter(mContext);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().startActivity(new Intent(getActivity(), NewsDetailActivity.class));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
