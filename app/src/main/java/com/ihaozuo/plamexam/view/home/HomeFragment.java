package com.ihaozuo.plamexam.view.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.common.Banner.XBanner;
import com.ihaozuo.plamexam.common.Constants;
import com.ihaozuo.plamexam.common.SimpleBaseAdapter;
import com.ihaozuo.plamexam.common.SwipeRLayout;
import com.ihaozuo.plamexam.common.dialog.ShareDialog;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBManager;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBPojo;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.DaggerHomeComponent;
import com.ihaozuo.plamexam.ioc.HomeModule;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.HomePresenter;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.util.ToastUtils;
import com.ihaozuo.plamexam.util.UIHelper;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.consult.ConsultDetailActivity;
import com.ihaozuo.plamexam.view.main.MainActivity;
import com.ihaozuo.plamexam.view.news.NewsDetailActivity;
import com.ihaozuo.plamexam.view.news.NewsListActivity;
import com.ihaozuo.plamexam.view.report.ReportListActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class HomeFragment extends AbstractView implements HomeContract.IHomeView, View.OnClickListener {
    public static final String FILTER_UPDATEBANNER_HOME = "FILTER_UPDATEBANNER_HOME";
    private static String DEFAULT_BANNER_URL = "DEFAULT_BANNER_URL";

    @Bind(R.id.listview_home)
    ListView mListView;

    @Inject
    HomePresenter mHomePresenter;
    HomeContract.IHomePresenter mPresenter;
    @Bind(R.id.SRLayoutHome)
    SwipeRLayout SRLayout;

    private XBanner mViewPager;
    private Context mContext;
    private int maxLength = 10000;// bannerPagerNumber
    private View rootView;
    private Subscription subscribePager;
    private ListAdapter newsListAdapter;
    private List<BannerBean> mBannerList;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.startAutoPlay();
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
            mPresenter.getBanner(UserManager.getInstance().getUserInfo().DepartCode);
            mPresenter.getNewsList(1, 4);
            mPresenter.getUnreadMartState(UserManager.getInstance().getUserInfo().AccountId);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.cancelRequest();
        if (SRLayout != null && SRLayout.isRefreshing()) {
            SRLayout.setRefreshing(false);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
        headerView.findViewById(R.id.layoutTJYY).setOnClickListener(this);
        headerView.findViewById(R.id.layoutGHYY).setOnClickListener(this);
        headerView.findViewById(R.id.layoutFXPG).setOnClickListener(this);
        newsListAdapter = new ListAdapter(mContext);
        mListView.addHeaderView(headerView);
        mListView.setAdapter(newsListAdapter);
        List<NewsDBPojo> list = NewsDBManager.queryPojo();
        refreshNewsList(list);
        SRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
            }
        });
        SRLayout.setProgressBackgroundColor(R.color.main_color_blue);
        SRLayout.setColorSchemeResources(android.R.color.white,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        initBannerView();
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
                MobclickAgent.onEvent(getActivity(), "doctorConsult");
                break;
            case R.id.layout_home_news:
                startActivity(new Intent(getActivity(), NewsListActivity.class));
                break;
            case R.id.layoutTJYY:
//                startActivity(new Intent(getActivity(), ExamActivity.class));
//                break;
            case R.id.layoutGHYY:
//                startActivity(new Intent(getActivity(), GuaHaoActivity.class));
//                break;
            case R.id.layoutFXPG:
//                startActivity(new Intent(getActivity(), RiskActivity.class));
                ToastUtils.showToast("敬请期待");
                break;
        }
    }

    @Override
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        if (filterAction.equals(FILTER_UPDATEBANNER_HOME)) {
            mPresenter.getBanner(UserManager.getInstance().getUserInfo().DepartCode);
        }
    }

    public void initBannerView() {
        mBannerList = new ArrayList<BannerBean>();

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mViewPager.measure(w, h);
        int height = mViewPager.getMeasuredHeight();
        int width = mViewPager.getMeasuredWidth();
        final ResizeOptions resizeOptions = new ResizeOptions(width, height);

        mViewPager.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, final SimpleDraweeView view, int position) {
//                view.measure(0,0);
//                ImageLoadUtils.getInstance().display(mBannerList.get(position).ImageUrl, view, R.drawable.banner);
                ImageLoadUtils.getInstance().display(mBannerList.get(position).ImageUrl, view, R.drawable.banner, resizeOptions);
            }
        });

        mViewPager.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                if (DEFAULT_BANNER_URL.equals(mBannerList.get(position).LinkUrl)){
                    return;
                }
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, mBannerList.get(position));
//                intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, mBannerList.get(position).LinkUrl);
                startActivity(intent);
            }
        });

        BannerBean defaultBanner = new BannerBean();
        defaultBanner.ImageUrl = Constants.IMAGEURL_HOMEBANNER_DEFAULT;
        defaultBanner.LinkUrl = DEFAULT_BANNER_URL;
        mBannerList.add(defaultBanner);
        mViewPager.setData(mBannerList);

    }

    @Override
    public void initBanner(final List<BannerBean> sourceList) {
        if (null != sourceList && sourceList.size() > 0) {
            mBannerList.clear();
            mBannerList.addAll(sourceList);
            mViewPager.setData(mBannerList);
        }
    }

    @Override
    public void stopRefreshing() {
        if (SRLayout.isRefreshing()) {
            SRLayout.setRefreshing(false);
        }
    }

    @Override
    public void showUnreadMark() {
        sendCustomBroadcast(MainActivity.SHOW_UNREAD_MARK);
    }


    public void refreshNewsList(List<NewsDBPojo> newsList) {
        newsListAdapter.refreshList(newsList);
    }

    private class ListAdapter extends SimpleBaseAdapter {
        private List<NewsDBPojo> newsList;
        private Context mContext;
        private LayoutInflater mInflater;

        public ListAdapter(Context context) {
            newsList = new ArrayList();
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        public void refreshList(List<NewsDBPojo> list) {
            newsList.clear();
            newsList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_newslist, null);
            }
            SimpleDraweeView imgNewslist = UIHelper.getAdapterView(convertView, R.id.img_newslist);
            TextView tvTitle = UIHelper.getAdapterView(convertView, R.id.tv_title);
            TextView tvCommiton = UIHelper.getAdapterView(convertView, R.id.tv_commiton);
            TextView btnShare = UIHelper.getAdapterView(convertView, R.id.btn_share);
            final NewsDBPojo newsEntity = newsList.get(position);

            ResizeOptions resizeOptions = new ResizeOptions(250, 170);
            ImageLoadUtils.getInstance().display(newsEntity.getImg(), imgNewslist, R.drawable.banner, resizeOptions);
            tvCommiton.setText(newsEntity.getTime());
            tvTitle.setText(newsEntity.getTitle());
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareDialog(mContext, R.style.draw_dialog,
                            newsEntity.getTitle(),
                            newsEntity.getUrl(),
                            newsEntity.getSubtitle(),
                            newsEntity.getImg()
                    ).show();

                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
//                    String url = newsEntity.getUrl();
                    intent.putExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY, newsEntity);
                    mContext.startActivity(intent);
                }
            });
            return convertView;
        }

    }

}
