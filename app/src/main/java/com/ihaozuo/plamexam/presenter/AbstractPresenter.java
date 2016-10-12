package com.ihaozuo.plamexam.presenter;

import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;


/**
 * Created by Administrator on 2016/7/4.
 */
public abstract class AbstractPresenter {

    public abstract IBaseView getBaseView();

    public abstract IBaseModel[] getBaseModelList();

    public void cancelRequest() {
        IBaseModel[] iBaseModelList = getBaseModelList();
        if (iBaseModelList == null) {
            return;
        }
        for (IBaseModel ibaseModel : iBaseModelList) {
            ibaseModel.cancelRequest();
        }
        IBaseView baseView = getBaseView();
        baseView.hideDialog();
    }


}
