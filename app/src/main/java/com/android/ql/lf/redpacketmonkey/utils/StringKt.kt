package com.android.ql.lf.redpacketmonkey.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

fun String.hiddenPhone(): String {
    return "${this.substring(0, 3)}****${this.substring(7, this.length)}"
}

fun String.hiddenBankCarNum(): String {
    try {
        val length = this.length
        if (length > 4) {
            val startNum = this.substring(0, 4)
            val endNum = this.substring(length - 4, length)
            return "$startNum **** **** $endNum"
        }
        return ""
    } catch (e: Exception) {
        return ""
    }
}

fun String.formatTime(): String {
    return try {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        simpleDateFormat.format(Date(this.toLong() * 1000))
    } catch (e: Exception) {
        ""
    }
}

fun String.checkBankCardNum(): Boolean {
    if (this.length < 15 || this.length > 19) {
        return false
    }
    val bit = this.getBankCardCheckCode(this.substring(0, this.length - 1))
    if (bit == 'N') {
        return false
    }
    return this[this.length - 1] == bit
}

fun String.getBankCardCheckCode(nonCheckCodeBankCard: String?): Char {
    if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim { it <= ' ' }.isEmpty() || !nonCheckCodeBankCard.matches("\\d+".toRegex())) {
        return 'N'
    }
    val chs = nonCheckCodeBankCard.trim { it <= ' ' }.toCharArray()
    var luhmSum = 0
    var i = chs.size - 1
    var j = 0
    while (i >= 0) {
        var k = chs[i] - '0'
        if (j % 2 == 0) {
            k *= 2
            k = k / 10 + k % 10
        }
        luhmSum += k
        i--
        j++
    }
    return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + '0'.toInt()).toChar()
}