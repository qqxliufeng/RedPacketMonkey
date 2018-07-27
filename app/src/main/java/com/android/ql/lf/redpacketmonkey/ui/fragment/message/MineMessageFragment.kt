package com.android.ql.lf.redpacketmonkey.ui.fragment.message

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MineMessageFragment :BaseRecyclerViewFragment<String>(){

    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.fragment_message_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<LinearLayout>(R.id.mLlMessageItemRight).setOnClickListener {
                notifyItemRemoved(helper.adapterPosition)
                mArrayList.remove(item)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_message_layout

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }


    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        if (view!!.id == R.id.mLlMessageItemRight){

        }
    }
}