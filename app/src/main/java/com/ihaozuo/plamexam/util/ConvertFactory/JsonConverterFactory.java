package com.ihaozuo.plamexam.util.ConvertFactory;

import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by hzguest3 on 2016/10/24.
 */
public class JsonConverterFactory extends Converter.Factory{

    public static JsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static JsonConverterFactory create(Gson gson) {
        return new JsonConverterFactory(gson);
    }

    private final Gson gson;

    private JsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

//    @Override
//    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
//        return new GsonResponseBodyConverter<>(gson, type);
//    }

    @Override public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new JsonRequestBodyConverter<>(gson, type);
    }

}
