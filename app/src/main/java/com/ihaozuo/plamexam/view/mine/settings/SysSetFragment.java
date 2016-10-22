package com.ihaozuo.plamexam.view.mine.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.common.dialog.SettingsDialog;
import com.ihaozuo.plamexam.manager.DoctorManager;
import com.ihaozuo.plamexam.manager.ReportManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.view.base.BaseFragment;
import com.ihaozuo.plamexam.view.login.LoginActivity;
import com.ihaozuo.plamexam.view.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SysSetFragment extends BaseFragment {


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
    private View rootView;

    public SysSetFragment() {
        // Required empty public constructor
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
                break;
            case R.id.layoutDisclaimer:
                getActivity().startActivity(new Intent(getActivity(), DisclaimerActivity.class));
                break;
            case R.id.layoutClearChache:
                new SettingsDialog(getActivity(), new SettingsDialog.OnDialogListener() {
                    @Override
                    public void OnDialogConfirmListener() {

                    }
                }).setContentText("是否清理缓存？").show();
                break;
            case R.id.btn_logout:
                new SettingsDialog(getActivity(), new SettingsDialog.OnDialogListener() {
                    @Override
                    public void OnDialogConfirmListener() {
                        UserManager.getInstance().clear();
                        DoctorManager.getInstance().clear();
                        ReportManager.getInstance().clear();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        sendCustomBroadcast(MainActivity.FINISHACTIVITY);
                        getActivity().finish();
                    }
                }).setContentText("确定退出登录？").show();
                break;
        }
    }

}
