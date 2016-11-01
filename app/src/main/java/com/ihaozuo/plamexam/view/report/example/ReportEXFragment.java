package com.ihaozuo.plamexam.view.report.example;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportEXFragment extends BaseFragment {

    @Bind(R.id.rb_error_report)
    RadioButton rbError;
    @Bind(R.id.rb_detail_report)
    RadioButton rbDetail;
    @Bind(R.id.rb_advice_report)
    RadioButton rbAdvice;
    @Bind(R.id.groupConsult)
    RadioGroup mRadioGroup;
    @Bind(R.id.viewpager_report)
    ViewPager mViewPager;
    private View rootView;
    private Context mContext;
//    private ReportContract.IReportDetailPresenter mPresenter;
    private List<Fragment> fragList = new ArrayList();
    private ReportDetailBean mReportDetailBean;

    public ReportEXFragment() {
        // Required empty public constructor
    }

    public static ReportEXFragment newInstance() {
        return new ReportEXFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        rootView = inflater.inflate(R.layout.report_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.report));
        ButterKnife.bind(this, rootView);
        initNews();
        initView();
        return rootView;
    }

    public void initNews() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = mContext.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("report.txt")));
            String line;
            while ((line = bf.readLine()) != null) {
                char s =line.trim().charAt(0);
                if(s==65279) {
                    if (line.length() > 1) {
                        line = line.substring(1);
                    }
                }
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = stringBuilder.toString();
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ReportDetailBean>() {}.getType();
        mReportDetailBean = gson.fromJson(json, type);
    }

    private void initView() {
        fragList.add(EXErrorFragment.newInstance(mReportDetailBean));
        fragList.add(EXDetailFragment.newInstance(mReportDetailBean));
        fragList.add(EXAdviceFragment.newInstance(mReportDetailBean.GeneralAdvices));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_error_report) {
                    mViewPager.setCurrentItem(0);
                }
                if (checkedId == R.id.rb_detail_report) {
                    mViewPager.setCurrentItem(1);
                }
                if (checkedId == R.id.rb_advice_report) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });
        PagerAdapter adapter = new ReportPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbError.setChecked(true);
                }
                if (position == 1) {
                    rbDetail.setChecked(true);
                }
                if (position == 2) {
                    rbAdvice.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    public void updateFragment(ReportDetailBean reportDetailBean) {
//        ((EXDetailFragment) fragList.get(1)).initView(reportDetailBean);
//        ((EXErrorFragment) fragList.get(0)).initView(reportDetailBean);
//        ((EXAdviceFragment) fragList.get(2)).initView(reportDetailBean.GeneralAdvices);
//    }

    private class ReportPagerAdapter extends FragmentPagerAdapter {

        public ReportPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragList.clear();
        fragList = null;
    }

}
