package stu.xuronghao.ledger.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHandler {

    //获取当前时间
    public static String getCurrentDatetime() {
        TimeZone zone = TimeZone.getTimeZone(ConstantVariable.TIME_ZONE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantVariable.DATETIME_FORMAT, Locale.CHINA);
        dateFormat.setTimeZone(zone);
        return dateFormat.format(new Date());
    }
    private DateHandler() {
    }
}