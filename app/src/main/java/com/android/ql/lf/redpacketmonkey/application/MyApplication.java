package com.android.ql.lf.redpacketmonkey.application;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.android.ql.lf.redpacketmonkey.component.AppComponent;
import com.android.ql.lf.redpacketmonkey.component.AppModule;
import com.android.ql.lf.redpacketmonkey.component.DaggerAppComponent;
import com.android.ql.lf.redpacketmonkey.data.room.AppDataBase;
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketDao;
import com.android.ql.lf.redpacketmonkey.present.RedPacketManager;
import com.android.ql.lf.redpacketmonkey.services.RedPacketServices;
import com.android.ql.lf.redpacketmonkey.utils.PreferenceUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static MyApplication application;

    public RedPacketDao redPacketDao;

    private Handler handler = new Handler();

    private MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_DISABLE);
        JMessageClient.registerEventReceiver(this);
        setupDataBase();
        startService(new Intent(MyApplication.this, RedPacketServices.class));
        mediaPlayer = MediaPlayer.create(this, getDefaultUri());
        if(Double.isNaN(10)){
            Log.e("TAG","is nan");
        }else {
            Log.e("TAG","is no nan");
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void onEvent(MessageEvent event) {
        try {
            String receiverJson = event.getMessage().getContent().toJsonElement().getAsJsonObject().get("text").getAsString();
            JSONObject jsonObject = new JSONObject(receiverJson);
            long groupId = jsonObject.optLong("group_red_group");
            if (!RedPacketManager.isBlockMessage(this, groupId)) {
                RedPacketManager.insertRedPacket(receiverJson);
                //插入成功，需要播放声音的，要播放声音
                if (PreferenceUtils.getPrefBoolean(this, "new_message_sound", false)) {
                    mediaPlayer.setLooping(false);
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getDefaultUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
    }

    private void setupDataBase() {
        AppDataBase db = Room.databaseBuilder(this, AppDataBase.class, "red_packet_data_base").addCallback(new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.e("TAG", "db create");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.e("TAG", "db onOpen");
            }
        }).allowMainThreadQueries().addMigrations().fallbackToDestructiveMigration().build();
        redPacketDao = db.redPacketDao();
    }

    static final Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table red_packet add column new_column TEXT");
        }
    };

    public static MyApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static RedPacketDao getRedPacketDao() {
        return MyApplication.getInstance().redPacketDao;
    }

    public void unRegisterEvent() {
        JMessageClient.unRegisterEventReceiver(this);
    }

}
