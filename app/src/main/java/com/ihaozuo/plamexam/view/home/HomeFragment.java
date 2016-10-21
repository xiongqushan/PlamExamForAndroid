package com.ihaozuo.plamexam.view.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.common.Banner.XBanner;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.DaggerHomeComponent;
import com.ihaozuo.plamexam.ioc.HomeModule;
import com.ihaozuo.plamexam.presenter.HomePresenter;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.consult.ConsultDetailActivity;
import com.ihaozuo.plamexam.view.news.NewsDetailActivity;
import com.ihaozuo.plamexam.view.news.NewsListActivity;
import com.ihaozuo.plamexam.view.news.NewsListAdapter;
import com.ihaozuo.plamexam.view.report.ReportListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class HomeFragment extends AbstractView implements HomeContract.IHomeView, View.OnClickListener {

    @Bind(R.id.listview_home)
    ListView mListView;

    @Inject
    HomePresenter mHomePresenter;
    HomeContract.IHomePresenter mPresenter;

    XBanner mViewPager;
    private Context mContext;
    private int maxLength = 10000;// bannerPagerNumber
    private View rootView;
    private Subscription subscribePager;
    private NewsListAdapter newsListAdapter;
    private List<BannerBean> mBannerList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            rootView = inflater.inflate(R.layout.home_frag, container, false);
            setCustomerTitle(rootView, getString(R.string.app_name));
            mBannerList = new ArrayList<BannerBean>();
            ButterKnife.bind(this, rootView);
            DaggerHomeComponent.builder()
                    .appComponent(HZApp.shareApplication()
                    .getAppComponent())
                    .homeModule(new HomeModule(this))
                    .build()
                    .inject(this);

            initView();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
//        startAutoBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
//        stopAutoBanner();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(HomeContract.IHomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

//    public void startAutoBanner() {
//        if (mViewPager.getChildCount() < 2 || mViewPager == null) {
//            return;
//        }
//        stopAutoBanner();
//        subscribePager = Observable.interval(Constants.TIME_DELAY_VIEWPAGER, TimeUnit.MILLISECONDS)
//                .compose(AbstractModel.<Long>applyAsySchedulers())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        if (!isDrag) {
//                            int index = mViewPager.getCurrentItem() + 1;
//                            mViewPager.setCurrentItem(index);
//                            Toast.makeText(getActivity(), "aLong=" + aLong, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    public void stopAutoBanner() {
//        if (subscribePager != null && !subscribePager.isUnsubscribed()) {
//            subscribePager.unsubscribe();
//        }
//    }


    private void initView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_homelist, null);
//        mViewPager = (AutoViewPager) headerView.findViewById(R.id.BannerPager);
        mViewPager = (XBanner) headerView.findViewById(R.id.BannerPager);
        headerView.findViewById(R.id.btn_report).setOnClickListener(this);
        headerView.findViewById(R.id.btn_consult).setOnClickListener(this);
        headerView.findViewById(R.id.layout_home_news).setOnClickListener(this);
//        initBanner(null);
        mPresenter.getBanner(123);

        newsListAdapter = new NewsListAdapter(mContext);
        mListView.addHeaderView(headerView);
        mListView.setAdapter(newsListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mListView.getHeaderViewsCount() || HZUtils.isFastDoubleClick()) {
                    return;
                }
                startActivity( new Intent(getActivity(), NewsDetailActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_report:
                startActivity(new Intent(mContext, ReportListActivity.class));
                break;
            case R.id.btn_consult:
//                mPresenter.removeUnreadMark(UserManager.getInstance().getUserInfo().AccountId);
                startActivity(new Intent(mContext, ConsultDetailActivity.class));
                break;
            case R.id.layout_home_news:
                startActivity(new Intent(getActivity(), NewsListActivity.class));
                break;
        }
    }

    @Override
    public void initBanner(final List<BannerBean> bannerList) {
//        PagerAdapter adapterPager = new HomePagerAdapter(getChildFragmentManager(), bannerList);
//        mViewPager.setAdapter(adapterPager);

        for (int i = 0;i<4;i++){
            BannerBean bannerBean = new BannerBean();
            bannerBean.ImageUrl = "http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg";
            mBannerList.add(bannerBean);
        }
        mViewPager.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, SimpleDraweeView view, int position) {
                ImageLoadUtils.getInstance(HZApp.shareApplication()).display(mBannerList.get(position).ImageUrl,view,R.drawable.banner);
            }
        });

        mViewPager.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(mContext,"点击了第"+position+"张图片",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY,mBannerList.get(position).LinkUrl);
                startActivity(intent);
            }
        });

        mViewPager.setData(mBannerList);

//        if (bannerList != null && bannerList.size() > 1) {
//            mViewPager.setCurrentItem((bannerList.size() * maxLength) / 2, false);
//        }
//        startAutoBanner();
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int arg0) {
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//                switch (arg0) {
//                    case ViewPager.SCROLL_STATE_DRAGGING:
//                        isDrag = true;
//                        break;
//                    case ViewPager.SCROLL_STATE_IDLE:
//                        isDrag = false;
//                        break;
//                    case ViewPager.SCROLL_STATE_SETTLING:
//                        isDrag = false;
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void showUnreadMark() {

    }

    //    @Override
    public void hideUnreadMark() {

    }


    @Override
    public void refreshNewsList(List<NewsBean> newsList) {
        newsListAdapter.refreshList(newsList);
    }


//    private class HomePagerAdapter extends FragmentPagerAdapter {
//
//        private List<BannerBean> bannerList;
//
//        public HomePagerAdapter(FragmentManager fm, List<BannerBean> bannerList) {
//            super(fm);
//            this.bannerList = bannerList;
//        }
//
//        @Override
//        public int getCount() {
//            if (bannerList == null || bannerList.size() < 2) {
//                return 1;
//            }
//            return bannerList.size() * maxLength;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if (bannerList == null || bannerList.size() < 2) {
//                return BannerFragment.newInstance(bannerList, 0);
//            }
//            return BannerFragment.newInstance(bannerList, position % bannerList.size());
//        }
//
//    }

}
