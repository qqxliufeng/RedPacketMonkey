package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.event.OfflineMessageEvent
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.data.GroupBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.android.ql.lf.redpacketmonkey.present.RedPacketManager
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.adapter.RedPacketAdapter
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_red_packet_list_layout.*
import org.jetbrains.anko.bundleOf

class RedPacketListFragment : BaseRecyclerViewFragment<RedPacketEntity>() {


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

    private val groupInfo by lazy {
        arguments!!.getSerializable(SendRedPacketFragment.GROUP_INFO_FLAG) as GroupBean
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<RedPacketEntity, BaseViewHolder> = RedPacketAdapter(mArrayList)

    override fun onRefresh() {
        //第一次取得所有记录
        MyApplication.getRedPacketDao().queryAll(groupInfo.group_id!!).let {
            mArrayList.clear()
            mBaseAdapter.addData(it)
            mBaseAdapter.notifyDataSetChanged()
            mRecyclerView.scrollToPosition(mArrayList.size - 1)
        }
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        onRequestEnd(-1)
        setRefreshEnable(false)

        //监听插入、删除所有的记录
        MyApplication.getRedPacketDao().queryLastOne(groupInfo.group_id!!).observe(this, Observer<RedPacketEntity> {
            if (it != null) {
                mBaseAdapter.addData(it)
                mRecyclerView.scrollToPosition(mArrayList.size - 1)
            } else {
                mArrayList.clear()
                mBaseAdapter.notifyDataSetChanged()
            }
        })
//        mBaseAdapter.isUpFetchEnable = true
//        mBaseAdapter.setStartUpFetchPosition(1)
//        mBaseAdapter.setUpFetchListener {
//            if (mBaseAdapter.isUpFetching && tempList!=null){
//                return@setUpFetchListener
//            }
//            mBaseAdapter.isUpFetching = true
//            mRecyclerView.postDelayed({
//                mBaseAdapter.addData(0,tempList!!)
//                mBaseAdapter.isUpFetching = false
//            }, 1000)
//        }
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
            FragmentContainerActivity.from(mContext).setExtraBundle(bundleOf(Pair(SendRedPacketFragment.GROUP_INFO_FLAG, groupInfo))).setClazz(SendRedPacketFragment::class.java).setTitle("发红包").setNeedNetWorking(true).start()
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
        FragmentContainerActivity.from(mContext).setClazz(GroupSettingFragment::class.java).setExtraBundle(bundleOf(Pair("groupInfo", groupInfo))).setTitle("群设置").setNeedNetWorking(true).start()
        return super.onOptionsItemSelected(item)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        when (view!!.id) {
            R.id.mRLRedPacketFromItemContainer -> {
                openRedPacket()
            }
            R.id.mRLRedPacketSendItemContainer -> {
            }
            else -> {
            }
        }
    }

    fun openRedPacket() {
        openRedPacketDialogFragment.show(childFragmentManager, "open_red_packet_dialog")
    }

}