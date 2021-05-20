package ru.job4j.grabber;

import ru.job4j.model.Post;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection ccn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver"));
            this.ccn = DriverManager.getConnection(
                cfg.getProperty("url"),
                cfg.getProperty("username"),
                cfg.getProperty("password")
        );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement preparedStatement = this.ccn.prepareStatement(
                "insert into post.post(name, text, link, created) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getText());
            preparedStatement.setString(3, post.getLink());
            preparedStatement.setString(4, post.getCreated());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.ccn.prepareStatement(
                "select * from post.post")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getString("created")
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
        try (PreparedStatement preparedStatement = this.ccn.prepareStatement(
                "select * from post.post where id = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getString("created")
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
        if (this.ccn != null) {
            this.ccn.close();
        }
    }

    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("src/main/resources/app.properties")) {
            Properties config = new Properties();
            config.load(in);
            PsqlStore psqlS = new PsqlStore(config);

            Post post1 = new Post(
                    "Вакансия Full stack JavaScript (NodeJS and ReactJS), полная занятость, 1800-4500$",
                    "Разработчики должны иметь подтвержденный опыт разработки приложений с упором на реализацию пользовательских интерфейсов с ReactJS с использованием Redux;",
                    "https://www.sql.ru/forum/1335833/vakansiya-full-stack-javascript-nodejs-and-reactjs-polnaya-zanyatost-1800-4500",
                    "3 май 21, 14:09"
            );

            Post post2 = new Post(
                "Вакансия Senior / Middle Node JS Developer, полная занятость, 1800-4500$",
                "- Знание HTML, CSS, JS;\n" +
                        "- Работа с фреймворком React;\n" +
                        "- Написание UnitTests, IntegrationTests;\n",
                "https://www.sql.ru/forum/1335834/vakansiya-senior-middle-node-js-developer-polnaya-zanyatost-1800-4500",
                "3 май 21, 14:10"
            );

            psqlS.save(post1);
            psqlS.save(post2);
            psqlS.findById("1");
            psqlS.getAll();
            psqlS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
