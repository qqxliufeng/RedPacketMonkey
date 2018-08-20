package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.utils.Constants
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.QRCodeUtil
import kotlinx.android.synthetic.main.fragment_share_code_layout.*

class ShareCodeFragment : BaseFragment() {

    companion object {
        fun start(context: Context) {
            FragmentContainerActivity.from(context).setTitle("分享码").setClazz(ShareCodeFragment::class.java).setHiddenToolBar(true).setNeedNetWorking(false).start()
        }
    }

    override fun getLayoutId() = R.layout.fragment_share_code_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).statusBarColor = Color.TRANSPARENT
        GlideManager.loadImage(mContext,"${Constants.BASE_IP}/api/user/code?uid=${UserInfo.getInstance().user_id}?time=${System.currentTimeMillis()}",mIvShareCodeImage)
//        mIvShareCodeImage.postDelayed({
//            val bitmap = QRCodeUtil.createQRCodeBitmap(UserInfo.getInstance().shareUrl, 500, 500)
//            mIvShareCodeImage.setImageBitmap(bitmap)
//        }, 200)
    }
}