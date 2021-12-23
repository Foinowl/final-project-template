<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/pagination.css"/>
    <link
            rel="stylesheet"
            href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
            integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"
            crossorigin="anonymous"
    />

    <title>To Do</title>
</head>
<body>
<div class="main-container">
    <aside id="side-nav">
        <div class="side-nav__content">
            <div class="side-nav__content--logo">
                <span class="nav-title">Категории</span>
                <button type="button" data-open="addCategory" id="add-icon">
                    close
                </button>
            </div>
            <div class="side-nav__content--wrapper">
                <ul class="side-nav__ul">
                    <form class="search-category-area" id="searchCategory">
                        <input
                                class="input-element"
                                maxlength="256"
                                placeholder="Поиск категорий"
                        />
                    </form>
                    <!-- виртуальная категория 'Все'-->
                    <li class="nav-item category">
                        <a class="nav-link">
                            <span class="category-title" style="width: 85%">Все</span>
                            <div style="display: inline-block;">
                                <span style="background-color: #eaeaea"
                                      id="sizeCategory"
                                      class="completed-count">${categoryList.size()}
                                </span>
                            </div>
                        </a>
                    </li>
                    <div class="line"></div>

                    <c:if test="${categotyList.size() le 0}">
                        <div>
                            <p class="not-found">Ничего не найдено</p>
                        </div>
                    </c:if>

                    <li class="nav-item category" id="listCategory" style="padding-bottom: 65px;">
                        <c:forEach var="category" items="${categoryList}">
                            <a class="nav-link" id="${category.title}">
                                <!--          название категории-->
                                <span class="category-title">${category.title}</span>

                                <span class="edit-category-icon-area"> </span>

                                <div style="display: inline-block;">
                                    <span class="completed-count">${category.completedCount}</span>
                                    <span class="uncompleted-count">${category.uncompletedCount}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </li>
                </ul>
            </div>
        </div>
    </aside>
    <main>
        <header id="header">
            <nav id="header__main-nav">
                <div class="header__main-nav--hamburger"></div>
                <ul class="header__main-nav--links">
                    <li class=""><a href="#">Home</a></li>
                    <li class=""><a href="#">About</a></li>
                    <li>
                        <div class="header__user">
                            <span class="header__user-name"
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
            <div class="common-stat">
                <div class="stat__card">
                    <div class="card-header">
                        <h3 id="completedTotal">${stat.completedTotal}</h3>
                    </div>
                    <div class="line"></div>
                    <div class="footer card-footer">Завершенные задачи</div>
                </div>
                <div class="stat__card">
                    <div class="card-header">
                        <h3 id="uncompletedTotal">${stat.uncompletedTotal}</h3>
                    </div>
                    <div class="line"></div>

                    <div class="footer card-footer">Не завершенные задачи</div>
                </div>
                <div class="stat__card">
                    <div class="card-header">
                        <h3 id="percentCompleted">${Math.round((stat.completedTotal / taskList.size())* 100)}%</h3>
                    </div>
                    <div class="line"></div>

                    <div class="footer card-footer">% завершенных задач</div>
                </div>
                <div class="stat__card">
                    <div class="card-header">
                        <h3 id="percentUncompleted">${Math.round((stat.uncompletedTotal / taskList.size()) * 100)}%</h3>
                    </div>
                    <div class="line"></div>

                    <div class="footer card-footer">% Не завершенные задачи</div>
                </div>
            </div>

            <div class="row">
                <button class="create-button" data-open="addTask">
                    Добавить задачу
                </button>
                <span class="founded" id="foundedTask">Найдено задач: ${taskList.size()}</span>
            </div>


            <div class="task-card">
                <ul class="table">
                    <li class="table-header" style="margin-bottom: 0">
                        <div class="col col-1"></div>
                        <div class="col col-2"></div>
                        <div class="col col-3">Название</div>
                        <div class="col col-4">Срок</div>
                        <div class="col col-5">Приоритет</div>

                        <div class="col col-6">Категория</div>

                        <div class="col col-7"></div>

                        <div class="col col-8"></div>
                        <div class="col col-9"></div>
                    </li>

                    <c:if test="${taskList.size() le 0}">
                        <div>
                            <p class="not-found">Ничего не найдено</p>
                        </div>
                    </c:if>

                    <%--                    <c:forEach var="task" items="${taskList}">--%>
                    <%--                        <li class="table-row" id="taskId${task.id}">--%>
                    <%--                            <div--%>
                    <%--                                    style="background-color: ${task.color}; height: 100%"--%>
                    <%--                                    class="col col-1"--%>
                    <%--                                    data-color--%>
                    <%--                            ></div>--%>
                    <%--                            <div class="col col-2" data-id>${task.id}</div>--%>
                    <%--                            <div class="col col-3" data-title>${task.title}</div>--%>
                    <%--                            <div class="col col-4" data-date>${task.date}</div>--%>
                    <%--                            <div class="col col-5" data-titlePriority>${task.titlePriority}</div>--%>
                    <%--                            <div class="col col-6" data-titleCategory>${task.titleCategory}</div>--%>
                    <%--                            <div class="col col-7">--%>
                    <%--                                        <span class="task__span" data-task="${task.id}">--%>
                    <%--                                            <i class="fas fa-trash"></i>--%>
                    <%--                                        </span>--%>
                    <%--                            </div>--%>
                    <%--                            <div class="col col-8">--%>
                    <%--                                        <span class="task__span" data-task="${task.id}">--%>
                    <%--                                            <i class="fas fa-edit"></i>--%>
                    <%--                                        </span>--%>
                    <%--                            </div>--%>
                    <%--                            <div class="col col-9">--%>
                    <%--                                <input--%>
                    <%--                                        type="checkbox"--%>
                    <%--                                        class="custom-checkbox"--%>
                    <%--                                        id="complete${task.id}"--%>
                    <%--                                        name="complete"--%>
                    <%--                                        <c:out value = "${ task.completed == 1 ? 'checked' : '' }"/>--%>
                    <%--                                        value=${task.completed}--%>
                    <%--                                />--%>

                    <%--                                <label for="complete${task.id}" data-task="${task.id}"></label>--%>
                    <%--                            </div>--%>
                    <%--                        </li>--%>
                    <%--                    </c:forEach>--%>
                </ul>
            </div>

            <div class="row container" style="justify-content: center">
                <div id="pagination-wrapper">

                </div>
            </div>
        </section>
    </main>
</div>

<!-- MODAL -->

<div
        class="modal"
        id="addCategory"
        data-animation="slideInOutLeft"
        data-modal
>
    <div class="modal-dialog" data-="">
        <header class="modal-header">
            <h1>Добавление категории</h1>
        </header>
        <section class="modal-content">
            <div class="modal-row">
                <input
                        data-input="title"
                        class="input-element input-element--modal"
                        maxlength="256"
                        placeholder="Название категории"
                />
            </div>
            <div class="row" id="buttonModal">
                <button class="create-button" id="createCategory">Добавить задачу</button>
                <button
                        class="create-button create-button--outline close-modal"
                        aria-label="close modal"
                        data-close
                >
                    Отмена
                </button>
            </div>
        </section>
    </div>
</div>

<div class="modal" id="addTask" data-animation="slideInOutLeft" data-modal>
    <div class="modal-dialog">
        <header class="modal-header">
            <h1>Добавление задачи</h1>
        </header>
        <section class="modal-content">
            <div class="modal-row">
                <input
                        data-input="title"
                        class="input-element input-element--modal"
                        maxlength="256"
                        placeholder="Название задачи"
                />
            </div>
            <div class="modal-row">
                <select
                        data-input="idCategory"
                        class="task__form-select"
                        id="categoryTask"
                        name="категория"
                        style="width: 100%"
                >
                    <option disabled selected value="">Выберите категорию</option>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.id}">${category.title}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="modal-row">
                <select
                        data-input="idPriority"
                        class="task__form-select"
                        id="prioritetTask"
                        name="приоритет"
                        style="width: 100%"
                >
                    <option disabled selected value="">Выберите приоритет</option>

                    <c:forEach var="prioritet" items="${priorityList}">
                        <option value="${prioritet.id}">${prioritet.title}</option>
                    </c:forEach>
                </select>

            </div>

            <div class="modal-row">
                <input
                        data-input="date"
                        class="input-element input-element--modal"
                        type="date"
                        id="date"
                        required
                />
            </div>
            <div class="row">
                <button class="create-button" id="createTask">Добавить задачу</button>
                <button
                        class="create-button create-button--outline close-modal"
                        aria-label="close modal"
                        data-close
                >
                    Отмена
                </button>
            </div>
        </section>
    </div>
</div>


<div
        class="modal"
        id="editTask"
        data-animation="slideInOutLeft"
        data-modal
>
    <div class="modal-dialog" data-="">
        <header class="modal-header">
            <h1>Редактирование задачи</h1>
        </header>
        <section class="modal-content">
            <div class="modal-row">
                <input
                        data-input="title"
                        class="input-element input-element--modal"
                        maxlength="256"
                        placeholder="Название задачи"
                />
            </div>
            <div class="modal-row">
                <select
                        data-input="idCategory"
                        class="task__form-select"
                        name="категория"
                        style="width: 100%"
                >
                    <option disabled selected value="">Выберите категорию</option>
                    <%--                    <c:forEach var="category" items="${categoryList}">--%>
                    <%--                        <option value="${category.id}">${category.title}</option>--%>
                    <%--                    </c:forEach>--%>
                </select>
            </div>
            <div class="modal-row">
                <select
                        data-input="idPriority"
                        class="task__form-select"
                        name="приоритет"
                        style="width: 100%"
                >
                    <option disabled selected value="">Выберите приоритет</option>

                    <%--                    <c:forEach var="prioritet" items="${priorityList}">--%>
                    <%--                        <option value="${prioritet.id}">${prioritet.title}</option>--%>
                    <%--                    </c:forEach>--%>
                </select>

            </div>

            <div class="modal-row">
                <input
                        data-input="date"
                        class="input-element input-element--modal"
                        type="date"
                        required
                />
            </div>
            <div class="row">
                <button class="create-button" id="editTaskBtn" data-editBtn>Добавить задачу</button>
                <button
                        class="create-button create-button--outline close-modal"
                        aria-label="close modal"
                        data-close
                >
                    Отмена
                </button>
            </div>
        </section>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="../../js/index.js"></script>
</body>
</html>
