$(document).ready(() => {
    $(".table-row").on("click", ".task__span", function (e) {
        const userId = +$(e.delegateTarget).data("user")
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            url: "/user/delete/"+userId,
            async: false,
            dataType: 'json',
            success: function (data) {
                $(".table").find(`[data-user=${userId}]`).remove()
                alert("delete user by Id - " + userId)
            },
            error: function (error) {
                alert(error)
            }
        })
    })

    $(".header__user").click(() => {
        toggleClass("#idSettings", "active")
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