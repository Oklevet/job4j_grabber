package ru.job4j.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


public class PostDetail {
    public static void parseRec() throws IOException {
        String link =
                "https://www.sql.ru/forum/1336096/clickhouse-specialist-altinity-com-udalenka-ot-3-000-do-5-000-usd";
        Document doc = Jsoup.connect(link).get();
        Elements msg = doc.getElementsByClass("msgBody");
        Elements dateMsg = doc.getElementsByClass("msgFooter");

        int count = 0;
        for (Element td : msg) {
            if (count == 1) {
                System.out.println(td.text());
                break;
            }
            count++;
        }

        for (Element td : dateMsg) {
            String[] footerMsg = td.text().split(" \\[");
            System.out.println(footerMsg[0]);
            break;
        }
    }

    public static void main(String[] args) throws IOException {
        parseRec();
    }
}