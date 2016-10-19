package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.UnreadMarkBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hzguest3 on 2016/10/17.
 */
public interface IConsultService {

    @POST(SysConfig.CONTROLLER_PRE_API_CONSULT + "Chats")
    Observable<BaseBean<List<ConsultDetailBean>>> getConsultDetail(@Body Map<String, Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_CONSULT + "Send")
    Observable<BaseBean<Boolean>> sendMessage(@Body Map<String, Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_CONSULT + "Informs")
    Observable<BaseBean<List<UnreadMarkBean>>> getUnreadMarkState(@Body Map<String, Object> params);


    @POST(SysConfig.CONTROLLER_PRE_API_CONSULT + "RemoveInform")
    Observable<BaseBean<Boolean>> RemoveUnreadMark(@Body Map<String, Object> params);

}
