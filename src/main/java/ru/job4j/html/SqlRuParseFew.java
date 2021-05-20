package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParseFew {

    public static void parsePage(String link, String conditionRecs, String conditionDates) {
        Document doc;
        try {
            doc = Jsoup.connect(String.valueOf(link)).get();
            List<String> altCol = new ArrayList<>();
            Elements Recs = doc.select(conditionRecs);
            Elements Dates = doc.select(conditionDates);
            int count = 0;
            for (Element td : Dates) {
                if (count % 2 != 0) {
                    altCol.add(td.text());
                }
                count++;
            }
            count = 0;

            for (Element tr : Recs) {
                Element href = tr.child(0);
                String date = altCol.get(count++);
//                SqlRuDateTimeParser srd = new SqlRuDateTimeParser();
//                System.out.println(srd.parse(date));
                System.out.println(date);
                System.out.println(href.text());
                System.out.println(href.attr("href"));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parsePages(String link, int numPage, String conditionRecs, String conditionDates) {
        int size = link.length();
        for (int i = 1; i <= numPage; i++) {
            link = link.replaceAll(link.substring(size - 1), "s");
            link = link + "/" + i;
            parsePage(link, conditionRecs, conditionDates);
        }
    }

    public static void main(String[] args) {
        String link             = new String("https://www.sql.ru/forum/job-offers");
        String conditionRecs    = ".postslisttopic";
        String conditionDates   = ".altCol";
        int numPages            = 1;
        parsePages(link, numPages, conditionRecs, conditionDates);
    }
}
