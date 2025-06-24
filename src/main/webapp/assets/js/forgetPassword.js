function alter_box(event){
    var id = event.currentTarget.id;
    var id_num = parseInt(id.split('')[1]);
    var key = event.key;

    if (key === 'Backspace' || key === 'Delete') {
        if(id_num != 1){
            var prev = 'o'+(id_num-1).toString();
            document.getElementById(prev).focus();
        }
    }else{
        if(id_num!=5){
            var next = 'o'+(id_num+1).toString();
            document.getElementById(next).focus();
        }
    }
    checkEmpty(event);
}

function checkEmpty(event) {
    const parent = event.currentTarget.closest('.group');
    const inputElements = parent.querySelectorAll('.otp-input');
    inputElements.forEach(input => {
        if(input.value) input.classList.remove('empty');
        else input.classList.add('empty');
    });
}

function verifyOTP(){
    var o1=document.getElementById('o1').value;
    var o2=document.getElementById('o2').value;
    var o3=document.getElementById('o3').value;
    var o4=document.getElementById('o4').value;
    var o5=document.getElementById('o5').value;

    var alert_box = document.getElementById('alert_box');
    if(o1!="" && o2!="" && o3!="" && o4!="" && o5!=""){
        var otp = parseInt(o1+o2+o3+o4+o5);
        alert_box.style.display = 'none';
        alert(otp);
    }else{
        alert_box.style.display = 'block';
    }
}

function countdown(seconds,countdown_e, resend_e) {
    const Element = document.querySelector(countdown_e);
    const countdownE = Element.parentNode;
    const resendE = document.querySelector(resend_e);
    let counter = seconds;
    if(!countdownE.classList.contains('active')) countdownE.classList.add('active');
    if(resendE.classList.contains('active')) resendE.classList.remove('active');
    const interval = setInterval(() => {
        Element.innerText=counter + 's';
        if (counter === 0) {
            if(countdownE.classList.contains('active')) countdownE.classList.remove('active');
            if(!resendE.classList.contains('active')) resendE.classList.add('active');
            clearInterval(interval);

        }
        counter--;
    }, 1000);
}
