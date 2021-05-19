package ru.job4j.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String str) throws Exception {
        Map<String, String> months = new HashMap<>();
        months.put("янв", "01");
        months.put("фев", "02");
        months.put("мар", "03");
        months.put("апр", "04");
        months.put("май", "05");
        months.put("июн", "06");
        months.put("июл", "07");
        months.put("авг", "08");
        months.put("сен", "09");
        months.put("окт", "10");
        months.put("ноя", "11");
        months.put("дек", "12");

        int comma = 0, hour, mins;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MM yy, HH:mm");
        LocalDateTime dayNow = LocalDateTime.now();

        if (Character.isDigit(str.charAt(0))) {
            String mon = str.substring(str.indexOf(" ") + 1, str.indexOf(" ", str.indexOf(" ") + 1));
            return LocalDateTime.parse(str.replaceAll(mon, months.get(mon)), formatter);
        } else {
            switch (str.charAt(0)) {
                case ('с'):
                    comma = str.indexOf(',');
                    hour = Integer.parseInt(str.substring(comma + 2, comma + 4));
                    mins = Integer.parseInt(str.substring(comma + 5, comma + 7));
                    System.out.println(hour + " " + mins);
                    return LocalDateTime.of(dayNow.getYear(), dayNow.getMonth(), dayNow.getDayOfMonth(), hour, mins);
                case ('в'):
                    comma = str.indexOf(',');
                    hour = Integer.parseInt(str.substring(comma + 2, comma + 4));
                    mins = Integer.parseInt(str.substring(comma + 5, comma + 7));
                    return LocalDateTime.of(dayNow.getYear(), dayNow.getMonth(), dayNow.getDayOfMonth() - 1, hour, mins);
                default:
                    throw new Exception("wrong format of date");
            }
        }
    }
}
