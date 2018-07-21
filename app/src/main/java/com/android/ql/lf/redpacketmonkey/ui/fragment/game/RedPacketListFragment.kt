package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_red_packet_list_layout.*

class RedPacketListFragment : BaseRecyclerViewFragment<String>() {


    private val openRedPacketDialogFragment by lazy {
        OpenRedPacketDialogFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_red_packet_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.addOnClickListener(R.id.mRLRedPacketItemContainer)
        }
    }


    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }


    override fun getLayoutId() = R.layout.fragment_red_packet_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        setLoadEnable(false)
        mIvRedPacketAdd.setOnClickListener {
            if (mLlRedPacketPayTypeContainer.visibility == View.GONE) {
                mLlRedPacketPayTypeContainer.visibility = View.VISIBLE
                mRecyclerView.scrollToPosition(mArrayList.size-1)
            }
            else {
                mLlRedPacketPayTypeContainer.visibility = View.GONE
            }
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val decoration = super.getItemDecoration() as DividerItemDecoration
        decoration.setDrawable(ColorDrawable(Color.TRANSPARENT))
        return decoration
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.red_packet, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        FragmentContainerActivity.from(mContext).setClazz(GroupMemberListFragment::class.java).setTitle("成员列表").setNeedNetWorking(true).start()
        return super.onOptionsItemSelected(item)
    }


    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        if (view!!.id == R.id.mRLRedPacketItemContainer){
            openRedPacketDialogFragment.show(childFragmentManager,"open_red_packet_dialog")
        }
    }

}