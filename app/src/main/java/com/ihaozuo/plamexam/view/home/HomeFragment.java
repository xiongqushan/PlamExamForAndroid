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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.common.BannerFragment;
import com.ihaozuo.plamexam.common.Constants;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.DaggerHomeComponent;
import com.ihaozuo.plamexam.ioc.HomeModule;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.AbstractModel;
import com.ihaozuo.plamexam.presenter.HomePresenter;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.consult.ConsultDetailActivity;
import com.ihaozuo.plamexam.view.news.NewsDetailActivity;
import com.ihaozuo.plamexam.view.news.NewsListActivity;
import com.ihaozuo.plamexam.view.report.ReportListActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class HomeFragment extends AbstractView implements HomeContract.IHomeView, View.OnClickListener {

    @Bind(R.id.listview_home)
    ListView mListView;

    @Inject
    HomePresenter mHomePresenter;
    HomeContract.IHomePresenter mPresenter;

    ViewPager mViewPager;
    private Context mContext;
    private boolean isDrag;
    private int maxLength = 10000;// bannerPagerNumber
    private View rootView;
    private Subscription subscribePager;
    private List newsList;


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

    @Override
    public void onPause() {
        super.onPause();
        stopAutoBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.getUnreadMartState(UserManager.getInstance().getUserInfo().AccountId);
        mPresenter.removeUnreadMark(UserManager.getInstance().getUserInfo().AccountId);
        // mPresenter.getBanner(123);
        startAutoBanner();
    }

    public void startAutoBanner() {
        if (mViewPager.getChildCount() < 2 || mViewPager == null) {
            return;
        }
        stopAutoBanner();
        subscribePager = Observable.interval(Constants.TIME_DELAY_VIEWPAGER, TimeUnit.MILLISECONDS)
                .compose(AbstractModel.<Long>applyAsySchedulers())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (!isDrag) {
                            int index = mViewPager.getCurrentItem() + 1;
                            mViewPager.setCurrentItem(index);
                            Toast.makeText(getActivity(), "aLong=" + aLong, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void stopAutoBanner() {
        if (subscribePager != null && !subscribePager.isUnsubscribed()) {
            subscribePager.unsubscribe();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            rootView = inflater.inflate(R.layout.home_frag, container, false);
            setCustomerTitle(rootView, getString(R.string.app_name));
            ButterKnife.bind(this, rootView);
            DaggerHomeComponent.builder().appComponent(HZApp.shareApplication()
                    .getAppComponent()).homeModule(new HomeModule(this)).build().inject(this);
            initView();
            mPresenter.getBanner(UserManager.getInstance().getUserInfo().DepartId);
        }
        return rootView;
    }


    private void initView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_homelist, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.BannerPager);
        headerView.findViewById(R.id.btn_report).setOnClickListener(this);
        headerView.findViewById(R.id.btn_consult).setOnClickListener(this);
        headerView.findViewById(R.id.layout_home_news).setOnClickListener(this);
        initBanner(null);
        BaseAdapter adapter = new SimpleBaseAdapter() {
            @Override
            public int getCount() {
                if (newsList == null) {
                    return 4;
                }
                if (newsList.size() > 4) {
                    return 4;
                }
                return newsList.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_newslist, null);
                }
                return convertView;
            }
        };
        mListView.addHeaderView(headerView);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mListView.getHeaderViewsCount() || HZUtils.isFastDoubleClick()) {
                    return;
                }
                startActivity(new Intent(getActivity(), NewsDetailActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
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
                startActivity(new Intent(mContext, ConsultDetailActivity.class));
                break;
            case R.id.layout_home_news:
                startActivity(new Intent(getActivity(), NewsListActivity.class));
                break;
        }
    }

    @Override
    public void initBanner(List<BannerBean> bannerList) {
        PagerAdapter adapterPager = new HomePagerAdapter(getChildFragmentManager(), bannerList);
        mViewPager.setAdapter(adapterPager);
        if (bannerList != null && bannerList.size() > 1) {
            mViewPager.setCurrentItem((bannerList.size() * maxLength) / 2, false);
        }
        startAutoBanner();
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
    public void initNews(List<NewsBean> newsList) {

    }

    @Override
    public void showUnreadMark() {

    }

    @Override
    public void hideUnreadMark() {

    }


    private class HomePagerAdapter extends FragmentPagerAdapter {

        private List<BannerBean> bannerList;

        public HomePagerAdapter(FragmentManager fm, List<BannerBean> bannerList) {
            super(fm);
            this.bannerList = bannerList;
        }

        @Override
        public int getCount() {
            if (bannerList == null || bannerList.size() < 2) {
                return 1;
            }
            return bannerList.size() * maxLength;
        }

        @Override
        public Fragment getItem(int position) {
            if (bannerList == null || bannerList.size() < 2) {
                return BannerFragment.newInstance(bannerList, 0);
            }
            return BannerFragment.newInstance(bannerList, position % bannerList.size());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
