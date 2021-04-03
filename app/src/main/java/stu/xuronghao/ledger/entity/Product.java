package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String proNo;
    private String proName;
    private int proNumber;
    private String proType;
    private int proPrice;
    private int proDiscount = 10;
    private boolean proStatus;
    private String adminNo;

    public Product() {
    }

    //用户查询自身库存
    public Product(String proNo, String proName, String proType) {
        this.proNo = proNo;
        this.proName = proName;
        this.proType = proType;
    }

    //商城拉取商品列表


    public Product(String proNo, String proName,
                   int proNumber, String proType,
                   int proPrice, int proDiscount) {
        this.proNo = proNo;
        this.proName = proName;
        this.proNumber = proNumber;
        this.proType = proType;
        this.proPrice = proPrice;
        this.proDiscount = proDiscount;
    }


    //管理员增加库存

    public Product(String proNo, String proName,
                   int proNumber, String proType,
                   int proPrice, String adminNo) {
        this.proNo = proNo;
        this.proName = proName;
        this.proNumber = proNumber;
        this.proType = proType;
        this.proPrice = proPrice;
        this.adminNo = adminNo;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getProNumber() {
        return proNumber;
    }

    public void setProNumber(int proNumber) {
        this.proNumber = proNumber;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public int getProPrice() {
        return proPrice;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    public int getProDiscount() {
        return proDiscount;
    }

    public void setProDiscount(int proDiscount) {
        this.proDiscount = proDiscount;
    }

    public boolean isProStatus() {
        return proStatus;
    }

    public void setProStatus(boolean proStatus) {
        this.proStatus = proStatus;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "proNo='" + proNo + '\'' +
                ", proName='" + proName + '\'' +
                ", proNumber=" + proNumber +
                ", proType='" + proType + '\'' +
                ", proPrice=" + proPrice +
                ", proDiscount=" + proDiscount +
                ", proStatus=" + proStatus +
                ", adminNo='" + adminNo + '\'' +
                '}';
    }
}
