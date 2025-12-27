package com.cf.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author WesChen
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取时间段内的所有日期
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static List<Date> getEachDateInRange(Date fromDate, Date toDate) {
        List<Date> eachDates = new ArrayList<>();
        Date itrDate = fromDate;
        while (itrDate.compareTo(toDate) <= 0) {
            eachDates.add(itrDate);
            //日期加一天
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(itrDate);
            calendar.add(Calendar.DATE, 1);
            itrDate = calendar.getTime();
        }
        return eachDates;
    }

    /**
     * 获取月份中的所有日期信息
     *
     * @param yearMonth yyyy-MM
     * @return
     */
    public static List<Date> getDatesInMonth(Date yearMonth) {
        List<Date> datesInMonth = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearMonth);
        // 获取当前月份的天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 打印当前日期
        for (int i = 0; i < daysInMonth; i++) {
            datesInMonth.add(calendar.getTime());
            // 将日期增加1天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return datesInMonth;
    }

    /**
     * 获取年份中的所有月份
     *
     * @param year yyyy
     * @return
     */
    public static List<Date> getMonthsInYear(Date year) {
        List<Date> monthsInYear = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(year);
        for (int i = 0; i < 12; i++) {
            monthsInYear.add(calendar.getTime());
            // 将日期增加1月
            calendar.add(Calendar.MONTH, 1);
        }
        return monthsInYear;
    }

    /**
     * 获取对应时区的日期
     *
     * @param ID
     * @param date
     * @param formatPattern
     * @return
     */
    public static Date getTimeZoneDate(String ID, String date, String formatPattern) {
        //中国时区
        TimeZone china = TimeZone.getTimeZone(ID);
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
        sdf.setTimeZone(china);
        Date timeZoneDate = null;
        try {
            timeZoneDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeZoneDate;
    }

    /**
     * 获取两个时间段内的天数
     *
     * @param startDate yyyy-MM-dd
     * @param endDate   yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static Integer daysBetween(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date beginOfDate(Date date) {
        // 创建Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将当前日期时间的时、分、秒、毫秒部分设置为0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取修改后的日期时间
        return calendar.getTime();
    }

    public static Date endOfDate(Date date) {
        // 创建Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将当前日期时间的时、分、秒、毫秒部分设置为0
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        // 获取修改后的日期时间
        return calendar.getTime();
    }

    public static boolean isSameYear(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int year1 = cal.get(Calendar.YEAR);
        cal.setTime(date2);
        int year2 = cal.get(Calendar.YEAR);
        return year1 == year2;
    }

    public static long getBetweenMilliseconds(String startDate, String endDate, String format) {
        try {
            Date from = parseDate(startDate, format);
            Date to = parseDate(endDate, format);
            return Math.abs(to.getTime() - from.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
