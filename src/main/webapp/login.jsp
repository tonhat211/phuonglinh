<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/login.js"></script>

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
    <link href="./assets/css/toast.css" rel="stylesheet">
    <link href="./assets/css/modal.css" rel="stylesheet">


    <!-- css tu them   -->
    <link href="./assets/css/login.css" rel="stylesheet">

</head>
<body>
<jsp:include page="header.jsp" />

<div class="content-container flex-roww login-container_mob login-container_tab">
    <div class="grid-col-6 img-container_mob img-container_tab">
        <img src="./assets/img/banner/login.png" alt="" style="width: 100%;margin-top: 30px">
    </div>
    <div class="grid-col-6 flex-roww login-form-container_mob login-form-container_tab" style="justify-content: center;align-items: unset">
        <div class="form-container sub-content grid-col-12_mob">
            <p style="font-size: 30px; font-weight: 700;text-align: center">Đăng nhập</p>
            <form action="login" id="login-form" style="width: 100%">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Nhập email" required>
                    <span class="form-error email-error"></span>
                </div>
                <div class="form-group">
                    <label>Mật khẩu</label>
                    <div class="flex-roww">
                        <input type="password" class="form-control" name="password" placeholder="Nhập mật khẩu" required>
                        <button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                    </div>
                    <span class="form-error pwd-error"></span>
                </div>
                <input type="text" name="page" value="<%=request.getAttribute("page")!=null?request.getAttribute("page"):""%>" hidden>
                <div class="flex-roww" style="justify-content: right;"><a href="" onclick="forgetPwd(event);" style="font-size: 13px;">Quên mật khẩu</a></div>
                <div class="flex-roww" style="justify-content: space-between;margin: 20px 0">
                    <a href="signup" style="font-size: 13px">Chưa có tài khoản. Đăng ký</a>
                    <button type="submit" class="btn btn-login">Đăng nhập</button>
                </div>
<%--                <p style="font-size: 14px; text-align: center;color:#b5b5b5">Đăng nhập bằng</p>--%>
<%--                <div class="flex-roww" style="justify-content: center; margin-top: 5px">--%>
<%--                    <div class="grid-col-1">--%>

<%--                        <a href=""><img src="./assets/img/icon/icons8-google-48.png" alt="" style="width: 100%"></a>--%>
<%--                    </div>--%>
<%--                </div>--%>
            </form>

        </div>
    </div>
    <div id="modal-container2">
        <div class="modall forget-pwd-modal" style=" background-color: rgba(182, 182, 182, 0.91);">
            <div class="flex-roww" style="justify-content: center;align-items: center;height: 100vh;">
                <div class="modall-content sub-content" style="width: 40%; height: fit-content;">
                    <form action="login" id="forgetPwdConfirmForm">
                        <input type="text" name="a" value="pwd" hidden>
                        <h3 style="text-align: center;padding-right: 10px;">Quên mật khẩu</h3>
                        <p style="font-style: italic;text-align: center">Mật khẩu mới sẽ được gửi đến email của bạn</p>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control"  name="email" aria-describedby="emailHelp" placeholder="Nhập email của bạn" required>
                        </div>
                        <div class="flex-roww" style="justify-content: space-around;">
                            <button class="btn btn-outline-primary" type="button" style="margin-top: 30px;" onclick="closeModal('.forger-pwd-modal')">Hủy</button>
                            <button class="btn btn-primary" type="submit" style="margin-top: 30px;">Lấy mật khẩu</button>
                        </div>
                    </form>


                </div>
            </div>
        </div>
        <div class="modall" id="verify-otp-modal" onclick="removeModal('#modal-container');">
            <div class="flex-roww" style="justify-content: center;align-items: center;height: 100vh;">
                <div class="modall-content sub-content" style="width: 40%; height: fit-content;">
                    <form action="verify" method="post" id="reverifyAccount">
                        <h3 style="text-align: center;padding-right: 10px;">Chưa xác thực, xác thực ngay?</h3>
                        <p style="font-style: italic;text-align: center">OTP sẽ được gửi đến email của bạn</p>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control"  name="email" aria-describedby="emailHelp" placeholder="Nhập email của bạn" required>
                        </div>
                        <input type="text" name="fa" value="signup" hidden>
                        <div class="flex-roww" style="justify-content: space-around;">
                            <button class="btn btn-outline-primary" type="button" style="margin-top: 30px;" onclick="closeModal('.forger-pwd-modal')">Hủy</button>
                            <button class="btn btn-primary" type="submit" style="margin-top: 30px;">Lấy OTP</button>
                        </div>
                    </form>


                </div>
            </div>

        </div>

    </div>
    <div id="login-response"></div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>