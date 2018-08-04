package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.RecordBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.json.JSONObject

class RedPacketRecordForReceivedFragment : BaseRecyclerViewFragment<RecordBean>() {

    override fun createAdapter() = object : BaseQuickAdapter<RecordBean, BaseViewHolder>(R.layout.adapter_red_packet_record_received_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: RecordBean?) {
            helper?.setText(R.id.mIvRedRecordReceivedItemNickName, item?.nickname)
            helper?.setText(R.id.mIvRedRecordReceivedItemTime, item?.times)
            helper?.setText(R.id.mIvRedRecordReceivedItemMoney, item?.sum)
            GlideManager.loadFaceCircleImage(mContext, item?.pic, helper?.getView<ImageView>(R.id.mIvRedRecordReceivedItemFace))
        }
    }

    private val topView by lazy {
        View.inflate(mContext, R.layout.layout_red_packet_record_received_top_layout, null)
    }

    private val iv_pic by lazy {
        topView.findViewById<ImageView>(R.id.mTvRedPacketRecordTopPic)
    }

    private val tv_money by lazy {
        topView.findViewById<TextView>(R.id.mTvRedPacketRecordTopMoney)
    }


    override fun initView(view: View?) {
        super.initView(view)
        GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, iv_pic)
        topView.findViewById<TextView>(R.id.mTvRecPacketRecordTopTitle).text = "共收到"
        mBaseAdapter.addHeaderView(topView)
    }

    override fun getEmptyMessage() = "暂无收到的红包"

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRecordParam(5, currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRecordParam(5, currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        processList(result as String, RecordBean::class.java)
        if (currentPage == 0 && !TextUtils.isEmpty(result)) {
            val resultJson = JSONObject(result)
            tv_money.text = resultJson.optDouble("sumcou").toString()
        }
    }
}