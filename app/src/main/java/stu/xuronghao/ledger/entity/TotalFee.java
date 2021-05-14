package stu.xuronghao.ledger.entity;

public class TotalFee {
    private int img;
    private Double fee;
    private String type;

    public TotalFee(int img, double fee, String type) {
        this.img = img;
        this.fee = fee;
        this.type = type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TotalFee{" +
                "img=" + img +
                ", fee=" + fee +
                ", type='" + type + '\'' +
                '}';
    }
}

