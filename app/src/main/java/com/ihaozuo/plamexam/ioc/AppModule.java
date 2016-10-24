package com.ihaozuo.plamexam.ioc;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.service.IConsultService;
import com.ihaozuo.plamexam.service.IHomeService;
import com.ihaozuo.plamexam.service.IReportService;
import com.ihaozuo.plamexam.service.IUserService;
import com.ihaozuo.plamexam.util.JsonUtil;
import com.ihaozuo.plamexam.util.StringUtil;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by zhangzhongyao on 2016/10/09.
 */
@Module
public class AppModule {
    private static final String CURRENT_VERSION = SysConfig.CURRENT_BASE_VERSION;
    private static final String API_BASE_URL = SysConfig.BASE_API[0];
    private static final String BASIC_USER_NAME = SysConfig.BASE_API[1];
    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    private HZApp mHZApp;

    public AppModule(@NonNull HZApp application) {
        mHZApp = application;
    }

    @Provides
    @Singleton
    HZApp provideApp() {
        return mHZApp;
    }

    @Provides
    @Singleton
    IUserService createUserService(@NonNull Retrofit retrofit) {
        return retrofit.create(IUserService.class);
    }

    @Provides
    @Singleton
    IHomeService createHomeService(@NonNull Retrofit retrofit) {
        return retrofit.create(IHomeService.class);
    }

    @Provides
    @Singleton
    IConsultService createConsultService(@NonNull Retrofit retrofit) {
        return retrofit.create(IConsultService.class);
    }

    @Provides
    @Singleton
    IReportService createReportService(@NonNull Retrofit retrofit) {
        return retrofit.create(IReportService.class);
    }

    @Provides
    @Singleton
    OkHttpClient createHttpClient() {

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(SysConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(SysConfig.WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(SysConfig.READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String originUrl = request.urlString();
                request = request.newBuilder().url(originUrl).build();
                String sign = "";
                Map<String, Object> map = new TreeMap<String, Object>();
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                String postData = buffer.readUtf8();
                try {
                    JSONObject jasonObject = new JSONObject(postData);
                    map = JsonUtil.jsonToMap(jasonObject);
                    //先转小写，后排序
                    map.put("secret", BASIC_SIGN_SECRET);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sign = JsonUtil.mapToString(map).toLowerCase();
                sign = StringUtil.encodeByMD5(sign);
                String usernameAndPassword = BASIC_USER_NAME + ":" + sign;
                byte[] bytes = new byte[0];
                try {
                    bytes = usernameAndPassword.getBytes("ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
                encoded = "BasicAuth " + encoded;
                request = request.newBuilder()
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", encoded)
                        .build();
                return chain.proceed(request);
            }
        });
        return httpClient;
    }

    @Provides
    @Singleton
    Retrofit createRetrofit(@NonNull OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//        .addConverterFactory(JsonConverterFactory.create())

        return retrofit;
    }
}
