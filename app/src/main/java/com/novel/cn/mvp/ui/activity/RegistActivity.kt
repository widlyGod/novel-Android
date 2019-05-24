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
import com.jess.arms.integration.lifecycle.ActivityLifecycleable
import com.jess.arms.utils.RxLifecycleUtils

import com.novel.cn.di.component.DaggerRegistComponent
import com.novel.cn.di.module.RegistModule
import com.novel.cn.mvp.contract.RegistContract
import com.novel.cn.mvp.presenter.RegistPresenter

import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.app.click
import com.novel.cn.app.utils.RxUtils
import com.novel.cn.di.module.LoginModule
import com.novel.cn.mvp.contract.LoginContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.LoginPresenter
import com.novel.cn.utils.PartsUtil
import com.novel.cn.utils.StatusBarUtils
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function5
import kotlinx.android.synthetic.main.activity_regist.*
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject

class RegistActivity : BaseActivity<RegistPresenter>(), RegistContract.View, LoginContract.View {


    @Inject
    lateinit var mLoginPresenter: LoginPresenter

    private var isCountdown = false

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerRegistComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registModule(RegistModule(this))
                .loginModule(LoginModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_regist
    }


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)

        verification()

        iv_check.isSelected = true


        click(tv_regist, tv_get_email_code, iv_qq, iv_wechat, iv_weibo, iv_check, iv_eyes,iv_eyes2) {
            when (it) {
                tv_regist -> toRegist()
                tv_get_email_code -> sendCode()
                iv_qq -> mLoginPresenter.authorize(QQ.NAME)
                iv_wechat -> mLoginPresenter.authorize(Wechat.NAME)
                iv_weibo -> mLoginPresenter.authorize(SinaWeibo.NAME)     //  1、 新浪微博开放平台应用没有审核通过，不能用sso登陆，否则报错 加以下代码关闭
                iv_check -> {
                    iv_check.isSelected = !it.isSelected
                    et_email.text = et_email.text
                }
                iv_eyes -> hidePasswordTransformation(it,et_password)
                iv_eyes2 -> hidePasswordTransformation(it,et_password2)
            }
        }
    }

    /**
     * 明密文切换
     */
    private fun hidePasswordTransformation(it: View, edText: EditText) {
        it.isSelected = !it.isSelected
        if (it.isSelected) {
            edText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            edText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        edText.setSelection(edText.length())
    }

    /**
     * 发送验证码
     */
    private fun sendCode() {
        val email = et_email.text.toString()
        mPresenter?.sendCode(email)

    }

    @SuppressLint("CheckResult")
    override fun sendSuccess() {
        //倒计时
        RxUtils.countdown(180)
                .doOnSubscribe {
                    isCountdown = true
                    tv_get_email_code.isEnabled = false
                }
                .compose(RxLifecycle.bindUntilEvent(provideLifecycleSubject(), ActivityEvent.DESTROY))
                .doFinally {
                    isCountdown = false
                    tv_get_email_code.text = "获取验证码"
                    tv_get_email_code.isEnabled = true
                }.subscribe { seconds ->
                    tv_get_email_code.text = "${seconds}S"
                }
    }


    private fun toRegist() {
        val nickname = et_nickname.text.toString().trim()
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()
        val password2 = et_password2.text.toString().trim()
        val emailCode = et_email_code.text.toString().trim()

        when {
            nickname.length !in 1..20 -> tv_tip.text = "请输入昵称（1-20英文、中文或者数字组成）"
            !PartsUtil.isEmail(email) -> tv_tip.text = "邮箱格式不正确"
            password.length !in 6..12 -> tv_tip.text = "密码过短，请输入6-12位密码"
            password2 != password -> tv_tip.text = "两次输入的密码不一致"
            else -> mPresenter?.toRegist(nickname, email, password, emailCode)
        }
    }


    /**
     * 校验输入框,改变按钮状态
     */
    @SuppressLint("CheckResult")
    private fun verification() {
        Observable.combineLatest(
                RxTextView.textChanges(et_nickname),
                RxTextView.textChanges(et_email),
                RxTextView.textChanges(et_password),
                RxTextView.textChanges(et_password2),
                RxTextView.textChanges(et_email_code),
                Function5<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>
                { nickname, email, password, password2, emailCode ->

                    nickname.isNotEmpty() && email.isNotEmpty() &&
                            password.isNotEmpty() && password2.isNotEmpty()
                            && emailCode.isNotEmpty() && iv_check.isSelected
                }
        ).observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this as ActivityLifecycleable))
                .subscribe {
                    changeRegistButtonState(it)
                }
        RxTextView.textChanges(et_email)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this as ActivityLifecycleable))
                .subscribe {
                    tv_get_email_code.isEnabled = it.isNotEmpty() && !isCountdown
                }
    }


    fun changeRegistButtonState(check: Boolean) {
        tv_tip.text = null
        val delegate = tv_regist.delegate
        tv_regist.isClickable = check
        if (check) {
            delegate.backgroundColor = Color.parseColor("#74a7e2")
        } else {
            delegate.backgroundColor = Color.parseColor("#C7C7C7")
        }
    }

    override fun loginSuccess(data: LoginInfo) {
        //保存数据
        Preference.put(Constant.LOGIN_INFO,data)
        Preference.put(Constant.SESSION_ID,data.sessionId)
        finish()
    }

    override fun registSuccess(data: LoginInfo) {
        loginSuccess(data)
    }

}
