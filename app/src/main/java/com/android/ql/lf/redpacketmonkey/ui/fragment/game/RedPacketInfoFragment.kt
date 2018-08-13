package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.RedPacketInfoBean
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.packet.RedPacketRecordFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.formatTime
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_red_packet_info_layout.*
import org.jetbrains.anko.support.v4.toast

class RedPacketInfoFragment : BaseRecyclerViewFragment<RedPacketInfoBean>() {


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<RedPacketInfoBean, BaseViewHolder> = object : BaseQuickAdapter<RedPacketInfoBean, BaseViewHolder>(R.layout.adapter_red_packet_info_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: RedPacketInfoBean?) {
            GlideManager.loadFaceCircleImage(mContext, item!!.red_re_pic, helper!!.getView(R.id.mIvRedPacketInfoItemFace))
            helper.setText(R.id.mTvRedPacketInfoItemNickName, item.red_re_nickname)
            helper.setText(R.id.mTvRedPacketInfoItemTime, item.red_times?.toString()?.formatTime())
            helper.setText(R.id.mTvRedPacketInfoItemMoney, item.red_sum.toString())
            helper.setVisible(R.id.mTvRedPacketInfoItemLuck, item.red_luck == 1)
        }
    }

    override fun getLayoutId() = R.layout.fragment_red_packet_info_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.parseColor("#da5840"))
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mContext as FragmentContainerActivity).toolbar.setTitleTextColor(Color.WHITE)
        (mContext as FragmentContainerActivity).toolbar.navigationIcon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        super.initView(view)
        GlideManager.loadFaceCircleImage(mContext, arguments!!.getString("pic"), mIvRedPacketInfoFace)
        mTvRedPacketInfoNickName.text = arguments!!.getString("nick_name")
        mTvRedPacketInfoMoneyAndMine.text = arguments!!.getString("money")
        setLoadEnable(false)
        setRefreshEnable(false)
    }


    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRedPacketListsParam(arguments!!.getString("red_id")))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, RedPacketInfoBean::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.red_packet_info, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val textView = menu!!.getItem(0).actionView as TextView
        textView.text = "红包记录"
        textView.setTextColor(Color.WHITE)
        textView.setPadding(0, 0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f, resources.displayMetrics).toInt(), 0)
        textView.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("红包记录").setNeedNetWorking(false).setClazz(RedPacketRecordFragment::class.java).start()
        }
    }
}