package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.*
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_mine_recommend_layout.*

class MineRecommendFragment :BaseRecyclerViewFragment<String>(){


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.fragment_packet_info_list_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
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
            mRlMineRecommendShouYiContainer.alpha = 1 - Math.abs(verticalOffset).toFloat()/appBarLayout.totalScrollRange
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.recommend,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.mMenuRecommend){
            FragmentContainerActivity.from(mContext).setTitle("我的下线").setClazz(MineLowerLevelFragment::class.java).start()
        }
        return super.onOptionsItemSelected(item)
    }


}