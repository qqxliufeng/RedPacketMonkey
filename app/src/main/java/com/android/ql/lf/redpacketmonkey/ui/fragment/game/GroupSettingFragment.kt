package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.view.View
import android.widget.LinearLayout
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.alert
import kotlinx.android.synthetic.main.fragment_group_setting_layout.*

class GroupSettingFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_group_setting_layout

    override fun initView(view: View?) {
        (0 until 5).forEach {
            val childView = View.inflate(mContext, R.layout.adapter_group_member_item_layout, null) as LinearLayout
            val param = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            param.weight = 1.0f
            param.width = 0
            childView.layoutParams = param
            mLlGroupSettingMemberContainer.addView(childView)
        }
        mTvGroupSettingMemberCount.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(GroupMemberListFragment::class.java).setTitle("成员列表").setNeedNetWorking(true).start()
        }
        mTvGroupSettingLogoutGroup.setOnClickListener {
            alert("提示", "确定要退出群组吗？", "退出", "不退出", { _, _ ->
                finish()
            }, null)
        }
    }
}