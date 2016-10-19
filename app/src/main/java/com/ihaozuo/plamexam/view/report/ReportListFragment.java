package com.ihaozuo.plamexam.view.report;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportListFragment extends AbstractView implements ReportContract.ReportListView {
    public static final String REFRESH_REPORTLIST = "REPORTLIST_REPORTLISTFRAGMENT";

    ReportContract.ReportListPresenter mPresenter;

    @Bind(R.id.layout_report_add)
    RelativeLayout layoutReportAdd;
    @Bind(R.id.listview_report)
    ListView mListView;
    private View rootView;

    public ReportListFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_list_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.report));
        ButterKnife.bind(this, rootView);
        initView();
        registerCustomReceiver(REFRESH_REPORTLIST);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //   mPresenter.cancelRequest();
    }

    @Override
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        layoutReportAdd.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
    }

    private void initView() {
        BaseAdapter adapter = new SimpleBaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reportlist, null);
                }
                return convertView;
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ReportActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.layout_report_add)
    public void onClick() {
        startActivity(new Intent(getActivity(), AddReportActivity.class));
    }

    @Override
    public void setPresenter(ReportContract.ReportListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAddBtn() {
        layoutReportAdd.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showReportList(List<ReportItemBean> dataList) {
        layoutReportAdd.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
    }
}
