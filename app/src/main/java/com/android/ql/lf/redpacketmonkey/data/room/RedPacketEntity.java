package com.android.ql.lf.redpacketmonkey.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"redPacketId"}, unique = true)}, tableName = "red_packet")
public class RedPacketEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;


    public long redPacketId;

    public String sendUserFace;
    public String sendUserNickName;

    public String sendTime;

    @ColumnInfo(name = "new_column")
    public String newColumn;

}
