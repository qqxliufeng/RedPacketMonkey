package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_share_layout.*

class ShareFragment :BaseFragment(){

    private val shareDialogFragment by lazy {
        ShareDialogFragment()
    }

    override fun getLayoutId() = R.layout.fragment_share_layout

    override fun initView(view: View?) {
        mTvFragmentShare.setOnClickListener {
            shareDialogFragment.show(childFragmentManager,"share_dialog_fragment")
        }

        mTvFragmentShareCode.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(ShareCodeFragment::class.java).setHiddenToolBar(true).setNeedNetWorking(false).start()
        }

    }
}