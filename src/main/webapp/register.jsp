<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/signup.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
          integrity="sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <script src="https://kit.fontawesome.com/2bbac3a66c.js" crossorigin="anonymous"></script>
</head>

<body>
<style>
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background-color: rgb(0, 0, 0);
        background-image: url('${pageContext.request.contextPath}/images/pizza.jpg');
        background-size: cover;
        background-repeat: no-repeat;
    }

    /*font-size: 15px;*/
    /*color:red;*/
    /*visibility: hidden;*/
    .s-error {
        color: red;
        position: absolute;
        bottom: 5px;
        margin-top: 50px;
        font-size: 15px;
    }
</style>
<form id="register-form" action="controller" onsubmit="return validateFormAndSubmit()" method="POST">
    <input type="hidden" name="action" value="register">
    <div class="title" style="text-align: center;">
        <h2>Register and Grab A Slice!</h2>
        <h6 style="text-transform: none;">Choose a Strong password, at least 8 characters, one uppercase letter,
            lowercase, digit and special character</h6>
    </div>

    <!-- USERNAME -->
    <div class="input-group">
        <%
            if (session.getAttribute("errorMsg") != null) { %>
        <div class="s-error">Failed to Register User</div>
        <%
                session.removeAttribute("errorMsg");
            }
        %>
        <%
            if (session.getAttribute("umsg") != null) { %>
        <div class="s-error">Username or Email already in Use, please try again</div>
        <%
                session.removeAttribute("umsg");
            }
        %>
        <label for="username">Username</label>
        <input type="text" id="username" placeholder="Username" name="username">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p class="error-message">Error Message</p>
    </div>
    <!-- PHONE -->
    <div class="input-group">
        <%
            if (session.getAttribute("pnmsg") != null) { %>
        <div class="s-error">Phone number format error</div>
        <%
                session.removeAttribute("errorMsg");
            }
        %>
        <label for="number">Phone</label>
        <input type="number" id="number" placeholder="5654667876" name="phoneNumber">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p class="error-message">Error Message</p>
    </div>
    <!-- EMAIL -->
    <div class="input-group">
        <%
            if (session.getAttribute("emsg") != null) { %>
        <div class="s-error">Email format error</div>
        <%
                session.removeAttribute("emsg");
            }
        %>
        <label for="email">Email</label>
        <input type="email" id="email" placeholder="Email" name="email">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p class="error-message">Error Message</p>
    </div>
    <!-- PASSWORD -->
    <div class="input-group">
        <%
            if (session.getAttribute("pwvmsg") != null) { %>
        <div class="s-error">Password inconsistency</div>
        <%
                session.removeAttribute("pwvmsg");
            }
        %>
        <label for="password">Password</label>
        <input type="password" id="password" placeholder="Password" name="password">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p class="error-message">Error Message</p>
    </div>
    <!-- CONFIRM PASSWORD -->
    <div class="input-group">
        <%
            if (session.getAttribute("pwcmsg") != null) { %>
        <div class="s-error">Password inconsistency</div>
        <%
                session.removeAttribute("pwcmsg");
            }
        %>
        <label for="confirm-password">Confirm Password</label>
        <input type="password" id="confirm-password" placeholder="Password" name="passwordConfirmation">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p class="error-message">Error Message</p>
    </div>
    <button type="submit" name="register" class="btn">Register</button>
    <br><br> <br>
    <div class="card-footer">
        <div style="color:#fff;" class="d-flex justify-content-center links">
            Already have an account?<a style="color:#6b1a1a;" href="controller?action=view-login">Log In</a>
        </div>
        <div style="color:#fff;" class="d-flex justify-content-center">
            <a style="color:#6b1a1a;" href="controller?action=home">Return Home</a>
        </div>
    </div>
    <%--    <script src="${pageContext.request.contextPath}/scripts/validation.js" type="text/javascript"></script>--%>
    <script>

        function reloadPage() {
            sessionStorage.removeItem('hideErrors');
            location.reload();
        }

        const form = document.querySelector('#register-form');
        const usernameInput = document.querySelector('#username');
        const emailInput = document.querySelector('#email');
        const passwordInput = document.querySelector('#password');
        const phoneInput = document.querySelector('#number');
        const usertypeInput = document.querySelector('#usertype');
        const confirmPasswordInput = document.querySelector('#confirm-password');

        //s-error
        function hideErrorMessages() {
            const hideErrors = sessionStorage.getItem('hideErrors');
            if (hideErrors === 'true') {
                const sessionAttributes = document.querySelectorAll('.s-error');
                sessionAttributes.forEach(function (errorMessage) {
                    errorMessage.style.display = 'none';
                });
                // sessionStorage.removeItem('hideErrors');
            }
        }

        window.addEventListener('load', hideErrorMessages);

        form.addEventListener('submit', (event) => {
            event.preventDefault();
            validateForm();
        });

        const formSelect = document.querySelector('#register-form');

        // formSelect.addEventListener('submit', (event) => {
        //     sessionStorage.setItem('hideErrors', 'true');
        // });

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
            // hideErrorMessages();

            sessionStorage.setItem('hideErrors', 'false');

            //USERNAME
            if (usernameInput.value.trim() == '') {
                setError(usernameInput, 'Username cant be empty');
            } else if (usernameInput.value.trim().length < 5 || usernameInput.value.trim().length > 15) {
                setError(usernameInput, 'Username must be min 5 and max 15 characters');
            } else {
                setSuccess(usernameInput);
            }

            //PHONE
            if (phoneInput.value.trim() == '') {
                setError(phoneInput, 'Mobile Phone cant be empty');
            } else if (isMobileValid(phoneInput.value)) {
                setSuccess(phoneInput);
            } else {
                setError(phoneInput, 'Provide valid Phone Number');
            }

            //EMAIL
            if (emailInput.value.trim() == '') {
                setError(emailInput, 'Please Provide an Email Address');
            } else if (isEmailValid(emailInput.value)) {
                setSuccess(emailInput);
            } else {
                setError(emailInput, 'Provide valid email address');
            }

            //Password Checker
            if (passwordInput.value.trim() == '') {
                setError(passwordInput, 'Password can not be empty');
            } else {
                const passwordValue = passwordInput.value.trim();

                if (passwordValue.length < 8) {
                    setError(passwordInput, 'Password must be at least 8 characters');
                } else if (!/[a-z]/.test(passwordValue)) {
                    setError(passwordInput, 'Password must include at least one lowercase letter');
                } else if (!/[A-Z]/.test(passwordValue)) {
                    setError(passwordInput, 'Password must include at least one uppercase letter');
                } else if (!/\d/.test(passwordValue)) {
                    setError(passwordInput, 'Password must include at least one digit');
                } else if (!/[@$!%*?&]/.test(passwordValue)) {
                    setError(passwordInput, 'Password must include at least one special character');
                } else {
                    setSuccess(passwordInput);
                }
            }
            //CONFIRM PASSWORD
            if (confirmPasswordInput.value.trim() == '') {
                setError(confirmPasswordInput, 'Confirm Password can not be empty');
            } else if (confirmPasswordInput.value !== passwordInput.value) {
                setError(confirmPasswordInput, 'Password does not match');
            } else {
                setSuccess(confirmPasswordInput);
            }

            //USERTYPE
            // if (usertypeInput.value.trim() == '') {
            //     setError(usertypeInput, 'The User Type cannot be empty');
            // } else {
            //     setSuccess(usertypeInput);
            // }
            if (isFormValid()) {
                form.submit();
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
            const reg = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;

            return reg.test(email);
        }

        function isMobileValid(phoneInput) {
            const reg = /^\d{10}$/;
            return reg.test(phoneInput);
        }


        function reloadPage() {
            setTimeout(function () {
                location.reload();
            }, 5000);
        }


    </script>
</form>
</body>
</html>