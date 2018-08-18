package com.android.ql.lf.redpacketmonkey.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.ui.widgets.PayPsdInputView
import org.jetbrains.anko.windowManager

fun Context.checkGpsIsOpen(): Boolean {
    return (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun Activity.openGpsPage(requestCode: Int = -1) {
    startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), requestCode)
}

fun Context.hiddenKeyBoard(token: IBinder) {
    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Context.showKeyBoard(token: View) {
    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(token, InputMethodManager.SHOW_FORCED)
}

fun Fragment.startPhone(phone: String) {
    val builder = AlertDialog.Builder(this.context!!)
    builder.setMessage("是否拨打电话？")
    builder.setNegativeButton("否", null)
    builder.setPositiveButton("是") { _, _ ->
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    builder.create().show()
}

fun Context.getScreenSize(): BaseFragment.ScreenSize {
    val outMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(outMetrics)
    val screenSize = BaseFragment.ScreenSize()
    screenSize.height = outMetrics.heightPixels
    screenSize.width = outMetrics.widthPixels
    return screenSize
}


fun Context.startWebAliPay(url: String): Boolean {
    if (url.contains("platformapi/startapp")) {
        return try {
            Log.e("TAG",url)
            val intent: Intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            intent.component = null
            startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }
    return false
}


fun Fragment.alert(title: String? = "title",
                   message: String = "message",
                   positiveButton: String = "是",
                   negativeButton: String = "否",
                   positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
                   negativeAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null) =
        this.context?.alert(title, message, positiveButton, negativeButton, positiveAction, negativeAction)

fun Fragment.alert(message: String = "message",
                   positiveButton: String = "是",
                   negativeButton: String = "否", positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null) =
        this.alert(null, message, positiveButton, negativeButton, positiveAction, null)

fun Context.alert(title: String? = "title",
                  message: String = "message",
                  positiveButton: String = "是",
                  negativeButton: String = "否",
                  positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
                  negativeAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setTitle(title)
    builder.setNegativeButton(negativeButton, negativeAction)
    builder.setPositiveButton(positiveButton, positiveAction)
    builder.create().show()
}


fun Context.showPayPasswordDialog(resetAction: () -> Unit, forgetAction: () -> Unit, action: (String) -> Unit) {
    val dialog = Dialog(this)
    dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    val attributes = dialog.window.attributes
    attributes.y = -this.getScreenSize().height / 5
    dialog.window.attributes = attributes
    val contentView = View.inflate(this, R.layout.dialog_wallet_password_layout, null)
    contentView.findViewById<TextView>(R.id.mTvResetWalletPassword).setOnClickListener {
        dialog.dismiss()
        resetAction()

    }
    contentView.findViewById<TextView>(R.id.mTvForgetWalletPassword).setOnClickListener {
        dialog.dismiss()
        forgetAction()
    }
    val et_password = contentView.findViewById<PayPsdInputView>(R.id.mEtWalletPassword)
    et_password.setComparePassword(object : PayPsdInputView.onPasswordListener {
        override fun onDifference(oldPsd: String?, newPsd: String?) {
        }

        override fun onEqual(psd: String?) {
        }

        override fun inputFinished(inputPsd: String?) {
            action(inputPsd!!)
            dialog.dismiss()
        }
    })
    dialog.setContentView(contentView)
    dialog.show()
    contentView.postDelayed({ this.showKeyBoard(contentView.findViewById<EditText>(R.id.mEtWalletPassword)) }, 100)
}