const form = document.querySelector('#register-form');
const usernameInput = document.querySelector('#username');
const emailInput = document.querySelector('#email');
const passwordInput = document.querySelector('#password');
const phoneInput = document.querySelector('#number');
const usertypeInput = document.querySelector('#usertype');
const confirmPasswordInput = document.querySelector('#confirm-password');

form.addEventListener('submit', (event) => {

    validateForm();
    console.log(isFormValid());
    if (isFormValid() === true) {
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


    //USERNAME
    if (usernameInput.value.trim() === '') {
        setError(usernameInput, 'Username cant be empty');
    } else if (usernameInput.value.trim().length < 5 || usernameInput.value.trim().length > 15) {
        setError(usernameInput, 'Username must be min 5 and max 15 characters');
    } else {
        setSuccess(usernameInput);
    }

    //PHONE
    if (phoneInput.value.trim() === '') {
        setError(phoneInput, 'Mobile Phone cant be empty');
    } else if (isMobileValid(phoneInput.value)) {
        setSuccess(phoneInput);
    } else {
        setError(phoneInput, 'Provide valid Phone Number');
    }

    //EMAIL
    if (emailInput.value.trim() === '') {
        setError(emailInput, 'Please Provide an Email Address');
    } else if (isEmailValid(emailInput.value)) {
        setSuccess(emailInput);
    } else {
        setError(emailInput, 'Provide valid email address');
    }

    //Password Checker
    if (passwordInput.value.trim() === '') {
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
    //CONFIRM PASSWORD
    if (confirmPasswordInput.value.trim() === '') {
        setError(confirmPasswordInput, 'Confirm Password can not be empty');
    } else if (confirmPasswordInput.value !== passwordInput.value) {
        setError(confirmPasswordInput, 'Password does not match');
    } else {
        setSuccess(confirmPasswordInput);
    }

    //USERTYPE
    if (usertypeInput.value.trim() === '') {
        setError(usertypeInput, 'The User Type cannot be empty');
    }else {
        setSuccess(usertypeInput);
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

function isEmailValid(email) {
    const reg = /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return reg.test(email);
}

function isMobileValid(phoneInput) {
    const reg = /^\d{10}$/;
    return reg.test(phoneInput);
}



