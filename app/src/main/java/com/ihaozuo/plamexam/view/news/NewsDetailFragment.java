package com.ihaozuo.plamexam.view.news;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailFragment extends AbstractView {


    @Bind(R.id.WebView)
    WebView mWebView;
    private View rootView;

    public NewsDetailFragment() {
        // Required empty public constructor
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
        rootView = inflater.inflate(R.layout.news_detail_frag, container, false);
        setCustomerTitle(rootView, "文章详情");
        ButterKnife.bind(this, rootView);
        initView();
        String url = getActivity().getIntent().getStringExtra(NewsDetailActivity.URL_NEWSDETAILACTIVITY);
        if (url == null) {
            url = getString(R.string.url_default);
        }
        showDialog();
        mWebView.loadUrl(url);

//        mWebView.setOnKeyListener(new View.OnKeyListener() {
//           @Override
//           public boolean onKey(View v, int keyCode, KeyEvent event) {
//               if ((keyCode == android.view.KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//                   mWebView.goBack();
//                   return true;
//               }
//               return false;
//           }
//        });

        return rootView;
    }

    private void initView() {
        WebViewUtils.initWebView(mWebView, getActivity());
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                hideDialog();
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
        mWebView.setOnTouchListener(null);
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


}
