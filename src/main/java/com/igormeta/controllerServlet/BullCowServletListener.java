package com.igormeta.controllerServlet;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**Класс-"слушатель" для обработки событий контекста сервлета.
 *
 * Created by igor on 02.07.2020
 */

public class BullCowServletListener implements ServletContextListener {

    /*
    Метод, инициализирующий web-приложение.
    @param servletContextEvent - событие
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String rootPath = context.getRealPath("/");
        System.setProperty("webroot", rootPath);
        try {
            createDatabase(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    Метод, закрывающий контекст сервлета.
    @param servletContextEvent - событие
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /*
    Метод, создает инстанс БД (H2) и подключается к ней.
    Создаются таблицы.
    @param context - контекст
     */

    private void createDatabase(ServletContext context) throws ClassNotFoundException {
        String jdbcUrl = "jdbc:h2:mem:testdb";
        String jdbcUsername = "sa";
        String jdbcPassword = "";
        Class.forName ("org.h2.Driver");

        try (
                Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        Statement statement = connection.createStatement()) {


            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT(2) "+
                    "(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY, " +
                    "full_name VARCHAR(20) NOT NULL, " +
                    "user_name VARCHAR(20) NOT NULL, " +
                    "password VARCHAR(32) NOT NULL);");


            statement.execute("CREATE TABLE IF NOT EXISTS games (" +
                    "id INT(2) "+
                    "(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY, " +
                    "user_id ID(2) NOT NULL, " +
                    "attempts INT NOT NULL, " +
                    "CONSTRAINT game_user_forgk FOREIGN_KEY (user_id) REFERENCES user(id));");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
