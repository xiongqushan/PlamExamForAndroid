
package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hzguest3 on 2016/10/11.
 */
public interface IUserService {

    @POST(SysConfig.CONTROLLER_PRE_API_VALUES + "TestUserAPI")
    Observable<BaseBean<Boolean>> testPost(@Body Map<String,Object> params);

}
