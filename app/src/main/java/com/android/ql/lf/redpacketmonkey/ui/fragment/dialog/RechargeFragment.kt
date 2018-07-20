package com.android.ql.lf.redpacketmonkey.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.ql.lf.redpacketmonkey.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RechargeFragment :DialogFragment(){

    private val mArrayList by lazy {
        val tempList = arrayListOf<String>()
        tempList.add("100")
        tempList.add("300")
        tempList.add("500")
        tempList.add("1000")
        tempList.add("2000")
        tempList.add("3000")
        tempList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.dialog_recharge_layout,container,false)
        val mRvRechargeDialog = contentView.findViewById<RecyclerView>(R.id.mRvRechargeDialog)
        mRvRechargeDialog.layoutManager = GridLayoutManager(context,3)
        mRvRechargeDialog.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_recharge_item_layout,mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper!!.setText(R.id.mCtvRechargeDialogItem,item)
            }
        }
        contentView.findViewById<ImageView>(R.id.mIvRechargeDialogClose).setOnClickListener {
            dismiss()
        }
        return contentView
    }


}