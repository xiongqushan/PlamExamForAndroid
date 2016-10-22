package com.ihaozuo.plamexam.view.report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.common.pinnedheaderlistview.PinnedHeaderListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportDetailFragment extends Fragment {


    @Bind(R.id.listview_detail_report)
    PinnedHeaderListView mListView;
    private View rootView;

    public ReportDetailFragment() {
        // Required empty public constructor
    }

    public static ReportDetailFragment newInstance() {
        return new ReportDetailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_detail_frag, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void initView(ReportDetailBean bean) {
        ReportDetailAdapter adapter = new ReportDetailAdapter(bean);
        mListView.setPinHeaders(true);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                Toast.makeText(getActivity(), "艾欧尼亚", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
