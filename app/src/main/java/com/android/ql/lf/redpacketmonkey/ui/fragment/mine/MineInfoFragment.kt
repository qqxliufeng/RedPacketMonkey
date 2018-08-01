package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.app.Activity
import android.content.Intent
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData
import com.android.ql.lf.redpacketmonkey.ui.activity.SelectAddressActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.AliPayNameDialogFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import kotlinx.android.synthetic.main.fragment_mine_info_layout.*
import org.jetbrains.anko.support.v4.toast

class MineInfoFragment : BaseNetWorkingFragment() {

    private val aliPayNameDialogFragment by lazy {
        AliPayNameDialogFragment()
    }

    override fun getLayoutId() = R.layout.fragment_mine_info_layout

    override fun initView(view: View?) {

        mTvMineInfoNickName.text = UserInfo.getInstance().user_nickname

        mRlMineInfoNickNameContainer.setOnClickListener {
            aliPayNameDialogFragment.myShow(childFragmentManager, "reset_nick_name", "修改昵称", "昵称") {
                mPresent.getDataByPost(0x0, RequestParamsHelper.getNickNameParam(it))
            }
        }
        mRlMineInfoAddress.setOnClickListener {
            SelectAddressActivity.startAddressActivityForResult(this, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){

        }
    }


    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> { //修改昵称
                getFastProgressDialog("正在修改昵称")
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x0 -> {
                if (obj != null && obj is String) {
                    UserInfo.getInstance().user_nickname = obj
                    mTvMineInfoNickName.text = obj
                    UserInfoLiveData.postUserInfo()
                    toast("昵称修改成功")
                }
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> { //修改昵称
                "昵称修改失败"
            }
            else -> {
                "未知异常"
            }
        }
    }

}