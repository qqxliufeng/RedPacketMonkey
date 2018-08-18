package com.android.ql.lf.redpacketmonkey.data;


import android.content.Context;
import android.text.TextUtils;

import com.android.ql.lf.redpacketmonkey.utils.Constants;
import com.android.ql.lf.redpacketmonkey.utils.PreferenceUtils;
import java.text.DecimalFormat;

/**
 * Created by lf on 18.2.10.
 *
 * @author lf on 18.2.10
 */

public class UserInfo {


    public static final String USER_ID_FLAG = "user_id";

    public static final String LOGOUT_FLAG = "user_logout_flag";

    public static String loginToken = "NONE";

    public static void resetLoginSuccessDoActionToken() {
        loginToken = "NONE";
    }

    private UserInfo() {
    }

    private static UserInfo instance;

    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }


    private String user_id;
    private String user_phone;
    private String user_pass;
    private String user_code;
    private String user_nickname;
    private String user_pic;

    private String sharePic;
    private String shareTitle;
    private String shareIntro;
    private String shareUrl;

    private String user_rank;
    private String user_as;
    private String user_sex;
    private String user_dizhi;
    private String user_z_pass;
    private String money_id;
    private double money_sum_cou;
    private String money_sum_emit;
    private String money_sum_collect;
    private String money_sum_ti;
    private String money_is_show;
    private String money_times;
    private String money_uid;

    private int user_is_news;
    private int user_is_red;

    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getUser_is_news() {
        return user_is_news;
    }

    public void setUser_is_news(int user_is_news) {
        this.user_is_news = user_is_news;
    }

    public int getUser_is_red() {
        return user_is_red;
    }

    public void setUser_is_red(int user_is_red) {
        this.user_is_red = user_is_red;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_dizhi() {
        return user_dizhi;
    }

    public void setUser_dizhi(String user_dizhi) {
        this.user_dizhi = user_dizhi;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public String getUser_as() {
        return user_as;
    }

    public void setUser_as(String user_as) {
        this.user_as = user_as;
    }

    public String getMoney_id() {
        return money_id;
    }

    public void setMoney_id(String money_id) {
        this.money_id = money_id;
    }

    public String getMoney_sum_cou() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(money_sum_cou);
    }

    public void setMoney_sum_cou(double money_sum_cou) {
        this.money_sum_cou = money_sum_cou;
    }

    public String getMoney_sum_emit() {
        return money_sum_emit;
    }

    public void setMoney_sum_emit(String money_sum_emit) {
        this.money_sum_emit = money_sum_emit;
    }

    public String getMoney_sum_collect() {
        return money_sum_collect;
    }

    public void setMoney_sum_collect(String money_sum_collect) {
        this.money_sum_collect = money_sum_collect;
    }

    public String getMoney_sum_ti() {
        return money_sum_ti;
    }

    public void setMoney_sum_ti(String money_sum_ti) {
        this.money_sum_ti = money_sum_ti;
    }

    public String getMoney_is_show() {
        return money_is_show;
    }

    public void setMoney_is_show(String money_is_show) {
        this.money_is_show = money_is_show;
    }

    public String getMoney_times() {
        return money_times;
    }

    public void setMoney_times(String money_times) {
        this.money_times = money_times;
    }

    public String getMoney_uid() {
        return money_uid;
    }

    public void setMoney_uid(String money_uid) {
        this.money_uid = money_uid;
    }

    public String getSharePic() {
        if (TextUtils.isEmpty(sharePic)) {
            return Constants.BASE_IP;
        }
        if (sharePic.startsWith("http://") || sharePic.startsWith("https://")) {
            return sharePic;
        }
        return Constants.BASE_IP + sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareIntro() {
        return shareIntro;
    }

    public void setShareIntro(String shareIntro) {
        this.shareIntro = shareIntro;
    }

    public String getShareUrl() {
        if (TextUtils.isEmpty(shareUrl)) {
            return Constants.BASE_IP;
        }
        if (shareUrl.startsWith("http://") || shareUrl.startsWith("https://")) {
            return shareUrl;
        }
        return Constants.BASE_IP + shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_z_pass() {
        return user_z_pass;
    }

    public void setUser_z_pass(String user_z_pass) {
        this.user_z_pass = user_z_pass;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(user_id);
    }

    public void loginOut() {
        user_id = null;
        instance = null;
    }

    public void exitApp() {
        if (instance != null) {
            instance = null;
        }
    }

    public static void clearUserCache(Context context) {
        PreferenceUtils.setPrefString(context, USER_ID_FLAG, "");
    }

    public static boolean isCacheUserId(Context context) {
        return PreferenceUtils.hasKey(context, USER_ID_FLAG) && !TextUtils.isEmpty(PreferenceUtils.getPrefString(context, USER_ID_FLAG, ""));
    }

    public static String getUserIdFromCache(Context context) {
        return PreferenceUtils.getPrefString(context, USER_ID_FLAG, "");
    }

}
