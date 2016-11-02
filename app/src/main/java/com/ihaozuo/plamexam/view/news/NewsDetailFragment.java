package com.ihaozuo.plamexam.view.news;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.common.dialog.ShareDialog;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBPojo;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailFragment extends AbstractView {


    @Bind(R.id.WebView)
    WebView mWebView;
    @Bind(R.id.img_actionbar_left)
    ImageView imgActionbarLeft;
    @Bind(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @Bind(R.id.tv_addReport)
    TextView tvAddReport;
    @Bind(R.id.actionbar)
    RelativeLayout actionbar;
    private View rootView;
    private String mLinkURL;
    private Context mContext;
    private String mTitle;
    private String mSubTitle;
    private String mImgUrl;

    public NewsDetailFragment() {
        mContext = getContext();
    }

    static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        rootView = inflater.inflate(R.layout.news_detail_frag, container, false);
        setCustomerTitle(rootView, "文章详情");
        ButterKnife.bind(this, rootView);
        showDialog();
        initView();
        String url = "file:///android_asset/newsDetail.html";
        mWebView.loadUrl(url);
//        String url = getActivity().getIntent().getStringExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY);
        Object obj = getActivity().getIntent().getSerializableExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY);
        if (obj instanceof BannerBean) {
            mLinkURL = ((BannerBean) obj).LinkUrl;
        } else if (obj instanceof NewsBean) {
            mLinkURL = SysConfig.NEWS_DETAIL_URL[0] + ((NewsBean) obj).id + SysConfig.NEWS_DETAIL_URL[1];
            mTitle = ((NewsBean) obj).title;
            mSubTitle = ((NewsBean) obj).description;
            mImgUrl = ((NewsBean) obj).imgFormat;
            tvAddReport.setText("分享");
            tvAddReport.setVisibility(View.VISIBLE);
        } else if (obj instanceof NewsDBPojo) {
            mLinkURL = ((NewsDBPojo) obj).getUrl();
            mTitle = ((NewsDBPojo) obj).getTitle();
            mSubTitle = ((NewsDBPojo) obj).getSubtitle();
            mImgUrl = ((NewsDBPojo) obj).getImg();
            tvAddReport.setText("分享");
            tvAddReport.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private String header = "<div>大规模、有组织的抗清武装斗争结束之后，反清思想通过各种形式的文字作品在民间流传，并与以恢复明朝为目的的反清暴动结合起来，使满族统治不得安宁。雍正皇帝曾说：&ldquo;从来异姓先后继统，前朝宗姓臣服于后代者甚多，从未有如本朝奸民，假称朱姓，摇惑人心，若此之众者。&rdquo;清代文字狱泛滥有其特殊的历史原因。</div>";
    private String content = "<div>大规模、有组织的抗清武装斗争结束之后，反清思想通过各种形式的文字作品在民间流传，并与以恢复明朝为目的的反清暴动结合起来，使满族统治不得安宁。雍正皇帝曾说：&ldquo;从来异姓先后继统，前朝宗姓臣服于后代者甚多，从未有如本朝奸民，假称朱姓，摇惑人心，若此之众者。&rdquo;清代文字狱泛滥有其特殊的历史原因。</div>";

    private void initView() {
        WebViewUtils.initWebView(mWebView, getActivity());
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                hideDialog();
                mWebView.loadUrl("javascript:setContent('" + header + "','" + content + "')");
                mWebView.loadUrl("javascript:setData('" + header + "')");
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view,
                                          int newProgress) {
                if (newProgress == 90) {
                    hideDialog();
                }
            }
        });
        mWebView.setOnLongClickListener(null);
    }

    @Override
    public void onDestroyView() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        ButterKnife.unbind(this);
        super.onDestroyView();
    }


    @OnClick(R.id.tv_addReport)
    public void onClick() {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        new ShareDialog(mContext, R.style.draw_dialog, mTitle, mLinkURL, mSubTitle, mImgUrl).show();
    }
}
