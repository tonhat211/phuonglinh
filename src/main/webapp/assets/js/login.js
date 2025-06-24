// hien mat khau
function showPassword(event) {
    const btn = event.currentTarget.closest("button")
    btn.innerHTML="<i class=\"bi bi-eye\" onclick=\"hidePassword(event);\"></i>";
    const input = btn.parentNode.querySelector("input");
    input.type = "text";
}

// an mat khau
function hidePassword(event) {
    const btn = event.currentTarget.closest("button")
    btn.innerHTML="<i class=\"bi bi-eye-slash\" onclick=\"showPassword(event);\"></i>";
    const input = btn.parentNode.querySelector("input");
    input.type = "password";
}

function login(email,password,page) {
    console.log("login ajax");
    $.ajax({
        type: "POST",
        url: "login",
        data: { a: "login",email: email, password : password, page: page},
        success: function(data) {
            $("#login-response").html(data);
        },
        error: function(error) {
            $('#login-response').html(error.responseText);
        }
    });
}

function wrongPwd(form='#login-form',message='Sai mật khẩu') {
    const formE= document.querySelector(form);
    const errorSpan =formE.querySelector(".pwd-error");
    errorSpan.innerText = message;
    // formE.querySelector('input[name="password"]').value = '';
    formE.querySelector(".email-error").innerText = '';
}

function wrongNewPwd(form,message) {
    const formE= document.querySelector(form);
    formE.querySelector(".new-pwd-error").innerText = message;
    formE.querySelector(".re-new-pwd-error").innerText = message;
}

function existedEmail(form,message) {
    clearError(form);
    const formE= document.querySelector(form);
    formE.querySelector(".email-error").innerText = message;
}

function clearForm(form) {
    const formE= document.querySelector(form);
    const inputs = formE.querySelectorAll("input");
    inputs.forEach(input => {
        input.value = '';
    });
    const errorSpans = formE.querySelectorAll('.form-error');
    errorSpans.forEach(error => {
        error.innerText='';
    });
}

function clearError(form) {
    const formE= document.querySelector(form);
    const errorSpans = formE.querySelectorAll('.form-error');
    errorSpans.forEach(error => {
        error.innerText='';
    });
}

function unexistedEmail() {
    const errorSpan =document.querySelector("#login-form .email-error");
    errorSpan.innerText = "Email chưa được đăng ký!";
    document.querySelector('#login-form input[name="password"]').value = '';
    document.querySelector("#login-form .pwd-error").innerText="";
}
function forward(url) {
    window.location.href = url;
}

// sign up
function signup(name,email,password,repassword) {
    //  nhung loi: mat khau yeu, mat khau khong khop, email da duoc dang ky
    // const regex = /^(?=.*[a-z])(?=.*[A-Z]).{8,}$/; //test mat khau: it nhat 8 kí tu, chu thuong, chu hoa
    const regex = /^(?=.*[a-z]).{8,}$/; //test mat khau: it nhat 8 kí tu

    if(!regex.test(password)){
        clearError('#signup-form');
        wrongNewPwd('#signup-form',"* Mật khẩu phải ít nhất 8 ký tự");
        isLoading(false);
    } else if(password != repassword) {
        clearError('#signup-form');
        wrongNewPwd('#signup-form',"Mật khẩu không trùng khớp");
        isLoading(false);
    } else {

        $.ajax({
            url: "signup",
            method: "POST",
            data: { a: "signup",name: name, email: email, password : password,repassword: repassword},
            success: function(data) {
                $("#signup-response").html(data);
                isLoading(false);
            },
            error: function(error) {
                // $('#login-response').html(error.responseText);
                isLoading(false);
            }
        });
    }
}
function openModal2(modal) {
    const modalElement = document.querySelector(modal);
    modalElement.classList.add('active');
}


function closeModal(modal) {
    const modalElement = document.querySelector(modal);
    modalElement.classList.remove('active');
}

function forgetPwd(event) {
    event.preventDefault();
    openModal2('.forget-pwd-modal');
}
// dam bao dom da tai xong
document.addEventListener("DOMContentLoaded", function() {
    if(document.querySelector("#login-form")) {
        document.querySelector("#login-form").addEventListener('submit', function(event) {
            event.preventDefault();
            isLoading(true);
            console.log('form submit');
            var formdata = new FormData(this);
            var email = formdata.get("email");
            var password = formdata.get("password");
            var page = formdata.get("page");
            // login(email, password,page);
            $.ajax({
                type: "POST",
                url: "login",
                data: { a: "login",email: email, password : password, page: page},
                success: function(data) {
                    $("#login-response").html(data);
                    isLoading(false);
                },
                error: function(error) {
                    $('#login-response').html(error.responseText);
                    isLoading(false);
                }
            });
        });

    }
    if(document.querySelector("#signup-form")) {
        document.querySelector("#signup-form").addEventListener('submit', function(event) {
            event.preventDefault();
            console.log('signup form submit');
            isLoading(true);
            var formdata = new FormData(document.querySelector("#signup-form"));
            var name = formdata.get("name");
            var email = formdata.get("email");
            var password = formdata.get("password");
            var repassword = formdata.get("repassword");
            document.querySelector('#verifyAccountForm input[name="email"]').value=email;
            signup(name,email,password,repassword);
        });
    }
    if(document.querySelector('#forgetPwdConfirmForm')) {
        document.querySelector('#forgetPwdConfirmForm').addEventListener('submit', function(event) {
            event.preventDefault();
            isLoading(true);
            this.submit();
        }) ;
    }
    if(document.querySelector('#reverifyAccount')) {
        document.querySelector('#reverifyAccount').addEventListener('submit', function(event) {
            event.preventDefault();
            isLoading(true);
            this.submit();
        }) ;
    }
    if(document.querySelector('#verifyAccountForm')) {
        document.querySelector('#verifyAccountForm').addEventListener('submit', function(event) {
            event.preventDefault();
            isLoading(true);
            this.submit();
        }) ;
    }


});

function submitVerifyAccountForm() {
    document.querySelector('#btn-to-verify-account').click();
}

