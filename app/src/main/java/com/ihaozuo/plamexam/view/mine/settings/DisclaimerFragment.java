package com.ihaozuo.plamexam.view.mine.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.view.base.BaseFragment;


public class DisclaimerFragment extends BaseFragment {


    private View rootView;

    public DisclaimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.statement_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.disclaimer));
        WebView webView = (WebView) rootView.findViewById(R.id.webview);
        // 加载assets目录下的文件
        webView.loadUrl("file:///android_asset/disclaimer.html");
        return rootView;
    }

}
