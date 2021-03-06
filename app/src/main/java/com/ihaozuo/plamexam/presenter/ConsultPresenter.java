package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.DoctorManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.UserModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {

    private ConsultContract.IConsultView mIConsultView;
    private ConsultModel mConsultModel;
    private UserModel mUserModel;
    private UserBean mUserInfo;
    private String mDoctorID;
    List<ConsultDetailBean> consultDetailList;

    private boolean consultListBoolean;
    private boolean doctorIDBoolean;
    private boolean doctorListBoolean;

    @Inject
    public ConsultPresenter(@NonNull ConsultContract.IConsultView iConsultView, @NonNull ConsultModel consultModel, @NonNull UserModel userModel) {
        consultListBoolean = false;
        doctorIDBoolean = false;
        doctorListBoolean = false;
        mIConsultView = iConsultView;
        mConsultModel = consultModel;
        mUserModel = userModel;
        mIConsultView.setPresenter(this);
        mUserInfo = UserManager.getInstance().getUserInfo();
        mDoctorID = UserManager.getInstance().getDoctorID();
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel, mUserModel};
    }

    @Override
    public void start() {

        if (null != UserManager.getInstance().getDoctorID()) {
            doctorIDBoolean = true;
        }

        if (null != DoctorManager.getInstance().getDoctorList()) {
            doctorListBoolean = true;
        }

        if (!consultListBoolean || !doctorIDBoolean || !doctorListBoolean) {
            mIConsultView.showDialog();
            if (!consultListBoolean) getConsultDetail();
            if (!doctorIDBoolean) getDoctorID();
            if (!doctorListBoolean) getDoctorList();
        } else {
            toggleDialog();
        }
    }

    @Override
    public void getConsultDetail() {
        mConsultModel.getConsultDetail(mUserInfo.AccountId, new OnHandlerResultListener<RestResult<List<ConsultDetailBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<ConsultDetailBean>> resultData) {
                consultDetailList = resultData.Data;
                consultListBoolean = true;
//                removeUnreadMark();
                toggleDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mIConsultView.hideDialog(message);
                mIConsultView.hideRefresh();
            }

        });
    }

    @Override
    public void sendMessage(final int type, final String consultContent) {
        mIConsultView.showDialog();
        mConsultModel.sendMessage(mUserInfo.AccountId, type, consultContent, new OnHandlerResultListener<RestResult<Boolean>>() {

            @Override
            public void handlerResultSuccess(RestResult<Boolean> resultData) {
                mIConsultView.addReply(creatReplayContent(consultContent, type));
                mIConsultView.hideDialog();
                mIConsultView.setBtnSendClickable(true);
            }

            @Override
            public void handlerResultError(String message) {
                mIConsultView.hideDialog(message);
                mIConsultView.setBtnSendClickable(true);
            }
        });
    }

    public void getDoctorID() {
        mUserModel.getDoctorId(mUserInfo.AccountId, new OnHandlerResultListener<RestResult<String>>() {
            @Override
            public void handlerResultSuccess(RestResult<String> resultData) {
                if (resultData.Data != null) {
                    UserManager.getInstance().setDoctorID(resultData.Data);
                }
                doctorIDBoolean = true;
                toggleDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mIConsultView.hideDialog(message);
                mIConsultView.toggleRetryLayer(true);

            }
        });
    }

    public void getDoctorList() {
        mUserModel.getDoctorList(new OnHandlerResultListener<RestResult<List<DoctorInfoBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<DoctorInfoBean>> resultData) {
                if (resultData.Data != null) {
                    DoctorManager.getInstance().setDoctorList(resultData.Data);
                }
                doctorListBoolean = true;
                toggleDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mIConsultView.hideDialog(message);
                mIConsultView.toggleRetryLayer(true);
            }

        });
    }

    @Override
    public void removeUnreadMark(int type) {
        mConsultModel.removeUnreadMark(mUserInfo.AccountId, type, new OnHandlerResultListener<RestResult<Boolean>>() {
            @Override
            public void handlerResultSuccess(RestResult<Boolean> resultData) {
                mIConsultView.removeUnreadMark();
            }

            @Override
            public void handlerResultError(String message) {
                mIConsultView.hideDialog(message);
                mIConsultView.toggleRetryLayer(true);
            }
        });
    }

    public void checkDoctorID(){
        ConsultDetailBean consultDetailBean;
        String doctorID = new String();
        for (int i=consultDetailList.size()-1;i>=0;i--){
            consultDetailBean = consultDetailList.get(i);
            if(consultDetailBean.SourceType==2){
                doctorID = consultDetailBean.DoctorId;
                if (!doctorID.equals(UserManager.getInstance().getDoctorID())){
                    UserManager.getInstance().setDoctorID(doctorID);
                }
                return;
            }
        }
    }



    public void toggleDialog() {
        if (consultListBoolean && doctorIDBoolean && doctorListBoolean) {
            checkDoctorID();
            mIConsultView.setDoctorInfo();
            mIConsultView.refreshConsultList(consultDetailList);
            consultDetailList.clear();
            mIConsultView.hideDialog();
            mIConsultView.toggleRetryLayer(false);

            consultListBoolean = false;
//            doctorIDBoolean = false;
//            doctorListBoolean = false;
        }
    }

    public ConsultDetailBean creatReplayContent(String consultContent, int type) {
        ConsultDetailBean replyContent = new ConsultDetailBean();
        replyContent.AccountId = mUserInfo.AccountId;
        replyContent.SourceType = 1;//客户回复
        replyContent.Content = consultContent;
        replyContent.DoctorId = mDoctorID;
        replyContent.Type = type;
        replyContent.setDate();
        return replyContent;
    }


}
