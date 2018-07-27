package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MineLowerLevelItemFragment : BaseRecyclerViewFragment<String>() {


    override fun createAdapter() = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_mine_lower_level_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }
}