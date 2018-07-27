package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.graphics.Color
import android.support.v4.widget.NestedScrollView
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.dialog.CrashFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.dialog.RechargeFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.message.MineMessageFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.LoginFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.MineRecommendFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.AliPayFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.BankListFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.packet.MinePacketFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.setting.SettingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.share.ShareCodeFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.share.ShareFragment
import kotlinx.android.synthetic.main.fragment_mine_layout.*

class MineFragment : BaseFragment() {


    private val rechargeFragment by lazy {
        RechargeFragment()
    }


    private val crashFragment by lazy {
        CrashFragment()
    }

    private var toolBarHeight = 0

    override fun getLayoutId() = R.layout.fragment_mine_layout

    override fun initView(view: View?) {
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
            rechargeFragment.show(childFragmentManager, "recharge_dialog")
        }
        mTvMineCrash.setOnClickListener {
            crashFragment.show(childFragmentManager, "crash_dialog")
        }
        mTvMineSetting.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(false).setTitle("设置").setClazz(SettingFragment::class.java).start()
        }
        mClMineInfoContainer.setOnClickListener {
            LoginFragment.startLogin(mContext)
//            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("个人信息").setClazz(MineInfoFragment::class.java).start()
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
        mTvMessage.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("我的公告").setClazz(MineMessageFragment::class.java).start()
        }
        mTvBankList.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("银行卡").setClazz(BankListFragment::class.java).start()
        }
        mTvMineShareCode.setOnClickListener {
            ShareCodeFragment.start(mContext)
        }
    }
}