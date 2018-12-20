package com.novel.cn.model.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by YUNW-01 on 2017/10/10.
 */

public class BeanConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    BeanConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        Reader reader=value.charStream();
//        JsonReader jsonReader = gson.newJsonReader(reader);

        String response = value.string();
        if(response.contains("filecode")){
            return adapter.fromJson(response);
        }
//        String result= AesEncryption.decrypt(Constants.KEY.getBytes(),response);  old
        try {
//            return adapter.read(jsonReader);   oldold
//            return adapter.fromJson(result);   old
            return adapter.fromJson(response);
        } finally {
            value.close();
        }
    }
}
