package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.message.MineMessageInfoFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_message_layout.*

class MessageFragment : BaseRecyclerViewFragment<String>() {

    override fun createAdapter() = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.fragment_message_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<LinearLayout>(R.id.mLlMessageItemRight).setOnClickListener {
                notifyItemRemoved(helper.adapterPosition)
                mArrayList.remove(item)
            }
            helper.getView<ConstraintLayout>(R.id.mLlMessageItemContent).setOnClickListener {
                FragmentContainerActivity.from(mContext).setTitle("消息详情").setClazz(MineMessageInfoFragment::class.java).start()
            }
        }
    }

    override fun initView(view: View?) {
        (mTlMainMessage.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        super.initView(view)
    }

    override fun getLayoutId() = R.layout.fragment_message_layout

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

}