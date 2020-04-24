package com.wenruo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @ClassName ProDate
 * @Description 时间处理的工具类,在JDK1.8后推出了DateTimeFormatter，此类为线程安全的
 * @Author MuYao
 * @Date 2020/4/24 4:20
 * @Version 1.0
 **/
public class ProDate {
    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProDate.class);
    /** 格式为yyyy-MM-dd */
    public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /** 格式为HH:mm:ss */
    public final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    /** 格式为yyyy-MM-dd HH:mm:ss */
    public final static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /** 格式为yyyy-MM-dd HH:mm */
    public final static DateTimeFormatter DATE_TIME_FORMAT_EXCLUDE_SECOND = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /** 格式为yyyyMMdd */
    public final static DateTimeFormatter DATE_SIMPLE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    /** 格式为yyyyMM */
    public final static DateTimeFormatter DATE_SIMPLE_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
    /** 格式为yyyy-MM */
    public final static DateTimeFormatter DATE_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");
    /** 格式为HHmmss */
    public final static DateTimeFormatter TIME_SIMPLE_FORMAT = DateTimeFormatter.ofPattern("HHmmss");
    /** 格式为yyyyMMddHHmmss */
    public final static DateTimeFormatter DATE_TIME_SIMPLE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /** 格式为yyyy年MM月dd日 */
    public final static DateTimeFormatter DATE_CHN_FORMAT = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    /** 格式为HH时mm分ss秒 */
    public final static DateTimeFormatter TIME_CHN_FORMAT = DateTimeFormatter.ofPattern("HH时mm分ss秒");
    /** 格式为yyyy年MM月dd日 HH时mm分ss秒 */
    public final static DateTimeFormatter DATE_TIME_CHN_FORMAT = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
    /** 起始的时间后缀 00:00:00*/
    public final static String DATE_BEGIN = " 00:00:00";
    /** 起始的时间后缀 000000*/
    public final static String DATE_BEGIN_SHOT = "000000";
    /** 截止的时间后缀 23:59:59*/
    public final static String DATE_END = " 23:59:59";
    /** 截止的时间后缀 235959*/
    public final static String DATE_END_SHOT = "235959";

    /**
    * @Description: 根据时间字符串获取时间
    * @param: [dateString, dateTimeFormatter]
    * @return: java.time.LocalDateTime
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:42
    */
    public static LocalDateTime getDateTime(String dateString , DateTimeFormatter dateTimeFormatter){
        if (ProString.isBlank(dateString)) {
            return null;
        }
        return LocalDateTime.parse(dateString , dateTimeFormatter);
    }

    public static LocalDate getDate(String dateString , DateTimeFormatter dateTimeFormatter){
        if (ProString.isBlank(dateString)) {
            return null;
        }
        return LocalDate.parse(dateString , dateTimeFormatter);
    }

    /**
    * @Description: 获取当前时间
    * @param: [dateTimeFormatter]
    * @return: java.lang.String
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:32
    */
    public static String getCurrentDate(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }

    public static LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    }

    /**
    * @Description: 获取昨天的时间
    * @param: []
    * @return: java.lang.String
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:35
    */
    public static String getYesterdayDate(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(LocalDate.now().minusDays(1));
    }

    /**
    * @Description: 判断前一个日期字符串时间在后一个之前
    * @param: [beginDateStr, endDateStr, dateTimeFormatter]
    * @return: boolean 之前返回true  ， 之后返回false
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:47
    */
    public static boolean isBefore(String beginDateStr, String endDateStr,DateTimeFormatter dateTimeFormatter){
        LocalDateTime beginDate = getDateTime(beginDateStr, dateTimeFormatter);
        LocalDateTime endDate = getDateTime(endDateStr, dateTimeFormatter);
        return beginDate.isBefore(endDate);
    }


    /**
    * @Description: 日期时间格式字符串转换为其他格式字符串
    * @param: [dateStr, dateTimeFormatterFrom, dateTimeFormatterTo]
    * @return: java.lang.String
    * @author: yupeng.yang
    * @Date: 2019/7/17 10:50
    */
    public static String convertDatetime(String dateTimeStr, DateTimeFormatter dateTimeFormatterFrom,
                                                DateTimeFormatter dateTimeFormatterTo) {
        LocalDateTime dateTime = getDateTime(dateTimeStr, dateTimeFormatterFrom);
        return dateTimeFormatterTo.format(dateTime);
    }

    /**
     * @Description: 日期时间格式字符串转换为其他格式字符串,默认格式yyyyMMddHHmmss
     * @param: [dateStr, dateTimeFormatterFrom, dateTimeFormatterTo]
     * @return: java.lang.String
     * @author: yupeng.yang
     * @Date: 2019/7/17 10:50
     */
    public static String convertDatetime(String dateTimeStr, DateTimeFormatter dateTimeFormatterTo) {
        LocalDateTime dateTime = getDateTime(dateTimeStr, DATE_TIME_SIMPLE_FORMAT);
        return dateTimeFormatterTo.format(dateTime);
    }

    /**
     * @Description: 日期格式字符串转换为其他格式字符串
     * @param: [dateStr, dateTimeFormatterFrom, dateTimeFormatterTo]
     * @return: java.lang.String
     * @author: yupeng.yang
     * @Date: 2019/7/17 10:50
     */
    public static String convertDate(String dateStr, DateTimeFormatter dateTimeFormatterFrom,
                                         DateTimeFormatter dateTimeFormatterTo) {
        LocalDate date = getDate(dateStr, dateTimeFormatterFrom);
        return dateTimeFormatterTo.format(date);
    }

    /**
     * @Description: 日期格式字符串转换为其他格式字符串,默认格式yyyyMMdd
     * @param: [dateStr, dateTimeFormatterFrom, dateTimeFormatterTo]
     * @return: java.lang.String
     * @author: yupeng.yang
     * @Date: 2019/7/17 10:50
     */
    public static String convertDate(String dateStr, DateTimeFormatter dateTimeFormatterTo) {
        LocalDate date = getDate(dateStr, DATE_SIMPLE_FORMAT);
        return dateTimeFormatterTo.format(date);
    }

    /**
     * 时间戳转为16进制+随机数(1-100)返回的字符串
     * @return
     */
    public static String getUniqueCode(){
        return Long.toHexString(System.currentTimeMillis()).toUpperCase() + (int)(Math.random()*100+1);
    }
}
