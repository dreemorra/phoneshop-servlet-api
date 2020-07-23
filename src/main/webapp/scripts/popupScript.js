const popups = document.querySelectorAll(".popup")

for (const popup of popups) {
    popup.addEventListener('click', popupHandler)
}

let prevPopup;

function popupHandler(e) {
    const popup = e.currentTarget.querySelector('span')
    popup.classList.toggle('show')
    if (prevPopup !== undefined && prevPopup !== popup) {
        if (prevPopup.classList.contains('show')) {
            prevPopup.classList.remove('show')
        }
    }
    prevPopup = popup
}