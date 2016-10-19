package com.ihaozuo.plamexam.view.consult;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.Voice2TxtUtils;
import com.ihaozuo.plamexam.view.base.AbstractView;

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
    @Bind(R.id.img_actionbar_right)
    ImageView imgActionbarRight;
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

    private View rootView;
    private Context mContext;
    private ConsultContract.IConsultPresenter mIConsultPresenter;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private ConsultDetailAdapter mAdapter;
    private DoctorInfoBean mDoctorInfo;

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
        Voice2TxtUtils.initSpeechRecognizer(mContext);

        mRecyclerView = pullRefreshRecycler;
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConsultDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
        swipeLayout.setProgressBackgroundColor(R.color.main_color_blue);
        swipeLayout.setColorSchemeResources(R.color.white);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 mIConsultPresenter.getConsultDetail();
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

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIConsultPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Voice2TxtUtils.destroyIat();
    }


    @Override
    protected IBasePresenter getPresenter() {
        return mIConsultPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        mIConsultPresenter = presenter;
    }

    @OnClick({R.id.btn_voice, R.id.btn_send, R.id.fab, R.id.btn_report})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_voice:
                Voice2TxtUtils.getVoiceContent(edittxtMessage);
                break;

            case R.id.btn_report:

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
        if (null != mDoctorInfo.ImageSrc) {
            ImageLoadUtils.getInstance(mContext).display(mDoctorInfo.ImageSrc, imgHead);
        }
        tvName.setText(mDoctorInfo.RealName);
        tvSpeciality.setText(mDoctorInfo.Speciality);
    }

    @Override
    public void addReply(ConsultDetailBean replyContent) {
        mAdapter.addReply(replyContent);
        edittxtMessage.setText(null);
        mRecyclerView.smoothScrollToPosition(mRecyclerView.getBottom());
    }

    @Override
    public void refreshConsultList(List<ConsultDetailBean> consultDetailList) {
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
        mAdapter.refreshList(mContext, consultDetailList, getDoctorInfo());
        mRecyclerView.scrollToPosition(consultDetailList.size());
    }

    public void sendMessage(){
        String editContent = edittxtMessage.getText().toString();
        if (!StringUtil.isEmpty(editContent)) {
            mIConsultPresenter.sendMessage(1, editContent);
        } else {
            Toast.makeText(mContext, "请先输入内容！", Toast.LENGTH_LONG).show();
        }
    };


}
