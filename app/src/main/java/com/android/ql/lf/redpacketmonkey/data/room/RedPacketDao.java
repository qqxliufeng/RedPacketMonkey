package com.android.ql.lf.redpacketmonkey.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RedPacketDao {


    /**
     * 增加一条新的记录
     *
     * @param redPacketEntity 要添加的内容
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)//冲突替换策略
    public long insertRedPacket(RedPacketEntity redPacketEntity);

    /**
     * 更新一条新记录
     *
     * @param redPacketEntity 要更新的内容
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateRedPacket(RedPacketEntity redPacketEntity);

    /**
     * 删除一条记录
     *
     * @param redPacketEntity 要删除的内容
     */
    @Delete
    public void deleteRedPacket(RedPacketEntity redPacketEntity);


    /**
     * 删除全部
     */
    @Query("delete from red_packet where group_red_group = :gid;")
    public void deleteAll(long gid);


    /**
     * 根据id查询
     *
     * @param redPacketId
     * @return
     */
    @Query("select * from red_packet where group_red_id = :redPacketId limit 1;")
    public LiveData<RedPacketEntity> queryById(long redPacketId);

    /**
     * 查询所有的
     *
     * @return
     */
    @Query("select * from red_packet where group_red_group = :gid;")
    public List<RedPacketEntity> queryAll(long gid);


    @Query("select * from red_packet where group_red_group =:gid order by id desc limit 1;")
    public LiveData<RedPacketEntity> queryLastOne(long gid);

}
