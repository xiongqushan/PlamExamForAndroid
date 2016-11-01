package com.ihaozuo.plamexam.view.report.example;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EXAdviceFragment extends Fragment {


    @Bind(R.id.listview_adcive_report)
    ListView mListView;
    private View rootView;
    private ListAdapter adapter;
    private List<ReportDetailBean.GeneralAdvicesBean> dataList = new ArrayList<ReportDetailBean.GeneralAdvicesBean>();
    private static List<ReportDetailBean.GeneralAdvicesBean> advicesList;

    public EXAdviceFragment() {
        // Required empty public constructor
    }

    public static EXAdviceFragment newInstance(List<ReportDetailBean.GeneralAdvicesBean> data) {
        advicesList = data;
        return new EXAdviceFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_advice_frag, container, false);
        ButterKnife.bind(this, rootView);
        if (adapter == null) {
            adapter = new ListAdapter();
        }
        mListView.setAdapter(adapter);
        initView(advicesList);
        return rootView;
    }

    public void initView(List<ReportDetailBean.GeneralAdvicesBean> data) {
        if (adapter == null) {
            adapter = new ListAdapter();
        }
        adapter.refresh(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class ListAdapter extends SimpleBaseAdapter {


        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reportlist_advice, null);
            }
            TextView tvTitle = UIHelper.getAdapterView(convertView, R.id.tvReportAdviceTitle);
            TextView tvSubtitle = UIHelper.getAdapterView(convertView, R.id.tvReportAdviceSubtitle);
            tvTitle.setText(dataList.get(position).AdviceName);
            tvSubtitle.setText(dataList.get(position).AdviceDescription);
            return convertView;
        }

        public void refresh(List<ReportDetailBean.GeneralAdvicesBean> data) {
            if (data == null) {
                return;
            }
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }
}
