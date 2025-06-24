<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/login.js"></script>
    <script type="text/javascript" src="./assets/js/header.js"></script>

    <!-- Favicons -->

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!--icon-->
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.4.0-web/css/all.min.css">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">

    <!-- Template Main CSS File -->
    <link rel="stylesheet" href="./assets/css/css_bootstrap4/bootstrap.min.css">

    <link href="./assets/css/responsive.css" rel="stylesheet">
    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/modal.css" rel="stylesheet">

    <!-- css tu them   -->
    <link href="./assets/css/login.css" rel="stylesheet">

</head>
<body>
<jsp:include page="header.jsp" />
<div class="content-container flex-roww login-container_mob login-container_tab">
    <div class="grid-col-6 img-container_tab">
<%--        <img src="./assets/img/banner/login.png" alt="" style="width: 100%;margin-top: 30px">--%>
    </div>
    <div class="grid-col-6 flex-roww login-form-container_mob login-form-container_tab" style="justify-content: center;align-items: unset">
        <div class="sub-content form-container ">
            <p style="font-size: 30px; font-weight: 700;text-align: center">Đăng ký</p>
            <form action="signup" id="signup-form" style="width: 100%">
                <div class="form-group">
                    <label for="name">Họ tên</label>
                    <input type="text" class="form-control" id="name" name="name" aria-describedby="emailHelp" placeholder="Nhập họ và tên" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Nhập email" required>
                    <span class="form-error email-error"></span>
                </div>
                <div class="form-group">
                    <label>Mật khẩu</label>
                    <div class="flex-roww">
                        <input type="password" class="form-control btrr0 bbrr0" name="password" placeholder="Nhập mật khẩu" required>
                        <button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                    </div>
                    <span class="form-error new-pwd-error"></span>
                </div>
                <div class="form-group">
                    <label>Nhập lại mật khẩu</label>
                    <div class="flex-roww">
                        <input type="password" class="form-control btrr0 bbrr0" name="repassword" placeholder="Nhập lại mật khẩu" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                    </div>
                    <span class="form-error re-new-pwd-error"></span>
                </div>
                <div class="flex-roww" style="justify-content: space-between;margin: 20px 0">
                    <a href="login" style="font-size: 13px">Đã có tài khoản. Đăng nhập</a>
                    <button type="submit" class="btn btn-login">Đăng ký</button>
                </div>
            </form>
            <form action="verify" method="post" style="display: none" id="verifyAccountForm">
                <input type="text" name="email">
                <input type="text" name="fa" value="signup">
                <button type="submit" id="btn-to-verify-account"> </button>
            </form>

        </div>
    </div>
    <div id="signup-response"></div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>