package com.ihaozuo.plamexam.view.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.BannerFragment;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.consult.ConsultActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends AbstractView implements HomeContract.IHomeView {
    @Bind(R.id.btn_consult)
    View btnConsult;
    private Context mContext;
    private boolean isDrag;
    private boolean isStop;
    private int maxLength = 10000;// bannerPagerNumber
    @Bind(R.id.BannerPager)
    ViewPager mViewPager;
    private View rootView;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setPresenter(HomeContract.IHomePresenter presenter) {

    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        isStop = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isStop = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            rootView = inflater.inflate(R.layout.home_frag, container, false);
            setCustomerTitle(rootView, getString(R.string.app_name));
            ButterKnife.bind(this, rootView);
            initView();
            autoBanner();
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void autoBanner() {
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isDrag && !isStop) {
                    int index = mViewPager.getCurrentItem() + 1;
                    mViewPager.setCurrentItem(index);
                }
                mViewPager.postDelayed(this, 4000);
            }
        }, 4000);
    }

    private void initView() {
        PagerAdapter adapterPager = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapterPager);
        mViewPager.setCurrentItem(maxLength / 2, false);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isDrag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        isDrag = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isDrag = false;
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_consult)
    public void goConsult() {
        startActivity(new Intent(mContext, ConsultActivity.class));
    }

    private class HomePagerAdapter extends FragmentPagerAdapter {
        private HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return maxLength;
        }

        @Override
        public Fragment getItem(int position) {
            return BannerFragment.newInstance(position % 5);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
