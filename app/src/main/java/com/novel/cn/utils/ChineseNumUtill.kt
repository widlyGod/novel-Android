package com.novel.cn.utils

object ChineseNumUtill {

    fun numberToChinese(num: Int): String {
        var num = num
        if (num == 0) {
            return "零"
        }

        var weigth = 0//节权位
        var chinese = ""
        var chinese_section = ""
        var setZero = false//下一小节是否需要零，第一次没有上一小节所以为false
        while (num > 0) {
            val section = num % 10000//得到最后面的小节
            if (setZero) {//判断上一小节的千位是否为零，是就设置零
                chinese = TooltoCh(0) + chinese
            }
            chinese_section = sectionTrans(section)
            if (section != 0) {//判断是都加节权位
                chinese_section = chinese_section + getWeight(weigth)
            }

            chinese = chinese_section + chinese

            chinese_section = ""
            setZero = section < 1000 && section > 0
            num = num / 10000
            weigth++

        }
        if ((chinese.length == 2 || chinese.length == 3) && chinese.contains("一十")) {
            chinese = chinese.substring(1, chinese.length)
        }
        return chinese
    }

    fun sectionTrans(section: Int): String {
        var section = section
        val section_chinese = StringBuilder()
        var pos = 0//小节内部权位的计数器
        var zero = true//小节内部的置零判断，每一个小节只能有一个零。
        while (section > 0) {
            val v = section % 10//得到最后一个数
            if (v == 0) {
                if (!zero) {
                    zero = true//需要补零的操作，确保对连续多个零只是输出一个
                    section_chinese.insert(0, TooltoCh(0))
                }
            } else {
                zero = false//有非零数字就把置零打开
                section_chinese.insert(0, getPos(pos))
                section_chinese.insert(0, TooltoCh(v))
            }
            pos++
            section = section / 10
        }

        return section_chinese.toString()
    }

    fun TooltoCh(num: Int): String {

        when (num) {
            0 -> return "零"

            1 -> return "一"

            2 -> return "二"

            3 -> return "三"

            4 -> return "四"

            5 -> return "五"

            6 -> return "六"

            7 -> return "七"

            8 -> return "八"

            9 -> return "九"
        }

        return "erro"

    }

    fun getPos(pos: Int): String {
        when (pos) {
            1 -> return "十"

            2 -> return "百"

            3 -> return "千"
        }

        return ""

    }

    fun getWeight(weight: Int): String {
        when (weight) {
            1 -> return "万"

            2 -> return "亿"
        }

        return ""
    }

}

class ChineseChangeToNumber {
    fun ChineseToNumber(str: String): Int {
        var str = str
        var str1 = String()
        var str2 = String()
        var str3 = String()
        var k = 0
        var dealflag = true
        for (i in 0 until str.length) {//先把字符串中的“零”除去
            if ('零' == str[i]) {
                str = str.substring(0, i) + str.substring(i + 1)
            }
        }
        val chineseNum = str
        for (i in 0 until chineseNum.length) {
            if (chineseNum[i] == '亿') {
                str1 = chineseNum.substring(0, i)//截取亿前面的数字，逐个对照表格，然后转换
                k = i + 1
                dealflag = false//已经处理
            }
            if (chineseNum[i] == '万') {
                str2 = chineseNum.substring(k, i)
                str3 = str.substring(i + 1)
                dealflag = false//已经处理
            }
        }
        if (dealflag) {//如果没有处理
            str3 = chineseNum
        }
        return sectionChinese(str1) * 100000000 +
                sectionChinese(str2) * 10000 + sectionChinese(str3)
    }

    fun sectionChinese(str: String): Int {
        var value = 0
        var sectionNum = 0
        for (i in 0 until str.length) {
            val v = Tool.intList[str[i]] as Int
            if (v == 10 || v == 100 || v == 1000) {//如果数值是权位则相乘
                sectionNum = v * sectionNum
                value = value + sectionNum
            } else if (i == str.length - 1) {
                value = value + v
            } else {
                sectionNum = v
            }
        }
        return value
    }
}