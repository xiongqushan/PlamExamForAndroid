package com.ihaozuo.plamexam.ioc;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.framework.HZApp;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by zhangzhongyao on 2016/10/09.
 */
@Module
public class AppModule {
//    private static final String CURRENT_VERSION = SysConfig.CURRENT_BASE_VERSION;
//    private static final String API_BASE_URL = SysConfig.BASE_API[0];
//    private static final String BASIC_USER_NAME = SysConfig.BASE_API[1];
//    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    private HZApp mHZApp;

    public AppModule(@NonNull HZApp application) {
        mHZApp = application;
    }

    @Provides
    @Singleton
    HZApp provideApp() {
        return mHZApp;
    }



//    @Provides
//    @Singleton
//    OkHttpClient createHttpClient() {
//        OkHttpClient httpClient = new OkHttpClient();
//        httpClient.setConnectTimeout(SysConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS);
//        httpClient.setWriteTimeout(SysConfig.WRITE_TIMEOUT, TimeUnit.SECONDS);
//        httpClient.setReadTimeout(SysConfig.READ_TIMEOUT, TimeUnit.SECONDS);
//        httpClient.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                String originUrl = request.urlString();
//                long timespan = System.currentTimeMillis() / 1000L;
//                if (originUrl.contains("?")) {
//                    originUrl += "&timespan=" + timespan;
//                } else {
//                    originUrl += "?timespan=" + timespan;
//                }
//                request = request.newBuilder().url(originUrl).build();
//                String sign = "";
//                if (request.method().toLowerCase().equals("get")) {
//                    sign = request.urlString() + "|" + BASIC_SIGN_SECRET;
//                } else {
//                    final Request copy = request.newBuilder().build();
//                    final Buffer buffer = new Buffer();
//                    copy.body().writeTo(buffer);
//                    String postData = buffer.readUtf8();
//                    sign = request.urlString() + "|" + postData + "|" + BASIC_SIGN_SECRET;
//                }
//                sign = StringUtil.encodeByMD5(sign);
//                String usernameAndPassword = BASIC_USER_NAME + ":" + sign;
//                byte[] bytes = new byte[0];
//                try {
//                    bytes = usernameAndPassword.getBytes("ISO-8859-1");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
//                encoded = "Basic " + encoded;
//                request = request.newBuilder()
//                        .addHeader("Content-Type", "application/json; charset=UTF-8")
//                        .addHeader("Accept", "application/json")
//                        .addHeader("Authorization", encoded)
//                        .build();
//                return chain.proceed(request);
//            }
//        });
//        return httpClient;
//    }

    @Provides
    @Singleton
    Retrofit createRetrofit(@NonNull OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
