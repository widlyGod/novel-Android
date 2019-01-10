package com.novel.cn.util;

/**
 * Created by jackieli on 2019/1/10.
 */

public class CalculationUtil {




    //获取赠送点数
    public static String givePoint(String text){
        double dou=Double.parseDouble(text);
        String zenson="0";
        if(dou<50){
            zenson=dou*0.15+"";
        }else if(dou<100){
            zenson=dou*0.18+"";
        }else if(dou<200){
            zenson=dou*0.22+"";
        }else{
            zenson=dou*0.25+"";
        }


//        String pointLeft="";
//        String pointRight="";
        if (zenson.contains(".")) {
            String[]points=zenson.split("\\.");
            int length=points.length;
            if(Double.parseDouble("0."+points[1])>0.5){
                points[1]=".5";
            }else{
                points[1]=".0";
            }
            zenson=points[0]+points[1];
        }


        return "(+"+zenson+"点数)";
    }


}
