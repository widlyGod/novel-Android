package com.novel.cn.view.read

import android.support.annotation.ColorRes
import com.novel.cn.R

enum class PageStyle private constructor(@param:ColorRes val fontColor: Int, @param:ColorRes val bgColor: Int){

    BG_0(R.color.read_font,R.color.page_color_0),
    BG_1(R.color.read_font,R.color.page_color_1),
    BG_2(R.color.read_font,R.color.page_color_2),
    BG_3(R.color.read_font,R.color.page_color_3),
    BG_NIGHT(R.color.read_font,R.color.page_color_4)

}