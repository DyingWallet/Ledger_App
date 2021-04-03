package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userNo;
    private String userName;
    private String userPasswd;
    private int userStatus = 0;
    private int userCredits = 0;

    public User() {
    }

    //注册
    public User(String userName, String userPasswd) {
        this.userName = userName;
        this.userPasswd = userPasswd;
    }

    //删除
    public User(String userNo) {
        this.userNo = userNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserCredits() {
        return userCredits;
    }

    public void setUserCredits(int userCredits) {
        this.userCredits = userCredits;
    }

    @Override
    public String toString() {
        return "User{" +
                "userNo='" + userNo + '\'' +
                ", userName='" + userName + '\'' +
                ", userPasswd='" + userPasswd + '\'' +
                ", userStatus=" + userStatus +
                ", userCredits=" + userCredits +
                '}';
    }
}
