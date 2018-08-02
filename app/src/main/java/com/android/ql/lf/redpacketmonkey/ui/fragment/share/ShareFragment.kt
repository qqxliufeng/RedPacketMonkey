package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.utils.Constants
import com.android.ql.lf.redpacketmonkey.utils.ThirdShareManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.fragment_share_layout.*
import org.jetbrains.anko.support.v4.toast
import java.lang.Exception

class ShareFragment : BaseFragment(), IUiListener {

    private val shareDialogFragment by lazy {
        ShareDialogFragment()
    }


    private val iwxapi by lazy {
        WXAPIFactory.createWXAPI(mContext, Constants.WX_APP_ID, true)
    }

    override fun getLayoutId() = R.layout.fragment_share_layout

    override fun initView(view: View?) {
        mTvFragmentShare.setOnClickListener {
            shareDialogFragment.myShow(childFragmentManager, "share_dialog_fragment"){
                when(it){
                    ShareDialogFragment.WX_SHARE->{
                        Glide.with(this)
                                .load(UserInfo.getInstance().sharePic)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(object : SimpleTarget<Bitmap>(150, 150) {
                                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                                        ThirdShareManager.wxShare(iwxapi, resource, SendMessageToWX.Req.WXSceneSession)
                                    }

                                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                                        super.onLoadFailed(e, errorDrawable)
                                        toast("分享失败")
                                    }
                                })
                    }
                    ShareDialogFragment.CIRCLE_SHARE->{
                        Glide.with(this)
                                .load(UserInfo.getInstance().sharePic)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(object : SimpleTarget<Bitmap>(150, 150) {
                                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                                        ThirdShareManager.wxShare(iwxapi, resource, SendMessageToWX.Req.WXSceneTimeline)
                                    }

                                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                                        super.onLoadFailed(e, errorDrawable)
                                        toast("分享失败")
                                    }
                                })

                    }
                    ShareDialogFragment.QQ_SHARE->{
                        ThirdShareManager.qqShare(
                                (mContext as Activity),
                                Tencent.createInstance(Constants.TENCENT_ID,
                                        mContext.applicationContext),
                                this)

                    }
                    ShareDialogFragment.ZONE_SHARE->{
                        ThirdShareManager.zoneShare(
                                (mContext as Activity),
                                Tencent.createInstance(Constants.TENCENT_ID,
                                        mContext.applicationContext),
                                this)
                    }
                }
            }
        }

        mTvFragmentShareCode.setOnClickListener {
            ShareCodeFragment.start(mContext)
        }

    }


    override fun onComplete(p0: Any?) {
        toast("分享成功")
    }

    override fun onCancel() {
        toast("分享取消")
    }

    override fun onError(p0: UiError?) {
        toast("分享失败")
    }
}