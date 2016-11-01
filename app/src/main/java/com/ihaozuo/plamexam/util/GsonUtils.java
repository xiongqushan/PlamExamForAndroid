package com.ihaozuo.plamexam.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public final class GsonUtils {

    public static <T> T parseJSON(String json, Class<T> clazz) {
        Gson gson = new Gson();
        // TODO 0526 使用 GsonBuilder解决android6.0 Gson与activeandroid的冲突引起的 Can't
        // make field constructor accessible
        // ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
        // @Override
        // public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        // return false;
        // }
        //
        // @Override
        // public boolean shouldSkipClass(Class<?> clazz) {
        // return clazz == Field.class || clazz == Method.class;
        // }
        // };
        //
        // Gson gson = new GsonBuilder()
        // .addSerializationExclusionStrategy(exclusionStrategy)
        // .addDeserializationExclusionStrategy(exclusionStrategy)
        // .create();

        T info = gson.fromJson(json, clazz);
        return info;
    }

    /**
     * Type type = new TypeToken&lt;ArrayList&lt;TypeInfo>>(){}.getType(); <br>
     * Type所在的包：java.lang.reflect <br>
     * TypeToken所在的包：com.google.gson.reflect.TypeToken
     *
     * @param jsonArr
     * @param type
     * @return
     */
    public static <T> T parseJSONArray(String jsonArr, Type type) {
        Gson gson = new Gson();
        T infos = gson.fromJson(jsonArr, type);
        return infos;
    }

    private GsonUtils() {
    }
}
