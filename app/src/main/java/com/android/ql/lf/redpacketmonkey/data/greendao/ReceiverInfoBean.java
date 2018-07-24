package com.android.ql.lf.redpacketmonkey.data.greendao;


public class ReceiverInfoBean {

    private Long redPacketId;

    private String time;

    private String userFaceUrl;

    private String userNickName;

    private String receiverTime;

    private String receiverMoney;


    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserFaceUrl() {
        return userFaceUrl;
    }

    public void setUserFaceUrl(String userFaceUrl) {
        this.userFaceUrl = userFaceUrl;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getReceiverTime() {
        return receiverTime;
    }

    public void setReceiverTime(String receiverTime) {
        this.receiverTime = receiverTime;
    }

    public String getReceiverMoney() {
        return receiverMoney;
    }

    public void setReceiverMoney(String receiverMoney) {
        this.receiverMoney = receiverMoney;
    }

    @Override
    public String toString() {
        return "ReceiverInfoBean{" +
                "redPacketId=" + redPacketId +
                ", time='" + time + '\'' +
                ", userFaceUrl='" + userFaceUrl + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", receiverTime='" + receiverTime + '\'' +
                ", receiverMoney='" + receiverMoney + '\'' +
                '}';
    }
}
