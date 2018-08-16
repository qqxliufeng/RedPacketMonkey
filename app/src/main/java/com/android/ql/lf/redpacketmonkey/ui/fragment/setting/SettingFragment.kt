package com.android.ql.lf.redpacketmonkey.ui.fragment.setting

import android.view.View
import cn.jpush.im.android.api.JMessageClient
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.utils.CacheDataManager
import com.android.ql.lf.redpacketmonkey.utils.RxBus
import com.android.ql.lf.redpacketmonkey.utils.VersionHelp
import com.android.ql.lf.redpacketmonkey.utils.alert
import kotlinx.android.synthetic.main.fragment_setting_layout.*
import org.jetbrains.anko.support.v4.toast

class SettingFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_setting_layout

    override fun initView(view: View?) {
        mTvSettingVersion.text = "当前版本${VersionHelp.currentVersionName(mContext)}"
        mTvSettingResetPassword.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("修改登录密码").setNeedNetWorking(true).setClazz(ResetPasswordFragment::class.java).start()
        }
        mTvSettingPayPassword.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("添加支付密码").setNeedNetWorking(true).setClazz(PayPasswordFragment::class.java).start()
        }
        mTvSettingNotifySwitch.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("开关设置").setNeedNetWorking(true).setClazz(SoundSettingFragment::class.java).start()
        }
        mTvSettingClearCache.text = CacheDataManager.getTotalCacheSize(mContext)
        mLlSettingClearCache.setOnClickListener {
            CacheDataManager.clearAllCache(mContext)
            mTvSettingClearCache.text = CacheDataManager.getTotalCacheSize(mContext)
            toast("缓存清理成功")
        }
        mBtSettingLogout.setOnClickListener{
            alert("提示","是否要退出当前账号？","退出","暂不退出",{_,_->
                JMessageClient.logout()
                RxBus.getDefault().post(UserInfo.getInstance())
                UserInfo.getInstance().loginOut()
                finish()
            },null)
        }
    }
}