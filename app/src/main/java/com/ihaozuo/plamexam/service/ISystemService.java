package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.testBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public interface ISystemService {

    @POST(SysConfig.CONTROLLER_PRE_API_System + "SMS")
    Observable<BaseBean<testBean>> getAuthCode(@Body Map<String, Object> params);



}
