package com.android.ql.lf.redpacketmonkey.ui.adapter

import android.text.TextUtils
import android.widget.RelativeLayout
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RedPacketAdapter(list: ArrayList<RedPacketEntity>) : BaseMultiItemQuickAdapter<RedPacketEntity, BaseViewHolder>(list) {

    init {
        addItemType(RedPacketEntity.SEND_RED_PACKET, R.layout.adapter_red_packet_send_item_layout)
        addItemType(RedPacketEntity.FROM_RED_PACKET, R.layout.adapter_red_packet_from_item_layout)
    }


    override fun convert(helper: BaseViewHolder?, item: RedPacketEntity?) {
        when (item!!.itemType) {
            RedPacketEntity.SEND_RED_PACKET -> {
                GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, helper!!.getView(R.id.mIvRedPacketSendItemFace))
                helper.setText(R.id.mTvRedPacketSendItemName, UserInfo.getInstance().user_nickname)
                helper.setText(R.id.mTvRedPacketSendItemTitle, "${item.group_red_sum} - ${item.group_red_mine}")
                val rl_container = helper.getView<RelativeLayout>(R.id.mRLRedPacketSendItemContainer)
                if (item.group_red_cou == "0"){
                    rl_container.setBackgroundResource(R.drawable.img_red_packet_bg_4)
                }else{
                    rl_container.setBackgroundResource(R.drawable.img_red_packet_bg_3)
                }
                helper.addOnClickListener(R.id.mRLRedPacketSendItemContainer)
            }
            RedPacketEntity.FROM_RED_PACKET -> {
                GlideManager.loadFaceCircleImage(mContext, item.group_red_pic, helper!!.getView(R.id.mIvRedPacketFromItemFace))
                helper.setText(R.id.mTvRedPacketFromItemName, if (TextUtils.isEmpty(item.group_red_name)) {
                    "顽皮猴"
                } else {
                    item.group_red_name
                })
                helper.setText(R.id.mTvRedPacketFromItemTitle, "${item.group_red_sum} - ${item.group_red_mine}")
                val rl_container = helper.getView<RelativeLayout>(R.id.mRLRedPacketFromItemContainer)
                if (item.group_red_cou == "0"){
                    rl_container.setBackgroundResource(R.drawable.img_red_packet_bg_2)
                }else{
                    rl_container.setBackgroundResource(R.drawable.img_red_packet_bg_1)
                }
                helper.addOnClickListener(R.id.mRLRedPacketFromItemContainer)
            }
        }
    }
}