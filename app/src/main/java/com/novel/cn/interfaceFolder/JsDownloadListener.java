package com.novel.cn.interfaceFolder;

/**
 * Created by jackieli on 2018/6/29.
 */

public interface JsDownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);


}
