package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.util.ArrayList;
import java.util.List;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        List<String> altCol = new ArrayList<>();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements col = doc.select(".altCol");
        int count = 0;
        for (Element td : col) {
            if (count % 2 != 0) {
                altCol.add(td.text());
            }
            count++;
        }
        count = 0;

        for (Element tr : row) {
            Element href = tr.child(0);
            String date = altCol.get(count++);
            SqlRuDateTimeParser srd = new SqlRuDateTimeParser();
            System.out.println(srd.parse(date));
            System.out.println(date);
            System.out.println(href.text());
            System.out.println(href.attr("href"));
            System.out.println();
        }
    }
}