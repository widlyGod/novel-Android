package com.novel.cn.model.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/9/1
 * owspace
 */
public class StringConverterFactory extends Converter.Factory {

//    public static StringConverterFactory create(){
//        return new StringConverterFactory();
//    }


    private final Gson gson;

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return new StringConverter();
        }

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new BeanConverter<>(gson, adapter);
//        return null;
    }

//    public static StringConverterFactory create() {
//        return create(new Gson());
//    }


    public static StringConverterFactory create(Gson gson) {
        return new StringConverterFactory(gson);
    }
//

    private StringConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }


}
