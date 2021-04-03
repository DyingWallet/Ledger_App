package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Sentence implements Serializable {
    private int SenId;
    private String Content;


    public Sentence() {

    }

    public Sentence(int senId, String content) {
        SenId = senId;
        Content = content;
    }

    public int getSenId() {
        return SenId;
    }

    public void setSenId(int senId) {
        SenId = senId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "SenId=" + SenId +
                ", Content='" + Content + '\'' +
                '}';
    }
}
