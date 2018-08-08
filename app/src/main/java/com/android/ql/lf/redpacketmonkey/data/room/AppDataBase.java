package com.android.ql.lf.redpacketmonkey.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RedPacketEntity.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract RedPacketDao redPacketDao();

}
