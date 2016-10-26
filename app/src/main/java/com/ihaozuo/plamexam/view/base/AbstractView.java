package com.ihaozuo.plamexam.view.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.dialog.CustomDialog;
import com.ihaozuo.plamexam.common.dialog.LoadingDialog;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.ToastUtils;


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
            ToastUtils.showToast(getContext(), msg);
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

}
