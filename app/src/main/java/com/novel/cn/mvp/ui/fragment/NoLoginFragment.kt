package com.novel.cn.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.IndexEvent
import com.jess.arms.utils.LoginEvent
import com.jess.arms.utils.TipDialog

import com.novel.cn.di.component.DaggerNoLoginComponent
import com.novel.cn.di.module.NoLoginModule
import com.novel.cn.mvp.contract.NoLoginContract
import com.novel.cn.mvp.presenter.NoLoginPresenter

import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.model.entity.LoginInfo
import kotlinx.android.synthetic.main.fragment_no_login.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/20/2019 10:52
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
 * @FragmentScope(請注意命名空間) class NullObjectPresenterByFragment
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class NoLoginFragment : BaseFragment<NoLoginPresenter>(), NoLoginContract.View {

    private val mLoading by lazy {
        TipDialog.Builder(context)
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在登录")
                .create(false)
    }

    override fun loginSuccess(data: LoginInfo) {
        //保存数据
        Preference.put(Constant.LOGIN_INFO, data)
        Preference.put(Constant.SESSION_ID, data.sessionId)
        EventBusManager.getInstance().post(IndexEvent().apply { index = 3 })
    }

    companion object {
        fun newInstance(): NoLoginFragment {
            val fragment = NoLoginFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerNoLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .noLoginModule(NoLoginModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_no_login, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv_login_qq.clicks().subscribe {
            mPresenter?.authorize(QQ.NAME)
        }.bindToLifecycle(this)

        tv_login_wechat.clicks().subscribe {
            mPresenter?.authorize(Wechat.NAME)
        }.bindToLifecycle(this)
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     *fun setData(data:Any) {
     *   if(data is Message){
     *       when (data.what) {
     *           0-> {
     *               //根据what 做你想做的事情
     *           }
     *           else -> {
     *           }
     *       }
     *   }
     *}
     *
     *
     *
     *
     *
     * // call setData(Object):
     * val data = Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        mLoading.show()
    }

    override fun hideLoading() {
        mLoading.hide()
    }


    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }
}
