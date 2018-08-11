package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.dialog.CrashFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.dialog.RechargeDialogFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.MineInfoFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.MineRecommendFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.AliPayFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.BankListFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.RechargeFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.packet.MinePacketFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.setting.SettingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.share.ShareCodeFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.share.ShareFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.hiddenPhone
import kotlinx.android.synthetic.main.fragment_mine_layout.*
import org.jetbrains.anko.support.v4.toast

class MineFragment : BaseNetWorkingFragment() {


    private val rechargeFragment by lazy {
        RechargeDialogFragment()
    }


    private val crashFragment by lazy {
        CrashFragment()
    }

    private var toolBarHeight = 0

    override fun getLayoutId() = R.layout.fragment_mine_layout

    override fun initView(view: View?) {
        if (UserInfo.getInstance().isLogin) {
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineNickName.text = UserInfo.getInstance().user_nickname
            mTvMinePhone.text = "TEL：${UserInfo.getInstance().user_phone.hiddenPhone()}"
        }
        UserInfoLiveData.observe(this, Observer<UserInfo> {
            mTvMineNickName.text = it?.user_nickname
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineMoneyCount.text = "￥ ${UserInfo.getInstance().money_sum_cou.toFloat()}"
        })
        (mTlMainMine.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mTlMainMine.post {
            toolBarHeight = mTlMainMine.measuredHeight
        }
        var alpha = 0
        var scale: Float
        mNSvMineContainer.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY <= toolBarHeight) {
                scale = (scrollY.toFloat() / toolBarHeight)
                alpha = (scale * 255).toInt()
                mTlMainMine.setBackgroundColor(Color.argb(alpha, 151, 53, 190))
            } else {
                if (alpha < 255) {
                    alpha = 255
                    mTlMainMine.setBackgroundColor(Color.argb(alpha, 151, 53, 190))
                }
            }
        }
        mTvMineRecharge.setOnClickListener {
            rechargeFragment.myShow(childFragmentManager, "recharge_dialog") {
                if (!TextUtils.isEmpty(it)) {
                    RechargeFragment.startRecharge(mContext,"1")
                } else {
                    toast("请输入充值金额")
                }
            }
        }
        mTvMineCrash.setOnClickListener {
            crashFragment.show(childFragmentManager, "crash_dialog")
        }
        mTvMineSetting.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(false).setTitle("设置").setClazz(SettingFragment::class.java).start()
        }
        mClMineInfoContainer.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("个人信息").setClazz(MineInfoFragment::class.java).start()
        }
        mTvMineAli.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("支付宝").setClazz(AliPayFragment::class.java).start()
        }
        mTvMineShare.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(false).setTitle("分享").setClazz(ShareFragment::class.java).start()
        }
        mRlMinePacket.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setHiddenToolBar(true).setClazz(MinePacketFragment::class.java).start()
        }
        mTvMineRecommend.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setHiddenToolBar(true).setClazz(MineRecommendFragment::class.java).start()
        }
        mTvBankList.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("银行卡").setClazz(BankListFragment::class.java).start()
        }
        mTvMineShareCode.setOnClickListener {
            ShareCodeFragment.start(mContext)
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在充值……")
            }
            0x1 -> {

            }
        }
    }


    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> {
                "充值失败"
            }
            0x1 -> {
                "充值失败"
            }
            else -> {
                "未知错误"
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when(requestID){
            0x0->{
                if (obj!=null){
                    toast("充值成功！")
                }
            }
            0x1->{

            }
        }
    }

}