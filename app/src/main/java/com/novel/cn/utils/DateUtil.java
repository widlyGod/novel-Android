package com.novel.cn.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.jess.arms.utils.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy.MM.dd";
    /**
     * 日期格式：yyyyMMdd
     **/
    public static final String DF_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    /**
     * 一分钟秒值
     */
    public final static long MS_MINUTE = 60 * 1000;// 1分钟

    /**
     * 一小时毫秒值
     */
    public final static long MS_HOUR = 60 * MS_MINUTE;// 1小时

    /**
     * 一天毫秒值
     */
    public final static long MS_DAY = 24 * MS_HOUR;// 1天

    /**
     * 一个月毫秒值
     */
    public final static long MS_MONTH = 31 * MS_DAY;// 月

    /**
     * 一年毫秒值
     */
    public final static long MS_YEAR = 12 * MS_MONTH;// 年

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatFriendly(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > MS_YEAR) {
            r = (diff / MS_YEAR);
            return r + "年前";
        }
        if (diff > MS_MONTH) {
            r = (diff / MS_MONTH);
            return r + "个月前";
        }
        if (diff > MS_DAY) {
            r = (diff / MS_DAY);
            return r + "天前";
        }
        if (diff > MS_HOUR) {
            r = (diff / MS_HOUR);
            return r + "个小时前";
        }
        if (diff > MS_MINUTE) {
            r = (diff / MS_MINUTE);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    public static String formatDateTime(long dateL) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD);
        Date date = new Date(dateL);
        return sdf.format(date);
    }

    /**
     * 将日期以指定格式格式化
     *
     * @param dateL    日期
     * @param formater 指定格式
     * @return
     * @see #DF_HH_MM
     * @see #DF_HH_MM_SS
     * @see #DF_YYYY_MM_DD
     * @see #DF_YYYY_MM_DD_HH_MM
     * @see #DF_YYYY_MM_DD_HH_MM_SS
     */
    public static String formatDateTime(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }

    /**
     * 将日期以指定格式格式化
     *
     * @param dateL 日期
     * @return
     * @see #DF_HH_MM
     * @see #DF_HH_MM_SS
     * @see #DF_YYYY_MM_DD
     * @see #DF_YYYY_MM_DD_HH_MM
     * @see #DF_YYYY_MM_DD_HH_MM_SS
     */
    public static String formatDateTime(Date dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(dateL);
    }

    /**
     * 将日期字符串以yyyy-MM-dd HH:mm:ss格式转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, DF_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     * @see #DF_HH_MM
     * @see #DF_HH_MM_SS
     * @see #DF_YYYY_MM_DD
     * @see #DF_YYYY_MM_DD_HH_MM
     * @see #DF_YYYY_MM_DD_HH_MM_SS
     */
    public static Date parseDate(String strDate, String formater) {
        if (TextUtils.isEmpty(formater)) {
            formater = DF_YYYY_MM_DD_HH_MM_SS;
        }
        DateFormat dateFormat = new SimpleDateFormat(formater);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            LogUtils.debugInfo("parseDate failed !");
        }
        return returnDate;
    }

    /**
     * 验证日期是否比当前日期早
     *
     * @param target1 比较时间1
     * @param target2 比较时间2
     * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    public static boolean compareDate(Date target1, Date target2) {
        boolean flag = false;
        try {
            String target1DateTime = formatDateTime(target1, DF_YYYY_MM_DD_HH_MM_SS);
            String target2DateTime = formatDateTime(target2, DF_YYYY_MM_DD_HH_MM_SS);
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true;
            }
        } catch (Exception e1) {
            System.out.println("比较失败，原因：" + e1.getMessage());
        }
        return flag;
    }

    /**
     * 对日期进行增加操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    public static Date addDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 对日期进行相减操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    public static Date subDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
    }

    public static String parseString(String strDate, String formater) {
        if (TextUtils.isEmpty(formater)) {
            formater = DF_YYYY_MM_DD;
        }
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYYMMDD);
        Date returnDate = null;
        String date="";
        try {
            returnDate = dateFormat.parse(strDate);
            SimpleDateFormat sdf = new SimpleDateFormat(formater);
            date= sdf.format(returnDate);
        } catch (ParseException e) {
            LogUtils.debugInfo("parseDate failed !");
        }
        return date;
    }
    /**
     * yyyyMMdd转化成yyyy-MM-dd
     *
     * @param
     * @return
     */
    public static String parseTime(int position){
        String sTime="";
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD);

        if(position==0){

            sTime=dateFormat.format(new Date())+"(今天)";
        }else if(position==1){
            sTime=getPastDate(6)+" 至 "+dateFormat.format(new Date())+"(近一周)";
        }if(position==2){
            sTime=getPastDate(29)+" 至 "+dateFormat.format(new Date())+"(近一个月)";
        }if(position==3){
            sTime=getPastDate(89)+" 至 "+dateFormat.format(new Date())+"(90天内)";
        }
        return sTime;

    }
    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
}
