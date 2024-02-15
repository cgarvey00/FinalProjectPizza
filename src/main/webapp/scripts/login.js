const form = document.querySelector('#login-form');
const usernameInput = document.querySelector('#username');
const passwordInput = document.querySelector('#password');
form.addEventListener('submit', (event) => {

    validateForm();
    console.log(isFormValid());
    if (isFormValid() == true) {
        form.submit();
    } else {
        event.preventDefault();
    }

});

function isFormValid() {
    const inputContainers = form.querySelectorAll('.input-group');
    let result = true;
    inputContainers.forEach((container) => {
        if (container.classList.contains('error')) {
            result = false;
        }
    });
    return result;
}

function validateForm() {

    // USERNAME
    if (usernameInput.value.trim() == '') {
        setError(usernameInput, 'Username cant be empty');
    } else if (usernameInput.value.trim().length < 5 || usernameInput.value.trim().length > 15) {
        setError(usernameInput, 'Username must be min 5 and max 15 charecters');
    } else {
        setSuccess(usernameInput);
    }

    //Password Checker which checks the password for many different cases
    if (passwordInput.value.trim() == '') {
        setError(passwordInput, 'Password can not be empty');
    } else {
        const passwordValue = passwordInput.value.trim();

        if (passwordValue.length < 8) {
            setError(passwordInput, 'Password must be at least 8 characters');
        }
        else if (!/[a-z]/.test(passwordValue)) {
            setError(passwordInput, 'Password must include at least one lowercase letter');
        }
        else if (!/[A-Z]/.test(passwordValue)) {
            setError(passwordInput, 'Password must include at least one uppercase letter');
        }
        else if (!/\d/.test(passwordValue)) {
            setError(passwordInput, 'Password must include at least one digit');
        }
        else if (!/[@$!%*?&]/.test(passwordValue)) {
            setError(passwordInput, 'Password must include at least one special character');
        } else {
            setSuccess(passwordInput);
        }
    }

}

function setError(element, errorMessage) {
    const parent = element.parentElement;
    if (parent.classList.contains('success')) {
        parent.classList.remove('success');
    }
    parent.classList.add('error');
    const paragraph = parent.querySelector('p');
    paragraph.textContent = errorMessage;
}

function setSuccess(element) {
    const parent = element.parentElement;
    if (parent.classList.contains('error')) {
        parent.classList.remove('error');
    }
    parent.classList.add('success');
}





