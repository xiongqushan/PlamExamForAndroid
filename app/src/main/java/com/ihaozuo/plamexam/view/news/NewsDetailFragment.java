package com.ihaozuo.plamexam.view.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends BaseFragment {


    @Bind(R.id.WebView)
    WebView mWebView;
    private View rootView;

    public NewsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_detail_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.app_name), getString(R.string.androidColorE));
        ButterKnife.bind(this, rootView);
        //initView();
        mWebView.getSettings().setJavaScriptEnabled(true);
        String url = "https://www.baidu.com/";
        mWebView.loadUrl(url);
        return rootView;
    }

    private void initView() {
        WebViewUtils.initWebView(mWebView, getActivity());
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view,
                                          int newProgress) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
