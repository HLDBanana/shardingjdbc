package com.xlhj.sharding.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author 86199
 * @Date 2021/2/3 11:00
 */
@Slf4j
public class DateUtil {
    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TIME_MILLS_PATTERN = "HHmmsss";
    public static final String PATTERN_DATE2 = "yyyyMMdd";
    public static final String PATTERN_DATE3 = "yyyyMM";
    public static final String YMD="yyyy-MM-dd";
    public static final String MD="MM-dd";
    private static String yesterday="yesterday";
    private static String today="today";
    private static String recentDays="recentDays";
    private static String month="month";
    public static final String YMD_HM="yyyy-MM-dd HHmm";

    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {

        List<String> list=  getYMBetweenDate(stringToDate("2020-05-12 00:00:00"),new Date());
        log.info("list size = {}",list.size());
    }


    /**
     * 获取两个日期之间的所有日期，格式为：yyyyMM
     * @param start
     * @param end
     * @return
     */
    public static List<String> getYMBetweenDate(Date start,Date end){
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE3);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
//        tempEnd.add(Calendar.MONTH, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.MONTH, 1);
        }
        return days;
    }

    public static List<String> getBetweenDays(String stime,String etime){
        SimpleDateFormat df = new SimpleDateFormat( MD);
        Date sdate = null;
        Date eDate = null;
        try {
            sdate = df.parse(stime);
            eDate = df.parse(etime);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
            return new ArrayList<>(0);
        }
        Calendar c = Calendar.getInstance();
        List<String> list = new ArrayList<>();
        while (sdate.getTime() <= eDate.getTime()) {
            list.add(df.format(sdate));
            c.setTime(sdate);
            c.add(Calendar.DATE, 1);
            sdate = c.getTime();
        }
        return list;
    }
    /**
     * 将字符串格式的时间转化为Date类型的日期，
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(YMD_HMS);
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取指定日期的前N天日期
     **/
    public static Date getBeforeDayDate(Date date, int beforeDay) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.add(Calendar.DATE, -beforeDay);
        return a.getTime();
    }

    /**
     * 添加指定的小时
     *
     * @param payDate
     * @param n
     * @return
     */
    public static Date addHour(Date payDate, int n) {
        // 当前系统时间转Calendar类型
        Calendar cal = dataToCalendar(payDate);
        // 增加n个小时
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }

    /**
     * 增加指定天
     * @param payDate   初始日期
     * @param n         增加天数
     * @return
     */
    public static Date addDays(Date payDate, int n) {
        // 当前系统时间转Calendar类型
        Calendar cal = dataToCalendar(payDate);
        // 增加n天
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 增加指定秒
     * @param payDate       初始日期
     * @param n             增加秒数
     * @return
     */
    public static Date addSecond(Date payDate, int n) {
        // 当前系统时间转Calendar类型
        Calendar cal = dataToCalendar(payDate);
        // 增加多少秒
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }

    /**
     * 增加指定分钟
     * @param payDate   初始时间
     * @param n         增加分钟数
     * @return
     */
    public static Date addMinute(Date payDate, int n) {
        // 当前系统时间转Calendar类型
        Calendar cal = dataToCalendar(payDate);
        // 增加多少秒
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }
    /**
     * 增加指定分钟
     * @param payDate   初始时间
     * @param n         增加分钟数
     * @return
     */
    public static String addMinuteStr(Date payDate, int n) {
        // 当前系统时间转Calendar类型
        Calendar cal = dataToCalendar(payDate);
        // 增加多少秒cal.getTime()
        cal.add(Calendar.MINUTE, n);
        return dateToString(cal.getTime());
    }
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将javaDate类型的日期转化为 yyyy-MM-dd HH:mm:ss 格式的字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date,YMD_HMS);
    }

    public static String dateToString(Date date,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
