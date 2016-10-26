package com.ihaozuo.plamexam.view.base;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.dialog.CustomDialog;
import com.ihaozuo.plamexam.common.dialog.LoadingDialog;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.ToastUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * Created by Administrator on 2016/7/5.
 */
@SuppressWarnings("ResourceType")
public abstract class AbstractView extends BaseFragment {
    private LoadingDialog loadingDialog;
    private CustomDialog comfirmDialog;


    protected AbstractView() {
    }

    protected abstract IBasePresenter getPresenter();

    protected abstract View getRootView();

    public void showDialog() {
        showDialog(null);
    }

    public void showDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        if (msg == null || msg.length() == 0) {
            msg = "数据加载中…";
        }
        loadingDialog.setMessage(msg);
        loadingDialog.show();
    }

    public void hideDialog() {
        hideDialog(null);
    }

    public void hideDialog(String msg) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (StringUtil.isNotTrimEmpty(msg)) {
            ToastUtils.showToast(msg);
        }
    }

    public void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(string);
        comfirmDialog.show();
    }


    public void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.setCancelText(cancelText);
        comfirmDialog.show();
    }


    public void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.show();
    }

    private static final int ID_BTNRELOAD = 1357902468;

//    public void showRetryLayer(int frameLayoutContainerId) {
//        showRetryLayer(frameLayoutContainerId,true,getString(R.string.connect_fail));
////        showRetryLayer(frameLayoutContainerId);
//    }

    public void showRetryLayer(int frameLayoutContainerId, String reloadTips) {
        FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        if (btnReload == null) {
            btnReload = LayoutInflater.from(getActivity()).inflate(R.layout.retrylayer_layout, null);
            btnReload.setId(ID_BTNRELOAD);
            TextView tv_reloadTips = (TextView) btnReload.findViewById(R.id.tv_reloadTips);
            tv_reloadTips.setText(reloadTips);
            btnReload.setBackground(null);
            rLayout.addView(btnReload);
        }
    }

    public void showRetryLayer(int frameLayoutContainerId) {
        FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        if (btnReload == null) {
            btnReload = LayoutInflater.from(getActivity()).inflate(R.layout.retrylayer_layout, null);
            btnReload.setId(ID_BTNRELOAD);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (HZUtils.isFastDoubleClick()) {
                        return;
                    }
                    IBasePresenter presenter = getPresenter();
                    presenter.start();
                }
            });
            rLayout.addView(btnReload);
        }
    }

    protected void hideRetryLayer(int frameLayoutContainerId) {
        final FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        if (btnReload != null) {
            rLayout.removeView(btnReload);
        }
    }


    public void setAlias(String alias) {
        if (!isValidTagAndAlias(alias)) {
            return;
        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };
    private static final String TAG = "JPush";
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(HZApp.shareApplication(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    private static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|￥¥]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

}
