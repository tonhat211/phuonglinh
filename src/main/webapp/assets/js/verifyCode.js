function verifyCode(action,code) {
    console.log(code.length);
    if(code.length!=5) tellWrongCode();

    else {
        $.ajax({
            url: "/ThietBiDiDong/verify",
            method: "POST",
            data: { action: action, code: code},
            success: function(data) {
                console.log(data);
                $("#verifyCode-response").html(data);
            }
        });
    }

}

function wrongOTP(form,message) {
    const formE= document.querySelector(form);
    formE.querySelector(".otp-error").innerText = message;
}
function tellVerifySuccessful() {
    document.querySelector(".otp-message").classList.add('active');
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#otp-form').addEventListener('submit',function(e){
        e.preventDefault();
        const form = document.querySelector('#otp-form');
        var a = form.querySelector('input[name="a"]').value;
        var email = form.querySelector('input[name="email"]').value;
        var action = form.querySelector('input[name="action"]').value;


        var o1=form.querySelector('#o1').value;
        var o2=form.querySelector('#o2').value;
        var o3=form.querySelector('#o3').value;
        var o4=form.querySelector('#o4').value;
        var o5=form.querySelector('#o5').value;

        // var alert_box = form.querySelector('#alert_box');
        if(o1!="" && o2!="" && o3!="" && o4!="" && o5!=""){
            console.log(a);
            console.log(email);
            console.log(action);
            var otp = parseInt(o1+""+o2+""+o3+""+o4+""+o5);
            $.ajax({
                url: 'verify',
                type: 'POST',
                data: {a: a,email: email, action: action,otp:otp},
                success: function(data) {
                    console.log(data);
                    $('#verify-code-response').html(data);
                },
                error: function(error) {

                }
            });
        }

    });
    document.querySelector('#btn-resend').addEventListener('click', function(event) {
        event.preventDefault();
        location.reload();
    });

});

function openModal2(modal) {
    const modalElement = document.querySelector(modal);
    modalElement.classList.add('active');
}
