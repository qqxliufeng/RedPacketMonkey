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

    private val noRedPacketDialogFragment by lazy {
        NoRedPacketDialogFragment()
    }

    private val noMoneyDialogFragment by lazy {
        NoMoneyDialogFragment()
    }

    private val myAccountDialogFragment by lazy {
        MyAccountDialogFragment()
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
        mRecyclerView.scrollToPosition(mArrayList.size - 1)
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        setLoadEnable(false)
        mIvRedPacketAdd.setOnClickListener {
            if (mLlRedPacketPayTypeContainer.visibility == View.GONE) {
                mLlRedPacketPayTypeContainer.visibility = View.VISIBLE
                mRecyclerView.scrollToPosition(mArrayList.size - 1)
            } else {
                mLlRedPacketPayTypeContainer.visibility = View.GONE
            }
        }
        mLlRedPacketListSendByRed.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(SendRedPacketFragment::class.java).setTitle("发红包").setNeedNetWorking(true).start()
            mLlRedPacketPayTypeContainer.visibility = View.GONE
        }
        mLlRedPacketListSendByAccount.setOnClickListener {
            myAccountDialogFragment.myShow(childFragmentManager, "my_account_dialog", "0.0")
            mLlRedPacketPayTypeContainer.visibility = View.GONE
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
        FragmentContainerActivity.from(mContext).setClazz(GroupSettingFragment::class.java).setTitle("群设置").setNeedNetWorking(true).start()
        return super.onOptionsItemSelected(item)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        if (view!!.id == R.id.mRLRedPacketItemContainer) {
            if (position == 0) {
                openRedPacketDialogFragment.show(childFragmentManager, "open_red_packet_dialog")
            } else if (position == 1) {
                noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog") {
                    FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("红包详情").setClazz(RedPacketInfoFragment::class.java).start()
                }
            } else {
                noMoneyDialogFragment.show(childFragmentManager, "no_money_dialog")
            }
        }
    }

}