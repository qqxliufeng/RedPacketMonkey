package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.webkit.*
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.Constants
import com.android.ql.lf.redpacketmonkey.utils.startWebAliPay
import kotlinx.android.synthetic.main.fragment_recharge_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast

class RechargeFragment : BaseNetWorkingFragment() {

    companion object {
        fun startRecharge(context: Context, money: String) {
            FragmentContainerActivity
                    .from(context)
                    .setClazz(RechargeFragment::class.java)
                    .setTitle("充值")
                    .setNeedNetWorking(true)
                    .setExtraBundle(bundleOf(Pair("money", money)))
                    .start()
        }
    }

    override fun getLayoutId() = R.layout.fragment_recharge_layout

    @SuppressLint("JavascriptInterface")
    override fun initView(view: View?) {
        val setting = mWbRecharge.settings
        setting.javaScriptEnabled = true
        setting.databaseEnabled = true
        setting.domStorageEnabled = true
        setting.allowFileAccess = true

        mWbRecharge.addJavascriptInterface(this, "AndroidWebView")

        mWbRecharge.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mPbRecharge.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mPbRecharge.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (request != null) {
                    if (mContext.startWebAliPay(request.url.toString())) {
                        toast("正在支付~")
                    } else {
                        view?.loadUrl(request.url.toString())
                    }
                }
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (mContext.startWebAliPay(url.toString())) {
                    toast("正在支付~")
                } else {
                    view?.loadUrl(url.toString())
                }
                return true
            }
        }
        mWbRecharge.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    mPbRecharge.visibility = View.GONE
                } else {
                    mPbRecharge.visibility = View.VISIBLE
                    mPbRecharge.progress = newProgress
                }
            }
        }
        mWbRecharge.loadUrl("${Constants.BASE_IP}api/money/recharge?money=${arguments!!.getString("money")}&uid=${UserInfo.getInstance().money_id}")
//        mWbRecharge.loadUrl("${Constants.BASE_IP}/api/money/appJs")
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    fun syntony() {
        finish()
    }
}