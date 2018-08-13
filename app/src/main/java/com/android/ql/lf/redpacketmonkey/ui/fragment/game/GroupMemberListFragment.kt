package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class GroupMemberListFragment : BaseRecyclerViewFragment<GroupMemberListFragment.GroupMemberBean>() {


    override fun createAdapter(): BaseQuickAdapter<GroupMemberBean, BaseViewHolder> = object : BaseQuickAdapter<GroupMemberBean, BaseViewHolder>(R.layout.adapter_group_member_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: GroupMemberBean?) {
            GlideManager.loadFaceCircleImage(mContext, item!!.group_user_pic, helper!!.getView(R.id.mIvGroupSettingMemberItemPic))
            helper.setText(R.id.mTvGroupSettingMemberItemNickName, item.group_user_name)
        }
    }

    override fun getLayoutId() = R.layout.fragment_group_member_list_layout

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        mManager = GridLayoutManager(mContext, 5)
        return mManager
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val decoration = super.getItemDecoration() as DividerItemDecoration
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        return decoration
    }

    override fun initView(view: View?) {
        super.initView(view)
        setLoadEnable(false)
        setRefreshEnable(false)
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getGroupUserList(arguments!!.getLong("groupId").toString()))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, GroupMemberBean::class.java)
    }

    override fun getEmptyMessage() = "暂无成员列表"


    class GroupMemberBean {
        var group_user_id: String? = null
        var group_user_group: String? = null
        var group_user_name: String? = null
        var group_user_pic: String? = null
    }
}