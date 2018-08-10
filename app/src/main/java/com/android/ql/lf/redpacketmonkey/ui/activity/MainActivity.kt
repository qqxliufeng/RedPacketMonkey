package com.android.ql.lf.redpacketmonkey.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.services.RedPacketServices
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.GameFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.MessageFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.bottom.MineFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.LoginFragment
import com.android.ql.lf.redpacketmonkey.utils.RxBus
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private var exsit:Long = 0L

    private val userLogoutSubscription by lazy {
        RxBus.getDefault().toObservable(UserInfo::class.java).subscribe {
            if (!it.isLogin) {
                UserInfo.clearUserCache(this)
                LoginFragment.startLogin(this)
                finish()
            }
        }
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        setStatusBarColor()
        userLogoutSubscription
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
        MyApplication.getInstance().handler.postAtTime({
            Log.e("TAG","postAtTime")
            startService(Intent(this@MainActivity, RedPacketServices::class.java))
        }, (1000 * 10)+ SystemClock.uptimeMillis())
    }

    override fun getStatusBarColor() = Color.TRANSPARENT

    override fun onDestroy() {
        if (!userLogoutSubscription.isUnsubscribed) {
            userLogoutSubscription.unsubscribe()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exsit > 2000){
            exsit = System.currentTimeMillis()
            toast("再按一次退出")
        }else{
            moveTaskToBack(false)
        }
    }

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
