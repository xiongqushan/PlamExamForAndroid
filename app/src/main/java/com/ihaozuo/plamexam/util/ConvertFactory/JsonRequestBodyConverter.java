package com.ihaozuo.plamexam.util.ConvertFactory;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by hzguest3 on 2016/10/24.
 */
public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final Type type;

    JsonRequestBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        //加密
        APIBodyData data = new APIBodyData();
        Log.e("xiaozhang", "request中传递的json数据：" + value.toString());
        data.setData(java.net.URLEncoder.encode(value.toString(), "utf-8"));
        String postBody = gson.toJson(data); //对象转化成json
        Log.e("xiaozhang", "转化后的数据：" + postBody);
        return RequestBody.create(MEDIA_TYPE, postBody);
//        ###这里需要，特别注意的是，request是将T转换成json数据。你要在T转换成json之后再做加密。再将数据post给服务器，同时要注意，你的T到底指的那个对象
    }
}
