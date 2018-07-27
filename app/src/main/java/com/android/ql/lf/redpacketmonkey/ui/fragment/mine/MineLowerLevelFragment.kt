package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine_lower_level_layout.*

class MineLowerLevelFragment : BaseFragment() {

    companion object {
        val TITLES = arrayListOf("全部(3)", "直属粉丝(3)", "间接粉丝(3)")
    }


    override fun getLayoutId() = R.layout.fragment_mine_lower_level_layout

    override fun initView(view: View?) {
        mVpMineLowerLevel.adapter = MineLowerLevelAdapter(childFragmentManager)
        mTlMineLowerLevel.setupWithViewPager(mVpMineLowerLevel)
    }

    class MineLowerLevelAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = when(position){
            0->{
                MineLowerLevelItemFragment()
            }
            1->{
                MineLowerLevelItemFragment()
            }
            2->{
                MineLowerLevelItemFragment()
            }
            else->{
                null
            }
        }

        override fun getCount() = TITLES.size

        override fun getPageTitle(position: Int) = TITLES[position]

    }

}