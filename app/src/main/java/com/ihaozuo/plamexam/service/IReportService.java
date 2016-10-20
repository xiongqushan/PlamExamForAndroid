package com.ihaozuo.plamexam.service;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.framework.SysConfig;

import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by zhangzhongyao on 2016/10/18.
 */
public interface IReportService {
    @POST(SysConfig.CONTROLLER_PRE_API_REPORT + "Add")
    Observable<BaseBean<List<ReportItemBean>>> addReport(@Body Map<String, Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_REPORT + "Reports")
    Observable<BaseBean<List<ReportItemBean>>> getReportList(@Body Map<String, Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_REPORT + "ReportInfo")
    Observable<BaseBean<ReportDetailBean>> getReportDetail(@Body Map<String, Object> params);
}
