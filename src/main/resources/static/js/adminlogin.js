let id = $('#id');
let pw = $('#pw');
let btn = $('#btn');

$(btn).on('click', function() {
    if($(id).val() == "") {
        $(id).next('label').addClass('warning');
        setTimeout(function() {
            $('label').removeClass('warning');
        }, 1500);
    }
    else if($(pw).val() == "") {
        $(pw).next('label').addClass('warning');
        setTimeout(function() {
            $('label').removeClass('warning');
        }, 1500);
    }
});

$(document).ready(function() {
    // 사용자 로그인 링크 클릭 시 사용자 로그인 폼 표시, 공급자 로그인 폼 숨김
    $('#userLogin').click(function() {
      $('#userLoginForm').show();
      $('#providerLoginForm').hide();
    });
  
    // 공급자 로그인 링크 클릭 시 공급자 로그인 폼 표시, 사용자 로그인 폼 숨김
    $('#providerLogin').click(function() {
      $('#userLoginForm').hide();
      $('#providerLoginForm').show();
    });
  });
  