window.onload = function () {
    const ownerJoinForm = document.querySelector("form"); // 공급자 회원가입 폼 선택

    ownerJoinForm.addEventListener("submit", function(event) {
        event.preventDefault();

        let business_num = document.getElementById('business_num').value;
        let owner_pwd = document.getElementById('owner_pwd').value;
        let owner_repwd = document.getElementById('owner_repwd').value;
        let owner_name = document.getElementById('owner_name').value;
        let owner_tel = document.getElementById('owner_tel').value;
        let email = document.getElementById('email').value;

        let telReg = /^\d{3}-\d{2,4}-\d{4}$/; // 전화번호 정규식
        let nameReg = /^[가-힣]{2,}$/; // 한글 2글자 이상
        let emailReg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/; // 이메일 주소 정규식
        let businessNumReg = /^\d{3}-\d{2}-\d{5}$/; // 사업자 등록 번호 정규식

        // 필드 유효성 검사
        if (
            business_num === "" ||
            owner_pwd === "" ||
            owner_repwd === "" ||
            owner_name === "" ||
            owner_tel === "" ||
            email === "" ||
            !businessNumReg.test(business_num) || // 사업자 등록 번호 유효성 검사
            owner_pwd !== owner_repwd || // 비밀번호 일치 여부 검사
            !telReg.test(owner_tel) || // 전화번호 유효성 검사
            !nameReg.test(owner_name) || // 이름 유효성 검사
            !emailReg.test(email) // 이메일 주소 유효성 검사
        ) {
            let errorMessage = "";

            if (!businessNumReg.test(business_num)) {
                errorMessage += "사업자 등록 번호를 올바르게 입력하세요.\n";
            }

            if (!nameReg.test(owner_name)) {
                errorMessage += "이름을 올바르게 입력하세요.\n";
            }

            if (!telReg.test(owner_tel)) {
                errorMessage += "전화번호 형식이 올바르지 않습니다.\n";
            }

            if (!emailReg.test(email)) {
                errorMessage += "올바른 이메일 주소를 입력하세요.\n";
            }

            alert(errorMessage);
            return false; // 폼이 제출되지 않고 초기화
        }

        // 유효한 경우, 폼을 서버로 제출
        alert("회원가입을 축하합니다!");
        ownerJoinForm.submit();
    });
}

