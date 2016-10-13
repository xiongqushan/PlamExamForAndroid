package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.testBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hzguest3 on 2016/10/11.
 */
public interface IValuesService {

    @POST(SysConfig.CONTROLLER_PRE_API_VALUES + "Post")
    Observable<BaseBean<testBean>> testPost(@Body Map<String,Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_VALUES + "AAA")
    Observable<BaseBean<testBean>> testPost2(@Body Map<String,Object> params);

}
