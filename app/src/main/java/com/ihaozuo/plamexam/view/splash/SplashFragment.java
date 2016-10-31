package com.ihaozuo.plamexam.view.splash;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.VersionInfoBean;
import com.ihaozuo.plamexam.common.UpdateService;
import com.ihaozuo.plamexam.common.dialog.VersionDialog;
import com.ihaozuo.plamexam.contract.SplashContract;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBManager;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.guide.GuideActivity;
import com.ihaozuo.plamexam.view.login.LoginActivity;
import com.ihaozuo.plamexam.view.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashFragment extends AbstractView implements SplashContract.ISplashView {
    private final long TURNTIMEDELAY = 2500;
    private long lastTime;

    private View rootView;
    private SplashContract.ISplashPresenter mPresenter;

    public SplashFragment() {
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

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.splash_frag, container, false);
            lastTime = System.currentTimeMillis();
            if (PreferenceManager.getInstance().readNewsState()) {
                NewsDBManager.initNews(getActivity());
                PreferenceManager.getInstance().writeNewsState(false);
            }
//            if (ConnectedUtils.isConnected(getActivity().getApplicationContext())) {
//                mPresenter.start();
//            } else {
            turnNextAty();
//            }
        }

        return rootView;
    }

    @Override
    public void updateInfo(final VersionInfoBean bean) {
        if (bean.Status != 0) {//TODO 还能用
            if (StringUtil.isTrimEmpty(bean.Message)) {
                turnNextAty();
            } else {
                new VersionDialog(getActivity(), new VersionDialog.OnDialogListener() {
                    @Override
                    public void OnDialogConfirmListener() {
                        Intent intent = new Intent(getActivity(), UpdateService.class);
                        intent.putExtra(UpdateService.INTENTKEY_UPDATE_URL,
                                bean.DownLink);
                        getActivity().startService(intent);
                        turnNextAty();
                    }

                    @Override
                    public void OnDialogCancelListener() {
                        turnNextAty();
                    }
                }).setTitle("检测到更新")
                        .setSubtitle(bean.Message)
                        .setConfirmText("马上下载")
                        .setCancelText("暂不更新")
                        .setCanCancel(false)
                        .show();
            }

        } else {//TODO 不能用
            new VersionDialog(getActivity(), new VersionDialog.OnDialogListener() {
                @Override
                public void OnDialogConfirmListener() {
                    //TODO 跳AppStore，没有就关闭 待修改为跳转到固定的下载界面
                    Intent intent = new Intent(getActivity(), UpdateService.class);
                    intent.putExtra(UpdateService.INTENTKEY_UPDATE_URL,
                            bean.DownLink);
                    getActivity().startService(intent);
                    getActivity().finish();
                }

                @Override
                public void OnDialogCancelListener() {
                }
            }).setTitle("检测到更新").setSubtitle("当前版本不再维护,点击确定下载掌上体检最新版")
                    .setCanCancel(false).show();
        }
    }

    @Override
    public void turnNextAty() {
        TimerTask task = new TimerTask() {
            public void run() {
                turnAction();
            }
        };
        Timer timer = new Timer();
        long time = System.currentTimeMillis();
        long requestTime = time - lastTime;
        if (requestTime < TURNTIMEDELAY) {
            timer.schedule(task, TURNTIMEDELAY - requestTime);
        } else {
            turnAction();
        }
    }

    private void turnAction() {
        if (PreferenceManager.getInstance().readGuideState()) {
            if (UserManager.getInstance().exist()) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        } else {
            PreferenceManager.getInstance().writeGuideState(true);
            startActivity(new Intent(getActivity(), GuideActivity.class));
        }

        getActivity().finish();
    }

    @Override
    public void setPresenter(SplashContract.ISplashPresenter presenter) {
        mPresenter = presenter;
    }
}
