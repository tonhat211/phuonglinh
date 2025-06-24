<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.InterfaceImageDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý giao diện</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>
    <script type="text/javascript" src="./assets/js/adminInterface.js"></script>


    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="./assets/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="./assets/css/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">
    <link href="./assets/css/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="./assets/css/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="./assets/css/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="./assets/css/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="./assets/css/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="./assets/css/responsive.css" rel="stylesheet">
    <link href="./assets/css/style.css" rel="stylesheet">
    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/modal.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">



    <link href="./assets/css/adminBase.css" rel="stylesheet">
    <link href="./assets/css/adminInterface.css" rel="stylesheet">
</head>
<body>
<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<InterfaceImage> interfaceImages = (ArrayList<InterfaceImage>) request.getAttribute("interfaceImages");
        int totalPage = request.getAttribute("totalPage")!=null?(int) request.getAttribute("totalPage"):0;
        String script = (String) request.getAttribute("script");
        if (script==null) script="";
        String logoUrl = (String) session.getAttribute("logo");


    %>

    <main id="main" class="main grid-col-10 adminMain_tab grid-col-12_tab">
        <div id="toast-2">

        </div>
        <div class="pagetitle">
            <div class="flex-roww" style="align-items: start;">
                <button class="btn btn-outline-primary menu-btn hide show_tab" onclick="showMenu()"><i class="bi bi-list"></i></button>
                <div>
<%--                    <%--%>
<%--                        if(title==null) {--%>
<%--                    %>--%>
                        <h1>Quản lý giao diện</h1>

                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index.jsp">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý giao diện</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="align-items: end;">
                <div class="grid-col-12 grid-col-12_mob grid-col-12_tab ">
                    <div class="sub-content">
                        <ul class="tab-menu flex-roww group" style="align-items: end;">
                            <!--            <li class="order-menu-item active"><a href="cus-order?action=all">Tất cả</a></li>-->
<%--                            <li class="<%=currentStatus==Constant.WAITING?"active":""%>"><a href="order?a=status&status=<%=Constant.WAITING%>">Chờ xác nhận</a></li>--%>
                            <li class="tab-li active" data-value="banner" onclick="tabTo(event)"><a>Banner</a></li>
                            <li class="tab-li" data-value="logo" onclick="tabTo(event)"><a>Logo</a></li>
                            <li class="tab-li" data-value="storage" onclick="tabTo(event)"><a>Bộ nhớ</a></li>
                        </ul>

                    </div>
                </div>
            </div>
            <div class="main-content">
                <div class="tab-content active" id="banner-content">
                    <form enctype="multipart/form-data" id="updateBannerForm">
                        <div class="flex-roww" style="align-items: start;">
                            <div class="form-group grid-col-6 group" style="margin-top: 20px;">
                                <div class="flex-roww">
                                    <label style="min-width: fit-content;margin-right: 10px">Danh sách ảnh</label>
                                    <input type="file" multiple="" data-max_length="20" name="imgs" class="upload__inputfile" accept=".jpg, .jpeg, .png" onchange="previewImages(event);" disabled>
                                </div>
                                <div class="upload__box">
                                    <div class="upload__img-wrap">
                                        <%
                                            String imgUrls = "";
                                            for(InterfaceImage interfaceImage : interfaceImages) {
                                                imgUrls+=interfaceImage.url+"==";
                                        %>
                                        <div class="img-container grid-col-12 group" style="margin-top: 15px;position: relative;">
                                            <img src="./assets/img/banner/<%=interfaceImage.url%>" alt="" class="imgPreview" data-file="<%=interfaceImage.url%>" style="width:100%">
                                            <div class="upload__img-close" onclick="removeImg(event)"></div>
                                        </div>
                                        <%
                                            }
                                        %>
                                    </div>
                                    <input type="text" name="imgUrls" value="<%=imgUrls%>" hidden class="exception">
                                </div>
                            </div>
                            <div class="action-tool group grid-col-6" style="margin-top: 10px">
                                <div class="action-tool-con active">
                                    <button class="btn btn-primary" style="margin: 0 auto" type="button" onclick="updateBanner(event)">Cập nhật</button>
                                </div>
                                <div class="action-tool-main">
                                    <div class="flex-roww">
                                        <button class="btn btn-primary" style="margin: 0 auto" type="submit">Lưu</button>
                                        <button class="btn btn-outline-primary" style="margin: 0 auto;" type="button" onclick="cancelUpdateBanner(event)">Hủy</button>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </form>
                </div>
                <div class="tab-content" id="logo-content">
                    <form enctype="multipart/form-data" id="updateLogoForm">
                        <div class="flex-roww" style="align-items: start;">
                            <div class="form-group group grid-col-6" style="margin-top: 10px;">
                                <div class="flex-roww">
                                    <label style="min-width: fit-content;margin-right: 10px">Ảnh logo</label>
                                    <input class="thumbnailInput" type="file" name="thumbnail" accept=".jpg, .jpeg, .png, .webp" onchange="previewImage(event)" disabled>
                                </div>
                                <div class="thumbnail-img-container grid-col-3" style="margin-top: 15px;position: relative;">
                                    <img src="./assets/img/logo/<%=logoUrl%>" alt="PhuongLinh" class="thumbnailPreview" data-file="" style="width:100%">
                                    <input type="text" value="<%=logoUrl%>" name="thumbnailInput" class="exception" hidden>
<%--                                    <div class="upload__img-close" onclick="removeThumbnail(event)"></div>--%>
                                </div>
                            </div>
                            <div class="action-tool group grid-col-6" style="margin-top: 10px">
                                <div class="action-tool-con active">
                                    <button class="btn btn-primary" style="margin: 0 auto" type="button" onclick="updateLogo(event)">Cập nhật</button>
                                </div>
                                <div class="action-tool-main">
                                    <div class="flex-roww">
                                        <button class="btn btn-primary" style="margin: 0 auto" type="submit">Lưu</button>
                                        <button class="btn btn-outline-primary" style="margin: 0 auto;" type="button" onclick="cancelUpdateLogo(event)">Hủy</button>
                                    </div>
                                </div>

                            </div>

                        </div>

                    </form>
                </div>
                <div class="tab-content " id="storage-content">
                    <button class="btn btn-primary" style="margin-top: 20px;" onclick="clearImgStorage()" type="button">Dọn dẹp kho ảnh</button>
                </div>
            </div>


        </div>



    <div id="ainterface-response"></div>
    </main><!-- End #main -->

</div>
<%=script%>

</body>
</html>