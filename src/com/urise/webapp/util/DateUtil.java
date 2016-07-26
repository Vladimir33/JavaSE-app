package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by Vladimir on 21.07.2016.
 */
public class DateUtil {
    public static LocalDate of(int year, Month month){
        return LocalDate.of(year, month, 1);
    }
}
