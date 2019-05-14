package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerChapterCommentComponent
import com.novel.cn.di.module.ChapterCommentModule
import com.novel.cn.ext.textWatcher
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.presenter.ChapterCommentPresenter
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_chapter_comment.*
import kotlinx.android.synthetic.main.include_title.*


class ChapterCommentActivity : BaseActivity<ChapterCommentPresenter>(), ChapterCommentContract.View {

    private val mChapterId by lazy { intent.getStringExtra("chapterId") }

    private val mBookId by lazy { intent.getStringExtra("bookId") }

    private val mVolumeId by lazy { intent.getStringExtra("volumeId") }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerChapterCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .chapterCommentModule(ChapterCommentModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_chapter_comment
    }


    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
        mPresenter?.getChapterComment(mBookId, mChapterId, true)


        et_content.textWatcher {
            onTextChanged { charSequence, start, before, count ->
                tv_words.text = "${et_content.text.length}/200"
            }
        }

        tv_release.setOnClickListener {
            val content = et_content.text.toString()
            if(content.isEmpty()){
                toast("内容不能为空！")
                return@setOnClickListener
            }
            mPresenter?.chapterComment(
                    mBookId,mChapterId,mVolumeId,content)
        }
    }


}
