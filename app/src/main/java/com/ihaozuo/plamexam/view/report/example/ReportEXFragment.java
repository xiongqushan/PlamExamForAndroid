package com.ihaozuo.plamexam.view.report.example;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

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

    public ReportEXFragment() {
        // Required empty public constructor
    }

    public static ReportEXFragment newInstance() {
        return new ReportEXFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.report_example));
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }


    private void initView() {
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

      //  PagerAdapter adapter = new ReportPagerAdapter(getChildFragmentManager());
//        mViewPager.setAdapter(adapter);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    rbError.setChecked(true);
//                }
//                if (position == 1) {
//                    rbDetail.setChecked(true);
//                }
//                if (position == 2) {
//                    rbAdvice.setChecked(true);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }


//    private class ReportPagerAdapter extends FragmentPagerAdapter {
//
//        public ReportPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if (position == 0) {
//                return EXErrorFragment.newInstance();
//            }
//            if (position == 1) {
//                return EXDetailFragment.newInstance();
//            }
//            return EXAdviceFragment.newInstance();
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

}
