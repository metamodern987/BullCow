package com.igormeta.controllerServlet;

import com.igormeta.model.Game;
import com.igormeta.model.H2Database;
import com.igormeta.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Класс выступает диспетчером запросов.
 * Переадресует запросы на нужные страницы.
 * Created by igor on 30.06.2020
 */

public class DispatcherServlet extends HttpServlet {

    private static String INDEX = "/index.jsp";                           //основная страница
    private static String REGISTRATION = "/WEB-INF/jsp/registration.jsp"; //страница регистрации юзера
    private static String LOGIN = "/WEB-INF/jsp/login.jsp";              //страница для авторизации
    private static String GAME = "/WEB-INF/jsp/game.jsp";                //основная игровая страница
    private static String ERROR = "/WEB-INF/jsp/error.jsp";               // упс - ошибка

    private H2Database db = new H2Database();                           //объект для взаимодействий с базой

    /*
    Метод для создания сервлета на основе дескриптора web.xml
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        ServletContext servletContext = getServletContext();
        db.init(servletContext);
        servletContext.setAttribute("beanDb", db);
    }


    /*
    Метод для запросов GET.
    @param request - запрос
    @param response - ответ
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleReq(request,response);
    }

    /*
    Метод для запросов POST (от клиента).
    @param request - запрос
    @param response - ответ
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleReq(request,response);
    }

    /*
    Метод для обработки запросов, изначально поступающих от методов doGet, doPost.
    @param request - запрос
    @param response - ответ
     */

    public void handleReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //if ()
        HttpSession httpSession = request.getSession(true); //полуаем текущую сессию юзера
        String action = request.getParameter("action");     //получаем параметры запроса
        String url = INDEX;                            //основная страница по-дефолту

        if (action != null) {
            switch (action) {
                case "show-login":
                    url = LOGIN;
                    break;
                case "show-index":
                    url = INDEX;
                    break;
                case "show-registration":
                    url = REGISTRATION;
                    break;

                case "login":            //авторизация
                    String userName = request.getParameter("username");
                    String userPassword = request.getParameter("userpassword");
                    User loginDbUser = db.login(userName,userPassword);
                    if (loginDbUser != null) {
                        url = GAME;
                        Game newGame = new Game();
                        newGame.setUser(loginDbUser);
                        httpSession.setAttribute("game", newGame);
                    } else {
                        url = LOGIN;
                        request.setAttribute("loginMessage", "Такой пользователь не найден!");
                    }
                    break;

                case "registration":      //регистрация юзера
                    User registUser = new User();
                    registUser.setFullName(request.getParameter("fullname"));
                    registUser.setUserName(request.getParameter("username"));
                    registUser.setUserPassword(request.getParameter("userpassword"));
                    registUser = db.registration(registUser);
                    if (registUser != null) {
                        url = GAME;
                        Game newGame = new Game();
                        newGame.setUser(registUser);
                        httpSession.setAttribute("game", newGame);
                    } else {
                        url = REGISTRATION;
                        request.setAttribute("registMessage", "Пользователь уже зарегистрирован!");
                    }
                    break;


                case "show-game":
                    url = GAME;
                    break;

                case "show-rating":
                    url = GAME;
                    request.setAttribute("view", "rating");
                    break;

                case "show-rules":
                    url = GAME;
                    request.setAttribute("view", "rules");
                    break;

                case "enter-number":           //проверяем число, введенное текущим пользователем в текущей игре
                    url = GAME;
                    //request.setAttribute();
                    Game newGame = (Game) httpSession.getAttribute("game");
                    if (newGame != null) {
                        if (newGame.checkGuess()) {
                            newGame.startGame();
                        } else {
                            String guessNum = request.getParameter("guess-text");
                            newGame.verifyEnterNum(guessNum);
                            if (newGame.checkGuess()) {
                                User currUser = newGame.getUser();
                                int attemptGame = newGame.getGameMoves().size();
                                db.addCurrGame(currUser,attemptGame);                //сохранили юзера и кол-во попыток (его игра)
                            }
                        }
                    }
                    break;

                case "new-game":
                    url = GAME;
                    Game actGame = (Game) httpSession.getAttribute("game");
                    if (actGame != null) {
                        actGame.startGame();
                    }
                    break;

                case "logout":
                    url = INDEX;
                    httpSession.invalidate();
                    break;
                default:
                    url = ERROR;

                  //break;
            }
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
