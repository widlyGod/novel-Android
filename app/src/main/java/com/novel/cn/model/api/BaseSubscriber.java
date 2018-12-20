package com.novel.cn.model.api;

import android.content.Context;
import com.novel.cn.app.NovelApplication;
import com.novel.cn.util.NetworkUtil;
import rx.Subscriber;

/**
 * Created by jackieli on 2018/6/22.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    protected abstract void noConnectInternet();

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtil.isConnectInternet(NovelApplication.getInstance())) {
//            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            // 一定好主动调用下面这一句
//            onCompleted();
            noConnectInternet();
            return;
        }
    }




    //    @Override
//    public void onError(Throwable e) {
//        if(e instanceof Exception){
//            //访问获得对应的Exception
//            onError(ExceptionHandle.handleException(e));
//        }else {
//            //将Throwable 和 未知错误的status code返回
//            onError(new ExceptionHandle.ResponeThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
//        }
//    }

}
