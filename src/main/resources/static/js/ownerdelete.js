window.onload = function () {
    let owner_pwd = document.querySelector('#owner_pwd');
    let owner_repwd = document.querySelector('#owner_repwd');
    let owner_name = document.querySelector('#owner_name');
    let email = document.querySelector('#email');
    let owner_tel = document.querySelector('#owner_tel');
    let business_num = document.querySelector('#business_num');

    let pwdPattern = /^.{4,}$/;
    let namePattern = /^[가-힣]{2,}$/;
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    let telPattern = /^\d{3}-\d{3,4}-\d{4}$/;
    let businessNumPattern = /^\d{3}-\d{2}-\d{5}$/;

    owner_pwd.addEventListener("focusout", checkPwd);
    owner_repwd.addEventListener("focusout", checkPwdMatch);
    owner_name.addEventListener("focusout", checkName);
    email.addEventListener("focusout", checkEmail);
    owner_tel.addEventListener("focusout", checkTel);
    business_num.addEventListener("focusout", checkBusinessNum);

    const form = document.querySelector('form');
    form.addEventListener('submit', function (event) {
        if (!isFormValid()) {
            event.preventDefault(); // 폼 제출을 막음
        }
    });

    function isFormValid() {
        let isValid = true;

        if (owner_pwd.value === "" || !pwdPattern.test(owner_pwd.value)) {
            setErrorStyle('owner_pwd', '비밀번호를 입력하세요.');
            isValid = false;
        } else {
            resetErrorStyle('owner_pwd');
        }

        if (owner_repwd.value !== owner_pwd.value || owner_repwd.value === "") {
            setErrorStyle('owner_repwd', '비밀번호가 일치하지 않습니다.');
            isValid = false;
        } else {
            resetErrorStyle('owner_repwd');
        }

        if (owner_name.value === "" || !namePattern.test(owner_name.value)) {
            setErrorStyle('owner_name', '이름을 입력하세요.');
            isValid = false;
        } else {
            resetErrorStyle('owner_name');
        }

        if (email.value === "" || !emailPattern.test(email.value)) {
            setErrorStyle('email', '이메일을 입력하세요.');
            isValid = false;
        } else {
            resetErrorStyle('email');
        }

        if (owner_tel.value === "" || !telPattern.test(owner_tel.value)) {
            setErrorStyle('owner_tel', '전화번호를 입력하세요.');
            isValid = false;
        } else {
            resetErrorStyle('owner_tel');
        }

        if (business_num.value === "" || !businessNumPattern.test(business_num.value)) {
            setErrorStyle('business_num', '사업자 등록번호를 입력하세요.');
            isValid = false;
        } else {
            resetErrorStyle('business_num');
        }

        return isValid;
    }

    function checkPwd() {
        if (owner_pwd.value === "") {
            setErrorStyle('owner_pwd', '비밀번호를 입력하세요.');
        } else if (!pwdPattern.test(owner_pwd.value)) {
            setErrorStyle('owner_pwd', '비밀번호가 유효하지 않습니다.');
        } else {
            resetErrorStyle('owner_pwd');
        }
    }

    function checkPwdMatch() {
        if (owner_repwd.value === owner_pwd.value && owner_repwd.value !== "") {
            resetErrorStyle('owner_repwd');
        } else if (owner_repwd.value !== owner_pwd.value) {
            setErrorStyle('owner_repwd', '비밀번호가 일치하지 않습니다.');
        }

        if (owner_repwd.value === "") {
            setErrorStyle('owner_repwd', '비밀번호를 다시 입력하세요.');
        }
    }

    function checkName() {
        if (owner_name.value === "") {
            setErrorStyle('owner_name', '이름을 입력하세요.');
        } else if (!namePattern.test(owner_name.value)) {
            setErrorStyle('owner_name', '이름이 유효하지 않습니다.');
        } else {
            resetErrorStyle('owner_name');
        }
    }

    function checkEmail() {
        if (email.value === "") {
            setErrorStyle('email', '이메일을 입력하세요.');
        } else if (!emailPattern.test(email.value)) {
            setErrorStyle('email', '이메일이 유효하지 않습니다.');
        } else {
            resetErrorStyle('email');
        }
    }

    function checkTel() {
        if (owner_tel.value === "") {
            setErrorStyle('owner_tel', '전화번호를 입력하세요.');
        } else if (!telPattern.test(owner_tel.value)) {
            setErrorStyle('owner_tel', '전화번호가 유효하지 않습니다.');
        } else {
            resetErrorStyle('owner_tel');
        }
    }

    function checkBusinessNum() {
        if (business_num.value === "") {
            setErrorStyle('business_num', '사업자 등록번호를 입력하세요.');
        } else if (!businessNumPattern.test(business_num.value)) {
            setErrorStyle('business_num', '사업자 등록번호가 유효하지 않습니다.');
        } else {
            resetErrorStyle('business_num');
        }
    }

    function setErrorStyle(elementId, errorMessage) {
        const element = document.getElementById(elementId);
        element.classList.add('error-input');

        const messageElement = document.getElementById(elementId + 'Message');
        messageElement.style.color = 'red';

        messageElement.textContent = errorMessage;
    }

    function resetErrorStyle(elementId) {
        const element = document.getElementById(elementId);
        element.classList.remove('error-input');

        const messageElement = document.getElementById(elementId + 'Message');
        messageElement.style.color = 'black';
        messageElement.textContent = ''; 
    }
}
