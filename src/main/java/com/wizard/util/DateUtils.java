package com.wizard.util;

import com.wizard.model.TimeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils   {

    private static Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static final String SHOWING_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHOWING_DATE_DOT_FORMAT = "yyyy.MM.dd";
    public static final String SHOWING_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String EXCEl_DATE_TIME_FORMAT = "yyyyMMddHHmmssSSSSSS";

    public static final String CHINESE_DATE_TIME = "yyyy年MM月dd日 HH:mm";
    public static final String CHINESE_DATE = "yyyy年MM月dd日";
    public static final String CHINESE_DATE_WITHOUT_DAY = "yyyy年MM月";
    public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_YEAR = "yyyy";
    public static final String DEFAULT_DATE_WITHOUT_DAY = "yyyy-MM";
    public static final String DEFAULT_DATE_WITHOUT_DAY_LINE = "yyyyMM";
    public static final String DEFAULT_TIME = "HH:mm";
    public static final String LOG_DATE_TIME = "yyyyMMddHHmmssSSS";

    public static String formatDate(Date date, String dateFormate) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(dateFormate);
            return formatDate.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatDefaultDate(Date date) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(DEFAULT_DATE_TIME);
            return formatDate.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date addHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }
    public static Date addMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static String addDay(int field, int amount, String format) {
        if (StringUtils.isEmpty(format))
            return "日期格式为空";
        Calendar cal = Calendar.getInstance();
        cal.add(field, amount);
        return formatDate(cal.getTime(), format);
    }

    public static Date getCurrentMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        cal.set(year, month, 1, 0, 0, 0);
        return cal.getTime();
    }

    public static Date getMonthFirstDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        cal.set(year, month, 1, 0, 0, 0);
        return cal.getTime();
    }

    public static Date getMonthLastDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date getMonthLastDayWithEndTime(Date date) {
        if (null == date) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 根据4位年份(eg: 2014)，得到该年第一天开始时间
     * 
     * @Title: getYearFirstDay
     * @Description: 根据4位年份(eg: 2014)，得到该年第一天开始时间
     * @param year
     *            4位年份(eg: 2014)
     * @return
     */
    public static Date getYearFirstDay(String year) {
        if (StringUtils.isEmpty(year) || year.length() != 4) {
            return null;
        }
        String dateStr = year + "-01-01 00:00:00";
        return stringToDate(dateStr, SHOWING_DATE_TIME_FORMAT);
    }

    /**
     * 根据4位年份(eg: 2014)，得到该年最后一天结束时间
     * 
     * @Title: getYearLastDay
     * @Description: 根据4位年份(eg: 2014)，得到该年最后一天结束时间
     * @param year
     *            4位年份(eg: 2014)
     * @return
     */
    public static Date getYearLastDay(String year) {
        if (StringUtils.isEmpty(year) || year.length() != 4) {
            return null;
        }
        String dateStr = year + "-12-31 23:59:59";
        return stringToDate(dateStr, SHOWING_DATE_TIME_FORMAT);
    }

    public static String dateToString(Date date, String format) {
        return formatDateByFormat(date, format);
    }

    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null)
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                log.info((new StringBuilder()).append("date:").append(date).toString());
            }
        return result;
    }

    public static Date getDateWithEndTime(Date date) {
        if (date == null)
            return null;
        String dateStr = dateToString(date, SHOWING_DATE_FORMAT);
        dateStr = dateStr + " 23:59:59";
        return stringToDate(dateStr, SHOWING_DATE_TIME_FORMAT);
    }

    public static Date getDateWithStartTime(Date date) {
        if (date == null)
            return null;
        String dateStr = dateToString(date, SHOWING_DATE_FORMAT);
        dateStr = dateStr + " 00:00:00";

        return stringToDate(dateStr, SHOWING_DATE_TIME_FORMAT);
    }

    public static Date getHourWithEndTime(Date date) {
        if (date == null)
            return null;
        String dateStr = dateToString(date, "yyyy-MM-dd HH");
        dateStr = dateStr + ":59:59";
        return stringToDate(dateStr, SHOWING_DATE_TIME_FORMAT);
    }

    public static Date getHourWithStartTime(Date date) {
        if (date == null)
            return null;
        String dateStr = dateToString(date, "yyyy-MM-dd HH");
        dateStr = dateStr + ":00:00";
        return stringToDate(dateStr, SHOWING_DATE_TIME_FORMAT);
    }

    /**
     * 比较两个日期大小
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static Date stringToDate(String str, String format) {
        if (str != null) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            try {
                return dateFormat.parse(str);
            } catch (ParseException e) {
                // log.info(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public static String newFormat(String time, String fromFormat, String toFormat) {
        Date nowData = DateUtils.stringToDate(time, fromFormat);
        return DateUtils.dateToString(nowData, toFormat);
    }

    public static long dayDiff(Date fromDate, Date toDate) {
        return (toDate.getTime() - fromDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static long minuteDiff(Date fromDate, Date toDate) {
        return (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }

    public static long secondsDiff(Date fromDate, Date toDate) {
        return (toDate.getTime() - fromDate.getTime()) / 1000;
    }

    public static void main(String[] args) {
        /*
         * System.out.println(DateUtils.formateDate(new Date(),
         * DateUtils.SHOWING_DATE_FORMAT));
         * System.out.println(DateUtils.formateDate(DateUtils.addDay(new Date(),
         * 20), DateUtils.EXCEl_DATE_TIME_FORMAT));
         * 
         * System.out.println("month:"+DateUtils.formateDate(DateUtils.addMonth(new
         * Date(), 3), DateUtils.EXCEl_DATE_TIME_FORMAT));
         */
        String str = "2012年09月13日";
        Date date = DateUtils.stringToDate(str, "yyy年mm月dd日");
        System.out.println(date);
    }

    /**
     * 获取日期字符串列表，日期格式: yyyy-MM-dd
     * 
     * @Title: getDateStringList
     * @Description: 获取日期字符串列表，日期格式: yyyy-MM-dd
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return List<String>
     */
    public static List<String> getDateStringList(Date startDate, Date endDate, String format) {
        List<String> dsList = new ArrayList<String>();
        for (int i = 0; DateUtils.compare(DateUtils.addDay(startDate, i), endDate) < 0; i++) {
            dsList.add(formatDateByFormat(addDay(startDate, i), format));
        }
        return dsList;

    }

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1123 format.
     */
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1036 format.
     */
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";

    /**
     * Date format pattern used to parse HTTP date headers in ANSI C
     * <code>asctime()</code> format.
     */
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

    private static final String[] DEFAULT_PATTERNS = new String[] { PATTERN_RFC1036, PATTERN_RFC1123, PATTERN_ASCTIME };

    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;

    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(GMT);
        calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    /**
     * Parses a date value. The formats used for parsing the date value are
     * retrieved from the default http params.
     * 
     * @param dateValue
     *            the date value to parse
     * 
     * @return the parsed date
     * 
     * @throws DateParseException
     *             if the value could not be parsed using any of the supported
     *             date formats
     */

    /**
     * Formats the given date according to the RFC 1123 pattern.
     * 
     * @param date
     *            The date to format.
     * @return An RFC 1123 formatted date string.
     * 
     * @see #PATTERN_RFC1123
     */
    public static String formatDate(Date date) {
        return formatDate(date, PATTERN_RFC1123);
    }

    /** This class should not be instantiated. */
    private DateUtils() {
    }

    /**
     * A factory for {@link SimpleDateFormat}s. The instances are stored in a
     * threadlocal way because SimpleDateFormat is not threadsafe as noted in
     * {@link SimpleDateFormat its javadoc}.
     * 
     */
    final static class DateFormatHolder {

        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() {

            @Override
            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<Map<String, SimpleDateFormat>>(new HashMap<String, SimpleDateFormat>());
            }

        };

        /**
         * creates a {@link SimpleDateFormat} for the requested format string.
         * 
         * @param pattern
         *            a non-<code>null</code> format String according to
         *            {@link SimpleDateFormat}. The format is not checked
         *            against <code>null</code> since all paths go through
         *            {@link DateUtils}.
         * @return the requested format. This simple dateformat should not be
         *         used to {@link SimpleDateFormat#applyPattern(String) apply}
         *         to a different pattern.
         */
        public static SimpleDateFormat formatFor(String pattern) {
            SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
            Map<String, SimpleDateFormat> formats = ref.get();
            if (formats == null) {
                formats = new HashMap<String, SimpleDateFormat>();
                THREADLOCAL_FORMATS.set(new SoftReference<Map<String, SimpleDateFormat>>(formats));
            }

            SimpleDateFormat format = formats.get(pattern);
            if (format == null) {
                format = new SimpleDateFormat(pattern, Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                formats.put(pattern, format);
            }

            return format;
        }

    }

    /**
     * 获取今天0点0分0秒
     * 
     * @return date
     */
    public static Date getCurrentDay() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(SHOWING_DATE_FORMAT);
            return sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            log.error("获取当天时间异常", e);
            return null;
        }

    }

    /**
     * 获取当月首日0点0分0秒
     * 
     * @return date
     */
    public static Date getCurrentMonth() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_WITHOUT_DAY);
            return sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            log.error("获取当月时间异常", e);
            return null;
        }

    }

    public static TimeBean splitTime(String time){

        if(!StringUtils.isEmpty(time)){

            String timeSz[] = time.split(" - ");
            if(timeSz.length==2){
                String begin = timeSz[0].trim();
                String end = timeSz[1].trim();

                return new TimeBean(begin,end);
            }
        }

        return null;
    }

}
