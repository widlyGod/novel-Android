package com.novel.cn.app

import android.content.Context
import android.text.TextUtils
import com.google.gson.JsonObject

import com.jess.arms.http.GlobalHttpHandler
import com.jess.arms.http.log.RequestInterceptor
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.mvp.model.entity.BaseResponse
import okhttp3.*


class GlobalHttpHandlerImpl(private val context: Context) : GlobalHttpHandler {

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      [okhttp3.Interceptor.Chain]
     * @param response   [Response]
     * @return [Response]
     */
    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response {
        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body()!!.contentType())) {
            val data = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, BaseResponse::class.java)

            if (!data.success) {
                throw ApiException(data.message)
            }
        }
        return response
    }

    /**
     * 这里可以在请求服务器之前拿到 [Request], 做一些操作比如给 [Request] 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   [okhttp3.Interceptor.Chain]
     * @param request [Request]
     * @return [Request]
     */
    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {

        val newRequest = chain.request().newBuilder()

        newRequest.addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("Accept", "application/json")
                .addHeader("sessionId", Preference.getString(Constant.SESSION_ID))

        return newRequest.build()
    }


}
