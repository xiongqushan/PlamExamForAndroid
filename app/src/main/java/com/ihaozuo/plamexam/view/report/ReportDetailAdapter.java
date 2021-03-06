package com.ihaozuo.plamexam.view.report;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.common.pinnedheaderlistview.SectionedBaseAdapter;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.UIHelper;


public class ReportDetailAdapter extends SectionedBaseAdapter {

    private ReportDetailBean datalist;

    public ReportDetailAdapter(ReportDetailBean bean) {
        datalist = bean;
    }

    public void refresh(ReportDetailBean bean) {
        datalist = bean;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        if (datalist.CheckItems == null) return 0;
        return datalist.CheckItems.size();
    }

    @Override
    public int getCountForSection(int section) {
        if (datalist.CheckItems.get(section).CheckResults == null) return 0;
        String summaryFormat = datalist.CheckItems.get(section).SummaryFormat;
        if (StringUtil.isEmpty(summaryFormat)) {
            return datalist.CheckItems.get(section).CheckResults.size();
        }
        return datalist.CheckItems.get(section).CheckResults.size() + 1;
    }


    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_reportlist_detail_child, null);
        }
        View layoutSummary = UIHelper.getAdapterView(convertView, R.id.content_summary);
        View layoutComment = UIHelper.getAdapterView(convertView, R.id.content_comment);
        if (position == datalist.CheckItems.get(section).CheckResults.size()) {
            layoutSummary.setVisibility(View.VISIBLE);
            layoutComment.setVisibility(View.GONE);
            TextView tvSummary = UIHelper.getAdapterView(convertView, R.id.tvSummary);
            if (datalist.CheckItems.get(section).IsAbnormal) {
                tvSummary.setTextColor(Color.parseColor("#FFF25353"));
            } else {
                tvSummary.setTextColor(Color.parseColor("#FF333333"));
            }
            tvSummary.setText("小结 : " + datalist.CheckItems.get(section).SummaryFormat);
        } else {
            layoutSummary.setVisibility(View.GONE);
            layoutComment.setVisibility(View.VISIBLE);
            TextView tvTitle = UIHelper.getAdapterView(convertView, R.id.tvTitle);
            TextView tvSubtitle = UIHelper.getAdapterView(convertView, R.id.tvSubtitle);
            TextView tvValue = UIHelper.getAdapterView(convertView, R.id.tvResultValue);
            TextView tvUnit = UIHelper.getAdapterView(convertView, R.id.tvUnit);

            ReportDetailBean.CheckItemsBean.CheckResultsBean checkResultsBean = datalist.CheckItems.get(section).CheckResults.get(position);
            tvTitle.setText(checkResultsBean.CheckIndexName);
            if (checkResultsBean.IsAbnormalForamt) {
                tvValue.setTextColor(Color.parseColor("#FFF25353"));
            } else {
                tvValue.setTextColor(Color.parseColor("#FF333333"));
            }
            boolean unitEmpty = StringUtil.isTrimEmpty(checkResultsBean.Unit);
            boolean rangeEmpty = StringUtil.isTrimEmpty(checkResultsBean.ValueRefFormat);
            if (unitEmpty && rangeEmpty) {
                tvValue.setVisibility(View.GONE);
                tvUnit.setVisibility(View.GONE);
                if (StringUtil.isEmpty(checkResultsBean.ResultValue)) {
                    tvSubtitle.setText("未见异常");
                } else {
                    tvSubtitle.setText(checkResultsBean.ResultValue);
                }
                if (checkResultsBean.IsAbnormalForamt) {
                    tvSubtitle.setTextColor(Color.parseColor("#FFF25353"));
                } else {
                    tvSubtitle.setTextColor(Color.parseColor("#FF666666"));
                }
            } else {
                tvValue.setVisibility(View.VISIBLE);
                tvUnit.setVisibility(View.VISIBLE);
                tvValue.setText(checkResultsBean.ResultValue);
                tvSubtitle.setTextColor(Color.parseColor("#FF666666"));
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
        }
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, final ViewGroup parent) {
        View layout = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.item_reportlist_detail, null);
        } else {
            layout = convertView;
        }
        TextView tvTitle = UIHelper.getAdapterView(layout, R.id.tvReportMainItem);
        tvTitle.setText(datalist.CheckItems.get(section).CheckItemName);
        return layout;
    }

}
