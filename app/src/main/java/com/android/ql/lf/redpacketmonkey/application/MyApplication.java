package com.android.ql.lf.redpacketmonkey.application;

import android.support.multidex.MultiDexApplication;

import com.android.ql.lf.redpacketmonkey.component.AppComponent;
import com.android.ql.lf.redpacketmonkey.component.AppModule;
import com.android.ql.lf.redpacketmonkey.component.DaggerAppComponent;

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static MyApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static MyApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
