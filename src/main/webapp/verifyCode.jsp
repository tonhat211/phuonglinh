<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác thực</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/forgetPassword.js"></script>
    <script type="text/javascript" src="./assets/js/verifyCode.js"></script>


    <!-- Favicons -->

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!--icon-->
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.4.0-web/css/all.min.css">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">

    <!-- Template Main CSS File -->
    <link rel="stylesheet" href="./assets/css/css_bootstrap4/bootstrap.min.css">

    <link href="./assets/css/base.css" rel="stylesheet">

    <!-- css tu them   -->
    <link href="./assets/css/login.css" rel="stylesheet">
    <link href="./assets/css/forgetPassword.css" rel="stylesheet">
    <link href="./assets/css/modal.css" rel="stylesheet">


    <%
        String contextPath = request.getContextPath();
    %>
    <style>
        .bg-img {
            background-image: url('<%=contextPath%>/assets/img/banner/login.png');
        }
    </style>
</head>
<body>
<%
    String email = (String) request.getAttribute("email");
    String action = (String) request.getAttribute("action");
%>
<jsp:include page="header.jsp" />
<div class="content-container bg-img">
    <div class="static-overlay flex-roww" style="justify-content: center;align-items: unset;">
        <div class="grid-col-9 flex-roww otp-form active" style="justify-content: center;align-items: unset;height: fit-content; margin-top: 40px;">
            <div class="sub-content form-container">
                <div class="flex-roww" style="justify-content: space-between;">
                    <i class="bi bi-arrow-left btn-back"></i>
                    <p style="width: 100%; font-size: 30px;text-align: center">Nhập mã xác nhận</p>
                </div>
                <div class="flex-coll" style="justify-content: center;margin-top: 20px;">
                    <p>Mã xác thực sẽ được gửi đến email</p>
                    <p class="email-address"><%=email%></p>
                    <p class="note">Mã sẽ hết hạn trong vòng 5 phút</p>
                </div>
                <form action="verify" id="otp-form" style="width: 100%;margin-top: 15px;">
                    <input type="text" name="a" value="verify" hidden>
                    <input type="text" name="email" value="<%=email%>" hidden>
                    <input type="text" name="action" value="<%=action%>" hidden>
                    <div id="OTP_Div" class="group">
                        <input autofocus class="form-control otp-input empty" onkeyup="alter_box(event)" maxlength="1"  required type="number" id="o1" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o2" onkeyup="alter_box(event)" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o3" onkeyup="alter_box(event)" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o4" onkeyup="alter_box(event)" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o5" onkeyup="alter_box(event)" />
                    </div>
                    <p class="form-error otp-error"></p>
                    <button type="submit" class="btn btn-login" style="margin-top: 20px;width: 100%;">Xác nhận</button>
                </form>
                <span class="form-error otp-error"></span>
                <p style="text-align: center;margin-top: 10px;">Chưa nhận được mã?</p>
                <form action="verify">
                    <input type="text" name="a" value="resend" hidden>
                    <input type="text" name="email" value="<%=email%>" hidden>
                    <input type="text" name="action" value="<%=action%>" hidden>
                    <div class="flex-coll group" style="justify-content: center;margin-top: 15px;">
                        <p style="text-align: center;" id="countdown-container" class="active" >Vui lòng đợi <span id="countdown">...</span></p>
                        <a style="text-align: center;" href="" type="submit" id="btn-resend">Gửi lại</a>
                    </div>
                </form>
            </div>
        </div>

    </div>
    <script>
        countdown(60,'#countdown','#btn-resend');

    </script>


    <div id="modal-container2">
        <div class="modall verify-success-modal" style=" background-color: rgba(182, 182, 182, 0.91);">
            <div class="flex-roww" style="justify-content: center;align-items: center;height: 100vh;">
                <div class="modall-content sub-content" style="width: fit-content;height: fit-content;">
                    <h3 style="text-align: center;padding-right: 10px;">Xác thực tài khoản thành công, đăng nhập lại</h3>
                    <div class="flex-roww" style="justify-content: center;">
                        <a class="btn btn-primary" href="login" style="margin-top: 30px;">Đăng nhập</a>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div id="verify-code-response">

    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>