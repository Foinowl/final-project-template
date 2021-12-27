$(document).ready(function () {
    const lineErrors = Array.from($(".line__input--error"))

    if (lineErrors.length > 0) {
        lineErrors.forEach((ele) => {
            $(ele).on("input", function () {
                const value = $(this).children().first().val()
                const nameInput = $(this).attr("id")
                const validData = $(this).data("valid")
                if (validData <= value.length) {
                    $(this).removeClass("line__input--error")
                    $(this).children().last().text("");
                } else if(nameInput != "Date birth") {
                    $(this).addClass("line__input--error")
                    $(this).children().last().text(nameInput + " must be greater than " + validData);
                } else {
                    $(this).addClass("line__input--error")
                    $(this).children().last().text(nameInput + " the age must be over " + validData);
                }
            })
        })
    }
})