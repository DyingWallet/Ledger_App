package stu.xuronghao.ledger.entity;

public class ChatInfo {

    private String userNo;
    private String datetime;
    private String content;
    private int img;
    private int isMeSend;

    public ChatInfo() {
    }

    public ChatInfo(String userNo, String datetime, String content, int isMeSend) {
        this.userNo = userNo;
        this.datetime = datetime;
        this.content = content;
        this.isMeSend = isMeSend;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getIsMeSend() {
        return isMeSend;
    }

    public void setIsMeSend(int isMeSend) {
        this.isMeSend = isMeSend;
    }
}
