package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RedPacketRecordForSendFragment :BaseRecyclerViewFragment<String>(){

    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_red_packet_record_send_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        mBaseAdapter.addHeaderView(View.inflate(mContext,R.layout.layout_red_packet_record_send_top_layout,null))
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

}