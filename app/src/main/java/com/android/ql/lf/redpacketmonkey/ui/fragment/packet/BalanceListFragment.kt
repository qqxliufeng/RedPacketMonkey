package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class BalanceListFragment :BaseRecyclerViewFragment<String>(){

    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.fragment_packet_info_list_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun getLayoutId() = R.layout.fragment_balance_list_layout

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

}