package com.android.ql.lf.redpacketmonkey.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.android.ql.lf.redpacketmonkey.R;
import com.android.ql.lf.redpacketmonkey.application.MyApplication;
import com.android.ql.lf.redpacketmonkey.component.ApiServerModule;
import com.android.ql.lf.redpacketmonkey.component.DaggerApiServerComponent;
import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.present.GetDataFromNetPresent;
import com.android.ql.lf.redpacketmonkey.present.UserPresent;
import com.android.ql.lf.redpacketmonkey.ui.fragment.mine.LoginFragment;
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.DeviceInfo;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    /**
     * 权限标识
     */
    private static final int WRITE_AND_CAMERA = 0;

    /**
     * 需要的权限
     */
    private static final String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final String[] REQUEST_PERMISSIONS_DESCRIPTION = new String[]{"相机", "读取SD卡"};

    @Inject
    GetDataFromNetPresent mPresent;

    private UserPresent userPresent = new UserPresent();


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    public void initView() {
        DaggerApiServerComponent.builder().apiServerModule(new ApiServerModule()).appComponent(MyApplication.getInstance().getAppComponent()).build().inject(this);
        mPresent.setNetDataPresenter(this);
        if (hasPermissions()) {
            findViewById(R.id.mIvSplash).postDelayed(new Runnable() {
                @Override
                public void run() {
                    isLogin();
                }
            }, 2500);
        } else {
            requestPermission();
        }
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        EasyPermissions.requestPermissions(this, getResources().getString(R.string.app_name) + " 需要您的相机、存储", WRITE_AND_CAMERA, REQUEST_PERMISSIONS);
    }

    /**
     * 检测是否有相应的权限
     *
     * @return true if all permissions already have granted ,otherwise false
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, REQUEST_PERMISSIONS);
    }

    /**
     * 请求权限回调的方法
     *
     * @param requestCode  请求code
     * @param permissions  权限列表
     * @param grantResults 请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (hasPermissions()) {
            isLogin();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri packageURI = Uri.parse("package:" + getPackageName());
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    startActivityForResult(intent, AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setTitle("系统需要以下权限").setItems(REQUEST_PERMISSIONS_DESCRIPTION, null).create().show();

        } else {
            requestPermission();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {
        finish();
    }


    /**
     * 设置好权限回调的方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (hasPermissions()) {
                isLogin();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public <T> void onRequestSuccess(int requestID, T result) {
        super.onRequestSuccess(requestID, result);
        try {
            JSONObject json = new JSONObject(result.toString());
            if ("200".equals(json.optString("code"))) {
                userPresent.onLoginNoBus(json.optJSONObject("data"));
                JMessageClient.login(UserInfo.getInstance().getUser_as(), UserInfo.getInstance().getUser_as(), new RequestCallback<List<DeviceInfo>>() {
                    @Override
                    public void gotResult(int i, String s, List<DeviceInfo> deviceInfos) {
                        if (i == 0) { //表示登录成功
                            startMain();
                        } else { //表示登录异常，提示异常信息
                            startLogin();
                        }
                    }
                });
            } else {
                startLogin();
            }
        } catch (JSONException e) {
            startLogin();
        }
    }

    @Override
    public void onRequestFail(int requestID, @NotNull Throwable e) {
        super.onRequestFail(requestID, e);
        startLogin();
    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 所有有权限都已经请求到了，直接进入到主页面
     */
    private void isLogin() {
        if (UserInfo.isCacheUserId(this)) {
            mPresent.getDataByPost(0x0, RequestParamsHelper.Companion.getPersonalParam(UserInfo.getUserIdFromCache(this)));
        } else {
            startLogin();
        }
    }


    private void startLogin() {
        LoginFragment.Companion.startLogin(this);
        finish();
    }

}
