package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.Constants
import kotlinx.android.synthetic.main.fragment_message_layout.*


class MessageFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_message_layout

    override fun initView(view: View?) {
//        val redPacketEntity = RedPacketEntity()
//        redPacketEntity.sendTime = "2018-8-6"
//        redPacketEntity.redPacketId = 1
//        redPacketEntity.sendUserFace = "face"
//        redPacketEntity.sendUserNickName = "nickname"
//        MyApplication.getInstance().redPacketDao.insertRedPacket(redPacketEntity)

        MyApplication.getInstance().redPacketDao.queryAll().forEach {
            Log.e("TAG",it.sendUserNickName)
            Log.e("TAG",it.sendUserFace)
        }


        (mTlMainMessage.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        val setting = mWbMessageContent.settings
        setting.javaScriptEnabled = true
        setting.databaseEnabled = true
        setting.domStorageEnabled = true
        setting.allowFileAccess = true
        mWbMessageContent.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mSrlMessage.post {
                    mSrlMessage.isRefreshing = true
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mSrlMessage.post {
                    mSrlMessage.isRefreshing = false
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (request != null) {
                    view?.loadUrl(request.url.toString())
                }
                return true
            }

        }
        mWbMessageContent.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    mSrlMessage.post {
                        mSrlMessage.isRefreshing = false
                    }
                }
            }
        }
        mWbMessageContent.loadUrl(Constants.BASE_IP + "/api/system/gonggao")
//        mWbMessageContent.loadUrl("http://47.75.58.32/index.php")
        mSrlMessage.setOnRefreshListener {
            mWbMessageContent.reload()
        }
    }
}