package com.ihaozuo.plamexam.view.report;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.manager.ReportManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.UIHelper;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.consult.ConsultDetailActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportListFragment extends AbstractView implements ReportContract.IReportListView {
    public static final String FILTER_REFRESH_REPORTLIST = "REFRESH_REPORTLIST_REPORTLISTFRAGMENT";
    public static final String INTENTKEY_REPORTLIST = "INTENTKEY_REPORTLIST_REPORTLISTFRAGMENT";

    private List<ReportItemBean> dataList = new ArrayList();
    ReportContract.IReportListPresenter mPresenter;

    @Bind(R.id.layout_report_add)
    RelativeLayout layoutReportAdd;
    @Bind(R.id.listview_report)
    ListView mListView;
    @Bind(R.id.tv_addReport)
    TextView tvGetReport;
    private View rootView;
    private ListAdapter adapter;

    public ReportListFragment() {
        // Required empty public constructor
    }

    public static ReportListFragment newInstance() {
        return new ReportListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_list_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.report));
        ButterKnife.bind(this, rootView);
        initView();
        registerCustomReceiver(FILTER_REFRESH_REPORTLIST);
        List<ReportItemBean> reportList = ReportManager.getInstance().getReportList();
        if (reportList == null || reportList.size() == 0) {
            if (ReportManager.getInstance().getFirstRequest()) {
                mPresenter.start();
            } else {
                showAddBtn();
            }
        } else {
            showReportList(reportList);
        }
        registerCustomReceiver(FILTER_REFRESH_REPORTLIST);
        initView();
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.cancelRequest();
    }

    @Override
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        showReportList(ReportManager.getInstance().getReportList());
    }

    private void initView() {
        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtra(ReportActivity.INTENTKEY_WORKNO, dataList.get(position).WorkNo);
                intent.putExtra(ReportActivity.INTENTKEY_CHECKUNITCODE, dataList.get(position).CheckUnitCode);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_addReport, R.id.layout_report_add, R.id.btn_turn_consult})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_addReport:
            case R.id.layout_report_add:
                startActivity(new Intent(getActivity(), ReportGetActivity.class));
                break;
            case R.id.btn_turn_consult:
                startActivity(new Intent(getActivity(), ConsultDetailActivity.class));
                MobclickAgent.onEvent(getActivity(), "doctorConsult");
                break;
        }
    }


    @Override
    public void setPresenter(ReportContract.IReportListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAddBtn() {
        layoutReportAdd.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.INVISIBLE);
        tvGetReport.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toggleRetryLayer(boolean show) {
        if (show) {
            showRetryLayer(R.id.rLayout);
        } else {
            hideRetryLayer(R.id.rLayout);
        }
    }

    @Override
    public void showReportList(List<ReportItemBean> dataList) {
        adapter.refresh(dataList);
        layoutReportAdd.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
        tvGetReport.setVisibility(View.VISIBLE);
    }


    private class ListAdapter extends SimpleBaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        public void refresh(List<ReportItemBean> data) {
            dataList = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reportlist, null);
            }
            TextView tvDepart = UIHelper.getAdapterView(convertView, R.id.tvReportDepart);
            TextView tvTime = UIHelper.getAdapterView(convertView, R.id.tvReportTime);
            tvDepart.setText(dataList.get(position).ReportName);
            tvTime.setText(dataList.get(position).ReportDateFormat);
            return convertView;
        }
    }
}
