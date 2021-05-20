package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.model.Post;

import java.io.IOException;
import java.util.*;

public class SqlRuParse implements Parse {
    private String linkPost;

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new ArrayList<>();
        Document doc;
        doc = Jsoup.connect(String.valueOf(link)).get();
        Elements rec = doc.select(".postslisttopic");

        for (Element tr : rec) {
            Element href = tr.child(0);
            linkPost = href.attr("href");
            posts.add(detail(linkPost));
        }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements msg = doc.getElementsByClass("msgBody");
        Elements dateMsg = doc.getElementsByClass("msgFooter");
        String plot = "";
        String datePost = "";

        int count = 0;
        for (Element td : msg) {
            if (count == 1) {
                plot = td.text();
                break;
            }
            count++;
        }

        for (Element td : dateMsg) {
            String[] footerMsg = td.text().split(" \\[");
            datePost = footerMsg[0];
            break;
        }
        return new Post(datePost, link,  plot);
    }

    public Integer getMaxPage(String link) throws IOException {
        Document doc;
        doc = Jsoup.connect(String.valueOf(link)).get();
        Elements recs = doc.select(".sort_options");    //class[2]
        Element pages = recs.last();                            //style on from 2
        Element href = pages.child(0);
        String[] nums = href.text().split(" Показываются темы");
        String[] lastNum = nums[0].split("\\.\\. ");
        return Integer.parseInt(lastNum[1]);
    }

    public Map<Integer, List<Post>> parsePages(String link, int numPage) throws IOException {
        Map<Integer, List<Post>> book = new HashMap<>();
        String etalonLink = link;
        SqlRuParse srp1 = new SqlRuParse();
        for (int i = 1; i <= numPage; i++) {
            link = String.format("%s%s%s", etalonLink, "/", i);
            book.put(i, list(link));
        }
        return book;
   }

    public static void main(String[] args) throws IOException {
        Map<Integer, List<Post>> book = new HashMap<>();
        SqlRuParse srp      = new SqlRuParse();
        String link         = "https://www.sql.ru/forum/job-offers";
        int numPages        = 5;
        int allPages        = srp.getMaxPage(link);
        //System.out.println(allPages);             //количество страниц на форуме
        book = srp.parsePages(link, numPages);           //тестовые первые пять страниц форума
        //book = srp.parsePages(link, allPages);    //Полный парсинг
        System.out.println(book.size());
    }
}