package com.novel.cn.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.component.DaggerUserInfoComponent
import com.novel.cn.di.module.UserInfoModule
import com.novel.cn.mvp.contract.UserInfoContract
import com.novel.cn.mvp.presenter.UserInfoPresenter

import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.ui.dialog.ModifyNamePopup
import com.novel.cn.mvp.ui.dialog.UpdateGenderDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.utils.AppPermissions
import com.novel.cn.ext.setVisible
import com.novel.cn.mvp.ui.dialog.ModifySignaturePopup
import com.novel.cn.utils.Glide4Engine
import com.novel.cn.utils.ImageUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.include_title.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit


class UserInfoActivity : BaseActivity<UserInfoPresenter>(), UserInfoContract.View {

    override fun modifySuccess(user: User) {
        finish()
    }

    override fun modifyName(name: String) {
        userName = name
        tv_nickname.text = name
    }

    override fun modifySignature(name: String) {
        userSignature = name
        tv_intro.text = name
    }

    private val mUser by lazy { intent.getParcelableExtra<User?>("user") }
    private val mRxPermissions by lazy { RxPermissions(this) }
    private var distFileStr = ""
    private var userGender = 0
    private var userName = ""
    private var userSignature = ""

    private val mGenderDialog by lazy {
        UpdateGenderDialog(this, mUser?.userGender)
                .apply {
                    setOnSelectGenderListener {
                        dismiss()
                        this@UserInfoActivity.userGender = it
                        //这里有个坑，使用activity控件必须要activity实例点出来，如果不加activity实例，默认是使用dialog的（即使dialog没有这个控件）
                        this@UserInfoActivity.tv_gender.text = if (it == 0) "男" else "女"
                    }
                }
    }

    private val mModifyNamePopup by lazy { ModifyNamePopup(this, this) }

    private val mModifySignaturePopup by lazy { ModifySignaturePopup(this, this) }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userInfoModule(UserInfoModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_user_info //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_right_tv.setVisible(true)
        userGender = mUser!!.userGender

        mUser?.let {
            iv_avatar.loadImage(it.userPhoto)
            tv_nickname.text = it.userName
            tv_gender.text = if (it.userGender == 0) "男" else "女"
            tv_intro.text = it.userIntro
        }

        cl_gender.setOnClickListener {
            mGenderDialog.show()
        }

        toolbar_right_tv.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            if (distFileStr.isBlank() && userName.isBlank() && userSignature.isBlank() && userGender == mUser!!.userGender)
                finish()
            val params = HashMap<String, Any?>()
            params.put("userId", mUser?.userId)
            params.put("userGender", userGender)
            if (distFileStr.isNotBlank()) {
                params.put("base64str", getImageStr(distFileStr))
                params.put("userPhoto", mUser?.userPhoto)
                params.put("ext", "jpg")
            }
            if (userName.isNotBlank()) {
                params.put("userNickName", userName)
            }
            if (userSignature.isNotBlank()) {
                params.put("userIntroduction", userSignature)
            }
            mPresenter?.modifyUserInfo(params)
        }.bindToLifecycle(this)

        cl_modify_name.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            mModifyNamePopup.showPopupWindow()
        }.bindToLifecycle(this)

        cl_modify_signature.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            mModifySignaturePopup.showPopupWindow()
        }.bindToLifecycle(this)

        cl_user_head.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            AppPermissions.requestCameraPermission(mRxPermissions, this, tips = "选择照片需要相关权限，否则无法使用。") {
                Matisse.from(this)
                        .choose(setOf(MimeType.JPEG, MimeType.PNG))
//                            .capture(true)
//                            .captureStrategy(CaptureStrategy(true, "com.xmssx.baby.baby.fileprovider","test"))
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(Glide4Engine)
//                        .theme(R.style.Matisse_baby)
                        .forResult(0x01)
            }
        }.bindToLifecycle(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0x01 ->
                if (resultCode == Activity.RESULT_OK) {
                    val paths = Matisse.obtainPathResult(data)
                    if (paths.isEmpty())
                        return
                    if (paths.size == 1) {
                        val intent = Intent()
                        intent.setClass(this, CropImageActivity::class.java)
                        intent.putExtra("imageUri", paths.get(0).toString())
                        startActivityForResult(intent, 10003)
                    }
                }
            10003 ->
                if (data != null) {
                    val saveUriStr = data.getParcelableExtra<Uri>("saveUri")
                    var file = File(cacheDir, "${System.currentTimeMillis()}.jpg")
                    // 压缩图片
                    file = ImageUtil.compress(this, saveUriStr, file).blockingGet()
                    iv_avatar.loadImage(file)
                    distFileStr = file.absolutePath

                }
        }
    }

    fun getImageStr(imgFile: String): String {
        var inputStream: InputStream? = null
        var data: ByteArray?
        var result: String = ""
        try {
            inputStream = FileInputStream(imgFile)
            data = ByteArray(inputStream.available())
            inputStream.read(data)
            result = Base64.encodeToString(data, Base64.NO_WRAP)
        } catch (e: IOException) {

        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
        return result
    }


}
