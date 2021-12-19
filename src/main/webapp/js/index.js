const isVisible = "is-visible"

$(document).ready( ()  => {
    const openEls = $("[data-open]")
    const closeEls = $("[data-close]")
    const idUser = $("[data-userId]").data("userid")
    const loginUser = $("[data-userLogin]").data("userlogin")

    const {responseJSON: tableData} = $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/task/all/user/"+idUser,
        async:false,
        dataType: 'json',
        success: function (data) {
            return data
        },
        error: function (error) {
            alert(error)
        }
    })


    let state = {
        'querySet': tableData,
        'page': 1,
        'rows': 5,
        'window': 5,
    }

    buildTable()

    function pagination(querySet, page, rows) {

        const trimStart = state.querySet.length > rows ? (page - 1) * rows : 0
        const trimEnd = trimStart + rows

        const trimmedData = querySet.slice(trimStart, trimEnd)

        const pages = Math.ceil(querySet.length / rows);

        return {
            'querySet': trimmedData,
            'pages': pages,
        }
    }

    function pageButtons(pages) {

        const selector = "#pagination-wrapper"
        $(selector).empty()

        var maxLeft = (state.page - Math.floor(state.window / 2))
        var maxRight = (state.page + Math.floor(state.window / 2))

        if (maxLeft < 1) {
            maxLeft = 1
            maxRight = state.window
        }

        if (maxRight > pages) {
            maxLeft = pages - (state.window - 1)

            if (maxLeft < 1){
                maxLeft = 1
            }
            maxRight = pages
        }

        for (var page = maxLeft; page <= maxRight; page++) {
            $(selector).append(
                `<button data-buttonN=${page} value=${page} class="page">${page}</button>`
            )
        }

        if (state.page !== 1) {
            $(selector).prepend(
                `<button value=${1} class="page">First</button>`
            )
        }

        if (state.page !== pages) {
            $(selector).append(
                `<button value=${pages} class="page">Last</button>`
            )
        }

        $('.page').off("click")
        $('.page').on('click', function() {
            $('.table').empty()

            state.page = Number($(this).val())
            // addActive()

            buildTable()
        })

    }

    function buildTable() {
        var data = pagination(state.querySet, state.page, state.rows)

        var myList = data.querySet

        myList.forEach((el) => generateTemplateTask(el))

        pageButtons(data.pages)
    }

    $(".header__user").click(() => {
        toggleClass("#idSettings", "active")
    })

    openEls.each(function () {
        $(this).click(function () {
            const modalId = $(this).data("open");
            toggleClass("#"+modalId, isVisible)
        })
    })

    closeEls.each(function () {
        $(this).click(function () {
            toggleClass($(this).parents().get(3), isVisible)
            clearEditTask()
        })
    })

    $(document).click(function (e) {
        if (e.target === $(".modal.is-visible")[0]) {
            toggleClass(".modal.is-visible", isVisible)
            clearEditTask()
        }
    })


    $("#createCategory").click(function () {

        const mapCategory = {
            idUser,
        }

        $("#addCategory").find("input").each(function () {
            const name = $(this).data("input")
            mapCategory[name] = $(this).val()
        })
        sendDataCategory(mapCategory)
        toggleClass(".modal.is-visible", isVisible)
    })


    $("#createTask").click(function () {

        const mapTask = {
            idUser,
        }

        $("#addTask").find("input").each(function () {
            const name = $(this).data("input")
            // const value = $(this).attr("type") === 'date' ? Date.parse($(this).val()) : $(this).val()
            const value = $(this).val()
            mapTask[name] = value
        })

        $("#addTask").find("select").each(function () {
            const name = $(this).data("input")
            mapTask[name] = $(this).val()
        })
        sendDataTask(mapTask)
        toggleClass(".modal.is-visible", isVisible)
    })

    $(document).on('click', "[data-task]", function (e) {
        e.stopPropagation()

        const idTask = $(this).data('task')

        let type = null;
        let valueComplete = null
        try {
            type = $(this)
                .children()
                .first()
                .attr("class")
                .split('-')[1]
        } catch (error) {
            type = "complete"
            const idCheckbox = $(this).attr("for")
            if (+$("#"+idCheckbox).val() === 0 ) {
                valueComplete = 1
            } else {
                valueComplete = 0
            }
            $("#"+idCheckbox).val(valueComplete)

        }

        if (type === "trash") {
            deleteTaskById(idTask, state.querySet)
        } else if (type === "edit") {

            updateTaskById(idTask, idUser)
        } else if (type === "complete") {
            updateTaskComplete({id : idTask, idUser, completed: valueComplete})
        }

    })


    $("[data-editBtn]").click(function (e) {
        e.preventDefault();
        e.stopPropagation()
        const mapTask = {
            idUser,
            id: +$(this).attr("data-editBtn")
        }


        $("#editTask").find("input").each(function (index) {
            const name = $(this).data("input")
            const value = $(this).val()
            mapTask[name] = value
        })

        $("#editTask").find("select").each(function (index) {
            const name = $(this).data("input")
            const value = $(this).val()
            mapTask[name] = +value
        })

        const {responseJSON: updateTask} = $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: '/task/update/',
            data: JSON.stringify(mapTask),
            dataType: 'json',
            async: false,
            success: function (data) {
                return data
            },
            error: function (error) {
                console.log("ERRROR:", error)
            }
        });

        toggleClass(".modal.is-visible", isVisible)
        clearEditTask()
        changeRowTask(updateTask)
    })


    const toggleClass = (element, stringClass) => {
        element = $(element)
        if (element.hasClass(stringClass)) {
            element.removeClass(stringClass)
        } else {
            element.addClass(stringClass)
        }
    }

    function deleteTaskById(id, query) {
        const {responseJSON: isDelete} = $.ajax({
            type: "DELETE",
            contentType: "application/json",
            url: '/task/delete/' + id,
            async: false,
            dataType: 'json',
            success: function (data) {
                return data;
            },
            error: function (error) {
                console.log("ERRROR:", error)
                alert(error)
            }
        });

        state.querySet = query.filter(el => el.id !== id)
        $('.table').empty()
        buildTable()
    }

    function sendDataTask(mapTask) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/task/add",
            async: false,
            data: JSON.stringify(mapTask),
            dataType: 'json',
            success: function (data) {
                state.querySet.push(data)
                $('.table').empty()
                buildTable()
            },
            error: function (error) {
                alert(error)
            }
        });
    }

    $("#searchCategory").submit(function (e){
        e.preventDefault()
        const text = $($(this).children().get(0)).val()
        const obj = {
            text,
            loginUser
        }
        const {responseJSON: date} = $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/category/search",
            async: false,
            data: JSON.stringify(obj),
            dataType: 'json',
            success: function (data) {
                return data;
            },
            error: function (error) {
                alert(error)
            }
        });
        $('#listCategory').empty()
        date.forEach(el => generateTemplateCategory(el))
    })
})

function sendDataCategory(mapCategory) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/category/add",
        data: JSON.stringify(mapCategory),
        dataType: 'json',
        success: function (data) {
            generateTemplateCategory(data)
        },
        error: function (error) {
            alert(error)
        }
    });
}

function generateTemplateCategory(categoryDto) {
    const Item = ({title, completedCount, uncompletedCount}) => `
        <a class="nav-link">
            <span class="category-title">${title}</span>

            <div style="display: inline-block;">
                <span class="completed-count">${completedCount}</span>
                <span class="uncompleted-count">${uncompletedCount}</span>
            </div>
        </a>
`;

    $('#listCategory').append(
        Item(categoryDto)
    )
}

function generateTemplateTask(taskDto) {
    console.log(taskDto)
    const Item = (task) => `
                        <li class="table-row" id="taskId${task.id}" data-task="${task.id}">
                            <div
                                    style="background-color: ${task.color}; height: 100%"
                                    class="col col-1"
                                    data-color
                            ></div>
                            <div class="col col-2" data-id>${task.id}</div>
                            <div class="col col-3" data-title>${task.title}</div>
                            <div class="col col-4" data-date>${task.date}</div>
                            <div class="col col-5" data-titlePriority>${task.titlePriority}</div>
                            <div class="col col-6" data-titleCategory>${task.titleCategory}</div>
                            <div class="col col-7">
                                        <span class="task__span" data-task="${task.id}">
                                            <i class="fas fa-trash"></i>
                                        </span>
                            </div>
                            <div class="col col-8">
                                        <span class="task__span" data-task="${task.id}">
                                            <i class="fas fa-edit"></i>
                                        </span>
                            </div>
                            <div class="col col-9">
                                <input
                                        type="checkbox"
                                        class="custom-checkbox"
                                        id="complete${task.id}"
                                        name="complete"
                                        value=${task.completed}
                                        ${task.completed === 1 ? 'checked' : ''}
                                />

                                <label for="complete${task.id}" data-task="${task.id}"></label>
                            </div>
                        </li>
                                           
`;

    $('.table').append(
        Item(taskDto)
    )
}

function updateTaskById(idTask, idUser) {

    const {responseJSON: categoryList} = $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/category/all/user/' + idUser,
        async: false,
        dataType: 'json',
        success: function (data) {
            return data
        },
        error: function (error) {
            console.log("ERRROR:", error)
        }
    });

    const {responseJSON: priorityList} = $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/priority/all/',
        dataType: 'json',
        async: false,
        success: function (data) {
            return data
        },
        error: function (error) {
            console.log("ERRROR:", error)
        }
    });


    const {responseJSON: task} = $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/task/id/' + idTask,
        dataType: 'json',
        async: false,
        success: function (data) {
            return data
        },
        error: function (error) {
            console.log("ERRROR:", error)
        }
    });

    renderModalEditTask(categoryList, priorityList, task)
}


function renderModalEditTask(categoryList, priorityList, task) {

    $("#editTask").addClass(isVisible)

    $("#editTask").find("select").each(function (index) {

        let optionString = '';
        let data = null;
        let inputTitle = null;

        let ele = null;
        if ($(this).data("input") === 'idCategory') {
            data = categoryList;
            ele = $('*[data-input="idCategory"]')
            inputTitle = task.titleCategory
        } else {
            data = priorityList;
            ele = $('*[data-input="idPriority"]')
            inputTitle = task.titlePriority
        }

        data.forEach(function (item, index) {
            optionString += item.title === inputTitle ?
                '<option value="' + item.id + '" selected="true">' + item.title + '</option>'
                : '<option value="' + item.id + '">' + item.title + '</option>'
            ;
        });

        ele.append(optionString);
    })

    $($("#editTask").find("input").get(0)).val(task.title)
    $($("#editTask").find("input").get(1)).val(task.date)


    $("[id='editTaskBtn']").each(function () {
        $(this).attr("id", "editTaskBtn" + task.id);
    });

    $("[data-editBtn]").attr("data-editBtn", task.id)

}

function clearEditTask() {
    $("#editTask").find("select").each(function (index) {
        $(this).empty()
    })

    $("[data-editBtn]").attr("id", "editTaskBtn")
    $("[data-editBtn]").attr("data-editBtn", "")
}

function changeRowTask(updateTask) {
    const listElement = ["id", "title", "date", "titlePriority", "titleCategory"]

    $("#taskId" + updateTask.id)
        .find("[data-color]")
        .css({"background-color": `${updateTask.color}`})


    listElement.forEach((el) =>
        $("#taskId" + updateTask.id).find(`[data-${el}]`).text(updateTask[el])
    )
}

function  updateTaskComplete({...mapTask}) {
    console.log(JSON.stringify(mapTask))
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: '/task/update/',
        dataType: 'json',
        data: JSON.stringify(mapTask),
        async: false,
        success: function (data) {
            return data
        },
        error: function (error) {
            console.log("ERRROR:", error)
        }
    });
}