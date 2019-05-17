package com.novel.cn.wxapi

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import com.jess.arms.utils.LogUtils
import com.novel.cn.ext.toast
import com.novel.cn.view.TipDialog
import com.novel.cn.view.TipDialog.Builder.ICON_TYPE_LOADING
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.modelpay.PayResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL

class WechatSdk(internal var mContext: Activity, internal var mAppId: String) : IWXAPIEventHandler {
    private var api = WXAPIFactory.createWXAPI(mContext, mAppId, true)
    private var mListener: OnResult<BaseResp>? = null

    private val loading by lazy {
        TipDialog.Builder(mContext)
                .setTipWord("正在加载")
                .setIconType(ICON_TYPE_LOADING)
                .create()
    }

    init {
        api.registerApp(mAppId)
    }//        LogUtil.e(appId);

    fun authorize(listener: OnResult<BaseResp>) {
        if (!api.isWXAppInstalled) {
            toast("您未安装微信")
            return
        }
        mListener = listener

        LogUtils.warnInfo("send start")
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_ssdk_supertao"
        val ret = api.sendReq(req)
        LogUtils.warnInfo("send end = " + ret)

    }

    fun share(title: String, desc: String, photo: String, url: String, scene: Int) {

        if (!api.isWXAppInstalled) {
            toast("您未安装微信")
            return
        }
        val obj = WXWebpageObject()
        obj.webpageUrl = url

        val msg = WXMediaMessage(obj)
        msg.mediaObject = obj
        msg.title = title
        msg.description = desc


        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        req.scene = scene


        Observable.just<SendMessageToWX.Req>(req).subscribeOn(Schedulers.newThread()).subscribe { q ->

            try {
                val bmp = BitmapFactory.decodeStream(URL(photo).openStream())
                val thumb = thumb(bmp, 150, 150)
                bmp.recycle()
                msg.setThumbImage(thumb)
                api.sendReq(q)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun shareText(text: String, scene: Int) {

        if (!api.isWXAppInstalled) {
            toast("您未安装微信")
            return
        }
        val obj = WXTextObject()
        obj.text = text

        val msg = WXMediaMessage(obj)
        msg.mediaObject = obj
        //        msg.title = title;
        msg.description = text

        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        req.scene = scene

        api.sendReq(req)

        //        Observable.just(req).subscribeOn(Schedulers.newThread()).subscribe(q -> {
        //
        //            try {
        ////                Bitmap bmp = BitmapFactory.decodeStream(new URL(photo).openStream());
        ////                Bitmap thumb = thumb(bmp, 150, 150);
        ////                bmp.recycle();
        ////                msg.setThumbImage(thumb);
        //                api.sendReq(q);
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //
        //        });
    }

    fun share(photo: Bitmap, scene: Int) {


        try {
            val imgObj = WXImageObject(photo)

            val msg = WXMediaMessage()
            msg.mediaObject = imgObj
            val req = SendMessageToWX.Req()

            req.transaction = buildTransaction("img")
            req.message = msg
            req.scene = scene
            msg.setThumbImage(thumb(photo, 150, 150))
            photo.recycle()
            api.sendReq(req)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun share(photo: String, scene: Int) {
        if (!api.isWXAppInstalled) {
            toast("您未安装微信")
            return
        }

        Observable.just(1).subscribeOn(Schedulers.newThread()).subscribe { q ->
            try {
                val bmp = BitmapFactory.decodeStream(URL(photo).openStream())
                val imgObj = WXImageObject(bmp)
                val msg = WXMediaMessage()
                msg.mediaObject = imgObj
                val req = SendMessageToWX.Req()
                req.transaction = buildTransaction("img")
                req.message = msg
                req.scene = scene
                msg.setThumbImage(thumb(bmp, 150, 150))
                bmp.recycle()
                api.sendReq(req)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    fun pay(params: Map<String, String>) {
        val req = PayReq()
        req.appId = mAppId
        req.partnerId = params["partnerid"]
        req.prepayId = params["prepayid"]
        req.packageValue = params["packageValue"]
        req.nonceStr = params["noncestr"]
        req.timeStamp = params["timestamp"]
        req.sign = params["sign"]
        val ret = api.sendReq(req)
        LogUtils.warnInfo("send end = " + ret)

    }

    fun pay(order: String, listener: OnResult<BaseResp>) {
        mListener = listener
        val req = PayReq()
        req.appId = mAppId
        try {
            LogUtils.warnInfo(mAppId + "================" + order)
            /*{"appid":"wxdde872a4caf33f63",
            "noncestr":"ggbml4lvj1i3rsvylz15r5v4j5st1n2s",
            "package":"Sign=WXPay","partnerid":"1362922302",
            "prepayid":"wx20160707110610dbfb1daf340462592660",

            "timestamp":"1467860770",
              "sign":"4F59F4C064E1C4DB1052F8A411B3553A"}*/

            //            req.partnerId = "1900000109";
            //            req.prepayId= "1101000000140415649af9fc314aa427";
            //            req.packageValue = "Sign=WXPay";
            //            req.nonceStr= "1101000000140429eb40476f8896f4c9";
            //            req.timeStamp= "1398746574";
            //            req.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";


            val o = JSONObject(order)
            req.partnerId = o.getString("partnerid")
            req.prepayId = o.getString("prepayid")
            req.packageValue = o.getString("package")
            req.nonceStr = o.getString("noncestr")
            req.timeStamp = o.getString("timestamp")
            req.sign = o.getString("sign")
        } catch (e: Exception) {

            e.printStackTrace()
        }

        val ret = api.sendReq(req)
        if (ret) {
            if (!loading.isShowing)
                loading.show()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode != 1999 || mListener == null) {
            return
        }
        api.handleIntent(data, this)
    }


    interface OnResult<in T : BaseResp> {
        abstract fun onResult(info: T)
    }


    fun onWXPayEvent(data: Intent) {
        api.handleIntent(data, this)
    }

    override fun onReq(req: BaseReq) {
        LogUtils.warnInfo("===============")
        LogUtils.warnInfo("req ==> " + req.type)
        when (req.type) {
            ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX, ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX -> {
                LogUtils.warnInfo("req.transaction = " + req.transaction)
            }
            else -> {
            }
        }


    }

    override fun onResp(resp: BaseResp) {

        LogUtils.warnInfo("======>>>t " + resp.transaction + ", type = " + resp.type + ", errCode = " + resp.errCode + ", err = " + resp.errStr)

        loading.dismiss()

        when (resp.type) {
            ConstantsAPI.COMMAND_SENDAUTH -> onAuthResult(resp)
            ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX -> {
            }
            ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX -> {
            }

            ConstantsAPI.COMMAND_PAY_BY_WX -> {
                //            onWXPay((PayResp) resp);

                when (resp.errCode) {
                    0 -> toast("支付成功")
                    -1 -> toast("支付失败")
                    2 -> toast("取消支付")
                }

                if (mListener == null) {
                    return
                }
                mListener!!.onResult(resp as PayResp)
            }
            else -> {
            }
        }
    }

    internal fun onShareResult(resp: BaseResp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                LogUtils.warnInfo("transaction = " + resp.transaction)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
            }
            else -> {
            }
        }
    }

    internal fun onAuthResult(resp: BaseResp) {
        LogUtils.warnInfo("==============>>>" + resp.errCode)
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                val sp = resp as SendAuth.Resp
                val code = sp.code

                LogUtils.warnInfo("code = " + code)
                mListener!!.onResult(sp)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> toast("验证失败")
            else -> {
            }
        }
    }

    companion object {


        fun shareText(context: Context, text: String, scene: Int) {

            val intent = Intent(Intent.ACTION_SEND)
            intent.setClassName("com.tencent.mm", if (scene == 0) "com.tencent.mm.ui.tools.ShareImgUI" else "com.tencent.mm.ui.tools.ShareToTimeLineUI")
            intent.type = "text/plain"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.putExtra("Kdescription", text)
            intent.putExtra(Intent.EXTRA_TEXT, text)
            context.startActivity(intent)
        }

        internal fun compressImage(image: Bitmap, maxSize: Int, offset: Int): ByteArray? {
            val baos = ByteArrayOutputStream()
            try {
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                var options = 100
                var size = baos.toByteArray().size
                while (size / 1024 > maxSize) {
                    baos.reset()
                    options -= offset
                    image.compress(Bitmap.CompressFormat.JPEG, options, baos)
                    size = baos.toByteArray().size
                }
                val toByteArray = baos.toByteArray()
                if (baos != null) {
                    try {
                        baos.close()
                    } catch (e: IOException) {
                    }

                }
                return toByteArray
            } catch (th: Throwable) {
                if (baos != null) {
                    try {
                        baos.close()
                    } catch (e2: IOException) {
                    }

                }
            }

            return null
        }

        fun thumb(src: Bitmap, dstWidth: Int, dstHeight: Int): Bitmap {
            val width = src.width
            val height = src.height
            val sx = dstWidth / width.toFloat()
            val sy = dstHeight / height.toFloat()
            val m = Matrix()
            m.setScale(sx, sy)
            val dst = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.RGB_565)
            val canvas = Canvas(dst)
            canvas.drawColor(Color.WHITE)
            canvas.drawBitmap(src, m, null)
            return dst
        }
    }
}