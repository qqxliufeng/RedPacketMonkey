package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_main_game_layout.*

class GameFragment :BaseRecyclerViewFragment<String>(){


    override fun getLayoutId() = R.layout.fragment_main_game_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_game_house_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        (mTlMainGame.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mBaseAdapter.addHeaderView(View.inflate(mContext,R.layout.layout_game_top_layout,null))
        setLoadEnable(false)
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val decoration = super.getItemDecoration() as DividerItemDecoration
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        return decoration
    }


}