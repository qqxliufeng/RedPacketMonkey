package com.android.ql.lf.redpacketmonkey.present;

import android.util.Log;

import com.android.ql.lf.redpacketmonkey.application.MyApplication;
import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class RedPacketManager {

    public static final long EXECUTE_FAILED = -1;

    public static long insertRedPacket(String json) {
        try {
            if (json != null) {
                RedPacketEntity redPacketEntity = new Gson().fromJson(json, RedPacketEntity.class);
                if (UserInfo.getInstance().getUser_id().equals(String.valueOf(redPacketEntity.group_red_uid))) {
                    redPacketEntity.setItemType(RedPacketEntity.SEND_RED_PACKET);
                } else {
                    redPacketEntity.setItemType(RedPacketEntity.FROM_RED_PACKET);
                }
                return MyApplication.getRedPacketDao().insertRedPacket(redPacketEntity);
            } else {
                return EXECUTE_FAILED;
            }
        } catch (JsonSyntaxException e) {
            return EXECUTE_FAILED;
        }
    }


    /**
     * 根据群id 删除所有的红包
     * @param gid 群id
     */
    public static void deleteRedPacketAll(long gid) {
        try {
            MyApplication.getRedPacketDao().deleteAll(gid);
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
        }
    }

}
