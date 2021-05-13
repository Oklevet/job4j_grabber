package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private static int getInterval() {
        String interval = "";
        try (FileReader reader = new FileReader("src/main/resources/rabbit.properties")) {
            Properties properties = new Properties();
            properties.load(reader);
            interval = properties.getProperty("rabbit.interval");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(interval);
    }

    private static Connection connect() {
        Connection connection = null;
        try (FileReader reader = new FileReader("src/main/resources/rabbit.properties")) {
            Properties properties = new Properties();
            properties.load(reader);
            Class.forName(properties.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
//            System.out.println(properties.toString());                                      //debug zone
//            System.out.println(connection.toString());                                      //debug zone
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void insert(Connection cnt, long value) {
        try (PreparedStatement pr = cnt.prepareStatement(
                "insert into rabbit(created_date) values (?)")) {
            pr.setLong(1, value);
            pr.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void selectAll(Connection cnt) {
        try (PreparedStatement pr = cnt.prepareStatement(
                "select * from rabbit")) {
            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("value"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection cn = connect()) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("cn", cn);
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(getInterval())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(cn);
        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) jobExecutionContext.getJobDetail().getJobDataMap().get("cn");
            insert(cn, System.currentTimeMillis());
            selectAll(cn);
        }
    }
}
