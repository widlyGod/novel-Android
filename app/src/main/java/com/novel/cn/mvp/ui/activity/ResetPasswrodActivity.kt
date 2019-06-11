package com.novel.cn.mvp.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.lifecycle.ActivityLifecycleable
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.utils.RxUtils
import com.novel.cn.di.component.DaggerResetPasswrodComponent
import com.novel.cn.di.module.ResetPasswrodModule
import com.novel.cn.mvp.contract.ResetPasswrodContract
import com.novel.cn.mvp.presenter.ResetPasswrodPresenter
import com.novel.cn.utils.PartsUtil
import com.novel.cn.utils.StatusBarUtils
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.activity_reset_passwrod.*
import kotlinx.android.synthetic.main.include_title.*


class ResetPasswrodActivity : BaseActivity<ResetPasswrodPresenter>(), ResetPasswrodContract.View {


    private var isCountdown = false

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerResetPasswrodComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .resetPasswrodModule(ResetPasswrodModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_reset_passwrod //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {
        verification()


        click(tv_get_email_code, tv_confirm, iv_eyes) {
            when (it) {
                tv_get_email_code -> {
                    val email = et_email.text.toString().trim()
                    if (!PartsUtil.isEmail(email)) {
                        tv_tip.text = "邮箱格式不正确"
                    } else {
                        mPresenter?.sendCode(email)
                    }
                }
                tv_confirm -> toResetPassword()
                iv_eyes -> hidePasswordTransformation(it, et_password)
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


    private fun toResetPassword() {
        val email = et_email.text.toString().trim()
        val code = et_email_code.text.toString().trim()
        val password = et_password.text.toString().trim()
        when {
            !PartsUtil.isEmail(email) -> tv_tip.text = "邮箱格式不正确"
            password.length !in 6..12 -> tv_tip.text = "密码过短，请输入6-12位密码"
            else -> mPresenter?.resetPassword(email, code, password)
        }
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

    /**
     * 校验输入框,改变按钮状态
     */
    @SuppressLint("CheckResult")
    private fun verification() {

        et_email.textChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this as ActivityLifecycleable))
                .subscribe {
                    tv_get_email_code.isEnabled = it.isNotEmpty() && !isCountdown
                }

        Observable.combineLatest(
                et_email.textChanges(),
                et_password.textChanges(),
                et_email_code.textChanges(),
                Function3<CharSequence, CharSequence, CharSequence, Boolean> { email, password, code ->
                    email.trim().isNotEmpty() && password.trim().isNotEmpty() && code.isNotEmpty()
                }
        ).subscribe {
            tv_tip.text = null
            val delegate = tv_confirm.delegate
            tv_confirm.isEnabled = it
            if (it) {
                delegate.backgroundColor = Color.parseColor("#74a7e2")
            } else {
                delegate.backgroundColor = Color.parseColor("#C7C7C7")
            }
        }
    }

    override fun resetSuccess(message: String) {
        ArmsUtils.makeText(this, message)
        finish()
    }

}
