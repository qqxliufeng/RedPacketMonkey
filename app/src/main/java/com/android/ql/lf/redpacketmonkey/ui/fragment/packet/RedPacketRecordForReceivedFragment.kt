package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.view.View
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RedPacketRecordForReceivedFragment : BaseRecyclerViewFragment<String>() {

    override fun createAdapter() = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_red_packet_record_received_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        val topView = View.inflate(mContext, R.layout.layout_red_packet_record_received_top_layout, null)
        topView.findViewById<TextView>(R.id.mTvRecPacketRecordTopTitle).text = "共收到"
        mBaseAdapter.addHeaderView(topView)
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }
}