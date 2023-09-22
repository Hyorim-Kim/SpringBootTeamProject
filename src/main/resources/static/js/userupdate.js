// ***** 회원 가입일 때 각 필드의 유효성 검사 *****
window.onload = function () {
    const userJoinForm = document.querySelector("form"); // 회원가입 폼 선택

    userJoinForm.addEventListener("submit", function(event) {
        event.preventDefault();

        let user_id = document.getElementById('user_id').value;
        let user_pwd = document.getElementById('user_pwd').value;
        let user_repwd = document.getElementById('user_repwd').value;
        let user_name = document.getElementById('user_name').value;
        let user_email = document.getElementById('user_email').value;
        let user_tel = document.getElementById('user_tel').value;
        let user_jumin = document.getElementById('user_jumin').value;

        let telReg = /^\d{3}-\d{3,4}-\d{4}$/;
        let juminReg = /^\d{6}-\d{7}$/;
        let nameReg = /^[가-힣]{2,}$/;
        let pwdReg = /^.{4,}$/;
        let emailReg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        // 모든 필드가 비어있을 때 경고 메시지 표시
        if (
            user_id === "" ||
            user_pwd === "" ||
            user_repwd === "" ||
            user_name === "" ||
            user_email === "" ||
            user_tel === "" ||
            user_jumin === ""
        ) {
            confirm("가입란을 모두 채워주세요.");
            return false;
        }

        // 다른 유효성 검사는 여기에 계속 작성합니다.
        if (
            !telReg.test(user_tel) ||
            !juminReg.test(user_jumin) ||
            !nameReg.test(user_name) ||
            !pwdReg.test(user_pwd) ||
            user_pwd !== user_repwd ||
            !emailReg.test(user_email)
        ) {
            let errorMessage = "";

            if (!nameReg.test(user_name)) {
                errorMessage += "이름을 올바르게 입력하세요.\n";
            }

            if (!telReg.test(user_tel)) {
                errorMessage += "전화번호 형식이 올바르지 않습니다.\n";
            }

            if (!juminReg.test(user_jumin)) {
                errorMessage += "주민번호 형식이 올바르지 않습니다.\n";
            }

            if (!pwdReg.test(user_pwd)) {
                errorMessage += "비밀번호는 4글자 이상이어야 합니다.\n";
            }

            if (user_pwd !== user_repwd) {
                errorMessage += "비밀번호가 일치하지 않습니다.\n";
            }

            if (!emailReg.test(user_email)) {
                errorMessage += "올바른 이메일 주소를 입력하세요.\n";
            }

            confirm(errorMessage);
            return false;
        }
        confirm("정보가 수정되었습니다!");
        // 회원가입 정보가 유효한 경우, 폼을 서버로 제출
        userJoinForm.submit();
    });
}





// **** 주소 등록하기 **** (광진) //
function user_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var roadAddr = data.roadAddress;
            var extraRoadAddr = '';

            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }

            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }

            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            document.getElementById('user_postcode').value = data.zonecode; // 우편번호 필드 설정 주석 처리
            document.getElementById("user_roadAddress").value = roadAddr;
            document.getElementById("user_jibunAddress").value = data.jibunAddress;

            if (roadAddr !== '') {
                document.getElementById("user_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("user_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");

            if (data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';
            } else if (data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }

            // 상세주소를 가져온다.
            var detailAddress = document.getElementById("user_detailAddress").value;

            // 주소를 합쳐서 출력 필드에 설정
            var combinedAddress = roadAddr + ' ' + data.jibunAddress + ' ' + detailAddress;
            document.getElementById("user_combinedAddress").value = combinedAddress;
        }
    }).open();
    
    // 상세주소 필드 값이 변경될 때마다 주소를 재결합하여 출력 필드에 설정
	document.getElementById("user_detailAddress").addEventListener("input", function() {
    var roadAddr = document.getElementById("user_roadAddress").value || ''; // 도로명 주소
    var jibunAddr = document.getElementById("user_jibunAddress").value || ''; // 지번 주소
    //var extraRoadAddr = document.getElementById("user_extraAddress").value || ''; // 참고항목
    var detailAddress = this.value || ''; // 상세주소

    // 주소를 합쳐서 출력 필드에 설정
    var combinedAddress = '';


    if (roadAddr) {
        combinedAddress += roadAddr + ' ';
    }

    if (jibunAddr) {
        combinedAddress += jibunAddr + ' ';
    }


    if (detailAddress) {
        combinedAddress += detailAddress;
    }
	
	// 앞뒤 공백 제거
    document.getElementById("user_combinedAddress").value = combinedAddress.trim(); 
	});
}





