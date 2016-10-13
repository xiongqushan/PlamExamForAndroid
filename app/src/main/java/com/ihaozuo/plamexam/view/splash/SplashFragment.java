package com.ihaozuo.plamexam.view.splash;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.UpdateInfoBean;
import com.ihaozuo.plamexam.contract.SplashContract;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.view.base.AbstractView;
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
//            if (ConnectedUtils.isConnected(getActivity().getApplicationContext())) {
//                mPresenter.start();
//            } else {
            turnNextAty();
//            }
        }
        return rootView;
    }

    @Override
    public void updateInfo(final UpdateInfoBean bean) {
        if (bean.IsValid) {//TODO 还能用
            if (StringUtil.isTrimEmpty(bean.Notification)) {
                turnNextAty();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("检测到更新")
                        .setCancelable(false)
                        .setMessage(bean.Notification)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                turnNextAty();
                            }
                        }).show();
            }

        } else {//TODO 不能用
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("检测到更新，当前版本不再维护")
                    .setCancelable(false)
                    .setMessage(bean.Notification)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO 跳AppStore，没有就关闭 待修改为跳转到固定的下载界面
                            HZUtils.turnStore(getActivity());
                            getActivity().finish();
                        }
                    }).show();
        }
    }

    @Override
    public void turnNextAty() {
        TimerTask task = new TimerTask() { public void run() { turnAction();  }  };
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
        if (UserManager.getInstance().exist()) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        getActivity().finish();
    }

    @Override
    public void setPresenter(SplashContract.ISplashPresenter presenter) {
        mPresenter = presenter;
    }
}
