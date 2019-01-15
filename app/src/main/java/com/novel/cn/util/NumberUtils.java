package com.novel.cn.util;

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
     *将千以内的数字转换为中文数字;
     *
     *@paramnum
     *@return
     */

    public static String numberKArab2CN(Integer num) {
        char[] numChars = (num +"").toCharArray();
        String tempStr ="";
        int inc =units.length- numChars.length;
        for(int i = 0; i < numChars.length; i++) {
            if(numChars[i] !='0') {
                tempStr +=numberCharArab2CN(numChars[i]) +units[i + inc];
            }else{
                tempStr +=numberCharArab2CN(numChars[i]);
            }

        }
//将连续的零保留一个
        tempStr = tempStr.replaceAll(numZero+"+",numZero+"");
//去掉未位的零
        tempStr = tempStr.replaceAll(numZero+"$","");
        return tempStr;
    }


    /**
     *处理千以内中文数字,返回4位数字字符串,位数不够以"0"补齐;
     *
     *@paramnumberCN
     *@return
     */

    private static String numberKCN2Arab(String numberCN) {
        if("".equals(numberCN)) {
            return"";
        }
        int[] nums =new int[4];
        if(numberCN !=null) {
            for(int i = 0; i <units.length; i++) {
                int idx = numberCN.indexOf(units[i]);
                if(idx > 0) {
                    char tempNumChar = numberCN.charAt(idx - 1);
                    int tempNumInt =numberCharCN2Arab(tempNumChar);
                    nums[i] = tempNumInt;
                }
            }
            //处理十位
            char ones = numberCN.charAt(numberCN.length() - 1);
            nums[nums.length- 1] =numberCharCN2Arab(ones);
            //处理个位
            if((numberCN.length() == 2 || numberCN.length() == 1)
                    && numberCN.charAt(0) =='十') {
                nums[nums.length- 2] = 1;
            }
        }
        //返回结果
        String tempNum ="";
        for(int i = 0; i < nums.length; i++) {
            tempNum += nums[i];
        }
        return(tempNum);
    }


    /**

     *将一位数字转换为一位中文数字;eg:1返回一;

     *

     *@paramonlyArabNumber

     *@return

     */

    public static char numberCharArab2CN(char onlyArabNumber) {
        if(onlyArabNumber =='0') {
            return numZero;
        }
        if(onlyArabNumber >'0'&& onlyArabNumber <='9') {
            return numChars[onlyArabNumber -'0'- 1];
        }
        return onlyArabNumber;
    }




    /**

     *将一位中文数字转换为一位数字;eg:一返回1;

     *

     *@paramonlyCNNumber

     *@return

     */

    public static int numberCharCN2Arab(char onlyCNNumber) {
        if(numChars[0] == onlyCNNumber) {
            return 1;
        }else if(numChars[1] == onlyCNNumber || onlyCNNumber =='两') {//处理中文习惯用法(二,两)
            return 2;
        }else if(numChars[2] == onlyCNNumber) {
            return 3;
        }else if(numChars[3] == onlyCNNumber) {
            return 4;
        }else if(numChars[4] == onlyCNNumber) {
            return 5;
        }else if(numChars[5] == onlyCNNumber) {
            return 6;
        }else if(numChars[6] == onlyCNNumber) {
            return 7;
        }else if(numChars[7] == onlyCNNumber) {
            return 8;
        }else if(numChars[8] == onlyCNNumber) {
            return 9;
        }
        return 0;
    }
}
