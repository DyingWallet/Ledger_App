package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Cost implements Serializable {
    private String costNo;
    private String costEvent = "支出";
    private String costType;
    private double costAmount;
    private String costDate;
    private String costRemark;
    private String userNo;

    public Cost() {
    }

    public Cost(String costNo, String costEvent,
                String costType, double costAmount,
                String costDate, String costRemark,
                String userNo) {
        this.costNo = costNo;
        this.costEvent = costEvent;
        this.costType = costType;
        this.costAmount = costAmount;
        this.costDate = costDate;
        this.costRemark = costRemark;
        this.userNo = userNo;
    }

    public Cost(String event, String selected,
                double parseDouble, String date,
                String remark, String userNo) {
        this.costEvent = event;
        this.costType = selected;
        this.costAmount = parseDouble;
        this.costDate = date;
        this.costRemark = remark;
        this.userNo = userNo;
    }

    public String getCostNo() {
        return costNo;
    }

    public void setCostNo(String costNo) {
        this.costNo = costNo;
    }

    public String getCostEvent() {
        return costEvent;
    }

    public void setCostEvent(String costEvent) {
        this.costEvent = costEvent;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public double getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(double costAmount) {
        this.costAmount = costAmount;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostRemark() {
        return costRemark;
    }

    public void setCostRemark(String costRemark) {
        this.costRemark = costRemark;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "costNo='" + costNo + '\'' +
                ", costEvent='" + costEvent + '\'' +
                ", costType='" + costType + '\'' +
                ", costAmount=" + costAmount +
                ", costDate=" + costDate +
                ", costRemark='" + costRemark + '\'' +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
