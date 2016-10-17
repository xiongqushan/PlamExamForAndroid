package com.ihaozuo.plamexam.view.report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportErrorFragment extends Fragment {


    @Bind(R.id.listview_error_report)
    ListView mListView;
    private View rootView;

    public ReportErrorFragment() {
        // Required empty public constructor
    }

    public static ReportErrorFragment newInstance() {
        return new ReportErrorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_error_frag, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        BaseAdapter adapter = new ListAdapter();
        mListView.setAdapter(adapter);
    }

    private class ListAdapter extends SimpleBaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reportlist_error, null);
            }
            return convertView;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
