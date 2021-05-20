package stu.xuronghao.ledger.entity;

public class AnnoInfo {

    private String annoTitle;
    private String annoDate;

    public AnnoInfo(String annoTitle, String annoDate) {
        this.annoTitle = annoTitle;
        this.annoDate = annoDate;
    }

    public String getAnnoTitle() {
        return annoTitle;
    }

    public void setAnnoTitle(String annoTitle) {
        this.annoTitle = annoTitle;
    }

    public String getAnnoDate() {
        return annoDate;
    }

    public void setAnnoDate(String annoDate) {
        this.annoDate = annoDate;
    }
}
