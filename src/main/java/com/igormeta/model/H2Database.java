package com.igormeta.model;

import com.igormeta.additional.Additional;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**Класс определяет методы, которые работают с
 * базой данных H2.
 *
 * Created by igor on 02.07.2020
 */

public class H2Database {

    private String h2Url = "jdbc:h2:mem:testdb";
    private String h2UserName = "sa";
    private String h2Password = "";


    /*
    Метод инициализации данных для подключения к базе.
    @param h2Url - подключение к базе
    @param hUserName - имя к базе
    @param h2Password - пароль к базе
     */
    public void init(String h2Url, String h2UserName, String h2Password) {
        this.h2Url = h2Url;
        this.h2UserName = h2UserName;
        this.h2Password = h2Password;
    }

    /*
    Метод инициализации подключения к бд через контекст.
    @param h2Url - подключение к базе
     */
    public void init(ServletContext context) {
        h2Url = context.getInitParameter("h2Url");
        h2UserName = context.getInitParameter("h2UserName");
        h2Password = context.getInitParameter("h2Password");
    }

    /*
    Метод авторизации юзера.
    @param userName - имя
    @param password - пароль.
    @return user - имя юзера если совпали имя и пароль, либо null
     */
    public User login(String userName, String password) {

        User user = null;
        String sqlReq = "SELECT * FROM users WHERE user_name = ? AND password = ?";
        //if ()
        try (Connection connection = DriverManager.getConnection(h2Url, h2UserName, h2Password);
             PreparedStatement statement = connection.prepareStatement(sqlReq)) {
            statement.setString(1, userName);
            statement.setString(2, Additional.getHashPass(password));
            try (ResultSet resultSets = statement.executeQuery()) {
                if (resultSets.next()) {
                    user = new User();
                    user.setId(resultSets.getLong("id"));
                    user.setFullName(resultSets.getString("full_name"));
                    user.setUserName(resultSets.getString("user_name"));
                    user.setUserPassword(resultSets.getString("password"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            user = null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }

    /*
    Метод регистрации нового юзера.
    @param user - инстанс класса User
    @return user - юзера, которого записали в базу, либо null, если юзер уже был в базе.
     */
    public User registration(User user) {

        String sqlReq = "INSERT INTO users (full_name, user_name, password) values (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(h2Url, h2UserName, h2Password);
             PreparedStatement statement = connection.prepareStatement(sqlReq, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getUserName());
            statement.setString(3, Additional.getHashPass(user.getUserPassword()));

            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    user.setId(resultSet.getLong(1));

                } catch (SQLException sqlException) {
                    throw sqlException;
                }
            } else {
                user = null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }

    /*
    Метод для подсчета и вывода рейтинга в зависимости от попыток юзера.
    @return rating - текущий рейтинг (возарастание среднего числа попыток)
     */

    public List<RatingUsers> getRatingUser() {
        String sqlReq = "SELECT u.user_name, AVG(g.attempts * 1.0) AS avg_attempts, COUNT(*) AS games_count " +
                          "FROM users u, games g " +
                           "WHERE u.id = g.user_id " +
                            "GROUP BY u.id, u.user_name " +
                              "ORDER BY avg_attempts, u.user_name;";

        List<RatingUsers> ratingUsers = new ArrayList<RatingUsers>();
        //if ()
         try (Connection connection = DriverManager.getConnection(h2Url, h2UserName, h2Password);
         PreparedStatement statement = connection.prepareStatement(sqlReq);
         ResultSet resultSet = statement.executeQuery()) {
             //do
          while (resultSet.next()) {
              RatingUsers ratUser = new RatingUsers();
              ratUser.setUserName(resultSet.getString("user_name"));
              ratUser.setAverAttempts(resultSet.getDouble("avg_attempts"));
              ratUser.setCountGames(resultSet.getInt("games_count"));
              ratingUsers.add(ratUser);

          }
         } catch (SQLException sqlException) {
             ratingUsers = null;
             sqlException.printStackTrace();
         }
         return ratingUsers;
    }

    /*
    Метод добавляет информацию о завершившейся игре в базу, чтобы отображать
    рейтинг (попытки) сыгравшего юзера.
    @param user - текущий юзер
    @param attempts - попытки юзера

     */

    public void addCurrGame(User user, int attempts) {
        String sqlReq = "INSERT INTO games (user_id) values (?, ?);";
        try (Connection connection = DriverManager.getConnection(h2Url, h2UserName, h2Password);
        PreparedStatement statement = connection.prepareStatement(sqlReq)) {
            statement.setLong(1, user.getId());
            statement.setInt(2, attempts);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
