package com.ihaozuo.plamexam.view.report;


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
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.UIHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportErrorFragment extends Fragment {


    @Bind(R.id.listview_error_report)
    ListView mListView;
    @Bind(R.id.btnReportError)
    Button btnReportError;
    private View rootView;
    private ListAdapter adapter;
    private List<ReportDetailBean.CheckItemsBean.CheckResultsBean> dataList = new ArrayList<>();
    private Map<Integer, Boolean> checkMap = new ArrayMap<Integer, Boolean>();
    private boolean showCheckBox;
    private ReportFragment mReportFragment;
    private ReportDetailBean mReportDetailBean;

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
        mReportFragment = (ReportFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frameContent);
        ButterKnife.bind(this, rootView);
        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
        return rootView;
    }

    public void initView(ReportDetailBean reportDetailBean) {
        mReportDetailBean = reportDetailBean;
        int CheckItemSize = reportDetailBean.CheckItems.size();
        for (int i = 0; i < CheckItemSize; i++) {
            int CheckResultSize = reportDetailBean.CheckItems.get(i).CheckResults.size();
            for (int j = 0; j < CheckResultSize; j++) {
                if (reportDetailBean.CheckItems.get(i).CheckResults.get(j).IsAbnormalForamt || reportDetailBean.CheckItems.get(i).CheckResults.get(j).IsAbandon) {
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
        showCheckBox = !showCheckBox;
        adapter.notifyDataSetChanged();
        if (showCheckBox) {
            btnReportError.setText("咨询");
        } else {
            StringBuffer selectData = getSelectData();
            Toast.makeText(getActivity(), selectData, Toast.LENGTH_SHORT).show();
            btnReportError.setText("选择异常项咨询");
            if (mReportDetailBean == null) {
                return;
            }
//            mReportFragment.sendMsgForReport(mReportDetailBean, String.valueOf(selectData));
            mReportFragment.sendMsg(mReportDetailBean, String.valueOf(selectData));
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
            if (showCheckBox) {
                cb.setVisibility(View.VISIBLE);
            } else {
                cb.setVisibility(View.GONE);
            }
            cb.setChecked(checkMap.get(position));
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = cb.isChecked();
                    checkMap.put(position, checked);
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
                if (StringUtil.isTrimEmpty(checkResultsBean.LowValueRef)) {
                    tvSubtitle.setText("");
                } else {
                    tvSubtitle.setText("参考范围 : " + checkResultsBean.LowValueRef);
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

    private StringBuffer getSelectData() {
        int num = 0;
        StringBuffer data = new StringBuffer();
        Set<Integer> keySet = checkMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Integer index = iterator.next();
            if (checkMap.get(index)) {
                num++;
                data.append("\n" + "[" + dataList.get(index).CheckIndexName + " : " + dataList.get(index).ResultValue + "]");
//                if (StringUtil.isNotEmpty(dataList.get(index).ValueRefFormat)) {
//                    data.append("  参考值 : " + dataList.get(index).ValueRefFormat + " " + dataList.get(index).Unit);
//                }
            }
        }
        if (num > 0) {
            data.insert(0, "异常 ( " + num + " ) 项 " + ": ");
        }
        return data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
