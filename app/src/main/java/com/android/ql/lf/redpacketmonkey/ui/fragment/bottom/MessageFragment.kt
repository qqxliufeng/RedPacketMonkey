package com.android.ql.lf.redpacketmonkey.ui.fragment.bottom

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.VersionInfo
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.Constants
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.VersionHelp
import com.android.ql.lf.redpacketmonkey.utils.alert
import kotlinx.android.synthetic.main.fragment_message_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject



class MessageFragment : BaseNetWorkingFragment() {

    companion object {
        val INSTALL_PACKAGES_REQUESTCODE = 0
    }


    override fun getLayoutId() = R.layout.fragment_message_layout

    override fun initView(view: View?) {
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
        mSrlMessage.setOnRefreshListener {
            mWbMessageContent.reload()
        }
        mPresent.getDataByPost(0x0, RequestParamsHelper.getVerupdateParam())
    }


    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        if (requestID == 0x0) {
            try {
                if (obj != null && obj is JSONObject) {
                    VersionInfo.getInstance().versionCode = obj.optString("appApkVer").toInt()
                    VersionInfo.getInstance().content = obj.optString("appApkIntro")
                    VersionInfo.getInstance().downUrl = obj.optString("appApk")
                    if (VersionInfo.getInstance().versionCode > VersionHelp.currentVersionCode(mContext)) {
                        install()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            val canInstalls = mContext.packageManager.canRequestPackageInstalls()
//                            if (canInstalls){
//                                install()
//                            }else{
//                                alert("提示","发现新版本，但需要安装权限才能继续安装","打开","取消",{_,_->
//                                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
//                                    startActivityForResult(intent, INSTALL_PACKAGES_REQUESTCODE)
//                                },null)
//                            }
//                        }else{
//                            install()
//                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (requestCode == INSTALL_PACKAGES_REQUESTCODE && mContext.packageManager.canRequestPackageInstalls()) {
                install()
            }
        }
    }

    private fun install() {
        alert("发现新版本", VersionInfo.getInstance().content, "立即下载", "暂不下载", { _, _ ->
            toast("正在下载……")
            VersionHelp.downNewVersion(mContext, Uri.parse(VersionInfo.getInstance().downUrl), "${System.currentTimeMillis()}")
        }, null)
    }

}