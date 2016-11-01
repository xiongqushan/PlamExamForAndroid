package com.ihaozuo.plamexam.view.report.example;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EXErrorFragment extends Fragment {


    @Bind(R.id.listview_error_report)
    ListView mListView;
    @Bind(R.id.btnReportError)
    Button btnReportError;
    private View rootView;
    private ListAdapter adapter;
    private List<ReportDetailBean.CheckItemsBean.CheckResultsBean> dataList = new ArrayList<>();
    private Map<Integer, Boolean> checkMap = new ArrayMap<Integer, Boolean>();
    private static ReportDetailBean mReportDetailBean;

    public EXErrorFragment() {
        // Required empty public constructor
    }

    public static EXErrorFragment newInstance(ReportDetailBean reportDetailBean) {
        mReportDetailBean = reportDetailBean;
        return new EXErrorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_error_frag, container, false);
        ButterKnife.bind(this, rootView);
        btnReportError.setVisibility(View.GONE);
        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
        initView(mReportDetailBean);
        return rootView;
    }

    public void initView(ReportDetailBean reportDetailBean) {
        int CheckItemSize = reportDetailBean.CheckItems.size();
        for (int i = 0; i < CheckItemSize; i++) {
            int CheckResultSize = reportDetailBean.CheckItems.get(i).CheckResults.size();
            for (int j = 0; j < CheckResultSize; j++) {
                if (reportDetailBean.CheckItems.get(i).CheckResults.get(j).IsAbnormalForamt) {
                    dataList.add(reportDetailBean.CheckItems.get(i).CheckResults.get(j));
                }
            }
        }
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            checkMap.put(i, false);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnReportError)
    public void onClick() {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }

    }


    private class ListAdapter extends SimpleBaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reportlist_error, null);
            }
            TextView tvTitle = UIHelper.getAdapterView(convertView, R.id.tvTitle);
            TextView tvSubtitle = UIHelper.getAdapterView(convertView, R.id.tvSubtitle);
            TextView tvValue = UIHelper.getAdapterView(convertView, R.id.tvResultValue);
            TextView tvUnit = UIHelper.getAdapterView(convertView, R.id.tvUnit);
            final CheckBox cb = UIHelper.getAdapterView(convertView, R.id.checkbox_report_error);
            cb.setChecked(checkMap.get(position));
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = cb.isChecked();
                    checkMap.put(position, checked);
                    if (checked) {
                        numSeleted++;
                    } else {
                        numSeleted--;
                    }
                    if (numSeleted == 0) {
                        btnReportError.setText("关闭选择");
                    } else {
                        btnReportError.setText("咨询(" + numSeleted + "项) ");
                    }
                }
            });
            ReportDetailBean.CheckItemsBean.CheckResultsBean checkResultsBean = dataList.get(position);
            tvTitle.setText(checkResultsBean.CheckIndexName);
            boolean unitEmpty = StringUtil.isTrimEmpty(checkResultsBean.Unit);
            boolean rangeEmpty = StringUtil.isTrimEmpty(checkResultsBean.ValueRefFormat);
            if (unitEmpty && rangeEmpty) {
                tvValue.setVisibility(View.GONE);
                tvUnit.setVisibility(View.GONE);
                String resultValue = checkResultsBean.ResultValue;
                if (resultValue.equals("")) resultValue = "未见异常";
                tvSubtitle.setText(resultValue);
            } else {
                tvValue.setVisibility(View.VISIBLE);
                tvUnit.setVisibility(View.VISIBLE);
                tvValue.setText(checkResultsBean.ResultValue);
                if (StringUtil.isTrimEmpty(checkResultsBean.ValueRefFormat)) {
                    tvSubtitle.setText("");
                } else {
                    tvSubtitle.setText("参考范围 : " + checkResultsBean.ValueRefFormat);
                }
                if (StringUtil.isTrimEmpty(checkResultsBean.Unit)) {
                    tvUnit.setText("");
                } else {
                    tvUnit.setText("单位 : " + checkResultsBean.Unit);
                }

            }
            return convertView;
        }
    }

    private int numSeleted;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
