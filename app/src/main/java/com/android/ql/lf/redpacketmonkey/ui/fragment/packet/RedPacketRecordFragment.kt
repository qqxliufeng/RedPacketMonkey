package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_red_packet_record_layout.*

class RedPacketRecordFragment : BaseFragment() {

    companion object {
        val TITLES = arrayOf("我收到的", "我发出的")
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_record_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.parseColor("#d95940"))
        (mContext as FragmentContainerActivity).toolbar.setTitleTextColor(Color.WHITE)
        (mContext as FragmentContainerActivity).toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)

        mVpRedPacketRecord.adapter = MyRedPacketRecordAdapter(childFragmentManager)
        mTlRedPacketRecord.setupWithViewPager(mVpRedPacketRecord)
    }


    class MyRedPacketRecordAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = when (position) {
            0 -> {
                RedPacketRecordForReceivedFragment()
            }
            1 -> {
                RedPacketRecordForSendFragment()
            }
            else -> {
                null
            }
        }

        override fun getCount() = TITLES.size

        override fun getPageTitle(position: Int) = TITLES[position]



    }

}