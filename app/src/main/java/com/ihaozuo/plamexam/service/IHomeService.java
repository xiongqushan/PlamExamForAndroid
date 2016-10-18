package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by zhangzhongyao on 2016/10/18.
 */
public interface IHomeService {

    @POST(SysConfig.CONTROLLER_PRE_API_HOME + "Banners")
    Observable<BaseBean<List<BannerBean>>> getBanner(@Body Map<String, Object> params);
}