package com.novel.cn.mvp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import br.com.instachat.emojilibrary.controller.EmojiKeyboard
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.BookEvent
import com.jess.arms.utils.CircleEvent
import com.jess.arms.utils.IndexEvent

import com.novel.cn.di.component.DaggerPublishComponent
import com.novel.cn.di.module.PublishModule
import com.novel.cn.mvp.contract.PublishContract
import com.novel.cn.mvp.presenter.PublishPresenter

import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.clicks
import com.novel.cn.ext.toast
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.Novel
import com.novel.cn.mvp.ui.adapter.PublicCircleImageAdapter
import com.novel.cn.utils.AppPermissions
import com.novel.cn.utils.Glide4Engine
import com.novel.cn.utils.ImageUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_publish.*
import kotlinx.android.synthetic.main.item_circle.view.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class PublishActivity : BaseActivity<PublishPresenter>(), PublishContract.View {

    companion object {
        private const val MAX_IMAGE_COUNT = 9
    }

    private val mImageList = mutableListOf<String>()
    private val mImageListAdapter by lazy { PublicCircleImageAdapter() }

    private val mRxPermissions by lazy { RxPermissions(this) }
    private var publicType = 0
    private lateinit var mEmojiKeyboard: EmojiKeyboard
    private lateinit var mCurtain: LinearLayout
    private var emojiShow = false
    private lateinit var mUnregistrar: Unregistrar
    private var showEmoji = false
    private var hotNovels = mutableListOf<BookInfo>()
    private var novelId = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerPublishComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .publishModule(PublishModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_publish //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun publicSuccess() {
        EventBusManager.getInstance().post(CircleEvent())
        finish()
    }

    private fun loadArguments() {
        intent?.apply {
            publicType = getIntExtra("type", 0)
        }
        when (publicType) {
            0 -> {
                rv_images.visible(false)
                iv_add_book_comment.visible(false)
                rl_book_detail.visible(false)
            }
            1 -> {
                rv_images.visible(true)
                iv_add_book_comment.visible(false)
                rl_book_detail.visible(false)
            }
            2 -> {
                rv_images.visible(false)
                iv_add_book_comment.visible(true)
                rl_book_detail.visible(false)
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadArguments()
        mImageList.add("")
        mCurtain = findViewById(br.com.instachat.emojilibrary.R.id.curtain)
        mEmojiKeyboard = EmojiKeyboard(this, et_title)
        rv_images.adapter = mImageListAdapter.apply {
            setNewData(mImageList)
        }
        mImageListAdapter.clicks()
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe {
                    if (it.second == mImageListAdapter.data.size - 1)
                        addImage()
                }
                .bindToLifecycle(this)
        tv_send.clicks().subscribe {
            if (et_title.text.toString().isBlank()) {
                toast("请输入标题")
                return@subscribe
            }
            if (et_main.text.toString().isBlank()) {
                toast("请输入内容")
                return@subscribe
            }
            if (publicType == 1 && mImageList.size <= 1) {
                toast("请添加图片")
                return@subscribe
            } else if (publicType == 2 && novelId.isBlank()) {
                toast("请添加书籍")
                return@subscribe
            }
            mPresenter?.public(publicType, et_title.text.toString(), et_main.text.toString(), "", mImageList, novelId)
        }.bindToLifecycle(this)
        iv_emoji.clicks().subscribe {
            if (!emojiShow) {
                if (KeyboardVisibilityEvent.isKeyboardVisible(this)) {
                    showEmoji = true
                    hideSoftKeyboard()
                } else {
                    showEmojiKeyboard()
                }
            } else
                showSoftKeyboard()
        }.bindToLifecycle(this)
        et_main.setOnFocusChangeListener { _, hasFocus ->
            hindEmojiKeyboard()
            if (hasFocus)
                mEmojiKeyboard.setmInput(et_main)
        }
        et_title.setOnFocusChangeListener { _, hasFocus ->
            hindEmojiKeyboard()
            if (hasFocus)
                mEmojiKeyboard.setmInput(et_title)
        }
        tv_cancel.clicks().subscribe {
            finish()
        }.bindToLifecycle(this)
        iv_delete.clicks().subscribe {
            novelId = ""
            rl_book_detail.visible(false)
            iv_add_book_comment.visible(true)
        }.bindToLifecycle(this)
        iv_add_book_comment.clicks().subscribe {
            if (hotNovels.isEmpty()) {
                mPresenter?.getHotSearch()
            } else
                JumpManager.jumpSearch(this, hotNovels, 0, true)
        }.bindToLifecycle(this)
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(this) { isOpen ->
            if (isOpen)
                hindEmojiKeyboard()
            else
                if (showEmoji) {
                    showEmojiKeyboard()
                    showEmoji = false
                }
        }
    }

    private fun showEmojiKeyboard() {
        hideSoftKeyboard()
        mCurtain.visible(false)
        mEmojiKeyboard.emojiKeyboardLayout.visibility = LinearLayout.VISIBLE
        emojiShow = true
    }

    private fun hindEmojiKeyboard() {
        mCurtain.visible(true)
        mEmojiKeyboard.emojiKeyboardLayout.visibility = LinearLayout.GONE
        emojiShow = false
    }

    private fun showSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun addImage() {
        var imageCount = 0
        for (media in mImageList) {
            imageCount++
        }
        val maxImage = MAX_IMAGE_COUNT - imageCount + 1

        AppPermissions.requestCameraPermission(mRxPermissions, this, tips = "选择照片需要相关权限，否则无法使用。") {
            Matisse.from(this)
                    .choose(setOf(MimeType.JPEG, MimeType.PNG))
                    .showSingleMediaType(true)
                    .maxSelectablePerMediaType(maxImage, 1)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(Glide4Engine)
                    .forResult(101_01)
        }
    }

    override fun getHotSearchSuccess(list: List<BookInfo>) {
        hotNovels.addAll(list)
        JumpManager.jumpSearch(this, hotNovels, 0)
    }

    override fun getMomentNovelSuccess(novel: Novel) {
        rl_book_detail.visible(true)
        iv_add_book_comment.visible(false)
        novelId = novel.novelId
        iv_book_image.loadImage(novel.novelPhoto)
        tv_book_name.text = novel.novelTitle
        tv_book_detail.text = "书评${novel.commentNum}  书友${novel.readNum}  周排名${novel.weeklyRank}"
    }


    override fun onBackPressed() {
        if (emojiShow) {
            hindEmojiKeyboard()
        } else {
            super.onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null)
            return
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101_01 -> {
                    mImageList.removeAt(mImageList.lastIndex)
                    val paths = Matisse.obtainPathResult(data)
                    paths.forEach {
                        var file = File(cacheDir, "${System.currentTimeMillis()}.jpg")
                        // 压缩图片
                        file = ImageUtil.compress(this, File(it), file).blockingGet()

                        mImageList.add(file.path)
                    }

                    if (mImageList.size < MAX_IMAGE_COUNT) {
                        mImageList.add("")
                    }
                    mImageListAdapter.setNewData(mImageList)
                }
            }
        }
    }

    override fun onDestroy() {
        mUnregistrar.unregister()
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddBookComment(event: BookEvent) {
        mPresenter?.getMomentNovel(event.novelId)
    }

}
