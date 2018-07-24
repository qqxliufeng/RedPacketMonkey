package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.utils.getTextString
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import org.jetbrains.anko.support.v4.toast

class AliPayNameDialogFragment : DialogFragment() {

    private var title: String = "修改真实姓名"
    private var hint: String = "姓名"

    private var listener: ((String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.dialog_ali_pay_name_layout, container, false)
        contentView.findViewById<TextView>(R.id.mTvAliPayCancel).setOnClickListener { dismiss() }
        val mTvTitle = contentView.findViewById<TextView>(R.id.mTvAliPayTitle)
        mTvTitle.text = title
        val mEtContent = contentView.findViewById<EditText>(R.id.mEtAliPayName)
        mEtContent.hint = "编辑$hint"
        contentView.findViewById<TextView>(R.id.mTvAliPaySubmit).setOnClickListener {
            if (mEtContent.isEmpty()) {
                toast("请输入$hint")
                return@setOnClickListener
            }
            listener?.invoke(mEtContent.getTextString())
            dismiss()
        }
        return contentView
    }


    fun myShow(manager: FragmentManager?, tag: String?, title: String = "修改真实姓名", hint: String = "姓名", listener: (String) -> Unit) {
        this.title = title
        this.hint = hint
        this.listener = listener
        super.show(manager, tag)
    }

}