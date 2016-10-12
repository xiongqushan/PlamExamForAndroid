package com.ihaozuo.plamexam.ioc;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.service.IUserService;
import com.ihaozuo.plamexam.util.StringUtil;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
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
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                String postData = buffer.readUtf8().toLowerCase();
                sign = StringUtil.encodeByMD5(postData);
                request = request.newBuilder()
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("Accept", "application/json")
                        .addHeader("basickey", sign)
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
        return retrofit;
    }
}
