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
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.data.GroupBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.android.ql.lf.redpacketmonkey.present.RedPacketManager
import com.android.ql.lf.redpacketmonkey.present.UserPresent
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.adapter.RedPacketAdapter
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_red_packet_list_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

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

    private val userPresent by lazy {
        UserPresent()
    }

    private var currentRedPacketEntity: RedPacketEntity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<RedPacketEntity, BaseViewHolder> = RedPacketAdapter(mArrayList)

    override fun onRefresh() {
        //第一次取得所有记录
        val tempList = MyApplication.getRedPacketDao().queryByLimit(groupInfo.group_id!!, currentPage, 10)
        if (tempList != null && !tempList.isEmpty()) {
            tempList.reverse()
            mBaseAdapter.addData(tempList)
            mRecyclerView.scrollToPosition(mArrayList.size - 1)
        }
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_list_layout

    override fun initView(view: View?) {
        isNeedLoad = false
        super.initView(view)
        setLoadEnable(false)
        onRequestEnd(-1)
        setRefreshEnable(false)

        //监听插入、删除所有的记录
        MyApplication.getRedPacketDao().queryLastOne(groupInfo.group_id!!).observe(this, Observer<RedPacketEntity> {
            when (RedPacketManager.current_mode) {
                RedPacketManager.ActionMode.INSERT -> {
                    if (it != null) {
                        if (mArrayList.isEmpty() || mArrayList[mArrayList.size - 1].id != it.id){ // 如果集合是空的 或者 集合中最后一条数据的id 不等于 it 的id 要插入
                            mBaseAdapter.addData(it)
                            mRecyclerView.scrollToPosition(mArrayList.size - 1)
                        }
                    }
                }
                RedPacketManager.ActionMode.DELETE -> {
                    mArrayList.clear()
                    mBaseAdapter.notifyDataSetChanged()
                }
                RedPacketManager.ActionMode.UPDATE -> {
                    if (currentRedPacketEntity != null) {
                        mBaseAdapter.notifyItemChanged(mArrayList.indexOf(currentRedPacketEntity))
                    }
                }
                else -> {
                }
            }
            RedPacketManager.current_mode = RedPacketManager.ActionMode.NONE
        })


        mBaseAdapter.isUpFetchEnable = true
        mBaseAdapter.setStartUpFetchPosition(0)
        mBaseAdapter.setUpFetchListener {
            if (mBaseAdapter.isUpFetching) {
                return@setUpFetchListener
            }
            Log.e("TAG","currentPage before --->  $currentPage")
            currentPage++
            Log.e("TAG","currentPage after --->  $currentPage")
            mBaseAdapter.isUpFetching = true
            val tempList = RedPacketManager.queryByLimit(groupInfo.group_id!!, currentPage, 10)
            if (tempList != null && !tempList.isEmpty()) {
                tempList.reverse()
                mRecyclerView.postDelayed({
                    mBaseAdapter.addData(0, tempList)
                }, 1000)
            }
            mBaseAdapter.isUpFetching = false
        }
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
            mPresent.getDataByPost(0x2, RequestParamsHelper.getPersonalParam(UserInfo.getInstance().user_id))
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
        currentRedPacketEntity = mArrayList[position]
        when (view!!.id) {
            R.id.mRLRedPacketFromItemContainer -> {
                openRedPacket()
            }
            R.id.mRLRedPacketSendItemContainer -> {
                openRedPacket()
            }
            else -> {
            }
        }
    }

    fun openRedPacket() {
        if (currentRedPacketEntity != null) {
            mPresent.getDataByPost(0x0, RequestParamsHelper.getRedPacketClickParam(currentRedPacketEntity!!.group_red_id.toString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        when (requestID) {
            0x0, 0x2 -> {
                getFastProgressDialog("正在加载……")
            }
        }
    }

    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        if (requestID == 0x1) {
            openRedPacketDialogFragment.dismiss()
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        if (requestID == 0x2) {
            toast("加载失败……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        when (requestID) {
            0x0 -> { //打开红包
                val checked = checkResultCode(result)
                if (checked != null) {
                    if (checked.code == SUCCESS_CODE) {
                        openRedPacketDialogFragment.myShow(childFragmentManager, "open_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "${currentRedPacketEntity!!.group_red_sum} - ${currentRedPacketEntity!!.group_red_mine}") {
                            mPresent.getDataByPost(0x1, RequestParamsHelper.getRedBiddingParam(currentRedPacketEntity!!.group_red_id.toString()))
                        }
                    } else if (checked.code == "300") {// 没有红包了
                        currentRedPacketEntity!!.group_red_cou = "0"
                        RedPacketManager.updateRedPacket(currentRedPacketEntity!!)
                        noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name) {
                            FragmentContainerActivity.from(mContext).setExtraBundle(bundleOf(
                                    Pair("red_id", currentRedPacketEntity!!.group_red_id.toString()),
                                    Pair("pic", currentRedPacketEntity!!.group_red_pic),
                                    Pair("nick_name", currentRedPacketEntity!!.group_red_name)
                            )).setTitle("红包详情").setNeedNetWorking(true).setClazz(RedPacketInfoFragment::class.java).start()
                        }
                    }
                }
            }
            0x1 -> { //获取红包钱
                val checked = checkResultCode(result)
                if (checked != null) {
                    if (checked.code == SUCCESS_CODE) {
                        FragmentContainerActivity.from(mContext).setExtraBundle(bundleOf(
                                Pair("red_id", currentRedPacketEntity!!.group_red_id.toString()),
                                Pair("pic", currentRedPacketEntity!!.group_red_pic),
                                Pair("nick_name", currentRedPacketEntity!!.group_red_name)
                        )).setTitle("红包详情").setNeedNetWorking(true).setClazz(RedPacketInfoFragment::class.java).start()
                    } else {
                        toast((checked.obj as JSONObject).optString(MSG_FLAG))
                    }
                }
            }
            0x2 -> { //加载余额
                val checked = checkResultCode(result)
                if (checked != null) {
                    if (checked.code == SUCCESS_CODE) {
                        userPresent.onLoginNoBus((checked.obj as JSONObject).optJSONObject("data"))
                        myAccountDialogFragment.myShow(childFragmentManager, "my_account_dialog", UserInfo.getInstance().money_sum_cou)
                    } else {
                        toast((checked.obj as JSONObject).optString(MSG_FLAG))
                    }
                } else {
                    toast("加载失败……")
                }
            }
        }
    }
}