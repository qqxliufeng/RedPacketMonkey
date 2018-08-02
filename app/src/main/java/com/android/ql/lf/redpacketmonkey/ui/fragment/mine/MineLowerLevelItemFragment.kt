package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.text.TextUtils
import android.util.Log
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.RecommendUserBean
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.lf.redpacketmonkey.utils.GlideManager
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.hiddenPhone
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

class MineLowerLevelItemFragment : BaseRecyclerViewFragment<RecommendUserBean>() {

    companion object {
        fun startMineLowerLevel(act: String): MineLowerLevelItemFragment {
            val mineLowerLevelItemFragment = MineLowerLevelItemFragment()
            mineLowerLevelItemFragment.arguments = bundleOf(Pair("act", act))
            Log.e("TAG","~~~~~")
            return mineLowerLevelItemFragment
        }
    }

    override fun createAdapter() = object : BaseQuickAdapter<RecommendUserBean, BaseViewHolder>(R.layout.adapter_mine_lower_level_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: RecommendUserBean?) {
            helper?.setText(R.id.mTvMineLowerLevelItemIndex, "${item?.user_number}")
            helper?.setText(R.id.mTvMineLowerLevelItemNickName, item?.user_nickname)
            helper?.setText(R.id.mTvMineLowerLevelItemPhone, item?.user_phone?.hiddenPhone())
            GlideManager.loadFaceCircleImage(mContext, item?.user_pic, helper?.getView(R.id.mTvMineLowerLevelItemFace))
        }
    }

    override fun getEmptyMessage() = "暂无下线"

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getRankParam(arguments!!.getString("act")))
    }

    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        setLoadEnable(false)
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, RecommendUserBean::class.java)
        if (!TextUtils.isEmpty(result)) {
            val json = JSONObject(result)
            val countJson = json.optJSONObject("cou")
            if (countJson != null) {
                val all = countJson.optInt("all")
                val one = countJson.optInt("one")
                val two = countJson.optInt("two")
                (parentFragment as MineLowerLevelFragment).changeTitle(all = all,one = one,two = two)
            }
        }
    }
}