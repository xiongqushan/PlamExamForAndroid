package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;
import com.ihaozuo.plamexam.view.consult.ConsultFragment;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {

    private ConsultContract.IConsultView mConsultFragment;
    private ConsultModel mConsultModel;

    @Inject
    public ConsultPresenter(@NonNull ConsultFragment consultFragment, @NonNull ConsultModel consultModel){
        mConsultFragment = (ConsultContract.IConsultView)consultFragment;
        mConsultModel = consultModel;
        mConsultFragment.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mConsultFragment;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {

    }
}
