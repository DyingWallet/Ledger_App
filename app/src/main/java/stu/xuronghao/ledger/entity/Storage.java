package stu.xuronghao.ledger.entity;

import java.io.Serializable;
import java.util.Date;

public class Storage implements Serializable {
    private String userNo;
    private String proNo;
    private Date   purDate;

    public Storage() {
    }

    public Storage(String userNo, String proNo, Date purDate) {
        this.userNo = userNo;
        this.proNo = proNo;
        this.purDate = purDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public Date getPurDate() {
        return purDate;
    }

    public void setPurDate(Date purDate) {
        this.purDate = purDate;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "userNo='" + userNo + '\'' +
                ", proNo='" + proNo + '\'' +
                ", purDate=" + purDate +
                '}';
    }
}
