package com.android.ql.lf.redpacketmonkey.ui.adapter

import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RedPacketAdapter(list: ArrayList<RedPacketEntity>) :BaseMultiItemQuickAdapter<RedPacketEntity,BaseViewHolder>(list){

    init {
        addItemType(RedPacketEntity.FROM_RED_PACKET, R.layout.adapter_red_packet_from_item_layout)
    }



    override fun convert(helper: BaseViewHolder?, item: RedPacketEntity?) {
    }

}