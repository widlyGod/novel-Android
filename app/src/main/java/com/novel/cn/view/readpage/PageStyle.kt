package com.novel.cn.view.readpage

import android.support.annotation.ColorRes
import com.novel.cn.R

enum class PageStyle private constructor(@param:ColorRes val fontColor: Int, @param:ColorRes val bgColor: Int,
                                         @param:ColorRes val thumbnail: Int, @param:ColorRes val thumbnailCheck: Int) {

    BG_0(R.color.read_font, R.color.page_color_0, R.drawable.bg_0, R.drawable.bg_0_checked),
    BG_1(R.color.read_font, R.color.page_color_1, R.drawable.bg_1, R.drawable.bg_1_checked),
    BG_2(R.color.read_font, R.color.page_color_2, R.drawable.bg_2, R.drawable.bg_2_checked),
    BG_3(R.color.read_font, R.color.page_color_3, R.drawable.bg_3, R.drawable.bg_3_checked),
    NIGHT2(R.color.read_font_4, R.color.page_color_4, R.drawable.bg_4, R.drawable.bg_4_checked),
    NIGHT(R.color.read_font_4, R.color.page_color_4, R.drawable.bg_4, R.drawable.bg_4_checked)

}