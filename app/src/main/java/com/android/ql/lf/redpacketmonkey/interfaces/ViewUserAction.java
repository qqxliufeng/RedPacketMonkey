package com.android.ql.lf.redpacketmonkey.interfaces;


import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.lf.redpacketmonkey.application.MyApplication;
import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class ViewUserAction implements IViewUserAction {

    @Override
    public boolean onLogin(@NotNull JSONObject result) {
        try {
            UserInfo.getInstance().setUser_id(result.optString("user_id"));
            UserInfo.getInstance().setUser_nickname(result.optString("user_nickname"));
            UserInfo.getInstance().setUser_phone(result.optString("user_phone"));
            UserInfo.getInstance().setUser_pic(result.optString("user_pic"));
            UserInfo.getInstance().setUser_code(result.optString("user_code"));
            UserInfo.getInstance().setUser_is_rank(result.optString("user_is_rank"));
            UserInfo.getInstance().setUser_is_vehicle(result.optString("user_is_vehicle"));
            UserInfo.getInstance().setUser_w_sum(result.optString("user_w_sum"));
            UserInfo.getInstance().setUser_y_sum(result.optString("user_y_sum"));
            UserInfo.getInstance().setKephone(result.optString("kephone"));
            UserInfo.getInstance().setSharePic(result.optString("sharePic"));
            UserInfo.getInstance().setShareTitle(result.optString("shareTitle"));
            UserInfo.getInstance().setShareIntro(result.optString("shareIntro"));

            PreferenceUtils.setPrefString(MyApplication.application, UserInfo.USER_ID_FLAG, UserInfo.getInstance().getUser_id());
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    @Override
    public boolean onLogout() {
        UserInfo.getInstance().loginOut();
        return true;
    }

    @Override
    public void onRegister(@NotNull JSONObject result) {
    }

    @Override
    public void onForgetPassword(@NotNull JSONObject result) {
    }

    @Override
    public void onResetPassword(@NotNull JSONObject result) {
    }

    @Override
    public boolean modifyInfoForName(@NotNull String name) {
        try {
            UserInfo.getInstance().setUser_nickname(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean modifyInfoForPic(@NotNull String result) {
        try {
            UserInfo.getInstance().setUser_pic(result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}