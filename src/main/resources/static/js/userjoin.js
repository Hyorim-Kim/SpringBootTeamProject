// ***** 회원 가입 (사용자) 수정 *****
window.onload = function () {
    let pw1 = document.querySelector('#user_pwd');
    let pw2 = document.querySelector('#user_repwd');
    let name = document.querySelector('#user_name');
    let email = document.querySelector('#user_email');
    let tel = document.querySelector('#user_tel');
    let jumin = document.querySelector('#user_jumin');
    let submitBtn = document.querySelector('#btnUserJoin'); // 추가: submit 버튼

    /* 이벤트 핸들러 연결하기 */
    pw1.addEventListener("focusout", checkPw);
    pw2.addEventListener("focusout", check2Pw);
    name.addEventListener("focusout", checkName);
    email.addEventListener("focusout", checkEmail);
    tel.addEventListener("focusout", checkTel);
    jumin.addEventListener("focusout", checkJumin);

    // 추가: submit 버튼 클릭 시 전체 필드 검사
    submitBtn.addEventListener("click", function (event) {
        event.preventDefault(); // 기본 submit 동작 방지
        if (checkAllFields()) {
            // 모든 필드가 유효한 경우 서버로 전송
            document.querySelector('form').submit();
        } else {
            // 모든 필드가 유효하지 않은 경우 경고 메시지 표시
            // showMessage('모든 필드를 올바르게 입력해주세요.');
        }
    });
    
    // 추가: 모든 필드 유효성 검사 함수
    function checkAllFields() {
        let isValid = true;

        // 각 필드별로 유효성 검사 수행
        if (!checkField(pw1)) isValid = false;
        if (!checkField(pw2)) isValid = false;
        if (!checkField(name)) isValid = false;
        if (!checkField(email)) isValid = false;
        if (!checkField(tel)) isValid = false;
        if (!checkField(jumin)) isValid = false;

        return isValid;
    }

    // 추가: 개별 필드 유효성 검사 함수
    function checkField(field) {
        let isValid = true;
        const value = field.value.trim(); // 앞뒤 공백 제거

        if (value === "") { // 공백일 때
            setErrorStyle(field.id);
            isValid = false;
        } else {
            resetErrorStyle(field.id);
        }

        return isValid;
    }


	
	function checkPw() {
		let pwPattern = /^.{4,}$/; // 정규식 패턴
		if (pw1.value === "") { // 공백일 때 
			setErrorStyle('user_pwd');
		} else if (!pwPattern.test(pw1.value)) {
			setErrorStyle('user_pwd');
		} else {
			resetErrorStyle('user_pwd');
		}
	}

	function check2Pw() {
		if (pw2.value === pw1.value && pw2.value !== "") {
			resetErrorStyle('user_repwd');
		} else if (pw2.value !== pw1.value) {
			setErrorStyle('user_repwd');
		}

		if (pw2.value === "") {
			setErrorStyle('user_repwd');
		}
	}

	function checkName() {
		let namePattern = /^[가-힣]{2,}$/;
		if (name.value === "") {
			setErrorStyle('user_name');
		} else if (!namePattern.test(name.value)) {
			setErrorStyle('user_name');
		} else {
			resetErrorStyle('user_name');
		}
	}

	function checkEmail() {
		let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		if (email.value === "") {
			setErrorStyle('user_email');
		} else if (!emailPattern.test(email.value)) {
			setErrorStyle('user_email');
		} else {
			resetErrorStyle('user_email');
		}
	}

	function checkTel() {
		let telPattern = /^\d{3}-\d{3,4}-\d{4}$/;
		if (tel.value === "") {
			setErrorStyle('user_tel');
		} else if (!telPattern.test(tel.value)) {
			setErrorStyle('user_tel');
		} else {
			resetErrorStyle('user_tel');
		}
	}

	function checkJumin() {
		let juminPattern = /^\d{6}-\d{7}$/;
		if (jumin.value === "") {
			setErrorStyle('user_jumin');
		} else if (!juminPattern.test(jumin.value)) {
			setErrorStyle('user_jumin');
		} else {
			resetErrorStyle('user_jumin');
		}
	}
	

	/* 메시지 표시 함수 */
	function showMessage(message) {
		const messageDiv = document.createElement('div');
		messageDiv.className = 'message';
		messageDiv.textContent = message;
		// 메세지를 표시할 위치의 id를 설정
		const messageContainer = document.getElementById('messageContainer'); // 수정 필요
		messageContainer.appendChild(messageDiv);
	}

	/* 에러 스타일 적용 함수 */
	function setErrorStyle(elementId) {
		const element = document.getElementById(elementId);
		element.classList.add('error-input'); // 예를 들어, 'error-input' 클래스를 추가하여 스타일링

		// 해당 메시지 요소에 빨간색 텍스트 스타일 적용
		const messageElement = document.getElementById(elementId + 'Message');
		messageElement.style.color = 'red';

		// 에러 메시지 설정
		messageElement.textContent = '입력한 ' + element.getAttribute('placeholder') + '이(가) 올바르지 않습니다.';
	}

	/* 에러 스타일 초기화 함수 */
	function resetErrorStyle(elementId) {
		const element = document.getElementById(elementId);
		element.classList.remove('error-input'); // 'error-input' 클래스를 제거하여 초기 스타일로 복원

		// 해당 메시지 요소 초기화
		const messageElement = document.getElementById(elementId + 'Message');
		messageElement.style.color = 'black'; // 원하는 색상으로 변경 가능
		messageElement.textContent = ''; // 에러 메시지 초기화
	}
}
	


// **** 주소 등록하기 **** (광진) //
function user_execDaumPostcode() {
    new daum.Postcode({
		// 이 함수는 Daum 우편번호 서비스에서 주소 검색이 완료될 때 실행된다. 또한 다양한 주소 정보를 처리하고 해당 정보를 웹 페이지의 입력 필드에 설정한다.
        oncomplete: function(data) {
			// data 객체에서 도로명 주소를 가져와 roadAddr 변수에 저장
            var roadAddr = data.roadAddress;
            // 추가 도로명 주소 정보를 저장할 빈 문자열을 생성
            var extraRoadAddr = '';
			// 만약 data.bname이 비어 있지 않고, 끝에 '동', '로', 또는 '가'가 있는지를 정규 표현식으로 검사한다. 
			// 만약 조건이 참이라면, 해당 문자열을 extraRoadAddr에 추가한다.
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





