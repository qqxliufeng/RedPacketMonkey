package com.android.ql.lf.redpacketmonkey.data.livedata

import android.arch.lifecycle.LiveData
import android.util.Log
import com.android.ql.lf.redpacketmonkey.data.UserInfo

object UserInfoLiveData : LiveData<UserInfo>() {


    fun postUserInfo() {
        value = UserInfo.getInstance()
    }

}