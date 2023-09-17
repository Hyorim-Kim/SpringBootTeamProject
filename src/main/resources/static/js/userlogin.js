window.onload = function (){
	document.querySelector("#userLoginBtn").onclick = function (){
    if (confirm("로그인에 성공하였습니다.")) {
        frm.action = "user/login";
        frm.method = "get";
        frm.submit();
    } 
}

}
  