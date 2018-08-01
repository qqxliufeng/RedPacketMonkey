package com.android.ql.lf.redpacketmonkey.utils

fun String.hiddenPhone(): String {
    return "${this.substring(0, 3)}****${this.substring(7, this.length)}"
}