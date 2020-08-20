const inputs = document.querySelectorAll("input")

for (input of inputs) {
    input.addEventListener('input', inputListener)
}

function inputListener(e) {
    const inputId = e.target.id.substring(6)
    const value = e.target.value
    const button = document.querySelector(`#button-${inputId}`)

    let attr = button.getAttribute('formaction')
    attr = attr.substring(0, attr.lastIndexOf('=') + 1) + value
    button.setAttribute('formaction', attr)
}