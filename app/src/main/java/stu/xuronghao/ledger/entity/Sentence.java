package stu.xuronghao.ledger.entity;

import java.io.Serializable;

public class Sentence implements Serializable {
    private int sentenceId;
    private String content;

    public Sentence() {
    }

    public Sentence(int sentenceId, String content) {
        this.sentenceId = sentenceId;
        this.content = content;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "SenId=" + sentenceId +
                ", Content='" + content + '\'' +
                '}';
    }
}
