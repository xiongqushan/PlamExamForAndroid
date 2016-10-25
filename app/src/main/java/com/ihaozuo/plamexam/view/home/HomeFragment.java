package com.ihaozuo.plamexam.view.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.ihaozuo.plamexam.common.Constants;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.DaggerHomeComponent;
import com.ihaozuo.plamexam.ioc.HomeModule;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.HomePresenter;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.consult.ConsultDetailActivity;
import com.ihaozuo.plamexam.view.main.MainActivity;
import com.ihaozuo.plamexam.view.news.NewsDetailActivity;
import com.ihaozuo.plamexam.view.news.NewsListActivity;
import com.ihaozuo.plamexam.view.news.NewsListAdapter;
import com.ihaozuo.plamexam.view.report.ReportListActivity;
import com.umeng.socialize.UmengTool;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class HomeFragment extends AbstractView implements HomeContract.IHomeView, View.OnClickListener {
    public static final String FILTER_UPDATEBANNER_HOME = "FILTER_UPDATEBANNER_HOME";

    @Bind(R.id.listview_home)
    ListView mListView;

    @Inject
    HomePresenter mHomePresenter;
    HomeContract.IHomePresenter mPresenter;
    @Bind(R.id.SRLayoutHome)
    SwipeRefreshLayout SRLayout;

    private XBanner mViewPager;
    private Context mContext;
    private int maxLength = 10000;// bannerPagerNumber
    private View rootView;
    private Subscription subscribePager;
    private NewsListAdapter newsListAdapter;
    private List<BannerBean> mBannerList;


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
            registerCustomReceiver(FILTER_UPDATEBANNER_HOME);
//            mPresenter.getBanner(UserManager.getInstance().getUserInfo().DepartCode);
            mPresenter.getBanner("bjbr003");
            UmengTool.getSignature(getActivity());
        }


        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void setPresenter(HomeContract.IHomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @SuppressWarnings({"deprecation", "ResourceAsColor"})
    private void initView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_homelist, null);
        mViewPager = (XBanner) headerView.findViewById(R.id.BannerPager);
        headerView.findViewById(R.id.btn_report).setOnClickListener(this);
        headerView.findViewById(R.id.btn_consult).setOnClickListener(this);
        headerView.findViewById(R.id.layout_home_news).setOnClickListener(this);
        newsListAdapter = new NewsListAdapter(mContext);
        mListView.addHeaderView(headerView);
        mListView.setAdapter(newsListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mListView.getHeaderViewsCount() || HZUtils.isFastDoubleClick()) {
                    return;
                }
                startActivity(new Intent(getActivity(), NewsDetailActivity.class));
            }
        });
        SRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SRLayout.setRefreshing(true);
                SRLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SRLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2500);
            }
        });
        SRLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        if (filterAction.equals(FILTER_UPDATEBANNER_HOME)) {
            mPresenter.getBanner(UserManager.getInstance().getUserInfo().DepartCode);
        }
    }

    @Override
    public void initBanner(final List<BannerBean> sourceList) {
        mBannerList = new ArrayList<BannerBean>();
        mViewPager.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, SimpleDraweeView view, int position) {
                ImageLoadUtils.getInstance(HZApp.shareApplication()).display(mBannerList.get(position).ImageUrl, view, R.drawable.banner);
            }
        });

        mViewPager.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(mContext, "点击了第" + position + "张图片", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, mBannerList.get(position).LinkUrl);
                startActivity(intent);
            }
        });

        if (null == sourceList || sourceList.size() == 0) {
            BannerBean defaultBanner = new BannerBean();
            defaultBanner.ImageUrl = Constants.IMAGEURL_HOMEBANNER_DEFAULT;
            defaultBanner.LinkUrl = Constants.LINKURL_HOMEBANNER_DEFAULT;
            mBannerList.add(defaultBanner);
        } else {
            mBannerList.addAll(sourceList);
        }
        mViewPager.setData(mBannerList);
    }

    @Override
    public void showUnreadMark() {
        sendCustomBroadcast(MainActivity.SHOW_UNREAD_MARK);
    }


    @Override
    public void refreshNewsList(List<NewsBean> newsList) {
        newsListAdapter.refreshList(newsList);
    }


}
