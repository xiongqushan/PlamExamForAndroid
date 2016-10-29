package com.ihaozuo.plamexam.view.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewUtils {
    private static String DIR_CACHE = "PECache";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static void initWebView(WebView mWebView, Context mContext) {
        mWebView.getSettings().setJavaScriptEnabled(true);
       // mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
        // 设置缓存模式
        if (isNetworkAvailable(mContext)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = mContext.getFilesDir().getAbsolutePath()
                + File.separator + DIR_CACHE;
        // 设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        mWebView.getSettings().setAllowFileAccess(true);

    }

    /**
     * 清除WebView缓存
     */
    @SuppressLint("SdCardPath")
    public static void clearWebViewCache(Context mContext) {
        // 清理Webview缓存数据库
        try {
            mContext.deleteDatabase("webview.db");
            mContext.deleteDatabase("webviewCache.db");
            mContext.deleteDatabase("webviewCookiesChromium.db");
            mContext.deleteDatabase("webviewCookiesChromiumPrivate.db");
        } catch (Exception e) {
        }
        // WebView 缓存文件
        File appCacheDir = new File(mContext.getFilesDir().getAbsolutePath()
                + File.separator + DIR_CACHE);
        File webviewCacheDir = new File(mContext.getCacheDir()
                .getAbsolutePath() + "/webviewCache");
        File webviewCacheChromiumDir = new File(mContext.getCacheDir()
                .getAbsolutePath() + "/webviewCacheChromium");
        File newWebviewCacheDir = new File(
                "/data/data/com.yinongjing.xmqx/app_webview");

        // 删除webview 缓存目录
        if (webviewCacheDir.exists()) {
//			FileUtils.delFilesFromPath(webviewCacheDir);
        }
        // 删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
//			FileUtils.delFilesFromPath(appCacheDir);
        }
        // 删除android4.4webview 缓存目录
        if (newWebviewCacheDir.exists()) {
//			FileUtils.delFilesFromPath(newWebviewCacheDir);
        }
        if (webviewCacheChromiumDir.exists()) {
//			FileUtils.delFilesFromPath(webviewCacheChromiumDir);
        }
    }

}
