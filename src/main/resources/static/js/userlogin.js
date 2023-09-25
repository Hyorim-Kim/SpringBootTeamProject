// // 아이디와 비밀번호가 공백일 때 적용되는 자바스크립트 (광진)ㄴ
let id = $('#id');
let pwd = $('#pwd');
let btn = $('#btn');

$(btn).on('click', function(){
    if($(id).val() == "" ){
        $(id).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
    else if($(pw).val () == ""){
        $(pw).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
});