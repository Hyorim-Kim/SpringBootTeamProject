// ****** 회원 가입일 때 각 필드의 유효성 검사 ****** (광진) //
function userJoinForm_check() {
	// 폼을 식별하는 부분
	let userJoinForm = document.forms["userJoinForm"];
	
    // 아이디, 비밀번호, 이름, 이메일, 전화번호 객체 생성
    let id = document.getElementById('user_id');
    let pwd1 = document.getElementById('user_pwd');
    let pwd2 = document.getElementById('user_repwd');
    let name = document.getElementById('user_name');
    let email = document.getElementById('user_email');
    let tel = document.getElementById('user_tel');

    if (id.value === "") {
        alert("아이디를 입력해 주세요.");
        id.focus();
        return false;
    }

    if (pwd1.value === "") {
        alert("비밀번호를 입력해 주세요.");
        pwd1.focus();
        return false;
    }

    if (pwd1.value !== pwd2.value) {
        alert("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
        pwd2.focus();
        return false;
    }

    if (name.value === "") {
        alert("이름을 입력해 주세요.");
        name.focus();
        return false;
    }

    if (email.value === "") {
        alert("이메일 주소를 입력해 주세요.");
        email.focus();
        return false;
    }

	if (tel.value === "") {
	    alert("전화번호를 입력해 주세요.");
	    tel.focus();
	    return false;
	}
	
	// 정규식을 사용하여 숫자와 하이픈을 포함되어 있는지 확인.
	let reg = /^[0-9-]+$/; 
	
	if (!reg.test(tel.value)) {
	    alert("전화번호는 숫자와 하이픈(-)만 입력할 수 있습니다.");
	    tel.focus();
	    return false;
	}
    
    // 주민번호 유효성 검사
    let jumin = document.getElementById('user_jumin').value;
	let juminRegex = /^\d{6}-\d{7}$/; 

	if (!juminRegex.test(jumin)) {
    alert("올바른 주민등록번호를 입력해 주세요.");
	}
	
	if (
        id.value !== "" &&
        pwd1.value !== "" &&
        pwd2.value !== "" &&
        name.value !== "" &&
        email.value !== "" &&
        tel.value !== "" &&
        jumin.value !== ""     
    ) {
        // 모든 필드가 채워져 있다면 축하 메시지를 띄우고 회원가입을 진행.
        alert("회원가입을 축하합니다!");
        userJoinForm.submit(); // 폼 제출을 허용.
    } else {
        // 필드 중 하나라도 누락되었을 경우 경고 메시지를 표시.
        alert("가입란을 확인하세요!");
        return false; // 폼 제출이 되지않는다.
    }
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





