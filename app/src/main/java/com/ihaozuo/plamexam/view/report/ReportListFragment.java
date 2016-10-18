package com.ihaozuo.plamexam.view.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportListFragment extends AbstractView {
    public static final String ADD_REPORT = "REPORTLISTFRAGMENT_ADD_REPORT";

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
        return null;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_list_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.report));
        ButterKnife.bind(this, rootView);
        initView();
        registerCustomReceiver(ADD_REPORT);
        return rootView;
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
}
