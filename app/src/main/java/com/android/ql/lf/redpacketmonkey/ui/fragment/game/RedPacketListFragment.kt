package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
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
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.MineFragment
import com.android.ql.lf.redpacketmonkey.utils.PreferenceUtils
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.RxBus
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_red_packet_list_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.collections.forEachReversedByIndex
import org.jetbrains.anko.collections.forEachReversedWithIndex
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class RedPacketListFragment : BaseRecyclerViewFragment<RedPacketEntity>() {

    companion object {
        //两个红包之间的间隔时间
        const val INTERVAL_TIME = 60 * 2
    }

    /**
     * 显示打开红包的对话框
     */
    private val openRedPacketDialogFragment by lazy {
        OpenRedPacketDialogFragment()
    }

    /**
     * 显示没有红包的对话框
     */
    private val noRedPacketDialogFragment by lazy {
        NoRedPacketDialogFragment()
    }

    /**
     * 显示余额不足的对话框
     */
    private val noMoneyDialogFragment by lazy {
        NoMoneyDialogFragment()
    }

    /**
     * 显示我的余额的对话框
     */
    private val myAccountDialogFragment by lazy {
        MyAccountDialogFragment()
    }

    /**
     * 群组信息
     */
    private val groupInfo by lazy {
        arguments!!.getSerializable(SendRedPacketFragment.GROUP_INFO_FLAG) as GroupBean
    }

    private val userPresent by lazy {
        UserPresent()
    }

    /**
     * 退出群组事件
     */
    private val logoutGroupSubscription by lazy {
        RxBus.getDefault().toObservable(GroupSettingFragment.GroupLogoutBean::class.java).subscribe {
            finish()
        }
    }


    /**
     * 媒体播放组件
     */
    private val mediaPlayer by lazy {
        MediaPlayer.create(mContext, R.raw.diaoluo_da)
    }

    private var currentRedPacketEntity: RedPacketEntity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<RedPacketEntity, BaseViewHolder> = RedPacketAdapter(mArrayList)

    override fun onRefresh() {
        //第一次取得所有记录
        currentPage = 0
        mArrayList.clear()
        val tempList = MyApplication.getRedPacketDao().queryByLimit(groupInfo.group_id!!, currentPage, 10)
        if (tempList != null && !tempList.isEmpty()) {
            tempList.reverse()
            sortTime(tempList as ArrayList<RedPacketEntity>)
            mBaseAdapter.addData(tempList)
            mRecyclerView.scrollToPosition(mArrayList.size - 1)
        }
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_list_layout

    override fun initView(view: View?) {
        isNeedLoad = false
        super.initView(view)
        logoutGroupSubscription
        setLoadEnable(false)
        onRequestEnd(-1)
        setRefreshEnable(false)

        //监听插入、删除所有的记录
        MyApplication.getRedPacketDao().queryLastOne(groupInfo.group_id!!).observe(this, Observer<RedPacketEntity> {
            when (RedPacketManager.current_mode) {
                RedPacketManager.ActionMode.INSERT -> { //当前为插入模式
                    if (it != null) {
                        if (mArrayList.isEmpty()) { // 如果集合是空的 或者 集合中最后一条数据的id 不等于 it 的id 要插入
                            it.isShowTime = true
                            mBaseAdapter.addData(it)
                            mRecyclerView.scrollToPosition(mArrayList.lastIndex)
                        } else if (mArrayList[mArrayList.lastIndex].id != it.id) {
                            it.isShowTime = it.group_red_times.toLong() - mArrayList[mArrayList.lastIndex].group_red_times.toLong() > INTERVAL_TIME
                            mBaseAdapter.addData(it)
                            mRecyclerView.scrollToPosition(mArrayList.lastIndex)
                        }
                    }
                }
                RedPacketManager.ActionMode.DELETE -> { //当前为删除模式
                    mArrayList.clear()
                    mBaseAdapter.notifyDataSetChanged()
                }
                RedPacketManager.ActionMode.UPDATE -> { //当前为更新模式
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
            currentPage++
            mBaseAdapter.isUpFetching = true
            val tempList = RedPacketManager.queryByLimit(groupInfo.group_id!!, currentPage, 10)
            if (tempList != null && !tempList.isEmpty()) {
                tempList.reverse()
                sortTime(tempList as ArrayList<RedPacketEntity>)
                mRecyclerView.postDelayed({
                    mBaseAdapter.addData(0, tempList)
                }, 1000)
            }
            mBaseAdapter.isUpFetching = false
        }
        mIvRedPacketAdd.setOnClickListener {
            if (mLlRedPacketPayTypeContainer.visibility == View.GONE) {
                mLlRedPacketPayTypeContainer.visibility = View.VISIBLE
                mRecyclerView.scrollToPosition(mArrayList.lastIndex)
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

    /**
     * 打开红包，做一定的判断
     */
    private fun openRedPacket() {
        if (currentRedPacketEntity != null) {
            if (groupInfo.group_as == UserInfo.getInstance().user_as) { //判断当前用户是不是群主，若是，则直接看手气
                noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "您已经抢过该红包了") {
                    openRedPacketInfo()
                }
                return
            }
            if (currentRedPacketEntity!!.groud_red_is_get == 0) { //判断自己是否已经抢过了 0 没有 1 抢过了
                if (currentRedPacketEntity!!.group_red_quit_times.toLong() * 1000 > System.currentTimeMillis()) { //判断红包是否已经过期了
                    mPresent.getDataByPost(0x0, RequestParamsHelper.getRedPacketClickParam(currentRedPacketEntity!!.group_red_id.toString()))
                } else {
                    noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "该红包已经过期了~") {
                        openRedPacketInfo()
                    }
                    mBaseAdapter.notifyItemChanged(mArrayList.indexOf(currentRedPacketEntity))
                }
            } else {
                noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "您已经抢过该红包了") {
                    openRedPacketInfo()
                }
            }
        }
    }

    /**
     * 根据间隔时间来排序
     */
    private fun sortTime(list: ArrayList<RedPacketEntity>?) {
        if (list != null) {
            if (list.size > 2) {
                list.forEachReversedWithIndex { i, redPacketEntity ->
                    if (i == 0) { //如果是最后一条数据，则直接显示时间
                        redPacketEntity.isShowTime = true
                        return
                    }
                    redPacketEntity.isShowTime = redPacketEntity.group_red_times.toLong() - list[i - 1].group_red_times.toLong() > INTERVAL_TIME
                }
            } else if (list.size == 1) { //如果只有 1 条 就直接显示时间
                list[0].isShowTime = true
            }
        }
    }

    private fun openRedPacketInfo() {
        FragmentContainerActivity.from(mContext).setExtraBundle(bundleOf(
                Pair("red_id", currentRedPacketEntity!!.group_red_id.toString()),
                Pair("pic", currentRedPacketEntity!!.group_red_pic),
                Pair("nick_name", currentRedPacketEntity!!.group_red_name),
                Pair("money", currentRedPacketEntity!!.group_red_sum + "-" + currentRedPacketEntity!!.group_red_mine),
                Pair("count", currentRedPacketEntity!!.group_red_cou)
        )).setTitle("红包详情").setNeedNetWorking(true).setClazz(RedPacketInfoFragment::class.java).start()
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
                    when (checked.code) {
                        SUCCESS_CODE -> { //正常抢红包
                            openRedPacketDialogFragment.myShow(childFragmentManager, "open_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "${currentRedPacketEntity!!.group_red_sum} - ${currentRedPacketEntity!!.group_red_mine}") {
                                //检查余额是否够抢红包的
                                mPresent.getDataByPost(0x3, RequestParamsHelper.getPersonalParam(UserInfo.getInstance().user_id))
                            }
                        }
                        "301" -> { //你已经抢过红包了!
                            //标记已经抢过红包了
                            currentRedPacketEntity!!.groud_red_is_get = 1
                            RedPacketManager.updateRedPacket(currentRedPacketEntity)

                            noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "您已经抢过该红包了") {
                                openRedPacketInfo()
                            }
                        }
                        "302" -> {//你得手慢了,红包已经被强光了!
                            currentRedPacketEntity!!.group_red_recou = currentRedPacketEntity!!.group_red_cou
                            RedPacketManager.updateRedPacket(currentRedPacketEntity)

                            noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "手慢了，红包派完了") {
                                openRedPacketInfo()
                            }
                        }
                        "303" -> {//该红包已经过期了!
                            noRedPacketDialogFragment.myShow(childFragmentManager, "no_red_packet_dialog", currentRedPacketEntity!!.group_red_pic, currentRedPacketEntity!!.group_red_name, "该红包已经过期了~") {
                                openRedPacketInfo()
                            }
                        }
                    }
                }
            }
            0x1 -> { //获取红包钱
                val checked = checkResultCode(result)
                if (checked != null) {
                    when (checked.code) {
                        SUCCESS_CODE -> {
                            if (PreferenceUtils.getPrefBoolean(mContext, "red_packet_sound", UserInfo.getInstance().user_is_red == 1)) {
                                playSound()
                            }
                            openRedPacketInfo()
                            //标记已经抢过红包了
                            currentRedPacketEntity!!.groud_red_is_get = 1
                            RedPacketManager.updateRedPacket(currentRedPacketEntity)
                            RxBus.getDefault().post(MineFragment.ReloadUserInfoBean())
                        }
                        "300" -> {
                            toast((checked.obj as JSONObject).optString(MSG_FLAG))
                            openRedPacketInfo()
                            //标记已经抢过红包了
                            currentRedPacketEntity!!.groud_red_is_get = 1
                            RedPacketManager.updateRedPacket(currentRedPacketEntity)
                            RxBus.getDefault().post(MineFragment.ReloadUserInfoBean())
                        }
                        else -> toast((checked.obj as JSONObject).optString(MSG_FLAG))
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
            0x3 -> {
                val checked = checkResultCode(result)
                if (checked != null) {
                    if (checked.code == SUCCESS_CODE) {
                        userPresent.onLoginNoBus((checked.obj as JSONObject).optJSONObject("data"))
                        if (UserInfo.getInstance().money_sum_cou.toFloat() < currentRedPacketEntity!!.group_red_sum.toFloat() * 1.5) {
                            noMoneyDialogFragment.show(fragmentManager, "no_money_dialog")
                            openRedPacketDialogFragment.dismiss()
                        } else {
                            //如果余额够用，则打开红包
                            mPresent.getDataByPost(0x1, RequestParamsHelper.getRedBiddingParam(currentRedPacketEntity!!.group_red_id.toString()))
                        }
                    } else {
                        noMoneyDialogFragment.show(fragmentManager, "no_money_dialog")
                    }
                } else {
                    noMoneyDialogFragment.show(fragmentManager, "no_money_dialog")
                }
            }
        }
    }

    /**
     *播放红包音效
     */
    private fun playSound() {
        mediaPlayer.start()
    }

    override fun onDestroyView() {
        unsubscribe(logoutGroupSubscription)
        super.onDestroyView()
    }
}