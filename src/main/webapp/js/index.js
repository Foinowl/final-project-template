$(document).ready(function () {
    const openEls = $("[data-open]")
    const closeEls = $("[data-close]")
    const isVisible = "is-visible"

    const idUser = $("[data-user]").data("user")

    $(".header__user").click(() => {
        toggleClass("#idSettings", "active")
    })

    openEls.each(function () {
        $(this).click(function () {
            const modalId = $(this).data("open");
            $('#' + modalId).addClass(isVisible)
        })
    })

    closeEls.each(function () {
        $(this).click(function () {
            $($(this).parents().get(3)).removeClass(isVisible)
        })
    })

    $(document).click(function (e) {
        if (e.target == $(".modal.is-visible")[0]) {
            $(".modal.is-visible").removeClass(isVisible)
        }
    })


    $("#createCategory").click(function (e) {

        const mapCategory = {
            idUser,
        }

        $("#addCategory").find("input").each(function (index) {
            const name = $(this).data("input")
            const value = $(this).val()
            mapCategory[name] = value
        })
        sendDataCategory(mapCategory)
        $(".modal.is-visible").removeClass(isVisible)
    })


    $("#createTask").click(function (e) {

        const mapTask = {
            idUser,
        }

        $("#addTask").find("input").each(function (index) {
            const name = $(this).data("input")
            // const value = $(this).attr("type") === 'date' ? Date.parse($(this).val()) : $(this).val()
            const value = $(this).val()
            mapTask[name] = value
        })

        $("#addTask").find("select").each(function (index) {
            const name = $(this).data("input")
            const value = $(this).val()
            mapTask[name] = value
        })
        sendDataTask(mapTask)
        $(".modal.is-visible").removeClass(isVisible)

    })

    const toggleClass = (element, stringClass) => {
        element = $(element)
        if (element.hasClass(stringClass)) {
            element.removeClass(stringClass)
        } else {
            element.addClass(stringClass)
        }
    }
})


function sendDataTask(mapTask) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/task/add",
        data: JSON.stringify(mapTask),
        dataType: 'json',
        success: function (data) {
            generateTemplateTask(data)
        },
        error: function (error) {
            alert(error)
        }
    });
}


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
    const Item = (task) => `
       <li class="table-row">
                            <div
                                    style="background-color: ${task.color}; height: 100%"
                                    class="col col-1"
                            ></div>
                            <div class="col col-2">${task.id}</div>
                            <div class="col col-3">${task.title}</div>
                            <div class="col col-4">${task.date}</div>
                            <div class="col col-5">${task.titlePriority}</div>
                            <div class="col col-6">${task.titleCategory}</div>
                            <div class="col col-7">
                                        <span class="task__span">
                                            <i class="fas fa-trash"></i>
                                        </span>
                            </div>
                            <div class="col col-8">
                                        <span class="task__span">
                                            <i class="fas fa-edit"></i>
                                        </span>
                            </div>
                            <div class="col col-9">
                                <input
                                        type="checkbox"
                                        class="custom-checkbox"
                                        id="complete${task.id}"
                                        name="complete"
                                        value="yes"
                                />

                                <label for="complete${task.id}"></label>
                            </div>
                        </li>
`;

    $('.table').append(
        Item(taskDto)
    )
}