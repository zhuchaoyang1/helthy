package com.zcy.cn.util;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * LocalDate Convert to Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateCovertToDate(LocalDate localDate) {
        ZonedDateTime zonedDateBegin = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateBegin.toInstant());
    }

    /**
     * Date Convert to localDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateConvertToLocalDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return LocalDate.of(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Build current week LocalDate
     *
     * @param forms
     * @return
     */
    public static LocalDate[] buildCurrWeekLocalDate(LocalDate forms) {
        LocalDate[] weekLocalDates = new LocalDate[7];
        for (int i = 1; i < 8; i++) {
            weekLocalDates[i - 1] = forms.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(i);
        }
        return weekLocalDates;
    }

    /**
     * Build Seven Date
     *
     * @param localDates
     * @return
     */
    public static Date[] buildCurrentWeekDate(LocalDate[] localDates) {
        Date[] currentWeeksDate = new Date[7];
        for (int i = 0; i < 7; i++) {
            currentWeeksDate[i] = localDateCovertToDate(localDates[i]);
        }
        return currentWeeksDate;
    }

    public static String[] weekString() {
        return new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    }

}
