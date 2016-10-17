
package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hzguest3 on 2016/10/11.
 */
public interface IUserService {

    @POST(SysConfig.CONTROLLER_PRE_API_USER + "SMS")
    Observable<BaseBean<Boolean>> getAuthCode(@Body Map<String,Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_USER + "Register")
    Observable<BaseBean<UserBean>> register(@Body Map<String,Object> params);

}
