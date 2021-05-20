package ru.job4j.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


public class PostParseByLink {
    public static void parseRec() throws IOException {
        String link =
                "https://www.sql.ru/forum/1336096/clickhouse-specialist-altinity-com-udalenka-ot-3-000-do-5-000-usd";
        Document doc = Jsoup.connect(link).get();
        Elements msg = doc.getElementsByClass("msgBody");
        Elements dateMsg = doc.getElementsByClass("msgFooter");

        Element tr  = msg.get(1);
        System.out.println(tr.text());

        Element td = dateMsg.get(0);
        String[] footerMsg = td.text().split(" \\[");
        System.out.println(footerMsg[0]);
    }
    public static void main(String[] args) throws IOException {
        parseRec();
    }
}