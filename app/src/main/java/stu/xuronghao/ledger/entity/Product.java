package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String proNo;
    private String proName;
    private int proPrice;

    public Product(String proNo, String proName, int proPrice) {
        this.proNo = proNo;
        this.proName = proName;
        this.proPrice = proPrice;
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

    public int getProPrice() {
        return proPrice;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "proNo='" + proNo + '\'' +
                ", proName='" + proName + '\'' +
                ", proPrice=" + proPrice +
                '}';
    }
}
