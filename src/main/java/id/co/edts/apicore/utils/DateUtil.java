package id.co.edts.apicore.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {

    public static final String MIDDLEWARE_ISO_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String MIDDLEWARE_ISO_DATE_PATTERN = "yyyy-MM-dd";

    public static final String MIDDLEWARE_TRAN_DATE_PATTERN = "MMdd";
    public static final String MIDDLEWARE_TRAN_TIME_PATTERN = "HHmmss";
    public static final String MIDDLEWARE_TRAN_GMT_PATTERN = "MMddHHmmss";

    public static Date constructDate(String dateString, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat).parse(dateString);
        } catch (ParseException e) {
        }

        return null;
    }

    public static LocalDate constructLocalDate(String dateString, String dateFormat) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));
    }

    public static LocalDateTime constructLocalDateTime(String dateString, String dateFormat) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String constructDateString(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String constructDateString(Date date, String dateFormat, Locale locale) {
        return new SimpleDateFormat(dateFormat, locale).format(date);
    }

    public static String constructDateString(LocalDate date, String dateFormat) {
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String constructDateString(LocalDate date, String dateFormat, Locale locale) {
        return date.format(DateTimeFormatter.ofPattern(dateFormat, locale));
    }

    public static String constructDateString(LocalDateTime date, String dateFormat) {
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String constructDateString(LocalDateTime date, String dateFormat, Locale locale) {
        return date.format(DateTimeFormatter.ofPattern(dateFormat, locale));
    }

    public static String constructDateString(String date, String dateFormatOri, String dateFormatDest) {
        return constructDateString(constructDate(date, dateFormatOri), dateFormatDest);
    }

    public static String constructDateString(String date, String dateFormatOri, String dateFormatDest, Locale locale) {
        return constructDateString(constructDate(date, dateFormatOri), dateFormatDest, locale);
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static int getCurrentMonth() {
        return getMonth(new Date());
    }

    public static int getMonth(Date date) {
        if (date == null) return -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getCurrentYear() {
        return getMonth(new Date());
    }

    public static Date getStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date getEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    public static int getYear(Date date) {
        if (date == null) return -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static boolean isValid(String dateString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean sameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }

    public static boolean beforeDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }

    public static boolean afterDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) > cal2.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }


    public static boolean sameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
            return true;
        }

        return false;
    }

    public static boolean beforeMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
            return true;
        }

        return false;
    }

    public static boolean afterMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
            return true;
        }

        return false;
    }

    public static boolean sameYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            return true;
        }

        return false;
    }

    public static boolean beforeYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
            return true;
        }

        return false;
    }

    public static boolean afterYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
            return true;
        }

        return false;
    }

    public static boolean isAfterDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        var isAfterDay = false;
        var isSameDate = true;
        if (cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH)) {
            isAfterDay = true;
            isSameDate = false;
        }

        if (isSameDate) {
            if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)
                    || cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
                isAfterDay = true;
            }
        }

        return isAfterDay;
    }


    public static boolean isBeforeDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        var isBeforeDay = false;
        var isSameDate = true;
        if (cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH)) {
            isBeforeDay = true;
            isSameDate = false;
        }

        if (isSameDate) {
            if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)
                    || cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
                isBeforeDay = true;
            }
        }

        return isBeforeDay;
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 7);

        Calendar cal2 = Calendar.getInstance();

    }

    public static String constructDateStringIdLang(Date date, Boolean isWithDayName) {
        String[] idLangMonthsName = new String[]{
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
        };

        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (isWithDayName) {
            String[] idLangDaysName = new String[]{
                    "Minggu",
                    "Senin",
                    "Selasa",
                    "Rabu",
                    "Kamis",
                    "Jumat",
                    "Sabtu"
            };

            var test = calendar.get(Calendar.DAY_OF_WEEK);
            sb.append(idLangDaysName[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            sb.append(", ");
        }

        sb.append(calendar.get(Calendar.DATE));
        sb.append(" ");
        sb.append(idLangMonthsName[calendar.get(Calendar.MONTH)]);
        sb.append(" ");
        sb.append(calendar.get(Calendar.YEAR));

        return sb.toString();
    }

    public static String constructDateStringIdLangNoDate(Date date) {
        String[] idLangMonthsName = new String[]{
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
        };

        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        sb.append(idLangMonthsName[calendar.get(Calendar.MONTH)]);
        sb.append(" ");
        sb.append(calendar.get(Calendar.YEAR));

        return sb.toString();
    }

}
