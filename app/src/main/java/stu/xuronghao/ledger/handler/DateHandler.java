package stu.xuronghao.ledger.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import stu.xuronghao.ledger.handler.consts.ConstantVariable;

public class DateHandler {
    private static final TimeZone zone = TimeZone.getTimeZone(ConstantVariable.TIME_ZONE);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantVariable.DATETIME_FORMAT, Locale.CHINA);

    //获取当前时间
    public static String getCurrentDatetime() {
        dateFormat.setTimeZone(zone);
        return dateFormat.format(new Date());
    }

    //获取当前月份
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance(zone);
        return calendar.get(Calendar.MONTH);
    }

    //获取当前年份
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance(zone);
        return calendar.get(Calendar.YEAR);
    }

    //设置开始/结束日期
    public static String setDateRange(int code, int destinationMonth) {
        Calendar calendar = Calendar.getInstance(zone);
        calendar.set(Calendar.MONTH, destinationMonth);
        String result;
        switch (code) {
            case ConstantVariable.START_CODE:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                result = dateFormat.format(calendar.getTime()).split(ConstantVariable.TIME_REGEX)[0];
                return result + ConstantVariable.START_OF_DAY;
            case ConstantVariable.END_CODE:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                result = dateFormat.format(calendar.getTime()).split(ConstantVariable.TIME_REGEX)[0];
                return result + ConstantVariable.END_OF_DAY;
            default:
                return "";
        }
    }

    public static String getCurrentDateLimit(int startOrEnd) {
        return setDateRange(startOrEnd, getCurrentMonth());
    }

    private DateHandler() {
    }
}