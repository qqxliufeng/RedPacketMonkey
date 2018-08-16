package com.android.ql.lf.redpacketmonkey.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.ql.lf.redpacketmonkey.application.MyApplication;
import com.android.ql.lf.redpacketmonkey.component.ApiServerModule;
import com.android.ql.lf.redpacketmonkey.component.DaggerApiServerComponent;
import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.present.GetDataFromNetPresent;
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper;

import javax.inject.Inject;

public class RedPacketServices extends IntentService {

    @Inject
    GetDataFromNetPresent mGetDataFromNetPresent;

    public RedPacketServices() {
        super("red_packet_services");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mGetDataFromNetPresent == null) {
            DaggerApiServerComponent.builder().apiServerModule(new ApiServerModule()).appComponent(MyApplication.getInstance().getAppComponent()).build().inject(this);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null && intent.getLongExtra("group_red_id",0) != 0L){
            if (mGetDataFromNetPresent!=null && UserInfo.getInstance().isLogin()){
                mGetDataFromNetPresent.getDataByPost(0x0,RequestParamsHelper.Companion.getBackRedPacket(String.valueOf(intent.getLongExtra("group_red_id",0))));
            }
        }
    }
}
