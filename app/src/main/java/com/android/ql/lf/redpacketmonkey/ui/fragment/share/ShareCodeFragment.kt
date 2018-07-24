package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.graphics.Color
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.utils.QRCodeUtil
import kotlinx.android.synthetic.main.fragment_share_code_layout.*

class ShareCodeFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_share_code_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).statusBarColor = Color.TRANSPARENT
        mIvShareCodeImage.postDelayed({
            val bitmap = QRCodeUtil.createQRCodeBitmap("123456", 500, 500)
            mIvShareCodeImage.setImageBitmap(bitmap)
        }, 200)
    }
}