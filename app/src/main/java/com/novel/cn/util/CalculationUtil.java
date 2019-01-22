package com.novel.cn.util;

import com.novel.cn.model.entity.ChapterBean;

import java.util.ArrayList;
import java.util.List;

/**计算工具类
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

    //计算章节提示信息
    public static List<String> calChapters(ChapterBean bean ){

        List<String>chapterArr=new ArrayList<>();
        //新增
        List<ChapterBean.DataBean.ListBean>list=bean.getData().getList();
        int startChapterNum = Integer.parseInt(list.get(0).getChapter());
        int endChapterNum = Integer.parseInt(list.get(list.size()-1).getChapter());

        int pageSize = 100;
        boolean isASC = true;
        int totalDESC=bean.getData().getTotal();
        if (startChapterNum >= endChapterNum) {
            //  从大到小    降序
            pageSize = -100;
            isASC = false;
        }

        int firstPage =Integer.parseInt(list.get(0).getChapter());
        int lastPage = firstPage;
        int tmp = 0;

        int total=bean.getData().getTotal();
        int pageCount = (int) Math.ceil((double)total / Math.abs(pageSize));

        for (int i = 0; i < pageCount; ++ i) {
            if (isASC) {
                tmp = ((pageCount - 1) == i) ? (lastPage + ((int)total - lastPage + firstPage - 1)) : (lastPage + pageSize - 1);
            } else {
                if((pageCount - 1) == i){
                    tmp =totalDESC>100?startChapterNum-totalDESC+1:endChapterNum;
                }else{
                    tmp =lastPage+ pageSize+ 1;
                }
            }
            chapterArr.add("第"+lastPage+"章 - 第"+tmp+"章");
            LogUtil.e("执行了该calChapters 第"+lastPage+"章 - 第"+tmp+"章");
            lastPage =isASC==true? tmp + 1:tmp-1;
        }

        return chapterArr;
    }



}
