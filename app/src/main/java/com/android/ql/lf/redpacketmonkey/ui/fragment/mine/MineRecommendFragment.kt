package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.content.Context
import android.graphics.Color
import android.view.*
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.RecordBean
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_mine_recommend_layout.*
import org.json.JSONObject

class MineRecommendFragment : BaseRecyclerViewFragment<RecordBean>() {


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter() = object : BaseQuickAdapter<RecordBean, BaseViewHolder>(R.layout.fragment_packet_info_list_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: RecordBean?) {
            helper!!.setText(R.id.mTvRecordItemEventName,item!!.name)
            helper.setText(R.id.mTvPacketInfoListItemMoney,"${item.fu}${item.sum}")
            helper.setText(R.id.mTvPacketInfoListItemTime,item.times)
        }
    }

    override fun getLayoutId() = R.layout.fragment_mine_recommend_layout


    override fun initView(view: View?) {
        super.initView(view)
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlMineRecommend.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        (mContext as FragmentContainerActivity).setSupportActionBar(mTlMineRecommend)
        (mContext as FragmentContainerActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTlMineRecommend.setNavigationOnClickListener { finish() }
        mAlMineRecommend.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mRlMineRecommendShouYiContainer.alpha = 1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRecordParam(6, currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRecordParam(6, currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        processList(result as String, RecordBean::class.java)
        if (currentPage == 0) {
            val json = JSONObject(result)
            mTvMineRecommendMoney.text = json.optDouble("sumcou").toString()
            mTvMineRecommendJin.text = json.optDouble("jincou").toString()
            mTvMineRecommendZuo.text = json.optDouble("zuocou").toString()
        }
    }

    override fun getEmptyMessage() = "暂无收益记录"

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.recommend, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.mMenuRecommend) {
            FragmentContainerActivity.from(mContext).setTitle("我的下线").setClazz(MineLowerLevelFragment::class.java).start()
        }
        return super.onOptionsItemSelected(item)
    }

}