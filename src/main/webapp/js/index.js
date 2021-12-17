// const app = () => {
// 	// Suggestentions users menu
// 	let selectorProfileMenu
// 	let selectorProfileOptions
//
// 	// modal
// 	let openEls
// 	let closeEls
// 	let isVisible
//
// 	const init = () => {
// 		selectorProfileMenu = document.querySelector(".header__user")
// 		selectorProfileOptions = document.querySelector(".header__settings")
//
// 		openEls = document.querySelectorAll("[data-open]")
// 		closeEls = document.querySelectorAll("[data-close]")
// 		isVisible = "is-visible"
// 		applyListeners()
// 	}
//
// 	const applyListeners = () => {
// 		selectorProfileMenu.addEventListener("click", () =>
// 			toggleClass(selectorProfileOptions, "active")
// 		)
//
// 		applyModalListeners()
// 	}
//
// 	const toggleClass = (element, stringClass) => {
// 		console.log("log", element)
// 		if (element.classList.contains(stringClass))
// 			element.classList.remove(stringClass)
// 		else element.classList.add(stringClass)
// 		console.log("log", element)
// 	}
//
// 	const applyModalListeners = () => {
// 		for (const el of openEls) {
// 			el.addEventListener("click", function () {
// 				const modalId = this.dataset.open
// 				document.getElementById(modalId).classList.add(isVisible)
// 			})
// 		}
//
// 		for (const el of closeEls) {
// 			el.addEventListener("click", function () {
// 				this.parentElement.parentElement.parentElement.parentElement.classList.remove(
// 					isVisible
// 				)
// 			})
// 		}
//
// 		document.addEventListener("click", (e) => {
// 			if (e.target == document.querySelector(".modal.is-visible")) {
// 				document.querySelector(".modal.is-visible").classList.remove(isVisible)
// 			}
// 		})
//
// 		document.addEventListener("keyup", (e) => {
// 			// if we press the ESC
// 			if (e.key == "Escape" && document.querySelector(".modal.is-visible")) {
// 				document.querySelector(".modal.is-visible").classList.remove(isVisible)
// 			}
// 		})
// 	}
//
// 	init()
// }
//
// app()


$(document).ready(function () {
	const openEls = $("[data-open]")
	const closeEls = $("[data-close]")
	const isVisible = "is-visible"

	$(".header__user").click(() => {
		toggleClass("#idSettings", "active")
	})

	openEls.each(function() {
		$(this).click(function () {
			const modalId = $(this).data("open");
			$('#' + modalId).addClass(isVisible)
		})
	})

	closeEls.each(function() {
		$(this).click(function () {
			$($(this).parents().get(3)).removeClass(isVisible)
		})
	})

	$(document).click(function (e) {
		if (e.target == $(".modal.is-visible")[0]) {
			$(".modal.is-visible").removeClass(isVisible)
		}
	})

	const toggleClass = (element, stringClass) => {
		element = $(element)
		if (element.hasClass(stringClass)) {
			element.removeClass(stringClass)
		}
		else {
			element.addClass(stringClass)
		}
	}
})
