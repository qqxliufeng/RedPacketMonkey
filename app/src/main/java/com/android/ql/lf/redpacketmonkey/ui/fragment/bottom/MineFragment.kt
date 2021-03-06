package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.BankCardInfoBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData
import com.android.ql.lf.redpacketmonkey.present.UserPresent
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.dialog.CrashFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.dialog.RechargeDialogFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.MineInfoFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.MineRecommendFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.BankListFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.RechargeFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.packet.MinePacketFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.setting.SettingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.share.ShareCodeFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.share.ShareFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import kotlinx.android.synthetic.main.activity_fragment_container_layout.*
import kotlinx.android.synthetic.main.fragment_mine_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class MineFragment : BaseNetWorkingFragment() {

    private val rechargeFragment by lazy {
        RechargeDialogFragment()
    }

    private val crashFragment by lazy {
        CrashFragment()
    }

    private var toolBarHeight = 0

    private var bankCardInfoBean: BankCardInfoBean? = null

    private val bankCardSubscription by lazy {
        RxBus.getDefault().toObservable(BankCardInfoBean::class.java).subscribe {
            bankCardInfoBean = it
            crashFragment.setBankCardName(if(TextUtils.isEmpty(it.card_name))"暂无所属银行" else it.card_name!!)
        }
    }

    private val reloadUserInfoSubscription by lazy {
        RxBus.getDefault().toObservable(ReloadUserInfoBean::class.java).subscribe {
            mPresent.getDataByPost(0x2, RequestParamsHelper.getPersonalParam(UserInfo.getInstance().user_id))
        }
    }

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_mine_layout

    override fun initView(view: View?) {
        bankCardSubscription
        reloadUserInfoSubscription
        mSrfMine.setOnRefreshListener {
            mPresent.getDataByPost(0x2, RequestParamsHelper.getPersonalParam(UserInfo.getInstance().user_id))
        }
        if (UserInfo.getInstance().isLogin) {
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineNickName.text = UserInfo.getInstance().user_nickname
            mTvMinePhone.text = "TEL：${UserInfo.getInstance().user_phone.hiddenPhone()}"
        }
        UserInfoLiveData.observe(this, Observer<UserInfo> {
            mTvMineNickName.text = it?.user_nickname
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineMoneyCount.text = "￥ ${UserInfo.getInstance().money_sum_cou}"
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
                    RechargeFragment.startRecharge(mContext, it)
                } else {
                    toast("请输入充值金额")
                }
            }
        }
        mTvMineCrash.setOnClickListener {
            val tip = if(TextUtils.isEmpty(bankCardInfoBean?.card_name))"暂无所属银行" else bankCardInfoBean?.card_name!!
            crashFragment.myShow(childFragmentManager, "crash_dialog", tip,listener = {
                if (bankCardInfoBean == null) {
                    toast("请先选择银行")
                    return@myShow
                }
                if (bankCardInfoBean!!.card_number != null){
                    mPresent.getDataByPost(0x3, RequestParamsHelper.getCrashParam(bankCardInfoBean!!.card_number!!, it))
                    crashFragment.dismiss()
                }else{
                    toast("请先选择银行")
                }
            }, selectBankCardListener = {
                BankListFragment.startBankCardList(mContext, true)
            })
        }
        mTvMineSetting.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(false).setTitle("设置").setClazz(SettingFragment::class.java).start()
        }
        mClMineInfoContainer.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("个人信息").setClazz(MineInfoFragment::class.java).start()
        }
//        mTvMineAli.setOnClickListener {
//            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("支付宝").setClazz(AliPayFragment::class.java).start()
//        }
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
            BankListFragment.startBankCardList(mContext, false)
        }
        mTvMineShareCode.setOnClickListener {
            ShareCodeFragment.start(mContext)
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x3 -> {
                getFastProgressDialog("正在申请提现……")
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x3 -> {
                "申请提现失败"
            }
            else -> {
                super.showFailMessage(requestID)
            }
        }
    }

    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        if (requestID == 0x2) {
            mSrfMine.post { mSrfMine.isRefreshing = false }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x2 -> {
                userPresent.onLoginNoBus(obj as JSONObject)
            }
            0x3 -> {
                if (obj is Int){
                    UserInfo.getInstance().setMoney_sum_cou(obj * 1.0)
                }else if (obj is Double){
                    UserInfo.getInstance().setMoney_sum_cou(obj)
                }
                UserInfoLiveData.postUserInfo()
                toast("提现成功，请等待后台审核……")
            }
        }
    }

    override fun onDestroyView() {
        unsubscribe(reloadUserInfoSubscription)
        unsubscribe(bankCardSubscription)
        super.onDestroyView()
    }

    class ReloadUserInfoBean

}