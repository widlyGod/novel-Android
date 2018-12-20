package com.novel.cn.model.api;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/9/1
 * owspace
 */
public class StringConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value) throws IOException {
        String response = value.string();
//        String result= AesEncryption.decrypt(Constants.KEY.getBytes(),response);
//        return result;
        return response;
    }
//public class StringConverter<T> implements Converter<ResponseBody, T> {
//
//    private final Gson gson;
//    private final TypeAdapter<T> adapter;
//
//    StringConverter(Gson gson, TypeAdapter<T> adapter) {
//        this.gson = gson;
//        this.adapter = adapter;
//    }
//
//    @Override public T convert(ResponseBody value) throws IOException {
////        Reader reader=value.charStream();
////        JsonReader jsonReader = gson.newJsonReader(reader);
//        String response = value.string();
//        String result= AesEncryption.decrypt(Constants.KEY.getBytes(),response);
//        try {
////            return adapter.read(jsonReader);
//            return adapter.fromJson(result);
//        } finally {
//            value.close();
//        }
//    }
}
