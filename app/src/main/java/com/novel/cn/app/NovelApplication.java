package com.novel.cn.app;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;
import com.novel.cn.util.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jackieli on 2018/12/19.
 */

public class NovelApplication extends MultiDexApplication {


    private List<Activity> activityList = new LinkedList<Activity>();
    private static NovelApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        MobSDK.init(this);

        initFresco();

    }


    private void initFresco() {
        Fresco.initialize(this);
    }

    //得到application
    public static NovelApplication getInstance() {
        return instance;
    }


    //栈管理
    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }
    // 遍历所有Activity finish
    public void delete() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        if (activityList.size() == 0){
            activityList.clear();
        }
    }

    public void clearAllActivity(){
        activityList.clear();
    }


}
