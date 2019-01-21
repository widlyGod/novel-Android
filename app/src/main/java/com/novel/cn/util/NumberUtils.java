package com.novel.cn.util;

import com.luck.picture.lib.tools.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jackieli on 2019/1/15.
 */

public class NumberUtils {

    /*
    *基本数字单位;
    */
    private static final String[] units = {"千", "百", "十", ""};//个位

    /*
    *大数字单位;
    */
    private static final String[] bigUnits = {"万", "亿"};

    /*
    *中文数字;
    */
    private static final char[] numChars = {'一', '二', '三', '四', '五', '六', '七', '八', '九'};

    //private static final char[] numMouneyChars =
    //{ '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
    private static char numZero = '零';


    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String formatNum(String num, Boolean kBool) {
        StringBuffer sb = new StringBuffer();
        if (!isNumeric(num))
            return "0";
        if (kBool == null)
            kBool = false;
        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);
        String formatNumStr = "";
        String nuit = "";
        // 以千为单位处理
        if (kBool) {
            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                return "999+";
            }
            return num;
        }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            sb.append(b3.toString());
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            formatNumStr = b3.divide(b1).toString();
            nuit = "万";
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            formatNumStr = b3.divide(b2).toString();
            nuit = "亿";
        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(nuit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(nuit);
                }
            }
        }
        if (sb.length() == 0)
            return "0";
        return sb.toString();
    }




    /**
     * 将千以内的数字转换为中文数字;
     *
     * @return
     * @paramnum
     */
    public static String numberKArab2CN(Integer num) {
        char[] numChars = (num + "").toCharArray();
        String tempStr = "";
        int inc = units.length - numChars.length;
        for (int i = 0; i < numChars.length; i++) {
            if (numChars[i] != '0') {
                tempStr += numberCharArab2CN(numChars[i]) + units[i + inc];
            } else {
                tempStr += numberCharArab2CN(numChars[i]);
            }

        }
        //将连续的零保留一个
        tempStr = tempStr.replaceAll(numZero + "+", numZero + "");
        //去掉未位的零
        tempStr = tempStr.replaceAll(numZero + "$", "");
        return tempStr;
    }


    /**
     * 处理千以内中文数字,返回4位数字字符串,位数不够以"0"补齐;
     *
     * @return
     * @paramnumberCN
     */

    private static String numberKCN2Arab(String numberCN) {
        if ("".equals(numberCN)) {
            return "";
        }
        int[] nums = new int[4];
        if (numberCN != null) {
            for (int i = 0; i < units.length; i++) {
                int idx = numberCN.indexOf(units[i]);
                if (idx > 0) {
                    char tempNumChar = numberCN.charAt(idx - 1);
                    int tempNumInt = numberCharCN2Arab(tempNumChar);
                    nums[i] = tempNumInt;
                }
            }
            //处理十位
            char ones = numberCN.charAt(numberCN.length() - 1);
            nums[nums.length - 1] = numberCharCN2Arab(ones);
            //处理个位
            if ((numberCN.length() == 2 || numberCN.length() == 1)
                    && numberCN.charAt(0) == '十') {
                nums[nums.length - 2] = 1;
            }
        }
        //返回结果
        String tempNum = "";
        for (int i = 0; i < nums.length; i++) {
            tempNum += nums[i];
        }
        return (tempNum);
    }


    /**
     * 将一位数字转换为一位中文数字;eg:1返回一;
     *
     * @return
     * @paramonlyArabNumber
     */

    public static char numberCharArab2CN(char onlyArabNumber) {
        if (onlyArabNumber == '0') {
            return numZero;
        }
        if (onlyArabNumber > '0' && onlyArabNumber <= '9') {
            return numChars[onlyArabNumber - '0' - 1];
        }
        return onlyArabNumber;
    }


    /**
     * 将一位中文数字转换为一位数字;eg:一返回1;
     *
     * @return
     * @paramonlyCNNumber
     */

    public static int numberCharCN2Arab(char onlyCNNumber) {
        if (numChars[0] == onlyCNNumber) {
            return 1;
        } else if (numChars[1] == onlyCNNumber || onlyCNNumber == '两') {//处理中文习惯用法(二,两)
            return 2;
        } else if (numChars[2] == onlyCNNumber) {
            return 3;
        } else if (numChars[3] == onlyCNNumber) {
            return 4;
        } else if (numChars[4] == onlyCNNumber) {
            return 5;
        } else if (numChars[5] == onlyCNNumber) {
            return 6;
        } else if (numChars[6] == onlyCNNumber) {
            return 7;
        } else if (numChars[7] == onlyCNNumber) {
            return 8;
        } else if (numChars[8] == onlyCNNumber) {
            return 9;
        }
        return 0;
    }
}
