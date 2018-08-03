package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.content.Context
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.RecordBean
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.bundleOf

class BalanceListFragment : BaseRecyclerViewFragment<RecordBean>() {

    companion object {
        fun startRecord(context: Context, title: String, type: Int) {
            FragmentContainerActivity.from(context).setClazz(BalanceListFragment::class.java).setNeedNetWorking(true).setExtraBundle(bundleOf(Pair("type", type))).setTitle(title).start()
        }
    }

    override fun createAdapter() = object : BaseQuickAdapter<RecordBean, BaseViewHolder>(R.layout.fragment_packet_info_list_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: RecordBean?) {
            helper?.setText(R.id.mTvRecordItemEventName,item?.name)
            helper?.setText(R.id.mTvPacketInfoListItemMoney,item?.fu+item?.sum)
            helper?.setText(R.id.mTvPacketInfoListItemTime,item?.times)
        }
    }

    override fun getLayoutId() = R.layout.fragment_balance_list_layout

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRecordParam(arguments!!.getInt("type"),currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRecordParam(arguments!!.getInt("type"),currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        processList(result as String,RecordBean::class.java)
    }

}