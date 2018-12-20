package com.novel.cn.model.api;

/**
 * Created by YUNW-01 on 2017/9/29.
 */


import com.novel.cn.app.NovelApplication;
import com.novel.cn.interfaceFolder.JsDownloadListener;
import com.novel.cn.interfaceFolder.JsResponseBody;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.SharePrefUtil;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */
public class HttpInterceptor implements Interceptor {

    //两个值每次都需要复原
    private boolean isAddHead = true;
    private boolean isDownloadLarge = false;
    private JsDownloadListener downloadListener;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder;
        if (isAddHead) {
            String seesionId = SharePrefUtil.getString(NovelApplication.getInstance(), "token", "");

            LogUtil.e("seesionId=" + seesionId);
//            LogUtil.e("User-Agent=" + getUserAgent());
            //shareprefence取值
            // Request customization: add request headers
            //test    bodyString={"pageNo":1,"pageSize":100,"rebackStatus":2}----sessionId=438c603a04bb48858a65343ac61ecb5e
            requestBuilder = original.newBuilder()
                    .header("sessionId", seesionId) // <-- this is the important line
                    .header("User-Agent", getUserAgent()); // <-- this is the important line
        } else {
            // Request customization: add request headers
            requestBuilder = original.newBuilder()
                    .header("User-Agent", getUserAgent()); // <-- this is the important line
        }

        isAddHead = true;

        //Request为共用
        Request request = requestBuilder.build();

        //大文件返回自定义的Response   if里面代码为大文件Response
        if(isDownloadLarge){
            isDownloadLarge=false;
            Response response = chain.proceed(request);
            return response.newBuilder().body(
                    new JsResponseBody(response.body(), downloadListener)).build();

        }

        isDownloadLarge=false;
        return chain.proceed(request);

    }


    public void isAddHead(boolean isAddHeadx) {
        isAddHead = isAddHeadx;
    }


    public void isDownloadLargeFiles(boolean isDownloadLarge) {
        this.isDownloadLarge = isDownloadLarge;
    }

    public void setJsDownloadListener(JsDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }


    private static String getUserAgent() {
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        userAgent = System.getProperty("http.agent");//Dalvik/forgetpstwo.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
//        Log.e("User-Agent","User-Agent: "+ sb.toString());
        return sb.toString();
    }
}
