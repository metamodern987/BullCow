<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.igormeta.model.Game" %>
<%@ page import="com.igormeta.model.GameMoves" %>
<%@ page import="com.igormeta.model.User" %>
<%@ page import="com.igormeta.model.RatingUsers" %>
<jsp:useBean id="beanDb" scope="application" class="com.igormeta.model.H2Database"/>
<%
    request.setCharacterEncoding("UTF-8");
    // игра из сессии
    Game game = (Game) session.getAttribute("game");
    String uname = "Игрок";
    if (game != null) {
        User user = game.getUser();
        if (user != null) {
            uname = user.getUserName();
        }
    }
    // вид из запроса
    String view = (String) request.getAttribute("view");
    String title = "Игра";
    if (view != null) {
        if (view.equals("rating")) {
            title = "Рейтинг";
        } else if (view.equals("rules")) {
            title = "Правила";
        }
    }
%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=title%></title>
</head>
<body>
<p align="center">
    <form name="game-form" class="game-form" method="POST" action="controller">
        <div class="game-message">
            <b><%=uname%></b>,привет ,можешь начать игру или ознакомиться c правилами и рейтингом!&nbsp;
            <button type="submit" name="action" value="logout" class="btn-link" formnovalidate>Выйти</button>
        </div>
        <br>
        <div class="tab">
            <button type="submit" name="action" value="show-game" class="tablink" formnovalidate>Игра</button>
            <button type="submit" name="action" value="show-rating" class="tablink" formnovalidate>Рейтинг</button>
            <button type="submit" name="action" value="show-rules" class="tablink" formnovalidate>Правила</button>
        </div>
        <%
            if (view == null && game != null) {
                String msg = "Попробуй отгадать загаданное компьютером число";
                String btnState = "";
                if (game.checkGuess()) {
                    String secret = game.getSecretNum();
                    msg = "Ура, число угадано: " + secret + "!";
                    btnState = "disabled";
                }
        %>
        <div id="game-tab" class="tabcontent">
            <p class="game-message"><%=msg%></p>
            <table class="game-table">
                <tr>
                    <td width="105">
                        <table class="game-moves">
                            <caption class="game-label">Предыдущие попытки</caption>
                            <thead>
                                <tr>
                                    <th>№</th>
                                    <th>Число</th>
                                    <th>Быки</th>
                                    <th>Коровы</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<GameMoves> moves = game.getGameMoves();
                                    for (int i = 0; i < moves.size(); i++) {
                                        String move = String.valueOf(i + 1);
                                        String number = moves.get(i).getEnterNumber();
                                        String bull = moves.get(i).getBull();
                                        String cow = moves.get(i).getCow();
                                %>
                                <tr>
                                    <td><%=move%></td>
                                    <td><%=number%></td>
                                    <td><%=bull%></td>
                                    <td><%=cow%></td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td width="190">
                        <table class="game-controls">
                            <caption class="game-label">Введи число</caption>
                            <tr>
                                <td colspan="7">
                                    <input type="text" id="guess-text" name="guess-text" class="guess-text" maxlength="4"
                                           required onkeydown="keyDown(event)" onkeypress="keyPress(event)">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5"></td>
                            </tr>
                            <tr>
                                <td><button type="button" class="table-button" onclick="btnClick('0')">0</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('1')">1</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('2')">2</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('3')">3</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('4')">4</button></td>
                            </tr>
                            <tr>
                                <td><button type="button" class="table-button" onclick="btnClick('5')">5</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('6')">6</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('7')">7</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('8')">8</button></td>
                                <td><button type="button" class="table-button" onclick="btnClick('9')">9</button></td>
                            </tr>
                            <tr>
                                <td colspan="5">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <button type="submit" name="action" value="enter-number" class="table-button" <%=btnState%>>Угадать</button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <button type="submit" name="action" value="new-game" class="table-button" formnovalidate>Новая игра</button>
                                </td>
                            </tr>
                        </table>
                    </td>
                <tr>
            </table>
        </div>

<%
            } else if (view.equals("rating")) {
        %>
        <div id="rating-tab" class="tabcontent">
            <p class="game-message">Рейтинг игроков на основе среднего количества попыток до угадывания числа</p>
            <table class="game-rating">
                <thead>
                <tr>
                    <th width="60">Позиция в рейтинге</th>
                    <th width="165">Логин игрока</th>
                    <th width="100">Количество попыток</th>
                    <th width="100">Всего игр сыграно</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<RatingUsers> rating = beanDb.getRatingUser();
                    for (int i = 0; i < rating.size(); i++) {
                        String place = String.valueOf(i + 1);
                        String name = rating.get(i).getUserName();
                        String avg = String.format("%.1f", rating.get(i).getAverAttempts());
                        String cnt = String.valueOf(rating.get(i).getCountGames());
                %>
                <tr>
                    <td width="60"><%=place%></td>
                    <td width="165"><%=name%></td>
                    <td width="100"><%=avg%></td>
                    <td width="100"><%=cnt%></td>
                </tr>
                <%
                    }
                %>
                </tbody>
                            </table>
                        </div>
                        <%
                            } else if (view.equals("rules")) {
                        %>
                        <div id="rules-tab" class="tabcontent">
                            <div class="form-message">
                                Приложение загадывает четырёхзначное число, цифры которого не повторяются.
                                Задача игрока - угадать загаданное число используя минимум попыток.
                                Игрок предлагает вариант числа, а приложение сообщает сколько цифр угадано точно (бык) и сколько цифр
                                угадано без учёта позиции (корова). В игре есть рейтинг пользователей на основе среднего количества
                                попыток до угадывания числа.<br>
                                Пример последовательности для загаданного числа 4108:<br>

                                3451 - 0Б2К<br>
                                4610 - 1Б2К<br>
                                4107 - 3Б0К<br>
                                4108 - 4Б0К
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </form>
                </div>
                </body>
</html>