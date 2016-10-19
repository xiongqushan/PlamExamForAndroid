package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UnreadMarkBean;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.listener.OnHandlerResultWithCompletedListener;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/10.
 */
public class HomePresenter extends AbstractPresenter implements HomeContract.IHomePresenter {
    HomeContract.IHomeView mHomeView;
    private HomeModel mHomeModel;
    private ConsultModel mConsultModel;

    @Inject
    public HomePresenter(@NonNull HomeContract.IHomeView view, @NonNull HomeModel homeModel, @NonNull ConsultModel consultModel) {
        mHomeView = view;
        mHomeModel = homeModel;
        mConsultModel = consultModel;
        mHomeView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mHomeView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mHomeModel};
    }


    @Override
    public void start() {
        mHomeModel.initData(new OnHandlerResultWithCompletedListener<RestResult>() {
            @Override
            public void handlerResult(RestResult bean) {
                if (bean.Data instanceof BannerBean) {
                    Log.e("BannerBean", "BannerBean");
                } else if (bean.Data instanceof NewsBean) {
                    Log.e("NewsBean", "NewsBean");
                }
            }

            @Override
            public void onCompleted() {
                Log.e("Listener-nCompleted", "Listener-Completed");
            }
        });
    }


    @Override
    public void getBanner(int departId) {
        mHomeView.showDialog();
        mHomeModel.getBaner(departId, new OnHandlerResultListener<RestResult<List<BannerBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<BannerBean>> resultData) {
                mHomeView.initBanner(resultData.Data);
                mHomeView.hideDialog();
            }

            @Override
            public void handlerResultError(RestResult<List<BannerBean>> resultData) {
                mHomeView.hideDialog(resultData.Message);
            }

        });
    }

    @Override
    public void getUnreadMartState(String accountId) {
        mConsultModel.getUnreadMarkState(accountId, new OnHandlerResultListener<RestResult<List<UnreadMarkBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<UnreadMarkBean>> resultData) {
                if (resultData.Data != null && resultData.Data.size() > 0) {
                    mHomeView.showUnreadMark();
                }
            }

            @Override
            public void handlerResultError(RestResult<List<UnreadMarkBean>> resultData) {

            }

        });
    }

    @Override
    public void removeUnreadMark(String accountId) {
        mConsultModel.removeUnreadMark(accountId, new OnHandlerResultListener<RestResult<Boolean>>() {
            @Override
            public void handlerResultSuccess(RestResult<Boolean> resultData) {

            }

            @Override
            public void handlerResultError(RestResult<Boolean> resultData) {

            }

        });
    }
}
