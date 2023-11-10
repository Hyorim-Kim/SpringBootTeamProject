window.onload = function () {
    let business_num = document.querySelector('#business_num');
    let owner_pwd = document.querySelector('#owner_pwd');
    let owner_repwd = document.querySelector('#owner_repwd');
    let owner_name = document.querySelector('#owner_name');
    let owner_tel = document.querySelector('#owner_tel');
    let email = document.querySelector('#email');
    
    let submitBtn = document.querySelector('#btnOwnerJoin');

    business_num.addEventListener("focusout", checkBusinessNum);
    owner_pwd.addEventListener("focusout", checkPwd);
    owner_repwd.addEventListener("focusout", checkPwdMatch);
    owner_name.addEventListener("focusout", checkName);
    owner_tel.addEventListener("focusout", checkTel);
    email.addEventListener("focusout", checkEmail);

    submitBtn.addEventListener("click", function (event) {
        event.preventDefault();
        if (checkAllFields()) {
            document.querySelector('form').submit();
        } else {
                   
        }
    });

    function checkAllFields() {
        let isValid = true;

        if (!checkField(business_num)) isValid = false;
        if (!checkField(owner_pwd)) isValid = false;
        if (!checkField(owner_name)) isValid = false;
        if (!checkField(owner_tel)) isValid = false;
        if (!checkField(email)) isValid = false;


        return isValid;
    }

    function checkField(field) {
        let isValid = true;
        const value = field.value.trim();

        if (value === "") {
            setErrorStyle(field.id);
            isValid = false;
        } else {
            resetErrorStyle(field.id);
        }

        return isValid;
    }


    function checkBusinessNum() {
        let businessNumPattern = /^\d{3}-\d{2}-\d{5}$/;
        if (business_num.value === "") {
            setErrorStyle('business_num');
        } else if (!businessNumPattern.test(business_num.value)) {
            setErrorStyle('business_num');
        } else {
            resetErrorStyle('business_num');
        }
    }

    function checkPwd() {
        let pwdPattern = /^.{4,}$/;
        if (owner_pwd.value === "") {
            setErrorStyle('owner_pwd');
        } else if (!pwdPattern.test(owner_pwd.value)) {
            setErrorStyle('owner_pwd');
        } else {
            resetErrorStyle('owner_pwd');
        }
    }

    function checkPwdMatch() {
        if (owner_repwd.value === owner_pwd.value && owner_repwd.value !== "") {
            resetErrorStyle('owner_repwd');
        } else if (owner_repwd.value !== owner_pwd.value) {
            setErrorStyle('owner_repwd');
        }

        if (owner_repwd.value === "") {
            setErrorStyle('owner_repwd');
        }
    }

    function checkName() {
        let namePattern = /^[가-힣]{2,}$/;
        if (owner_name.value === "") {
            setErrorStyle('owner_name');
        } else if (!namePattern.test(owner_name.value)) {
            setErrorStyle('owner_name');
        } else {
            resetErrorStyle('owner_name');
        }
    }

    function checkTel() {
        let telPattern = /^\d{3}-\d{3,4}-\d{4}$/;
        if (owner_tel.value === "") {
            setErrorStyle('owner_tel');
        } else if (!telPattern.test(owner_tel.value)) {
            setErrorStyle('owner_tel');
        } else {
            resetErrorStyle('owner_tel');
        }
    }

    function checkEmail() {
        let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (email.value === "") {
            setErrorStyle('email');
        } else if (!emailPattern.test(email.value)) {
            setErrorStyle('email');
        } else {
            resetErrorStyle('email');
        }
    }

    function setErrorStyle(elementId) {
        const element = document.getElementById(elementId);
        element.classList.add('error-input');

        const messageElement = document.getElementById(elementId + 'Message');
        messageElement.style.color = 'red';

        messageElement.textContent = '입력한 ' + element.getAttribute('placeholder') + '이(가) 올바르지 않습니다.';
    }

    function resetErrorStyle(elementId) {
        const element = document.getElementById(elementId);
        element.classList.remove('error-input');

        const messageElement = document.getElementById(elementId + 'Message');
        messageElement.style.color = 'black';
        messageElement.textContent = '';
    }
}
