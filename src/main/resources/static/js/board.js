// 수정 버튼 클릭 이벤트 처리
document.querySelectorAll('.edit-button').forEach((button) => {
  button.addEventListener('click', (event) => {
    const itemId = event.target.getAttribute('data-id');
    // itemId를 사용하여 해당 게시물의 수정 페이지로 이동 또는 모달을 표시
    // 예: window.location.href = `/edit/${itemId}` 또는 모달 표시 코드
  });
});

// 삭제 버튼 클릭 이벤트 처리
document.querySelectorAll('.delete-button').forEach((button) => {
  button.addEventListener('click', (event) => {
    const itemId = event.target.getAttribute('data-id');
    if (confirm('정말로 삭제하시겠습니까?')) {
      // itemId를 사용하여 해당 게시물을 서버에서 삭제
      fetch(`/delete/${itemId}`, {
        method: 'DELETE'
      })
      .then(response => response.json())
      .then(data => {
        // 삭제가 성공적으로 완료되면 페이지 새로고침 또는 삭제된 항목을 UI에서 숨김
        if (data.success) {
          // 페이지 새로고침 또는 UI에서 삭제된 항목 숨김
        } else {
          alert('삭제 중 오류가 발생했습니다.');
        }
      })
      .catch(error => {
        console.error('삭제 요청 중 오류 발생:', error);
        alert('삭제 중 오류가 발생했습니다.');
      });
    }
  });
});
