package com.novel.cn.model.util;


import com.novel.cn.model.api.HttpInterceptor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;


public class HttpUtils {

    private HttpUtils() {}

    public static HttpInterceptor httpInterceptor;
    //20
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(createHttpLoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    private static HttpInterceptor createHttpLoggingInterceptor() {
        HttpInterceptor loggingInterceptor = new HttpInterceptor();
        httpInterceptor=loggingInterceptor;
        return loggingInterceptor;
    }



//  加密拦截器
//  .addInterceptor(createAESInterceptor())
//     private static AESInterceptor createAESInterceptor() {
//         AESInterceptor loggingInterceptor = new AESInterceptor();
//         return loggingInterceptor;
//     }


//    https的配置
//    mBuilder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory());
//    mBuilder.hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier());
//    private static SSLSocketFactory createSSLSocketFactory() {
//        SSLSocketFactory ssfFactory = null;
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
//            ssfFactory = sc.getSocketFactory();
//
//        } catch (Exception e) {
//        }
//
//        return ssfFactory;
//    }

}
