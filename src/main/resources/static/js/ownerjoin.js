function joinform_check() {
	
	let ownerForm = document.forms["ownerForm"];
	
    // 아이디, 비밀번호, 이름, 이메일, 주소, 전화번호, 사업자등록자번호 객체 초기화
    let bunum = document.getElementById('business_num'); 
    let owpwd = document.getElementById('owner_pwd'); 
    let owrepwd = document.getElementById('owner_repwd'); 
    let name = document.getElementById('owner_name'); 
    let tel = document.getElementById('owner_tel'); 
    let email = document.getElementById('email');  

    

    if (bunum.value === "") {
        alert("사업자 번호를 입력해 주세요.");
        bunum.focus();
        return false;
    }

    if (owpwd.value === "") {
        alert("비밀번호를 입력해 주세요.");
        owpwd.focus();
        return false;
    }

    if (owpwd.value !== owrepwd.value) {
        alert("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
        owrepwd.focus();
        return false;
    }

    if (name.value === "") {
        alert("이름을 입력해 주세요.");
        name.focus();
        return false;
    }

    if (tel.value === "") {
        alert("전화번호를 입력해 주세요.");
        tel.focus();
        return false;
    }
    
    // 정규식을 사용하여 숫자와 하이픈을 포함되어 있는지 확인.
	let owreg = /^[0-9-]+$/; 
	
	if (!owreg.test(tel.value)) {
	    alert("전화번호는 숫자와 하이픈(-)만 입력할 수 있습니다.");
	    tel.focus();
	    return false;
	}
    
    if (email.value === "") {
        alert("이메일 주소를 입력해 주세요.");
        email.focus();
        return false;
    }
    
    if (
        bunum.value !== "" &&
        owpwd.value !== "" &&
        owrepwd.value !== "" &&
        name.value !== "" &&
        tel.value !== "" &&
        email.value !== "" 
    
    ){
		// 모든 필드가 채워져 있다면 축하 메시지를 띄우고 회원가입을 진행.
        alert("회원가입을 축하합니다!");
        ownerForm.submit(); // 폼 제출을 허용.
	} else {
        // 필드 중 하나라도 누락되었을 경우 경고 메시지를 표시.
        alert("가입란을 확인하세요!");
        return false; // 폼 제출이 되지않는다.
    }
    
    


}
