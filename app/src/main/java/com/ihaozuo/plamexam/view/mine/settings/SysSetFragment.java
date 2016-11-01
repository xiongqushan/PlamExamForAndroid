package com.ihaozuo.plamexam.view.mine.settings;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.VersionInfoBean;
import com.ihaozuo.plamexam.common.UpdateService;
import com.ihaozuo.plamexam.common.dialog.SettingsDialog;
import com.ihaozuo.plamexam.common.dialog.VersionDialog;
import com.ihaozuo.plamexam.contract.SysSetContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.manager.DoctorManager;
import com.ihaozuo.plamexam.manager.ReportManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.ToastUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.login.LoginActivity;
import com.ihaozuo.plamexam.view.main.MainActivity;

import java.text.DecimalFormat;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SysSetFragment extends AbstractView implements SysSetContract.ISysSetView {

    SysSetContract.ISysSetPresenter mPresenter;
    @Bind(R.id.tvSetPhone)
    TextView tvSetPhone;
    @Bind(R.id.layoutSetPhone)
    RelativeLayout layoutSetPhone;
    @Bind(R.id.tvQQState)
    TextView tvQQState;
    @Bind(R.id.tvWeChatState)
    TextView tvWeChatState;
    @Bind(R.id.tvSinaState)
    TextView tvSinaState;
    @Bind(R.id.layoutAboutUs)
    RelativeLayout layoutAboutUs;
    @Bind(R.id.layoutFeedback)
    RelativeLayout layoutFeedback;
    @Bind(R.id.layoutCheckUpdate)
    RelativeLayout layoutCheckUpdate;
    @Bind(R.id.layoutDisclaimer)
    RelativeLayout layoutDisclaimer;
    @Bind(R.id.layoutClearChache)
    RelativeLayout layoutClearChache;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.btn_logout)
    Button btnLogout;
    private View rootView;

    public SysSetFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static SysSetFragment newInstance() {
        return new SysSetFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.set_sys_frag, container, false);
        setCustomerTitle(rootView, getString(R.string.system_set));
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvSetPhone.setText(UserManager.getInstance().getUserInfo().Mobile);
        tvVersion.setText(BuildConfig.VERSION_NAME);
//        tvQQState.setText(getString(R.string.unbind));
//        tvWeChatState.setText(getString(R.string.unbind));
//        tvSinaState.setText(getString(R.string.unbind));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layoutSetPhone,
            R.id.layoutBindQQ,
            R.id.layoutBindWeChat,
            R.id.layoutBindSina,
            R.id.layoutAboutUs,
            R.id.layoutFeedback,
            R.id.layoutCheckUpdate,
            R.id.layoutDisclaimer,
            R.id.layoutClearChache,
            R.id.btn_logout})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
//            case R.id.layoutSetPhone:
//                break;
//            case R.id.layoutBindQQ:
//                break;
//            case R.id.layoutBindWeChat:
//                break;
//            case R.id.layoutBindSina:
//                break;
            case R.id.layoutAboutUs:
                getActivity().startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.layoutFeedback:
                getActivity().startActivity(new Intent(getActivity(), AdviceActivity.class));
                break;
            case R.id.layoutCheckUpdate:
                mPresenter.getVersion();
                break;
            case R.id.layoutDisclaimer:
                getActivity().startActivity(new Intent(getActivity(), DisclaimerActivity.class));
                break;
            case R.id.layoutClearChache:
                new SettingsDialog(getActivity(), new SettingsDialog.OnDialogListener() {
                    @Override
                    public void OnDialogConfirmListener() {
                        clearCache();
                    }

                    @Override
                    public void OnDialogCancelListener() {
                    }
                }).setContentText("是否清理缓存？").show();
                break;
            case R.id.btn_logout:
                new SettingsDialog(getActivity(), new SettingsDialog.OnDialogListener() {
                    @Override
                    public void OnDialogConfirmListener() {
                    }

                    @Override
                    public void OnDialogCancelListener() {
                        UserManager.getInstance().clear();
                        DoctorManager.getInstance().clear();
                        ReportManager.getInstance().clear();
                        sendCustomBroadcast(MainActivity.FINISH_ACTIVITY);
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, ""));
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                }).setContentText("确定退出登录？").setCancelText("确定").setConfirmText("取消").show();
                break;
        }
    }

    @Override
    public void showUpdateInfo(final VersionInfoBean bean) {
        if (bean == null || bean.Status == 2) {//TODO 还能用
            new VersionDialog(getActivity(), new VersionDialog.OnDialogListener() {
                @Override
                public void OnDialogConfirmListener() {
                }

                @Override
                public void OnDialogCancelListener() {
                }
            }).setTitle("已是最新版")
                    .setSubtitle("感谢你的使用!")
                    .show();
            return;
        }
        if (bean.Status == 1) {
            new VersionDialog(getActivity(), new VersionDialog.OnDialogListener() {
                @Override
                public void OnDialogConfirmListener() {
                    if (UpdateService.isLording) {
                        ToastUtils.showToast("正在下载");
                    } else {
                        Intent intent = new Intent(getActivity(), UpdateService.class);
                        intent.putExtra(UpdateService.INTENTKEY_UPDATE_URL,
                                bean.DownLink);
                        getActivity().startService(intent);
                    }
                }

                @Override
                public void OnDialogCancelListener() {
                }
            }).setTitle("检测到更新")
                    .setSubtitle(bean.Message)
                    .setConfirmText("马上下载")
                    .setCancelText("暂不更新")
                    .setCanCancel(false)
                    .show();
        }
    }

//else{//TODO 不能用
//        new VersionDialog(getActivity(),new VersionDialog.OnDialogListener(){
//@Override
//public void OnDialogConfirmListener(){
//        Intent intent=new Intent(getActivity(),UpdateService.class);
//        intent.putExtra(UpdateService.INTENTKEY_UPDATE_URL,
//        bean.DownLink);
//        getActivity().startService(intent);
//        }
//
//@Override
//public void OnDialogCancelListener(){
//        }
//        }).setTitle("检测到更新").setSubtitle("当前版本不再维护,点击确定下载掌上体检最新版")
//        .setCanCancel(false).show();
//        }

    @Override
    public void setPresenter(SysSetContract.ISysSetPresenter presenter) {
        mPresenter = presenter;
    }

    private void clearCache() {
        long cacheSize = Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        String toastMessage;
        if (cacheSize <= 0) {
            toastMessage = "无需清理缓存！";
        } else {
            Fresco.getImagePipeline().clearCaches();
            toastMessage = "本次共清理了 ";
            float cacheSizeTemp1 = changToTwoDecimal(Math.round(cacheSize / 1024));
            float cacheSizeTemp2 = changToTwoDecimal(Math.round((cacheSize / 1024) / 1024));
            if (cacheSizeTemp1 < 1) {
                toastMessage += cacheSize + " B";
            } else if (((cacheSizeTemp1 >= 1) && (cacheSizeTemp2 < 1))) {
                toastMessage += cacheSizeTemp1 + " KB";
            } else if (cacheSizeTemp2 >= 1) {
                toastMessage += cacheSizeTemp2 + " MB";
            }
        }
        Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    static float changToTwoDecimal(float in) {
        DecimalFormat df = new DecimalFormat("0.00");
        String out = df.format(in);
        float result = Float.parseFloat(out);
        return result;
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
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                case MSG_SET_TAGS:
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
}
