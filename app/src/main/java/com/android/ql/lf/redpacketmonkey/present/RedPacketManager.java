package com.android.ql.lf.redpacketmonkey.present;

import android.util.Log;

import com.android.ql.lf.redpacketmonkey.application.MyApplication;
import com.android.ql.lf.redpacketmonkey.data.UserInfo;
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

public class RedPacketManager {

    public enum ActionMode {
        NONE, INSERT, UPDATE, DELETE, SELECT
    }

    public static ActionMode current_mode = ActionMode.NONE;


    public static final long EXECUTE_FAILED = -1;

    public static long insertRedPacket(String json) {
        try {
            if (json != null) {
                current_mode = ActionMode.INSERT;
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
            current_mode = ActionMode.NONE;
            return EXECUTE_FAILED;
        }
    }


    /**
     * 根据群id 删除所有的红包
     *
     * @param gid 群id
     */
    public static void deleteRedPacketAll(long gid) {
        try {
            current_mode = ActionMode.DELETE;
            MyApplication.getRedPacketDao().deleteAll(gid);
        } catch (Exception e) {
            current_mode = ActionMode.NONE;
            Log.e("TAG", e.getLocalizedMessage());
        }
    }


    /**
     * 更新红包
     *
     * @param redPacketEntity
     */
    public static void updateRedPacket(RedPacketEntity redPacketEntity) {
        try {
            current_mode = ActionMode.UPDATE;
            MyApplication.getRedPacketDao().updateRedPacket(redPacketEntity);
        } catch (Exception e) {
            current_mode = ActionMode.NONE;
            Log.e("TAG", e.getLocalizedMessage());
        }
    }

    public static List<RedPacketEntity> queryByLimit(long gid, int currentPage, int pageSize) {
        try {
            current_mode = ActionMode.SELECT;
            return MyApplication.getRedPacketDao().queryByLimit(gid, currentPage * pageSize, pageSize);
        } catch (Exception e) {
            current_mode = ActionMode.NONE;
            return null;
        }
    }
}
