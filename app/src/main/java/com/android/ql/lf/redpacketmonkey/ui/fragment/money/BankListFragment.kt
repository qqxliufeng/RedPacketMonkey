package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.content.Context
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.BankCardInfoBean
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.RxBus
import com.android.ql.lf.redpacketmonkey.utils.hiddenBankCarNum
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.bundleOf

class BankListFragment : BaseRecyclerViewFragment<BankCardInfoBean>() {

    companion object {
        fun startBankCardList(context: Context, isSelectMode: Boolean) {
            FragmentContainerActivity.from(context).setNeedNetWorking(true).setExtraBundle(bundleOf(Pair("is_select_mode", isSelectMode))).setTitle("银行卡").setClazz(BankListFragment::class.java).start()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter() = object : BaseQuickAdapter<BankCardInfoBean, BaseViewHolder>(R.layout.adapter_bank_list_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: BankCardInfoBean?) {
            helper?.setText(R.id.mTvBankListItemName, if (TextUtils.isEmpty(item?.card_name)) {
                "暂无所属银行"
            }else{
                item?.card_name
            })
            helper?.setText(R.id.mTvBankListItemCardNumber, item?.card_number?.hiddenBankCarNum())
        }
    }

    private val bankCardSubscription by lazy {
        RxBus.getDefault().toObservable(BankCardInfoBean::class.java).subscribe {
            onPostRefresh()
        }
    }


    override fun initView(view: View?) {
        super.initView(view)
        bankCardSubscription
    }

    override fun getEmptyMessage() = "暂无银行卡"

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getBankCarListParam())
    }

    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        setLoadEnable(false)
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


    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, BankCardInfoBean::class.java)
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (arguments!!.getBoolean("is_select_mode", false)) {
            RxBus.getDefault().post(mArrayList[position])
            finish()
        }
    }

    override fun onDestroyView() {
        unsubscribe(bankCardSubscription)
        super.onDestroyView()
    }


}