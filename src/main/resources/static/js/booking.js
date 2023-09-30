// 시작일자에서 오늘 이전으로 선택 안되게
let nowUtc = Date.now(); // 현재까지의 밀리초 (Number 형으로)
let timeOff = new Date().getTimezoneOffset() * 60000; // 분단위를 밀리초로 반환
let today = new Date(nowUtc - timeOff).toISOString().split("T")[0];
document.getElementById("start-day").setAttribute("min", today);
document.getElementById("end-day").setAttribute("min", today);

// 사이즈, 날짜별 요금 계산
const intMoney = document.getElementById("intmoney");
const stringSize = document.getElementById("stringsize");

function checkPrice() {
	const startDay = document.getElementById("start-day").value;
	const endDay = document.getElementById("end-day").value;
	const ar1 = startDay.split("-");
	const ar2 = endDay.split("-");
	const da1 = new Date(ar1[0], ar1[1], ar1[2]);
	const da2 = new Date(ar2[0], ar2[1], ar2[2]);
	const dateDiff = Math.abs((da2 - da1) / (24 * 60 * 60 * 1000));

	if (startDay && endDay) {
		intMoney.value = ((dateDiff * 2) * stringSize.value).toLocaleString() + "원";
	}
	if (startDay && endDay && dateDiff == 0) {
		intMoney.value = (stringSize.value).toLocaleString() + "원";
	}
}

// 예약하기 클릭 시
function insertOpt() {
	const stringCity = document.querySelector("input[name='cont_no']");
	const stringSize = document.getElementById("stringsize");
	const intMoney = document.getElementById("intmoney");
	const userId = document.getElementById("userId").value;
	const bookName = document.getElementById("book-name").value;
	const startDay = document.getElementById("start-day").value;
	const endDay = document.getElementById("end-day").value;
	const selectedSize = stringSize.options[stringSize.selectedIndex].value;

	if (startDay === "" || endDay === "" || stringCity.value === "" || selectedSize === "0") {
		alert("위 항목들을 선택해 주세요.");
		return false;
	} else if (confirm("예약하시겠습니까?")) {

		alert("예약되었습니다. 예약 정보를 서버에 전송했습니다."); // 실제로는 서버 응답을 기다려야 합니다.


	} else {
		alert('예약을 취소합니다.');
		return false;
	}


const nextBtn = document.getElementById("next-btn");
nextBtn.addEventListener("click", () => {
	const stringCity = document.getElementById("stringcity");
	if (stringCity.value == "" || stringSize.value == "" || intMoney.value < 5000) {
		alert("위 항목들을 선택해 주세요.");
		return false;
	} else if (confirm("예약하시겠습니까?")) {
		alert("예약되었습니다.");
	} else {
		alert('예약을 취소합니다.');
		return false;
	}
});

}