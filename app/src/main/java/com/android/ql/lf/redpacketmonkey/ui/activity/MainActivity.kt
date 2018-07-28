package com.android.ql.lf.redpacketmonkey.ui.activity

import android.graphics.Color
import android.os.Build
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.receiver.RedPacketBroadCastReceiver
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.GameFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.MessageFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.MineFragment
import com.android.ql.lf.redpacketmonkey.utils.Constants
import com.android.ql.lf.redpacketmonkey.utils.PollingUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        setStatusBarColor()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        mBvMainNavigation.setOnNavigationItemSelectedListener {
            when {
                it.itemId == R.id.mMenuGame -> {
                    mVpMainContainer.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                it.itemId == R.id.mMenuMessage -> {
                    mVpMainContainer.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    mVpMainContainer.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
        mVpMainContainer.offscreenPageLimit = 3
        mVpMainContainer.adapter = MainAdapter(supportFragmentManager)

        PollingUtils.startPollingService(MyApplication.application, 5, RedPacketBroadCastReceiver::class.java, Constants.RED_PACKET_ACTION)
    }

    override fun getStatusBarColor() = Color.TRANSPARENT


    class MainAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = when (position) {
            0 -> {
                MessageFragment()
            }
            1 -> {
                GameFragment()
            }
            else -> {
                MineFragment()
            }
        }

        override fun getCount() = 3

    }
}
