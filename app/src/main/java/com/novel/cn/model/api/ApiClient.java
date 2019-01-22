package com.novel.cn.model.api;


import com.novel.cn.model.util.EntityUtils;
import com.novel.cn.model.util.HttpUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by YUNW-01 on 2017/10/18.
 */

public final class ApiClient {



//    public static final String API_BASE_URL = "http://59.110.124.41/novelapi/";

    public static final String API_BASE_URL = "http://192.168.5.56:8080/novelapi/";

    private ApiClient() {

    }

    // converters 被添加的顺序将是它们被Retrofit尝试的顺序
    public static final ApiService service = new Retrofit.Builder()
            .addConverterFactory(StringConverterFactory.create(EntityUtils.gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(HttpUtils.client)
            .baseUrl(API_BASE_URL)
            .build()
            .create(ApiService.class);

//            .addConverterFactory(StringConverterFactory.create())
//              .addConverterFactory(BeanConverFactory.create(EntityUtils.gson))
//            .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))

}
