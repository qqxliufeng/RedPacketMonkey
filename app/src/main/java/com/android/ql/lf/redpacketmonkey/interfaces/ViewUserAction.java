package com.android.ql.lf.redpacketmonkey.interfaces;


import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.lf.redpacketmonkey.application.MyApplication;
import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData;
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

            UserInfo.getInstance().setSharePic(result.optString("sharePic"));
            UserInfo.getInstance().setShareTitle(result.optString("shareTitle"));
            UserInfo.getInstance().setShareIntro(result.optString("shareIntro"));
            UserInfo.getInstance().setShareUrl(result.optString("shareUrl"));

            UserInfo.getInstance().setUser_code(result.optString("user_code"));
            UserInfo.getInstance().setUser_rank(result.optString("user_rank"));
            UserInfo.getInstance().setUser_as(result.optString("user_as"));
            UserInfo.getInstance().setUser_sex(result.optString("user_sex"));
            UserInfo.getInstance().setUser_dizhi(result.optString("user_dizhi"));

            UserInfo.getInstance().setMoney_id(result.optString("money_id"));
            UserInfo.getInstance().setMoney_sum_cou(result.optString("money_sum_cou"));
            UserInfo.getInstance().setMoney_sum_emit(result.optString("money_sum_emit"));
            UserInfo.getInstance().setMoney_sum_collect(result.optString("money_sum_collect"));
            UserInfo.getInstance().setMoney_sum_ti(result.optString("money_sum_ti"));
            UserInfo.getInstance().setMoney_uid(result.optString("money_uid"));
            PreferenceUtils.setPrefString(MyApplication.application, UserInfo.USER_ID_FLAG, UserInfo.getInstance().getUser_id());
            UserInfoLiveData.INSTANCE.postUserInfo();
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
