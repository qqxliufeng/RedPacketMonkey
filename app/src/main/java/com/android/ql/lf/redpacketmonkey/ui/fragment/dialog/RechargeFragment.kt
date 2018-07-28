package com.android.ql.lf.redpacketmonkey.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.ImageView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import com.android.ql.lf.redpacketmonkey.utils.isMoney
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.support.v4.toast

class RechargeFragment : DialogFragment() {


    private var listener: ((String) -> Unit)? = null

    private var selectedItem: String? = null

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
        val et_content = contentView.findViewById<EditText>(R.id.mEtRechargeDialogContent)
        mArrayList.forEach {
            if (it.isSelect){
                et_content.setText(it.name)
                et_content.setSelection(it.name.lastIndex + 1)
                return@forEach
            }
        }
        mRvRechargeDialog.layoutManager = GridLayoutManager(context, 3)
        mRvRechargeDialog.adapter = object : BaseQuickAdapter<RechargeDataBean, BaseViewHolder>(R.layout.adapter_recharge_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: RechargeDataBean?) {
                helper!!.setText(R.id.mCtvRechargeDialogItem, item?.name)
                helper.setChecked(R.id.mCtvRechargeDialogItem, item!!.isSelect)
                helper.getView<CheckedTextView>(R.id.mCtvRechargeDialogItem).setOnClickListener {
                    et_content.setText(item.name)
                    et_content.setSelection(item.name.lastIndex + 1)
                    item.isSelect = true
                    mArrayList.forEach { it.isSelect = it == item }
                    notifyDataSetChanged()
                }
            }
        }
        contentView.findViewById<ImageView>(R.id.mIvRechargeDialogClose).setOnClickListener {
            dismiss()
        }
        contentView.findViewById<Button>(R.id.mBtRechargeDialogSubmit).setOnClickListener {
            if (!et_content.isEmpty()) {
                if (!et_content.isMoney()) {
                    toast("请输入合法的金额")
                    return@setOnClickListener
                }
                selectedItem = et_content.text.toString()
            } else {
                toast("请输入金额")
                return@setOnClickListener
            }
            this.listener?.invoke(selectedItem!!.trim())
            dismiss()
        }
        return contentView
    }


    fun myShow(manager: FragmentManager?, tag: String?, listener: ((String) -> Unit)?) {
        this.listener = listener
        super.show(manager, tag)
    }

    class RechargeDataBean(var name: String = "", var isSelect: Boolean = false)
}