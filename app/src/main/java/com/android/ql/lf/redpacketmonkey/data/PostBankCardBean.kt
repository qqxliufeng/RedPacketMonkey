package com.android.ql.lf.redpacketmonkey.data

import android.text.TextUtils
import com.android.ql.lf.redpacketmonkey.utils.checkBankCardNum
import com.android.ql.lf.redpacketmonkey.utils.isIdCard
import com.android.ql.lf.redpacketmonkey.utils.isPhone

class PostBankCardBean {

    var name: String? = null
    var cardNumber: String? = null
    var idCardNumber: String? = null
    var phone: String? = null
    var code: String? = null

    fun checked(): String {
        if (TextUtils.isEmpty(name)) {
            return "姓名不能为空"
        }
        if (TextUtils.isEmpty(cardNumber)) {
            return "银行卡号不能为空"
        }
        if (!cardNumber!!.checkBankCardNum()) {
            return "请输入合法的银行卡号"
        }
        if (TextUtils.isEmpty(idCardNumber)) {
            return "身份证号不能为空"
        }
        if (!idCardNumber!!.isIdCard()) {
            return "请输入合法的身份证号"
        }
        if (TextUtils.isEmpty(phone)) {
            return "手机号不能为空"
        }
        if (!phone!!.isPhone()) {
            return "请输入合法的手机号"
        }
        if (TextUtils.isEmpty(code)) {
            return "验证码不能为空"
        }
        return ""
    }
}