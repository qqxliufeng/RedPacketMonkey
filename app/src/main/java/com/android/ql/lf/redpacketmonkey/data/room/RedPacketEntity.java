package com.android.ql.lf.redpacketmonkey.data.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.chad.library.adapter.base.entity.MultiItemEntity;

@Entity(indices = {@Index(value = {"group_red_id"}, unique = true)}, tableName = "red_packet")
public class RedPacketEntity implements MultiItemEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    //红包id
    public long group_red_id;

    //群组id
    public long group_red_group;

    //用户id
    public long group_red_uid;

    //"红包金额
    public String group_red_sum;

    //红包底线金额
    public String group_red_min;

    //红包数量
    public String group_red_cou;

    //已领取数量
    public String group_red_recou;

    //雷数字
    public int group_red_mine;

    //退款秒数
    public String group_red_quit;

    //退款时间
    public String group_red_quit_times;

    //是否结束 1 = 结束 2 = 未结束
    public int group_red_is_quit;

    //时间
    public String group_red_times;

    //红包类型
    public int group_red_type;

    //IM群ID
    public long group_red_group_gid;

    //发送人的昵称
    public String group_red_name;

    //发送人的头像
    public String group_red_pic;

    //记录是否已经抢过红包了
    public int groud_red_is_get;


    @Ignore
    public boolean isShowTime = false;

    public static final int SEND_RED_PACKET = 0;
    public static final int FROM_RED_PACKET = 1;
    public static final int TIME = 2;

    private int itemType = SEND_RED_PACKET;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
