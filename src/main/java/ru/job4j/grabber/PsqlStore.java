package ru.job4j.grabber;

import ru.job4j.model.Post;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.InputStream;
import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Properties cfg;
    private Connection cnn;

    public PsqlStore() {
        try (InputStream in = PsqlStore.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            cfg = new Properties();
            cfg.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        try {
            Class.forName(cfg.getProperty("driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        try {
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement preparedStatement = cnn.prepareStatement(
                "insert into post.post(name, text, link, created) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getText());
            preparedStatement.setString(3, post.getLink());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            if (preparedStatement.execute()) {
                System.out.println("insert not done " + post.getLink());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = cnn.prepareStatement(
                "select * from post.post")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(new Post(
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post post = null;
        try (PreparedStatement preparedStatement = cnn.prepareStatement(
                "select * from post.post where post.id = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        PsqlStore psqlS = new PsqlStore();
        Post post1 = new Post(
                "Вакансия Full stack JavaScript (NodeJS and ReactJS), полная занятость, 1800-4500$",
                "Разработчики должны иметь подтвержденный опыт разработки приложений с упором на реализацию пользовательских интерфейсов с ReactJS с использованием Redux;",
                "https://www.sql.ru/forum/1335833/vakansiya-full-stack-javascript-nodejs-and-reactjs-polnaya-zanyatost-1800-4500",
                new SqlRuDateTimeParser().parse("3 май 21, 14:09")
        );

        Post post2 = new Post(
                "Вакансия Senior / Middle Node JS Developer, полная занятость, 1800-4500$",
                "- Знание HTML, CSS, JS;\n" +
                        "- Работа с фреймворком React;\n" +
                        "- Написание UnitTests, IntegrationTests;",
                "https://www.sql.ru/forum/1335834/vakansiya-senior-middle-node-js-developer-polnaya-zanyatost-1800-4500",
                new SqlRuDateTimeParser().parse("3 май 21, 14:10")
        );
        psqlS.save(post1);
        System.out.println("--");
        psqlS.save(post2);
        System.out.println(psqlS.findById("18"));
        System.out.println("================================");
        System.out.println(psqlS.getAll());
        try {
            psqlS.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
