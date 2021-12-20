<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/admin.css"/>
    <link
            rel="stylesheet"
            href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
            integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"
            crossorigin="anonymous"
    />
    <title>Admin page</title>
</head>

<body>
<main>
    <header id="header">
        <nav id="header__main-nav">
            <div class="header__main-nav--hamburger"></div>
            <ul class="header__main-nav--links">
                <li class=""><a href="#">Home</a></li>
                <li class=""><a href="#">About</a></li>
                <li>
                    <div class="header__user">
                            <span class="header__user-name" data-userId="${user.id}"
                                  data-userLogin="${user.login}">${user.login}</span>
                        <span class="header__arrow">
										<i class="fas fa-arrow-down"></i>
									</span>

                    </div>
                    <div id="idSettings" class="header__settings">
                        <a class="settings__row" href="/logout" style="color: #2c2c2c"> Выйти </a>
                    </div>
                </li>
            </ul>
        </nav>
    </header>

    <section class="main">

        <div class="row">
            <button class="create-button" data-open="addTask">
                Добавить юзера
            </button>
            <span class="founded">Найдено пользователей: ${userPage.content.size()}</span>
        </div>

        <div class="task-card">
            <ul class="table">
                <li class="table-header" style="margin-bottom: 0">
                    <div
                            style="background-color: red; height: 100%"
                            class="col col-1"
                    ></div>
                    <div class="col col-2">Id</div>
                    <div class="col col-3">Имя</div>
                    <div class="col col-4">Фамилия</div>
                    <div class="col col-5">Отчество</div>

                    <div class="col col-6">Дата Рождения</div>

                    <div class="col col-7"></div>
                </li>


                <c:forEach items="${userPage.content}" var="item">
                    <li class="table-row" id="userId${task.id}" data-user="${item.id}">
                        <div
                                style="background-color: indianred; height: 100%"
                                class="col col-1"
                                data-color
                        ></div>
                        <div class="col col-2" data-id>${item.id}</div>
                        <div class="col col-3">${item.firstName}</div>
                        <div class="col col-4">${item.lastName}</div>
                        <div class="col col-5">${item.middleName}</div>
                        <div class="col col-6">${item.dateBirth}</div>
                        <div class="col col-7">
                        <span class="task__span">
                            <i class="fas fa-trash"></i>
                        </span>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>


        <div class="row container" style="justify-content: center">
            <div id="pagination-wrapper">
                <c:if test="${userPage.previousPage}">
                    <a class="page" href="/admin-dashboard?page=${userPage.number}">Previous</a>
                </c:if>
                <c:if test="${userPage.first}">
                    <a class="page" href="/admin-dashboard?page=${userPage.totalPages}">To end</a>
                </c:if>

                <c:forEach begin="0" end="${userPage.totalPages - 1}" var="i">
                    <c:choose>
                        <c:when test="${userPage.number eq i}">
                            <a class="page active">${i+1}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page" href="/admin-dashboard?page=${i+1}">${i+1}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${userPage.nextPage}">
                    <a class="page" href="/admin-dashboard?page=${userPage.number + 2}">Next</a>
                </c:if>
                <c:if test="${userPage.last}">
                    <a class="page" href="/admin-dashboard?page=${1}">To start</a>
                </c:if>
            </div>
        </div>

    </section>
</main>

<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="../../js/admin.js"></script>
</body>
</html>


