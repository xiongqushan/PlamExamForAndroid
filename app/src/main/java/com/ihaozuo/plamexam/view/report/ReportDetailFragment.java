package com.ihaozuo.plamexam.view.report;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.common.pinnedheaderlistview.PinnedHeaderListView;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportDetailFragment extends Fragment {


    @Bind(R.id.listview_report_detail_slide)
    ListView mListViewSlide;
    @Bind(R.id.listview_report_detail)
    PinnedHeaderListView mListView;
    @Bind(R.id.layoutPinnedHeader)
    View layoutPinnedHeader;
    @Bind(R.id.divider_report_detail)
    View dividerList;
    private View rootView;
    private boolean showSlideList;
    private ReportDetailBean mReportDetailBean;

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
        mReportDetailBean = bean;
        ReportDetailAdapter adapter = new ReportDetailAdapter(bean);
        mListView.setPinHeaders(true);
        mListView.setAdapter(adapter);
        if (bean.CheckItems.size() > 0) {
            mListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                }

                @Override
                public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                    toggleListview();
                }
            });
            layoutPinnedHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleListview();
                }
            });
            initSlideList(bean.CheckItems);
        }

    }

    private void toggleListview() {
        showSlideList = !showSlideList;
        if (showSlideList) {
            mListViewSlide.setVisibility(View.VISIBLE);
            dividerList.setVisibility(View.VISIBLE);
        } else {
            mListViewSlide.setVisibility(View.GONE);
            dividerList.setVisibility(View.GONE);
        }
    }

    private int CheckResultsSize;

    private void initSlideList(List<ReportDetailBean.CheckItemsBean> checkItems) {

        final SlideListAdapter adapterSlide = new SlideListAdapter(checkItems);
        mListViewSlide.setAdapter(adapterSlide);
        mListViewSlide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterSlide.setSelectedPosition(position);
                int index = 0;
                for (int i = 0; i < position; i++) {
                    if (mReportDetailBean.CheckItems.get(i).CheckResults != null){
                        CheckResultsSize = mReportDetailBean.CheckItems.get(i).CheckResults.size();
                    }else {
                        CheckResultsSize = 0;
                    }

                    if (StringUtil.isEmpty(mReportDetailBean.CheckItems.get(i).SummaryFormat)) {
                        index += CheckResultsSize + 1;
                    } else {
                        index += CheckResultsSize + 2;
                    }
                }
                mListView.setSelection(index);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int position = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (position != firstVisibleItem) {
                    position = firstVisibleItem;
                    int section = mListView.mAdapter.getSectionForPosition(firstVisibleItem);
                    adapterSlide.setSelectedPosition(section);
                    if ((section < mListViewSlide.getFirstVisiblePosition() || section > mListViewSlide.getLastVisiblePosition())) {
                        mListViewSlide.setSelectionFromTop(section, 0);
                    }
                }

            }
        });
    }

    private class SlideListAdapter extends SimpleBaseAdapter {
        private List<ReportDetailBean.CheckItemsBean> list = new ArrayList<ReportDetailBean.CheckItemsBean>();
        private int selectedPosition = 0;

        public SlideListAdapter(List<ReportDetailBean.CheckItemsBean> checkItems) {
            list.addAll(checkItems);
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reportlist_detail_silide, null);
            }
            TextView tvName = UIHelper.getAdapterView(convertView, R.id.tvName);
            TextView tvMark = UIHelper.getAdapterView(convertView, R.id.tvMark);
            tvName.setText(list.get(position).CheckItemName);
            if (selectedPosition == position) {
                tvName.setTextColor(Color.parseColor("#FF309BF2"));
                tvMark.setBackgroundColor(Color.parseColor("#FF309BF2"));
            } else {
                tvName.setTextColor(Color.parseColor("#FF333333"));
                tvMark.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
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
