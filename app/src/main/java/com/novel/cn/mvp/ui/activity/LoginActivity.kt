package com.novel.cn.mvp.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.app.click
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerLoginComponent
import com.novel.cn.di.module.LoginModule
import com.novel.cn.mvp.contract.LoginContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.LoginPresenter
import com.novel.cn.utils.PartsUtil
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.TipDialog
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity


class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {


    private val mLoading by lazy {
        TipDialog.Builder(this)
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在登录")
                .create(false)
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(LoginModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)

        verification()

        click(tv_login, iv_qq, iv_wechat, iv_weibo, tv_regist, tv_forget_password, iv_clean, iv_eyes) {
            when (it) {
                tv_login -> login()
                iv_qq -> mPresenter?.authorize(QQ.NAME)
                iv_wechat -> mPresenter?.authorize(Wechat.NAME)
                iv_weibo -> mPresenter?.authorize(SinaWeibo.NAME)     //  1、 新浪微博开放平台应用没有审核通过，不能用sso登陆，否则报错 加以下代码关闭
                tv_regist -> startActivity<RegistActivity>()
                tv_forget_password -> startActivity<ResetPasswrodActivity>()
                iv_eyes -> hidePasswordTransformation(it, et_password)
                iv_clean -> et_password.setText(null)
            }
        }
    }

    private fun hidePasswordTransformation(it: View, edText: EditText) {
        it.isSelected = !it.isSelected
        if (it.isSelected) {
            edText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            edText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        edText.setSelection(edText.length())
    }

    private fun login() {
        val userName = et_userName.text.toString().trim()
        val password = et_password.text.toString().trim()
        if (!PartsUtil.isEmail(userName)) {
            tv_tip.text = "用户名格式不正确"
        } else if (password.length !in 6..12) {
            tv_tip.text = "密码过短，请输入6-12位密码"
        } else {
            mPresenter?.login(userName, password)
        }
    }

    override fun showLoading() {
        mLoading.show()
    }

    override fun hideLoading() {
        mLoading.hide()
    }


    /**
     * 校验输入框,改变按钮状态
     */
    @SuppressLint("CheckResult")
    private fun verification() {
        Observable.combineLatest(
                RxTextView.textChanges(et_userName),
                RxTextView.textChanges(et_password),
                BiFunction<CharSequence, CharSequence, Boolean> { userName, password ->
                    iv_clean.visible(password.trim().isNotEmpty())
                    //都不为空返回true
                    return@BiFunction userName.trim().isNotEmpty() && password.trim().isNotEmpty()
                }
        ).subscribe {
            tv_tip.text = null
            val delegate = tv_login.delegate
            tv_login.isClickable = it
            if (it) {
                delegate.backgroundColor = Color.parseColor("#74a7e2")
            } else {
                delegate.backgroundColor = Color.parseColor("#C7C7C7")
            }
        }
    }

    /**
     * 登录成功回调
     */
    override fun loginSuccess(data: LoginInfo) {
        //保存数据
        Preference.put(Constant.LOGIN_INFO, data)
        Preference.put(Constant.SESSION_ID, data.sessionId)
        startActivity<MainActivity>()
        finish()
    }

}
