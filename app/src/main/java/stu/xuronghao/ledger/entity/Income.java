package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Income implements Serializable {
    private String incNo;
    private String incEvent = "收入";
    private String incType;
    private double incAmount;
    private String incRemark;
    private String incDate;
    private String userNo;

    public Income() {
    }

    public Income(String incNo, String incEvent,
                  String incType, double incAmount,
                  String incRemark, String incDate,
                  String userNo) {
        this.incNo = incNo;
        this.incEvent = incEvent;
        this.incType = incType;
        this.incAmount = incAmount;
        this.incRemark = incRemark;
        this.incDate = incDate;
        this.userNo = userNo;
    }

    public Income(String event, String selected,
                  double parseDouble, String dateStr,
                  String remark, String userNo) {
        this.incEvent = event;
        this.incType = selected;
        this.incAmount = parseDouble;
        this.incRemark = remark;
        this.incDate = dateStr;
        this.userNo = userNo;
    }

    public String getIncNo() {
        return incNo;
    }

    public void setIncNo(String incNo) {
        this.incNo = incNo;
    }

    public String getIncEvent() {
        return incEvent;
    }

    public void setIncEvent(String incEvent) {
        this.incEvent = incEvent;
    }

    public String getIncType() {
        return incType;
    }

    public void setIncType(String incType) {
        this.incType = incType;
    }

    public double getIncAmount() {
        return incAmount;
    }

    public void setIncAmount(double incAmount) {
        this.incAmount = incAmount;
    }

    public String getIncRemark() {
        return incRemark;
    }

    public void setIncRemark(String incRemark) {
        this.incRemark = incRemark;
    }

    public String getIncDate() {
        return incDate;
    }

    public void setIncDate(String incDate) {
        this.incDate = incDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "Income{" +
                "incNo='" + incNo + '\'' +
                ", incEvent='" + incEvent + '\'' +
                ", incType='" + incType + '\'' +
                ", incAmount=" + incAmount +
                ", incRemark='" + incRemark + '\'' +
                ", incDate='" + incDate + '\'' +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
