package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.data.GroupBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.services.RedPacketServices
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.game.RedPacketListFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.game.SendRedPacketFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_main_game_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject
import java.util.*

class GameFragment : BaseRecyclerViewFragment<GroupBean>() {


    override fun getLayoutId() = R.layout.fragment_main_game_layout

    override fun createAdapter(): BaseQuickAdapter<GroupBean, BaseViewHolder> = object : BaseQuickAdapter<GroupBean, BaseViewHolder>(R.layout.adapter_game_house_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: GroupBean?) {
            helper?.setText(R.id.mTvGameItemTitle, item?.group_name)
            helper?.setText(R.id.mTvGameItemDes, item?.group_desc)
            GlideManager.loadImage(mContext, item?.group_pic, helper?.getView(R.id.mIvGameItemPic))
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        (mTlMainGame.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mBaseAdapter.addHeaderView(View.inflate(mContext, R.layout.layout_game_top_layout, null))
        setLoadEnable(false)
        mBaseAdapter.setHeaderFooterEmpty(true, false)
        if (UserInfo.getInstance().isLogin) {
            mPresent.getDataByPost(0x1, RequestParamsHelper.getLoginRedParam())
        }

        Log.e("TAG", Calendar.getInstance().get(Calendar.MILLISECOND).toString())
        Log.e("TAG", System.currentTimeMillis().toString())

    }

    override fun getEmptyMessage() = "暂无群组"

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getGroupListParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getGroupListParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        if (requestID == 0x0) {
            processList(result as String, GroupBean::class.java)
        } else if (requestID == 0x1) {
            val checked = checkResultCode(result)
            if (checked != null) {
                if (checked.code == SUCCESS_CODE) {
                    val jsonArray = (checked.obj as JSONObject).optJSONArray("data")
                    if (jsonArray != null && jsonArray.length() > 0) {
                        (0..jsonArray.length()).forEach {
                            val jsonData = jsonArray.optJSONObject(it)
                            val time = jsonData.optLong("group_red_quit_times") * 1000 - System.currentTimeMillis()
                            if (time > 0) {
                                MyApplication.application.handler.postDelayed({
                                    val intent = Intent(MyApplication.application, RedPacketServices::class.java)
                                    intent.putExtra("red_id", jsonData.optLong("group_red_id"))
                                    MyApplication.application.startService(intent)
                                }, jsonData.optLong("group_red_quit_times") * 1000 - System.currentTimeMillis())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val decoration = super.getItemDecoration() as DividerItemDecoration
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        return decoration
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle(mArrayList[position].group_name).setExtraBundle(bundleOf(Pair(SendRedPacketFragment.GROUP_INFO_FLAG, mArrayList[position]))).setClazz(RedPacketListFragment::class.java).start()
    }
}