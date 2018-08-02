package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.ImageBean
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData
import com.android.ql.lf.redpacketmonkey.ui.activity.SelectAddressActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.ui.fragment.money.AliPayNameDialogFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import com.soundcloud.android.crop.Crop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_mine_info_layout.*
import okhttp3.MultipartBody
import org.jetbrains.anko.support.v4.toast
import java.io.File

class MineInfoFragment : BaseNetWorkingFragment() {

    private val aliPayNameDialogFragment by lazy {
        AliPayNameDialogFragment()
    }

    override fun getLayoutId() = R.layout.fragment_mine_info_layout

    override fun initView(view: View?) {

        GlideManager.loadFaceCircleImage(mContext,UserInfo.getInstance().user_pic,mIvMineInfoFace)

        mTvMineInfoAccount.text = UserInfo.getInstance().user_phone.hiddenPhone()

        mTvMineInfoNickName.text = UserInfo.getInstance().user_nickname

        mTvMineInfoSex.text = if (TextUtils.isEmpty(UserInfo.getInstance().user_sex) || "null" == UserInfo.getInstance().user_sex) {
            "男"
        } else {
            UserInfo.getInstance().user_sex
        }

        mTvMineInfoAddress.text = if (TextUtils.isEmpty(UserInfo.getInstance().user_dizhi)  || "null" == UserInfo.getInstance().user_dizhi) {
            "暂无"
        } else {
            UserInfo.getInstance().user_dizhi
        }

        mRlMineInfoNickNameContainer.setOnClickListener {
            aliPayNameDialogFragment.myShow(childFragmentManager, "reset_nick_name", "修改昵称", "昵称") {
                mPresent.getDataByPost(0x0, RequestParamsHelper.getNickNameParam(it))
            }
        }
        mRlMineInfoAddress.setOnClickListener {
            SelectAddressActivity.startAddressActivityForResult(this, 1)
        }
        mRlMineInfoSexContainer.setOnClickListener {
            var sex = "男"
            val builder = AlertDialog.Builder(mContext)
            builder.setPositiveButton("确定") { _, _ ->
                mPresent.getDataByPost(0x1, RequestParamsHelper.getSexParam(sex))
            }
            builder.setNegativeButton("取消", null)
            builder.setSingleChoiceItems(arrayOf("男", "女"), 0) { _, which ->
                sex = if (which == 0) {
                    "男"
                } else {
                    "女"
                }
            }
            builder.create().show()
        }
        mRlMineInfoFaceContainer.setOnClickListener {
            openImageChoose(MimeType.ofImage(),1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val selectAddressItemBean = data.getParcelableExtra<SelectAddressActivity.SelectAddressItemBean>(SelectAddressActivity.REQUEST_DATA_FALG)
                mPresent.getDataByPost(0x2, RequestParamsHelper.getAddressParam(selectAddressItemBean.name))
            }
        } else if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            val resultData = Matisse.obtainResult(data)
            resultData[0].let {
                val dir = File("${Constants.IMAGE_PATH}face/")
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val desUri = Uri.fromFile(File(dir, "${System.currentTimeMillis()}.jpg"))
                Crop.of(it, desUri).start(mContext, this@MineInfoFragment)
            }
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri = Crop.getOutput(data)
                ImageUploadHelper(object : ImageUploadHelper.OnImageUploadListener {
                    override fun onActionFailed() {
                        toast("头像上传失败，请稍后重试！")
                    }

                    override fun onActionStart() {
                        getFastProgressDialog("正在上传头像……")
                    }

                    override fun onActionEnd(builder: MultipartBody.Builder) {
                        mPresent.uploadFile(0x3, RequestParamsHelper.USER_MODEL, RequestParamsHelper.ACT_UPDATE_FACE, builder.build().parts())
                    }
                }).upload(arrayListOf(ImageBean(null, uri.path)))
            }
        }
    }


    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> { //修改昵称
                getFastProgressDialog("正在修改昵称")
            }
            0x1 -> {
                getFastProgressDialog("正在修改性别")
            }
            0x2 -> {
                getFastProgressDialog("正在修改地址")
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x0 -> {
                if (obj != null && obj is String) {
                    UserInfo.getInstance().user_nickname = obj
                    mTvMineInfoNickName.text = obj
                    UserInfoLiveData.postUserInfo()
                    toast("昵称修改成功")
                }
            }
            0x1 -> {
                if (obj != null && obj is String) {
                    UserInfo.getInstance().user_sex = obj
                    mTvMineInfoSex.text = obj
                    toast("性别修改成功")
                }
            }
            0x2 -> {
                if (obj != null && obj is String) {
                    UserInfo.getInstance().user_dizhi = obj
                    mTvMineInfoAddress.text = obj
                    toast("地址修改成功")
                }
            }
            0x3 -> {
                if (obj != null && obj is String) {
                    UserInfo.getInstance().user_pic = obj
                    GlideManager.loadFaceCircleImage(mContext,UserInfo.getInstance().user_pic,mIvMineInfoFace)
                    UserInfoLiveData.postUserInfo()
                    toast("头像修改成功")
                }
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> { //修改昵称
                "昵称修改失败"
            }
            0x1 -> { //修改性别
                "性别修改失败"
            }
            0x2 -> { //修改地址
                "地址修改失败"
            }
            0x3 -> {//修改头像
                "头像修改失败"
            }
            else -> {
                "未知异常"
            }
        }
    }

}