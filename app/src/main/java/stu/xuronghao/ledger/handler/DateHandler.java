package stu.xuronghao.ledger.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHandler {
    private static SimpleDateFormat dateFormat;
//    private static Calendar calendar;
//    private static int Start = 1,End = 2;

    //获取当前时间
    public static String getCurrentDatetime() {
        TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(zone);
        return dateFormat.format(new Date());
    }

    //废弃
    //获取月初/月末日期
//    public static String getMonthEdge(int month, int mode){
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        calendar.set(Calendar.MONTH,month);
//        if(mode == Start)
//            //月初
//            calendar.set(Calendar.DAY_OF_MONTH,1);
//        else if(mode == End)
//            //月末
//            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        return dateFormat.format(calendar.getTime());
//    }
//
}
