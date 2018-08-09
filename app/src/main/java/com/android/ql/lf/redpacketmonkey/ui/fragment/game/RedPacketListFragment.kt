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
        //加载数据
        mArrayList.clear()
        MyApplication.getRedPacketDao().queryAll(groupInfo.group_id!!).let {
            mArrayList.addAll(it)
            mBaseAdapter.notifyDataSetChanged()
            mRecyclerView.scrollToPosition(mArrayList.size - 1)
            onRequestEnd(-1)
        }
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        //监听插入的一条记录
        MyApplication.getRedPacketDao().queryLastOne(groupInfo.group_id!!).observe(this, Observer<RedPacketEntity> {
            if (it != null) {
                mArrayList.add(it)
                mBaseAdapter.notifyItemInserted(mArrayList.indexOf(it))
                mRecyclerView.scrollToPosition(mArrayList.size - 1)
            }
        })

//        //监听删除所有记录
//        MyApplication.getRedPacketDao().deleteAll(groupInfo.group_id!!).observe(this, Observer {
//            mArrayList.clear()
//            mBaseAdapter.notifyDataSetChanged()
//        })

        JMessageClient.registerEventReceiver(this)
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
        FragmentContainerActivity.from(mContext).setClazz(GroupSettingFragment::class.java).setExtraBundle(bundleOf(Pair("groupInfo",groupInfo))).setTitle("群设置").setNeedNetWorking(true).start()
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


    fun onEvent(event: OfflineMessageEvent) {
    }


    /**
     * 接收消息
     */
    fun onEvent(event: MessageEvent) {
        val receiverJson = event.message.content.toJsonElement().asJsonObject.get("text").asString
        RedPacketManager.insertRedPacket(receiverJson)
    }

    override fun onDestroyView() {
        JMessageClient.unRegisterEventReceiver(this)
        super.onDestroyView()
    }


}