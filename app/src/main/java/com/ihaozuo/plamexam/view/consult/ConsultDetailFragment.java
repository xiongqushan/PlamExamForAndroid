package com.ihaozuo.plamexam.view.consult;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.Voice2TxtUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.main.MainActivity;
import com.ihaozuo.plamexam.view.report.ReportListActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsultDetailFragment extends AbstractView implements ConsultContract.IConsultView {

    @Bind(R.id.layer_docInfo)
    LinearLayout layerDocInfo;
    @Bind(R.id.img_actionbar_left)
    ImageView imgActionbarLeft;
    @Bind(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @Bind(R.id.actionbar)
    RelativeLayout actionbar;
    @Bind(R.id.img_head)
    SimpleDraweeView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_Speciality)
    TextView tvSpeciality;
    @Bind(R.id.btn_voice)
    ImageView btnVoice;
    @Bind(R.id.pull_refresh_recycler)
    RecyclerView pullRefreshRecycler;
    @Bind(R.id.btn_report)
    ImageView btnReport;
    @Bind(R.id.swipe_Layout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.edittxt_message)
    EditText edittxtMessage;
    @Bind(R.id.fab)
    TextView fab;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    public static boolean isForeground = false;
    public static boolean isLive = false;
    public static final String REFRESH_COSULTD_LIST = "REFRESH_COSULTD_LIST";
    public static final String REFRESH_COSULTD_WITH_REPORT = "REFRESH_COSULTD_WITH_REPORT";
    @Bind(R.id.btn_send)
    ImageView btnSend;


    private View rootView;
    private Context mContext;
    private ConsultContract.IConsultPresenter mPresenter;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private ConsultDetailAdapter mAdapter;
    private DoctorInfoBean mDoctorInfo;
    private Voice2TxtUtils voice2TxtUtils;

    public ConsultDetailFragment() {
    }

    public static ConsultDetailFragment newInstance() {
        return new ConsultDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.consult_frag, container, false);
            mContext = getContext();
            setCustomerTitle(rootView, "健康咨询服务");
        }
        ButterKnife.bind(this, rootView);
        voice2TxtUtils = new Voice2TxtUtils();
        voice2TxtUtils.initSpeechRecognizer(mContext);

        mRecyclerView = pullRefreshRecycler;
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConsultDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
        swipeLayout.setProgressBackgroundColor(R.color.main_color_blue);
//        swipeLayout.setColorSchemeResources(R.color.white);
        swipeLayout.setColorSchemeResources(android.R.color.white,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getConsultDetail();
            }
        });

        //回复框回车监听
        edittxtMessage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        sendMessage();
                    }
                    return true;
                }
                return false;
            }
        });

        registerCustomReceiver(REFRESH_COSULTD_LIST);
        mPresenter.start();
        mPresenter.removeUnreadMark(1);
        return rootView;
    }

    @Override
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        if (REFRESH_COSULTD_LIST.equals(filterAction)) {
            mPresenter.getConsultDetail();
            mPresenter.removeUnreadMark(1);
        }
    }


    @Override
    public void removeUnreadMark() {
        sendCustomBroadcast(MainActivity.REMOVE_UNREAD_MARK);
    }


    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;
        isLive = true;
    }


    @Override
    public void onStop() {
        super.onStop();
        mPresenter.cancelRequest();
        isForeground = false;
    }

    @Override
    public void onDestroyView() {
        voice2TxtUtils.destroyIat();
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        isLive = false;
        super.onDestroy();
    }


    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        mPresenter = presenter;
    }

    @OnClick({R.id.btn_voice, R.id.btn_send, R.id.fab, R.id.btn_report})
    public void onClick(View view) {
        if (HZUtils.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_voice:
                voice2TxtUtils.getVoiceContent(edittxtMessage);
                break;

            case R.id.btn_report:
                startActivity(new Intent(mContext, ReportListActivity.class));
                break;

            case R.id.btn_send:
                sendMessage();
                break;

            case R.id.fab:
                startActivity(new Intent(mContext, ConsultGradeActivity.class));
                break;
        }
    }

    @Override
    public void setDoctorInfo() {
        mDoctorInfo = getDoctorInfo();

        if (null != mDoctorInfo){
             if( null != mDoctorInfo.ImageSrc) {
                ImageLoadUtils.getInstance().display(mDoctorInfo.ImageSrc, imgHead);
             }
            tvName.setText(mDoctorInfo.RealName);
            tvSpeciality.setText(mDoctorInfo.Speciality);
            tvDescription.setText(getString(R.string.workTime));
            fab.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void addReply(ConsultDetailBean replyContent) {
        mAdapter.addReply(replyContent);
        edittxtMessage.setText(null);
        mRecyclerView.smoothScrollToPosition(mRecyclerView.getBottom());
    }

    @Override
    public void refreshConsultList(List<ConsultDetailBean> consultDetailList) {
        hideRefresh();
        mAdapter.refreshList(mContext, consultDetailList, getDoctorInfo());
        mRecyclerView.scrollToPosition(consultDetailList.size() - 1);
    }

    public void sendMessage() {
        String editContent = edittxtMessage.getText().toString();
        if (!StringUtil.isEmpty(editContent)) {
            btnSend.setClickable(false);
            mPresenter.sendMessage(1, editContent);
//            edittxtMessage.setText(null);
        } else {
            Toast.makeText(mContext, "请先输入内容！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setBtnSendClickable(Boolean clickable){
        if (btnSend != null){
            btnSend.setClickable(clickable);
        }
    }

    @Override
    public void toggleRetryLayer(boolean show) {
        if (show) {
            showRetryLayer(R.id.rLayout);
        } else {
            hideRetryLayer(R.id.rLayout);
        }
    }

    @Override
    public void hideRefresh() {
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }


}
