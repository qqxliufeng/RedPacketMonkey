package com.android.ql.lf.redpacketmonkey.data.greendao;


import java.util.Date;
import java.util.List;

//@Entity
public class RedPacketBean {
//    @Id(autoincrement = true)
    private Long id;

    /**
     * 时间戳
     */
    private String time;
    /**
     * 红包发送时间
     */
    private Date sendDate;
    /**
     * 红包结束时间
     */
    private Date endDate;


    /**
     * 红包金额
     */
    private double account;

    /**
     * 最大领取的人数
     */
    private int maxReceiveNum;

    /**
     * 已经领取的人数
     */
    private int hasReceiveNum;
    /**
     * 领取人的id集合
     */
//    @Convert(columnType = String.class, converter = ReceiverConverter.class)
    private List<ReceiverInfoBean> receiverList;


//    @Generated(hash = 435798990)
    public RedPacketBean(Long id, String time, Date sendDate, Date endDate,
            double account, int maxReceiveNum, int hasReceiveNum,
            List<ReceiverInfoBean> receiverList) {
        this.id = id;
        this.time = time;
        this.sendDate = sendDate;
        this.endDate = endDate;
        this.account = account;
        this.maxReceiveNum = maxReceiveNum;
        this.hasReceiveNum = hasReceiveNum;
        this.receiverList = receiverList;
    }

//    @Generated(hash = 887423399)
    public RedPacketBean() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public int getMaxReceiveNum() {
        return maxReceiveNum;
    }

    public void setMaxReceiveNum(int maxReceiveNum) {
        this.maxReceiveNum = maxReceiveNum;
    }

    public int getHasReceiveNum() {
        return hasReceiveNum;
    }

    public void setHasReceiveNum(int hasReceiveNum) {
        this.hasReceiveNum = hasReceiveNum;
    }

    public List<ReceiverInfoBean> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<ReceiverInfoBean> receiverList) {
        this.receiverList = receiverList;
    }
}
