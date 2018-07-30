package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class BankListFragment : BaseRecyclerViewFragment<String>() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter() = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_bank_list_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.bank_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.mMenuBankAdd) {
            FragmentContainerActivity.from(mContext).setClazz(AddBankCarFragment::class.java).setTitle("添加银行卡").setNeedNetWorking(true).start()
        }
        return super.onOptionsItemSelected(item)
    }
}