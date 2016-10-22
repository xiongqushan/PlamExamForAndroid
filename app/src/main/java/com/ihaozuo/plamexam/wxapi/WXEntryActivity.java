package com.ihaozuo.plamexam.wxapi;


import android.os.Bundle;
import android.os.PersistableBundle;

import com.umeng.socialize.UmengTool;
import com.umeng.socialize.weixin.view.WXCallbackActivity;


public class WXEntryActivity extends WXCallbackActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        UmengTool.getSignature(this);
    }
}
