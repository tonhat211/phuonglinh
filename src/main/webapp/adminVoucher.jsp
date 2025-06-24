<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý Voucher</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>
    <script type="text/javascript" src="./assets/js/adminVoucher.js"></script>


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
    <link href="./assets/css/adminVoucher.css" rel="stylesheet">


</head>
<body>

<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<Voucher> vouchers = (ArrayList<Voucher>) request.getAttribute("vouchers");
        String script = (String) request.getAttribute("script");
        if (script==null) script="";
    %>
    <main id="main" class="main grid-col-10 adminMain_tab grid-col-12_tab">
        <div id="toast-2"></div>
        <div class="pagetitle">
            <div class="flex-roww" style="align-items: start;">
                <button class="btn btn-outline-primary menu-btn hide show_tab" onclick="showMenu()"><i class="bi bi-list"></i></button>
                <div>
                    <h1>Quản lý Voucher</h1>
                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý Voucher</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between">
                <form action="aemployee" method="get" class="search-bar grid-col-4 ">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" name="search" class="input-search"  placeholder="Tìm theo tên hoặc ID...">
                    <input type="text" name="a" value="search" hidden>
                    <input type="submit" hidden>
                </form>
                <p class="fake-a" id="add-btn">Thêm voucher</p>

            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                        <th scope="col" class="grid-col-0_5 text-center">Mã</th>
                        <th scope="col" class="grid-col-1">Giảm(%)</th>
                        <th scope="col" class="grid-col-1">Giảm tối đa(VND)</th>
                        <th scope="col" class="grid-col-1">Số lượng</th>
                        <th scope="col" class="grid-col-1">Thời gian bắt đầu</th>
                        <th scope="col" class="grid-col-1">Thời gian kết thúc</th>
                        <th scope="col" class="grid-col-1">Thao tác</th>

                    </tr>
                    </thead>
                    <tbody class="group">
                    <%
                        int stt=1;
                        for(Voucher v : vouchers) {
                    %>
                            <tr class="group <%=v.checkAvai()?"":"locked"%>" id="voucher<%=v.id%>">
                                <th scope="row" class="grid-col-0_5 text-center" >
                                    <span class="stt"><%=stt++%></span>
<%--                                    <span class="stt">1</span>--%>
                                </th>
                                <td class="grid-col-0_5 text-center employee-id">
                                    <span class="id"><%=v.id%></span>
<%--                                    <input class="info-input exception" type="text" name="id" value="<%=v.id%>" readonly>--%>

                                </td>
                                <td class="grid-col-1">
                                    <input class="info-input" type="text" name="discount" value="<%=v.discount%>" readonly>
                                </td>
                                <td class="grid-col-1">
                                    <input class="info-input" type="text" name="maxMoney" value="<%=v.maxMoney%>" readonly>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <input class="info-input" type="number" name="qty" value="<%=v.qty%>" readonly>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <input class="info-input" type="date" name="startTime" value="<%=v.startTime%>" readonly>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <input class="info-input" type="date" name="endTime" value="<%=v.startTime%>" readonly>
                                </td>
                                <td class="grid-col-1" onclick="event.stopPropagation();">
                                    <select name="action" onchange="handleChange(event);" data-default="none">
                                        <option value="none">...</option>
                                        <option class="lock-option" value="lock">Khóa</option>
                                        <option class="active-option" value="unlock">Mở khóa</option>
                                        <option value="pwd">Danh sách sản phẩm</option>
                                        <option value="pwd">Danh sách người dùng</option>
                                        <option value="description">Mô tả</option>
                                        <option value="delete">Xóa</option>
                                        <option value="update">Cập nhật</option>

                                    </select>
                                    <div class="flex-roww update-tool">
                                        <button class="btn btn-primary btn-save-update" onclick="saveUpdate(event)">Lưu</button>
                                        <button class="btn btn-outline-primary btn-cancel-update" onclick="cancelUpdate(event,null)">Hủy</button>

                                    </div>
                                </td>
                            </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>

        </div>
        <div class="modall" id="lock-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận khóa </br> <span class="object">Nhật</span></p>
                    <input type="text" name="id" hidden>

                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="lock(event);" type="button">Khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="unlock-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận mở khóa </br> <span class="object"> </span></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="active(event);" type="button">Mở khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="delete-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">

                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận xoá </br> <span class="object"> </span></p>
                    <p class="attention text-center">Không thể hoàn tác</p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="deleteVoucher(event);" type="button">Xóa</button>
                    </div>
                </div>


            </div>
        </div>
        <div class="modall" id="description-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
                <%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="avoucher" method="POST" id="updateDescriptionForm">
                        <h4 class="confirm-content" style="text-align: center">Mô tả voucher</h4>
                        <textarea contenteditable="true" class="editor" name="description" placeholder="Mô tả..." style="height: 100px;"></textarea>
                        <input type="text" name="id" hidden>
                        <div class="flex-roww update-tool active">
                            <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="button" onclick="saveUpdateDescription(event)">Cập nhật</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <div class="modall active" id="applied-product-list-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
                <%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <h4 class="confirm-content" style="text-align: center">Danh sách sản phẩm áp dụng</h4>
                    <div class="flex-roww" style="justify-content: right;margin: 10px 0">
                        <p class="fake-a">Thêm sản phẩm</p>
                    </div>
                    <table style="width: 100%;" id="appliedProductListTable" class="info-table">
                        <thead>
                        <tr>
                            <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                            <th scope="col" class="grid-col-0_5 text-center">ID</th>
                            <th scope="col" class="grid-col-0_5 text-center">Ảnh</th>
                            <th scope="col" class="grid-col-1_5">Tên</th>
                            <th scope="col" class="grid-col-1">Phân loại</th>
                            <th scope="col" class="grid-col-1">Thao tác</th>
                        </tr>
                        </thead>
                        <tbody class="group">
                        <tr class="group" id="product" onclick="showDetail(event);">
                            <th scope="row" class="grid-col-0_5 text-center" >
                                <span class="stt"></span>
                            </th>
                            <td class="grid-col-0_5 text-center product-id">
                                <span class="id"></span>
                            </td>
                            <td class="grid-col-0_5">
                                <div class="img-thumbnail-container">
                                    <img src="./assets/img/thumbnail/" alt="">
                                </div>
                            </td>
                            <td class="grid-col-1_5">
                                <span class="name"></span>
                            </td>
                            <td class="grid-col-1">
                                <span class="category"></span>
                            </td>
                            <td class="grid-col-1" onclick="event.stopPropagation();">
                                <button class="btn btn-primary" onclick="removeProduct(event)">Xóa</button>
                            </td>
                        </tr>


                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="modall" id="add-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
<%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="aemployee" method="POST" id="addEmployeeForm">
                        <h4 class="confirm-content" style="text-align: center">Thêm tài khoản nhân viên mới</h4>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-7">
                                <label>Tên nhân viên</label>
                                <input type="text" class="form-control" name="name" aria-describedby="emailHelp" placeholder="Nhập tên nhân viên" required="">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-7">
                                <label>Email nhân viên</label>
                                <input type="text" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Nhập email" required="">
                                <span class="form-error email-error"></span>
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-5">
                                <label>Chức vụ</label>
                                <input type="text" class="form-control" name="position" aria-describedby="emailHelp" placeholder="Nhập chức vụ" required="">
                            </div>
                            <div class="form-group grid-col-5">
                                <label>Phòng ban</label>
                                <input type="text" class="form-control" name="area" aria-describedby="emailHelp" placeholder="Nhập phòng ban" required="">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 15px;">
                            <div class="form-group grid-col-5">
                                <label>Mật khẩu mặc định</label>
                                <input type="text" class="form-control" name="defaultPassword" value="1234">
                            </div>
                        </div>
                        <input type="text" name="action" value="add" hidden>
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="button" onclick="add()">Thêm</button>
                        </div>
                    </form>



                </div>
            </div>

        </div>

    <div id="avoucher-response"></div>
    </main><!-- End #main -->
</div>
<%=script%>

</body>
</html>