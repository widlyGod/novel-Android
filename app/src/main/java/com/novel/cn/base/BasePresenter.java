package com.novel.cn.base;


/**
 * Created by hefuyi on 16/8/19.
 */
public interface BasePresenter<T extends BaseView> {

    Object loadCache();

    void setMvpView(T t, String cacheKey);


}
