/*// 사용자가 회원가입 후 로그인 버튼을 클릭했을 때 이벤트 발생
window.onload = function () {
	$(document).ready(function() {
		// 로그인 버튼 클릭 시
		$("#userLoginBtn").click(function(e) {
			e.preventDefault(); // 기본 이벤트 (페이지 리로드) 방지

			// 입력된 사용자 아이디와 비밀번호 가져오기
			let user_id = $("#user_id").val();
			let user_pwd = $("#user_pwd").val();

			// 서버로 데이터를 전송하고 로그인 시도
			$.ajax({
				type: "POST", // POST 방식으로 요청
				url: "userLogSuccess", // 로그인 처리를 담당하는 서버 측 URL로 변경해야 합니다.
				data: {
					user_id: user_id,
					user_pwd: user_pwd
				},
				success: function(response) {
					// 서버로부터의 응답을 처리.
					if (response === "success") {
						// 로그인 성공
						alert("로그인 성공!");
					} else {
						// 로그인 실패
						alert("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
					}
				},
				error: function() {
					// 요청 실패 시 처리
					alert("서버 요청 중 오류가 발생했습니다.");
				}
			});
		});
	});

}

  */