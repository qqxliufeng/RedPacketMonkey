package com.android.ql.lf.redpacketmonkey.application;

import android.support.multidex.MultiDexApplication;

import com.android.ql.lf.redpacketmonkey.component.AppComponent;
import com.android.ql.lf.redpacketmonkey.component.AppModule;
import com.android.ql.lf.redpacketmonkey.component.DaggerAppComponent;

import cn.jpush.im.android.api.JMessageClient;

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static MyApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
//        setupDataBase();
    }

//    private void setupDataBase() {
//        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "red_packet.db");
//        daoSession = new DaoMaster(openHelper.getWritableDb()).newSession();
//    }

    public static MyApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
