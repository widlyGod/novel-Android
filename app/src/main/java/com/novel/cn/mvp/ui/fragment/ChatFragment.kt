package com.novel.cn.mvp.ui.fragment

import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseFragment
import com.jess.arms.base.BaseLazyLoadFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.LogUtils

import com.novel.cn.di.component.DaggerChatComponent
import com.novel.cn.di.module.ChatModule
import com.novel.cn.mvp.contract.ChatContract
import com.novel.cn.mvp.presenter.ChatPresenter

import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.model.entity.ChatMessageBean
import com.novel.cn.mvp.model.entity.ChatSendBean
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.ui.adapter.ChatMessageAdapter
import com.novel.cn.mvp.ui.adapter.CircleAdapter
import com.novel.cn.utils.fromJson
import com.novel.cn.utils.im.JWebSocketClient
import com.novel.cn.utils.im.JWebSocketClientService
import com.novel.cn.utils.toJson
import kotlinx.android.synthetic.main.fragment_chat.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 10:54
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
class ChatFragment : BaseLazyLoadFragment<ChatPresenter>(), ChatContract.View {
    companion object {
        fun newInstance(): ChatFragment {
            val fragment = ChatFragment()
            return fragment
        }
    }

    private var client: JWebSocketClient? = null
    private lateinit var binder: JWebSocketClientService.JWebSocketClientBinder
    private lateinit var jWebSClientService: JWebSocketClientService
    private lateinit var chatMessageReceiver: ChatMessageReceiver
    private val chatList by lazy { mutableListOf<ChatMessageBean>() }

    @Inject
    lateinit var adapter: ChatMessageAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerChatComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .chatModule(ChatModule(this))
                .build()
                .inject(this)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("MainActivity", "服务与活动成功绑定")
            binder = iBinder as JWebSocketClientService.JWebSocketClientBinder
            jWebSClientService = binder.service
            client = jWebSClientService.client!!
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e("MainActivity", "服务与活动成功断开")
        }
    }

    private inner class ChatMessageReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("message")
            chatList.add(message.fromJson())
            adapter.notifyDataSetChanged()
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }


    override fun lazyLoadData() {
        //启动服务
        startJWebSClientService()
        //绑定服务
        mBindService()
        //注册广播
        doRegisterReceiver()
        //检测通知是否开启
        checkNotification(mContext)
    }

    override fun initData(savedInstanceState: Bundle?) {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        tv_chat_send.clicks().subscribe {
            val content = et_chat_message.text.toString()
            if (content.isEmpty()) {
                showMessage("消息不能为空哟")
                return@subscribe
            }
            val chatSendBean = ChatSendBean("0", content, null)

            if (client != null && client!!.isOpen) {
                jWebSClientService.sendMsg(chatSendBean.toJson())
                et_chat_message.setText("")
            } else {
                showMessage("连接已断开，请稍等或重启App哟")
            }
        }.bindToLifecycle(this)
        recycler_chat.adapter = adapter.apply {
            setUser(user!!)
            setNewData(chatList)
        }
    }

    /**
     * 绑定服务
     */
    private fun mBindService() {
        val bindIntent = Intent(mContext, JWebSocketClientService::class.java)
        mContext.bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE)
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private fun startJWebSClientService() {
        val intent = Intent(mContext, JWebSocketClientService::class.java)
        mContext.startService(intent)
    }

    /**
     * 动态注册广播
     */
    private fun doRegisterReceiver() {
        chatMessageReceiver = ChatMessageReceiver()
        val filter = IntentFilter("com.novel.cn.content")
        mContext.registerReceiver(chatMessageReceiver, filter)
    }


    /**
     * 检测是否开启通知
     *
     * @param context
     */
    private fun checkNotification(context: Context) {
        if (!isNotificationEnabled(context)) {
            AlertDialog.Builder(context).setTitle("温馨提示")
                    .setMessage("你还未开启系统通知，将影响消息的接收，要去开启吗？")
                    .setPositiveButton("确定") { dialog, which -> setNotification(context) }.setNegativeButton("取消") { dialog, which -> }.show()
        }
    }

    /**
     * 如果没有开启通知，跳转至设置界面
     *
     * @param context
     */
    private fun setNotification(context: Context) {
        val localIntent = Intent()
        //直接跳转到应用通知设置的代码：
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            localIntent.putExtra("app_package", context.packageName)
            localIntent.putExtra("app_uid", context.applicationInfo.uid)
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            localIntent.addCategory(Intent.CATEGORY_DEFAULT)
            localIntent.data = Uri.parse("package:" + context.packageName)
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts("package", context.packageName, null)
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.action = Intent.ACTION_VIEW
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails")
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
            }
        }
        context.startActivity(localIntent)
    }

    /**
     * 获取通知权限,监测是否开启了系统通知
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun isNotificationEnabled(context: Context): Boolean {

        val CHECK_OP_NO_THROW = "checkOpNoThrow"
        val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"

        val mAppOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val appInfo = context.applicationInfo
        val pkg = context.applicationContext.packageName
        val uid = appInfo.uid

        var appOpsClass: Class<*>? = null
        try {
            appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass!!.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String::class.java)
            val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)

            val value = opPostNotificationValue.get(Int::class.java) as Int
            return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}
