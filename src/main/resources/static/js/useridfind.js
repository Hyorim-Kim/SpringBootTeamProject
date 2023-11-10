// // 아이디와 비밀번호가 공백일 때 적용되는 자바스크립트 (광진)
let name = $('#user_name');
let email = $('#user_email');
let jumin = $('#user_jumin');

$(btn).on('click', function(){
    if($(name).val() === "" ){
        $(name).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
    else if($(email).val () === ""){
        $(email).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
    else if($(jumin).val () === ""){
        $(jumin).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
});