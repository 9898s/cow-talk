package com.suhwan.cowtalk.common.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtility {

  public static LocalDateTime getStartOfDay() {
    return LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
  }

  public static LocalDateTime getEndOfDay() {
    return getStartOfDay().plusDays(1).minusNanos(1);
  }

  public static LocalDateTime getStartOfWeek(LocalDateTime dateTime) {
    return dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .withHour(0).withMinute(0).withSecond(0);
  }

  public static LocalDateTime getEndOfWeek(LocalDateTime dateTime) {
    return dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        .withHour(23).withMinute(59).withSecond(59);
  }

  public static LocalDateTime getStartOfMonth(LocalDateTime dateTime) {
    return LocalDateTime.of(dateTime.toLocalDate().withDayOfMonth(1), LocalTime.MIN);
  }

  public static LocalDateTime getEndOfMonth(LocalDateTime dateTime) {
    return LocalDateTime.of(dateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()),
        LocalTime.MAX);
  }
}
