package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Admin implements Serializable {
    private String adminNo;
    private String adminName;
    private String adminPasswd;
    private int adminStatus;

    public Admin() {
    }

    public Admin(String name) {
        adminName = name;
    }

    public Admin(String adminNo, String name, String password) {
        this.adminNo = adminNo;
        this.adminName = name;
        this.adminPasswd = password;
        this.adminStatus = 0;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPasswd() {
        return adminPasswd;
    }

    public void setAdminPasswd(String adminPasswd) {
        this.adminPasswd = adminPasswd;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminNo='" + adminNo + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPasswd='" + adminPasswd + '\'' +
                ", adminStatus=" + adminStatus +
                '}';
    }
}
