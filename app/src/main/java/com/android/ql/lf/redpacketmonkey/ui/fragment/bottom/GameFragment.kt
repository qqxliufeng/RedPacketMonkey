package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.GetGroupIDListCallback
import cn.jpush.im.android.api.callback.GetGroupInfoCallback
import cn.jpush.im.android.api.model.GroupInfo
import cn.jpush.im.api.BasicCallback
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.GroupBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.game.RedPacketListFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_main_game_layout.*

class GameFragment :BaseRecyclerViewFragment<GroupBean>(){


    override fun getLayoutId() = R.layout.fragment_main_game_layout

    override fun createAdapter(): BaseQuickAdapter<GroupBean, BaseViewHolder> = object : BaseQuickAdapter<GroupBean,BaseViewHolder>(R.layout.adapter_game_house_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: GroupBean?) {
            helper?.setText(R.id.mTvGameItemTitle,item?.group_name)
            helper?.setText(R.id.mTvGameItemDes,item?.group_desc)
            GlideManager.loadImage(mContext,item?.group_pic,helper?.getView(R.id.mIvGameItemPic))
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
        mPresent.getDataByPost(0x0,RequestParamsHelper.getGroupListParam(currentPage))
    }


    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0,RequestParamsHelper.getGroupListParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        processList(result as String,GroupBean::class.java)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val decoration = super.getItemDecoration() as DividerItemDecoration
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        return decoration
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
//        FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("发红包").setClazz(RedPacketListFragment::class.java).start()

        val message = JMessageClient.createGroupTextMessage(mArrayList[position].group_gid!!,"test group")
        message.setOnSendCompleteCallback(object : BasicCallback() {
            override fun gotResult(p0: Int, p1: String?) {
                Log.e("TAG","p1----> $p1")
            }
        })
        JMessageClient.sendMessage(message)


//        JMessageClient.getGroupInfo(mArrayList[position].group_gid!!, object : GetGroupInfoCallback() {
//            override fun gotResult(p0: Int, p1: String?, p2: GroupInfo?) {
//                Log.e("TAG","info ---->  ${p2?.toString()}")
//                p2?.addGroupKeeper(p2.groupMembers, object : BasicCallback() {
//                    override fun gotResult(p0: Int, p1: String?) {
//                        Log.e("TAG","p1----> $p1")
//                    }
//                })
//            }
//        })
    }
}