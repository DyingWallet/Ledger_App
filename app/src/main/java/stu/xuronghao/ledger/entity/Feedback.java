package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String fbNo;
    private String fbTitle;
    private String fbType;
    private String fbContent;
    private int fbRead;
    private String fbDate;
    private String userNo;

    public Feedback() {
    }

    public Feedback(String fbNo, String fbTitle,
                    String fbType, String fbContent,
                    int fbRead, String fbDate, String userNo) {
        this.fbNo = fbNo;
        this.fbTitle = fbTitle;
        this.fbType = fbType;
        this.fbContent = fbContent;
        this.fbRead = fbRead;
        this.fbDate = fbDate;
        this.userNo = userNo;
    }

    public String getFbNo() {
        return fbNo;
    }

    public void setFbNo(String fbNo) {
        this.fbNo = fbNo;
    }

    public String getFbTitle() {
        return fbTitle;
    }

    public void setFbTitle(String fbTitle) {
        this.fbTitle = fbTitle;
    }

    public String getFbType() {
        return fbType;
    }

    public void setFbType(String fbType) {
        this.fbType = fbType;
    }

    public String getFbContent() {
        return fbContent;
    }

    public void setFbContent(String fbContent) {
        this.fbContent = fbContent;
    }

    public int getFbRead() {
        return fbRead;
    }

    public void setFbRead(int fbRead) {
        this.fbRead = fbRead;
    }

    public String getFbDate() {
        return fbDate;
    }

    public void setFbDate(String fbDate) {
        this.fbDate = fbDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "fbNo='" + fbNo + '\'' +
                ", fbTitle='" + fbTitle + '\'' +
                ", fbType='" + fbType + '\'' +
                ", fbContent='" + fbContent + '\'' +
                ", fbRead=" + fbRead +
                ", fbDate='" + fbDate + '\'' +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
