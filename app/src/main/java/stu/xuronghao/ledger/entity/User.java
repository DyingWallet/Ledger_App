package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userNo;
    private String userName;
    private String userPasswd;
    private String userStorage;
    private double userBudget;
    private int userStatus;
    private int userCredits;

    public User() {
    }

    public User(User user){
        this.userNo = user.getUserNo();
        this.userName = user.getUserName();
        this.userPasswd = user.getUserPasswd();
        this.userStorage = user.getUserStorage();
        this.userBudget = user.getUserBudget();
        this.userStatus = user.getUserStatus();
        this.userCredits = user.getUserCredits();
    }

    //注册
    public User(String userNo, String userName, String userPasswd) {
        this.userNo = userNo;
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

    public String getUserStorage() {
        return userStorage;
    }

    public void setUserStorage(String userStorage) {
        this.userStorage = userStorage;
    }

    public double getUserBudget() {
        return userBudget;
    }

    public void setUserBudget(double userBudget) {
        this.userBudget = userBudget;
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
