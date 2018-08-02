package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.utils.hiddenPhone
import kotlinx.android.synthetic.main.fragment_mine_lower_level_layout.*

class MineLowerLevelFragment : BaseFragment() {

    companion object {
        val TITLES = arrayListOf("全部", "直属粉丝", "间接粉丝")
    }

    private val adapter by lazy {
        MineLowerLevelAdapter(childFragmentManager)
    }


    override fun getLayoutId() = R.layout.fragment_mine_lower_level_layout

    override fun initView(view: View?) {
        mTvMineLowerLevelAccount.text = "当前帐号：${UserInfo.getInstance().user_phone.hiddenPhone()}"
        mVpMineLowerLevel.adapter = MineLowerLevelAdapter(childFragmentManager)
        mVpMineLowerLevel.offscreenPageLimit = 3
        mTlMineLowerLevel.setupWithViewPager(mVpMineLowerLevel)
    }

    fun changeTitle(all:Int,one:Int,two:Int){
        TITLES[0] = "全部(${all}人)"
        TITLES[1] = "直属粉丝(${one}人)"
        TITLES[2] = "间接粉丝(${two}人)"
        adapter.notifyDataSetChanged()
    }

    class MineLowerLevelAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = when (position) {
            0 -> {
                MineLowerLevelItemFragment.startMineLowerLevel("rankAll")
            }
            1 -> {
                MineLowerLevelItemFragment.startMineLowerLevel("rankOne")
            }
            2 -> {
                MineLowerLevelItemFragment.startMineLowerLevel("rankTwo")
            }
            else -> {
                null
            }
        }

        override fun getCount() = TITLES.size

        override fun getPageTitle(position: Int) = TITLES[position]

    }

}