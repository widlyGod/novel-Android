package com.jess.arms.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import java.util.Calendar;
import java.util.Date;

public class ActivityLifeCycle implements Application.ActivityLifecycleCallbacks {
    /**
     * 上次检查时间，用于在运行时作为基准获取用户时间
     */
    public static long lastCheckTime = 0;
    /**
     * 前台Activity数量
     **/
    private int foregroundActivityCount = 0;
    /**
     * Activity是否在修改配置，
     */
    private boolean isChangingConfigActivity = false;
    /**
     * 应用将要切换到前台
     */
    private boolean willSwitchToForeground = false;
    /**
     * 当前是否在前台
     */
    private boolean isForegroundNow = false;
    /**
     * 上次暂停的Activity信息
     */
    private String lastPausedActivityName;
    private int lastPausedActivityHashCode;
    private long lastPausedTime;
    private long appUseReduceTime = 0;
    /**
     * 每次有Activity启动时的开始时间点
     */
    private long appStartTime = 0L;

    /**
     * 本次统计时，运行的时间
     */
    private long runTimeThisDay = 0L;

    /**
     * 每次阅读界面启动时的开始时间点
     */
    private long readStartTime = 0L;

    /**
     * 本次统计时阅读运行的时间
     */
    private long readRunTime = 0L;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        LogUtils.debugInfo("onActivityCreated" + getActivityName(activity));
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtils.debugInfo("onActivityStarted " + activity.getClass().getSimpleName() + " " + foregroundActivityCount);
        //前台没有Activity，说明新启动或者将从从后台恢复
        if (foregroundActivityCount == 0 || !isForegroundNow) {
            willSwitchToForeground = true;
        } else {
            //应用已经在前台，此时保存今日运行的时间。
            runTimeThisDay = System.currentTimeMillis() - appStartTime;
            lastCheckTime = System.currentTimeMillis();
            saveTodayPlayTime(activity, runTimeThisDay);
        }
        if ("ReadActivity".equals(activity.getClass().getSimpleName())) {
            readStartTime = System.currentTimeMillis();
        }
        appStartTime = System.currentTimeMillis();
        if (isChangingConfigActivity) {
            isChangingConfigActivity = false;
            return;
        }
        foregroundActivityCount += 1;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtils.debugInfo("onActivityResumed" + getActivityName(activity));
        //在这里更新检查时间点，是为了保证从后台恢复到前台，持续计时的准确性。
        lastCheckTime = System.currentTimeMillis();
        addAppUseReduceTimeIfNeeded(activity);
        if (willSwitchToForeground && isInteractive(activity)) {
            isForegroundNow = true;
            LogUtils.debugInfo("switch to foreground");
        }
        if (isForegroundNow) {
            willSwitchToForeground = false;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtils.debugInfo("onActivityPaused" + getActivityName(activity));
        lastPausedActivityName = getActivityName(activity);
        lastPausedActivityHashCode = activity.hashCode();
        lastPausedTime = System.currentTimeMillis();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtils.debugInfo("onActivityStopped" + getActivityName(activity));
        addAppUseReduceTimeIfNeeded(activity);
        //如果这个Activity实在修改配置，如旋转等，则不保存时间直接返回
        if (activity.isChangingConfigurations()) {
            isChangingConfigActivity = true;
            return;
        }
        //该Activity要进入后台，前台Activity数量-1。
        foregroundActivityCount -= 1;
        //当前已经是最后的一个Activity，代表此时应用退出了，保存时间。
        // 如果跨天了，则从新一天的0点开始计时
        if (foregroundActivityCount == 0) {
            isForegroundNow = false;
            LogUtils.debugInfo("switch to background (reduce time[" + appUseReduceTime + "])");
//            if (getTodayStartTime() > appStartTime){
//                runTimeThisDay = System.currentTimeMillis() - getTodayStartTime();
//            }else {
            runTimeThisDay = System.currentTimeMillis() - appStartTime;
//            }
            saveTodayPlayTime(activity, runTimeThisDay);
            lastCheckTime = System.currentTimeMillis();
            LogUtils.debugInfo("run time  :" + runTimeThisDay);
        }
        if ("ReadActivity".equals(activity.getClass().getSimpleName())) {
            readRunTime = System.currentTimeMillis() - readStartTime;
            saveReadTime(activity, readRunTime);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        LogUtils.debugInfo("onActivitySaveInstanceState" + getActivityName(activity));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.debugInfo("onActivityDestroyed" + getActivityName(activity));
    }

    private void addAppUseReduceTimeIfNeeded(Activity activity) {
        if (getActivityName(activity).equals(lastPausedActivityName) && activity.hashCode() == lastPausedActivityHashCode) {
            long now = System.currentTimeMillis();
            if (now - lastPausedTime > 1000) {
                appUseReduceTime += now - lastPausedTime;
            }
        }
        lastPausedActivityHashCode = -1;
        lastPausedActivityName = null;
        lastPausedTime = 0;
    }

    private boolean isInteractive(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            return pm.isScreenOn();
        }
    }

    private String getActivityName(final Activity activity) {
        return activity.getClass().getCanonicalName();
    }

    /**
     * 获取今日0点的时间点，/1000*1000保证每次取值相同。
     *
     * @return
     */
    private long getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long time = calendar.getTimeInMillis() / 1000 * 1000;
        return time;
    }

    /**
     * 保存今日运行时间
     *
     * @param context
     * @param time
     */
    private void saveTodayPlayTime(Context context, long time) {
        long todayTime = ShareprefUtils.getLong(context, "APP_USE_TIME", 0);
        LogUtils.debugInfo("today :" + String.valueOf(getTodayStartTime()) + " : time : " + todayTime);
        LogUtils.debugInfo("today totalTime:" + time + " :" + (todayTime + time));
        ShareprefUtils.saveLong(context, "APP_USE_TIME", todayTime + time);
        LogUtils.warnInfo(context.getClass().getName());
    }

    /**
     * 保存阅读运行时间
     *
     * @param context
     * @param time
     */
    private void saveReadTime(Context context, long time) {
        long todayTime = ShareprefUtils.getLong(context, "APP_READ_TIME", 0);
        LogUtils.debugInfo("read totalTime:" + time + " :" + (todayTime + time));
        ShareprefUtils.saveLong(context, "APP_READ_TIME", todayTime + time);
        LogUtils.warnInfo(context.getClass().getName());
    }

}
