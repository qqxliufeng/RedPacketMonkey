package com.android.ql.lf.redpacketmonkey.present;


import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.interfaces.IViewUserAction;
import com.android.ql.lf.redpacketmonkey.interfaces.ViewUserAction;
import com.android.ql.lf.redpacketmonkey.utils.RxBus;

import org.json.JSONObject;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class UserPresent {

    private IViewUserAction userAction;

    public UserPresent() {
        userAction = new ViewUserAction();
    }

    public void onLogin(JSONObject memberInfo) {
        if (userAction.onLogin(memberInfo)) {
            sendLoginSuccessMessage();
        }
    }

    public void onLoginNoBus(JSONObject memberInfo) {
        userAction.onLogin(memberInfo);
    }

    public void onLogout() {
        if (userAction.onLogout()) {
            sendLogoutSuccessMessage();
        }
    }

    public void modifyInfoForName(String result) {
        if (userAction.modifyInfoForName(result)) {
            sendModifyInfoSuccessMessage();
        }
    }

    public void modifyInfoForPic(String result) {
        if (userAction.modifyInfoForPic(result)) {
            sendModifyInfoSuccessMessage();
        }
    }

    public void sendLoginSuccessMessage() {
        RxBus.getDefault().post(UserInfo.getInstance());
    }

    public void sendLogoutSuccessMessage() {
        RxBus.getDefault().post(UserInfo.LOGOUT_FLAG);
    }

    public void sendModifyInfoSuccessMessage() {
        RxBus.getDefault().post("modify info success");
    }

    public void getCode(String phone) {
    }

    public void register(String name, String password) {
    }

    public void forgetPassword(String phone, String code) {
    }

    public void resetPassword(String oldPassword, String newPassword) {
    }

    public void qqLogin() {
    }

    public void weixinLogin() {
    }

}
