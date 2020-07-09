package stu.xuronghao.ledger.entity;


import java.io.Serializable;

public class Anno implements Serializable {
    private String annoNo;
    private String annoTitle;
    private String annoType = "Normal";
    private String annoContent;
    private String annoDate;
    private String adminNo;

    public Anno() {
    }

    public Anno(String annoNo, String annoTitle,
                String annoContent, String annoDate, String adminNo) {
        this.annoNo = annoNo;
        this.annoTitle = annoTitle;
        this.annoContent = annoContent;
        this.annoDate = annoDate;
        this.adminNo = adminNo;
    }

    public String getAnnoNo() {
        return annoNo;
    }

    public void setAnnoNo(String annoNo) {
        this.annoNo = annoNo;
    }

    public String getAnnoTitle() {
        return annoTitle;
    }

    public void setAnnoTitle(String annoTitle) {
        this.annoTitle = annoTitle;
    }

    public String getAnnoType() {
        return annoType;
    }

    public void setAnnoType(String annoType) {
        this.annoType = annoType;
    }

    public String getAnnoContent() {
        return annoContent;
    }

    public void setAnnoContent(String annoContent) {
        this.annoContent = annoContent;
    }

    public String getAnnoDate() {
        return annoDate;
    }

    public void setAnnoDate(String annoDate) {
        this.annoDate = annoDate;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    @Override
    public String toString() {
        return "Anno{" +
                "annoNo='" + annoNo + '\'' +
                ", annoTitle='" + annoTitle + '\'' +
                ", annoType='" + annoType + '\'' +
                ", annoContent='" + annoContent + '\'' +
                ", annoDate=" + annoDate +
                ", adminNo='" + adminNo + '\'' +
                '}';
    }
}
