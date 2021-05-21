package ru.job4j.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MM yy, HH:mm");
    Map<String, String> months = new HashMap<>();
        {
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
        }

    public int getMins(String str) {
        int comma = str.indexOf(',');
        return Integer.parseInt(str.substring(comma + 5, comma + 7));
    }

    public int getHours(String str) {
        int comma = str.indexOf(',');
        return Integer.parseInt(str.substring(comma + 2, comma + 4));
    }

    @Override
    public LocalDateTime parse(String str) throws Exception {
        Character firstSym = str.charAt(0);
        LocalDateTime dayNow = LocalDateTime.now();

        if (Character.isDigit(firstSym)) {
            String mon = str.substring(str.indexOf(" ") + 1, str.indexOf(" ", str.indexOf(" ") + 1));
            return LocalDateTime.parse(str.replaceAll(mon, months.get(mon)), formatter);
        } else {
            switch (firstSym) {
                case ('с'):
                    return LocalDateTime.of(dayNow.getYear(), dayNow.getMonth(), dayNow.getDayOfMonth(),
                            getHours(str), getHours(str));
                case ('в'):
                    return LocalDateTime.of(dayNow.getYear(), dayNow.getMonth(), dayNow.getDayOfMonth() - 1,
                            getHours(str), getMins(str));
                default:
                    throw new Exception("wrong format of d  ate");
            }
        }
    }
}
