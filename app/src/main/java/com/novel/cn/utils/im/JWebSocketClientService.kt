package com.novel.cn.utils.im

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.app.NotificationCompat
import android.util.Log


import org.java_websocket.handshake.ServerHandshake

import java.net.URI

import android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.mvp.model.api.Api
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.ui.activity.MainActivity

class JWebSocketClientService : Service() {
    var client: JWebSocketClient? = null
    private val mBinder = JWebSocketClientBinder()
    internal var wakeLock: PowerManager.WakeLock? = null//锁屏唤醒
    private val mHandler = Handler()
    private val heartBeatRunnable = object : Runnable {
        override fun run() {
            Log.e("JWebSocketClientService", "心跳包检测websocket连接状态")
            if (client != null) {
                if (client!!.isClosed) {
                    reconnectWs()
                }
            } else {
                //如果client已为空，重新初始化连接
                client = null
                initSocketClient()
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE)
        }
    }

    //灰色保活
    class GrayInnerService : Service() {

        override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
            startForeground(GRAY_SERVICE_ID, Notification())
            stopForeground(true)
            stopSelf()
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onBind(intent: Intent): IBinder? {
            return null
        }
    }

    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    @SuppressLint("InvalidWakeLockTag")
    private fun acquireWakeLock() {
        if (null == wakeLock) {
            val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ON_AFTER_RELEASE, "PostLocationService")
            if (null != wakeLock) {
                wakeLock!!.acquire()
            }
        }
    }

    //用于Activity和service通讯
    inner class JWebSocketClientBinder : Binder() {
        val service: JWebSocketClientService
            get() = this@JWebSocketClientService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //初始化websocket
        initSocketClient()
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE)//开启心跳检测

        //设置service为前台服务，提高优先级
        if (Build.VERSION.SDK_INT < 18) {
            //Android4.3以下 ，隐藏Notification上的图标
            startForeground(GRAY_SERVICE_ID, Notification())
        } else if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 25) {
            //Android4.3 - Android7.0，隐藏Notification上的图标
            val innerIntent = Intent(this, GrayInnerService::class.java)
            startService(innerIntent)
            startForeground(GRAY_SERVICE_ID, Notification())
        } else {
            //Android7.0以上app启动后通知栏会出现一条"正在运行"的通知
            startForeground(GRAY_SERVICE_ID, Notification())
        }

        acquireWakeLock()
        return Service.START_STICKY
    }


    override fun onDestroy() {
        closeConnect()
        super.onDestroy()
    }


    /**
     * 初始化websocket连接
     */
    private fun initSocketClient() {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)

        val uri = if (!user?.sessionId.isNullOrEmpty()) URI.create(Api.ws + user?.sessionId) else URI.create(Api.ws)
        client = object : JWebSocketClient(uri) {
            override fun onMessage(message: String) {
                Log.e("JWebSocketClientService", "收到的消息：$message")

                val intent = Intent()
                intent.action = "com.novel.cn.content"
                intent.putExtra("message", message)
                sendBroadcast(intent)

                checkLockAndShowNotification(message)
            }

            override fun onOpen(handshakedata: ServerHandshake) {
                super.onOpen(handshakedata)
                Log.e("JWebSocketClientService", "websocket连接成功")
            }
        }
        connect()
    }

    /**
     * 连接websocket
     */
    private fun connect() {
        object : Thread() {
            override fun run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client!!.connectBlocking()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }.start()

    }

    /**
     * 发送消息
     *
     * @param msg
     */
    fun sendMsg(msg: String) {
        if (null != client) {
            Log.e("JWebSocketClientService", "发送的消息：$msg")
            client!!.send(msg)
        }
    }

    /**
     * 断开连接
     */
    private fun closeConnect() {
        try {
            if (null != client) {
                client!!.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            client = null
        }
    }


    //    -----------------------------------消息通知--------------------------------------------------------

    /**
     * 检查锁屏状态，如果锁屏先点亮屏幕
     *
     * @param content
     */
    private fun checkLockAndShowNotification(content: String) {
        //管理锁屏的一个服务
        val km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (km.inKeyguardRestrictedInputMode()) {//锁屏
            //获取电源管理器对象
            val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isScreenOn) {
                @SuppressLint("InvalidWakeLockTag") val wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright")
                wl.acquire()  //点亮屏幕
                wl.release()  //任务结束后释放
            }
            sendNotification(content)
        } else {
            sendNotification(content)
        }
    }

    /**
     * 发送通知
     *
     * @param content
     */
    private fun sendNotification(content: String) {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java!!)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                // 设置该通知优先级
                .setPriority(Notification.PRIORITY_MAX)
//                .setSmallIcon(R.drawable.icon)
                .setContentTitle("服务器")
                .setContentText(content)
                .setVisibility(VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                // 向通知添加声音、闪灯和振动效果
                .setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_ALL or Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build()
        notifyManager.notify(1, notification)//id要保证唯一
    }

    /**
     * 开启重连
     */
    private fun reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable)
        object : Thread() {
            override fun run() {
                try {
                    Log.e("JWebSocketClientService", "开启重连")
                    client!!.reconnectBlocking()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }.start()
    }

    companion object {
        private const val GRAY_SERVICE_ID = 1001


        //    -------------------------------------websocket心跳检测------------------------------------------------
        private const val HEART_BEAT_RATE = (10 * 1000).toLong()//每隔10秒进行一次对长连接的心跳检测
    }
}
