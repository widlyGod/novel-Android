package com.novel.cn.utils.pay

import android.text.TextUtils

class Result() {
    var sResultStatus: MutableMap<String, String> = HashMap()
    lateinit var resultStatus: String
    lateinit var result: String
    lateinit var memo: String
    lateinit var resultText: String
    var isSuccess = false
    var isPending = false

    constructor (rawResult: String) : this() {
        sResultStatus.put("9000", "支付成功")
        sResultStatus.put("8000", "支付结果确认中")
        sResultStatus.put("4000", "系统异常")
        sResultStatus.put("4001", "数据格式不正确")
        sResultStatus.put("4003", "该用户绑定的支付宝账户被冻结或不允许支付")
        sResultStatus.put("4004", "该用户已解除绑定")
        sResultStatus.put("4005", "绑定失败或没有绑定")
        sResultStatus.put("4006", "订单支付失败")
        sResultStatus.put("4010", "重新绑定账户")
        sResultStatus.put("6000", "支付服务正在进行升级操作")
        sResultStatus.put("6001", "用户中途取消支付操作")
        sResultStatus.put("7001", "网页支付失败")


        if (TextUtils.isEmpty(rawResult)) return
        val resultParams = rawResult.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (resultParam in resultParams) {
            if (resultParam.startsWith("resultStatus")) {
                resultStatus = gatValue(resultParam, "resultStatus")
            } else if (resultParam.startsWith("result")) {
                result = gatValue(resultParam, "result")
            } else if (resultParam.startsWith("memo")) {
                memo = gatValue(resultParam, "memo")
            }
        }
        if (sResultStatus.containsKey(resultStatus)) {
            resultText = sResultStatus[resultStatus]!!
        } else {
            resultText = "支付失败"
        }
        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
        isSuccess = "9000" == resultStatus
        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
        isPending = "8000" == resultStatus

    }

    private fun gatValue(content: String, key: String): String {
        val prefix = key + "={"
        return content.substring(content.indexOf(prefix) + prefix.length, content.lastIndexOf("}"))
    }

    override fun toString(): String {
        return "resultStatus={$resultStatus};memo={$memo};result={$result}"
    }

}