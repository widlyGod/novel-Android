package com.novel.cn.utils;

import android.content.Context;

import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StringUtils {
    private static final int HOUR_OF_DAY = 24;
    private static final int DAY_OF_YESTERDAY = 2;
    private static final int TIME_UNIT = 60;

    //将时间转换成日期
    public static String dateConvert(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    //将日期转换成昨天、今天、明天
    public static String dateConvert(String source, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = format.parse(source);
            long curTime = calendar.getTimeInMillis();
            calendar.setTime(date);
            //将MISC 转换成 sec
            long difSec = Math.abs((curTime - date.getTime()) / 1000);
            long difMin = difSec / 60;
            long difHour = difMin / 60;
            long difDate = difHour / 60;
            int oldHour = calendar.get(Calendar.HOUR);
            //如果没有时间
            if (oldHour == 0) {
                //比日期:昨天今天和明天
                if (difDate == 0) {
                    return "今天";
                } else if (difDate < DAY_OF_YESTERDAY) {
                    return "昨天";
                } else {
                    DateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String value = convertFormat.format(date);
                    return value;
                }
            }

            if (difSec < TIME_UNIT) {
                return difSec + "秒前";
            } else if (difMin < TIME_UNIT) {
                return difMin + "分钟前";
            } else if (difHour < HOUR_OF_DAY) {
                return difHour + "小时前";
            } else if (difDate < DAY_OF_YESTERDAY) {
                return "昨天";
            } else {
                DateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd");
                String value = convertFormat.format(date);
                return value;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 将文本中的半角字符，转换成全角字符
     *
     * @param input
     * @return
     */
    public static String halfToFull(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) //半角空格
            {
                c[i] = (char) 12288;
                continue;
            }
            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i] > 32 && c[i] < 127)    //其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    //繁簡轉換
    public static String convertCC(String input, Context context) {
        ConversionType currentConversionType = ConversionType.S2TWP;
        int convertType = 0;

        if (input.length() == 0)
            return "";

        switch (convertType) {
            case 1:
                currentConversionType = ConversionType.TW2SP;
                break;
            case 2:
                currentConversionType = ConversionType.S2HK;
                break;
            case 3:
                currentConversionType = ConversionType.S2T;
                break;
            case 4:
                currentConversionType = ConversionType.S2TW;
                break;
            case 5:
                currentConversionType = ConversionType.S2TWP;
                break;
            case 6:
                currentConversionType = ConversionType.T2HK;
                break;
            case 7:
                currentConversionType = ConversionType.T2S;
                break;
            case 8:
                currentConversionType = ConversionType.T2TW;
                break;
            case 9:
                currentConversionType = ConversionType.TW2S;
                break;
            case 10:
                currentConversionType = ConversionType.HK2S;
                break;
        }

        return (convertType != 0) ? ChineseConverter.convert(input, currentConversionType, context) : input;
    }
}
