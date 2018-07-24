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

class RechargeFragment : DialogFragment() {

    private val mArrayList by lazy {
        val tempList = arrayListOf<RechargeDataBean>()
        tempList.add(RechargeDataBean("100", true))
        tempList.add(RechargeDataBean("300", false))
        tempList.add(RechargeDataBean("500", false))
        tempList.add(RechargeDataBean("1000", false))
        tempList.add(RechargeDataBean("2000", false))
        tempList.add(RechargeDataBean("3000", false))
        tempList
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        val contentView = inflater.inflate(R.layout.dialog_recharge_layout, container, false)
        val mRvRechargeDialog = contentView.findViewById<RecyclerView>(R.id.mRvRechargeDialog)
        mRvRechargeDialog.layoutManager = GridLayoutManager(context, 3)
        mRvRechargeDialog.adapter = object : BaseQuickAdapter<RechargeDataBean, BaseViewHolder>(R.layout.adapter_recharge_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: RechargeDataBean?) {
                helper!!.setText(R.id.mCtvRechargeDialogItem, item?.name)
                helper.setChecked(R.id.mCtvRechargeDialogItem, item!!.isSelect)
            }
        }
        contentView.findViewById<ImageView>(R.id.mIvRechargeDialogClose).setOnClickListener {
            dismiss()
        }
        return contentView
    }

    class RechargeDataBean(var name: String = "", var isSelect: Boolean = false)
}