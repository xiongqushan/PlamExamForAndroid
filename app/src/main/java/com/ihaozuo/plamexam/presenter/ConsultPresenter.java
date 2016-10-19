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

import java.util.ArrayList;
import java.util.Date;
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
    public ConsultPresenter(@NonNull ConsultContract.IConsultView iConsultView, @NonNull ConsultModel consultModel, @NonNull UserModel userModel){
        consultListBoolean = false;
        doctorIDBoolean = false;
        doctorListBoolean = false;
        consultDetailList = new ArrayList<>();

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
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {
        mIConsultView.showDialog();

        if (null != UserManager.getInstance().getDoctorID()){
            doctorIDBoolean = true;
        }
        if (null != DoctorManager.getInstance().getDoctorList()){
            doctorListBoolean = true;
        }
        if (!consultListBoolean) getConsultDetail();
        if (!doctorIDBoolean) getDoctorID();
        if (!doctorListBoolean) getDoctorList();
    }

    @Override
    public void getConsultDetail(){
        mConsultModel.getConsultDetail(mUserInfo.AccountId, new OnHandlerResultListener<RestResult<List<ConsultDetailBean>>>() {
            @Override
            public void handlerResult(RestResult<List<ConsultDetailBean>> resultData) {
                if (resultData.LogicSuccess){
//                    mIConsultView.refreshConsultList(resultData.Data);
                    consultDetailList = resultData.Data;
                    consultListBoolean = true;
//                    mIConsultView.hideDialog();
                    toggleDialog();
                }else {
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }


    @Override
    public void sendMessage(final int type, final String consultContent){
        mIConsultView.showDialog();
        mConsultModel.sendMessage(mUserInfo.AccountId, type, consultContent, new OnHandlerResultListener<RestResult<Boolean>>() {
            @Override
            public void handlerResult(RestResult<Boolean> resultData) {
                if (resultData.LogicSuccess){

                    mIConsultView.addReply(creatReplayContent(consultContent,type));
                    mIConsultView.hideDialog();
                }else {
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }



    public void getDoctorID() {
        mUserModel.getDoctorId(mUserInfo.AccountId, new OnHandlerResultListener<RestResult<String>>() {
            @Override
            public void handlerResult(RestResult<String> resultData) {
                if (resultData.LogicSuccess){
                    if(resultData.Data!= null){
                        UserManager.getInstance().setDoctorID(resultData.Data);
                    }
                    doctorIDBoolean = true;
//                    mLoginView.hideDialog();
                    toggleDialog();
                }else {
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }

    public void getDoctorList() {
        mUserModel.getDoctorList(new OnHandlerResultListener<RestResult<List<DoctorInfoBean>>>() {
            @Override
            public void handlerResult(RestResult<List<DoctorInfoBean>> resultData) {
                if (resultData.LogicSuccess){
                    if(resultData.Data!= null){
                        DoctorManager.getInstance().setDoctorList(resultData.Data);
                    }
//                    mLoginView.hideDialog();
                    doctorListBoolean = true;
                    toggleDialog();
                }else {
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }

    public void toggleDialog(){
        if (consultListBoolean && doctorIDBoolean && doctorListBoolean){
            mIConsultView.setDoctorInfo();
            mIConsultView.refreshConsultList(consultDetailList);
            mIConsultView.hideDialog();

            consultListBoolean = false;
//            doctorIDBoolean = false;
//            doctorListBoolean = false;
        }
    }

    public ConsultDetailBean creatReplayContent(String consultContent, int type){
        ConsultDetailBean replyContent = new ConsultDetailBean();
        replyContent.AccountId = mUserInfo.AccountId;
        replyContent.SourceType = 1;//客户回复
        replyContent.Content = consultContent;
        replyContent.DoctorId = mDoctorID;
        replyContent.Type = type;
        replyContent.setDate(new Date());
        return replyContent;
    }

}
