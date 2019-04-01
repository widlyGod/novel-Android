package com.novel.cn.app

import android.content.Context
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.mvp.ui.activity.BookDetailActivity
import org.jetbrains.anko.startActivity

/**
 * 跳转管理类
 */
object JumpManager {

    /**
     * 跳转书籍详情页面
     */
    fun jumpBookDetail(context: Context?, bookId: String?) {
        context?.startActivity<BookDetailActivity>("bookId" to bookId)
    }

}