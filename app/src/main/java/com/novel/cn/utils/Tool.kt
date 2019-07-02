package com.novel.cn.utils




object Tool {
    //数字位
    var chnNumChar = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
    var chnNumChinese = charArrayOf('零', '一', '二', '三', '四', '五', '六', '七', '八', '九')
    //节权位
    var chnUnitSection = arrayOf("", "万", "亿", "万亿")
    //权位
    var chnUnitChar = arrayOf("", "十", "百", "千")
    var intList = HashMap<Any,Int>()

    init {
        for (i in chnNumChar.indices) {
            intList.put(chnNumChinese[i], i)
        }

        intList.put('十', 10)
        intList.put('百', 100)
        intList.put('千', 1000)
    }

}