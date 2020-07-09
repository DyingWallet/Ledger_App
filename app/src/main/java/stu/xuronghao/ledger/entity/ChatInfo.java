package stu.xuronghao.ledger.entity;

public class ChatInfo {

    private String userNo;
    private String Datetime;
    private String Content;
    private int img;
    private int isMeSend;

    public ChatInfo() {
    }

    public ChatInfo(String userNo, String datetime, String content, int isMeSend) {
        this.userNo = userNo;
        Datetime = datetime;
        Content = content;
        this.isMeSend = isMeSend;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
