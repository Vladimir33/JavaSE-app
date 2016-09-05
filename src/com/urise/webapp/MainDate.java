package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vladimir on 18.07.2016.
 */
public class MainDate {

    public static void main(String[] args) {

        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());
        System.out.println(date.getYear());
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/YYYY");
        System.out.println(sdf.format(date));

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/YYYY");
        System.out.println(formatter.format(ldt));
    }
}
