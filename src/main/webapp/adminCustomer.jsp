<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý khách hàng</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.min.js"></script>
    <script type="text/javascript" src="./assets/js/admin.min.js"></script>
    <script type="text/javascript" src="./assets/js/adminCustomer.min.js"></script>


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
    <link href="./assets/css/adminCustomer.css" rel="stylesheet">


</head>
<body>

<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<User> customers = (ArrayList<User>) request.getAttribute("customers");
        int totalPage = request.getAttribute("totalPage")!=null?(int) request.getAttribute("totalPage"):0;
//        int cateID = request.getAttribute("cateID")!=null?(int) request.getAttribute("cateID"):Constant.ALL;

        String message = (String) request.getAttribute("message");
        String script = (String) request.getAttribute("script");
        if (script==null) script="";
    %>

    <%
        if(message!=null) {
    %>
    <script>
        alert('<%= script %>');
    </script>
    <%

        }
    %>

    <main id="main" class="main grid-col-10 adminMain_tab grid-col-12_tab adminMain_mob grid-col-12_mob adminCustomer_mob">
        <div id="toast"></div>
        <div id="toast-2"></div>
        <div class="pagetitle">
            <div class="flex-roww" style="align-items: start;">
                <button class="btn btn-outline-primary menu-btn hide show_tab" onclick="showMenu()"><i class="bi bi-list"></i></button>
                <div>
                    <h1>Quản lý khách hàng</h1>
                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý khách hàng</li>
                        </ol>
                    </nav>
                </div>
            </div>

        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between">
                <form action="acustomer" method="get" class="search-bar grid-col-4 ">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" name="search" class="input-search" placeholder="Tìm theo tên hoặc ID...">
                    <input type="text" name="a" value="search" hidden>
                    <input type="submit" hidden>
                </form>
                <p class="fake-a" onclick="openModal('#add-modal');">Thêm tài khoản</p>
            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                        <th scope="col" class="grid-col-0_5 text-center">ID</th>
                        <th scope="col" class="grid-col-2">Tên</th>
                        <th scope="col" class="grid-col-2">Email</th>
                        <th scope="col" class="grid-col-1">Đơn hàng</th>
                        <th scope="col" class="grid-col-1">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody class="group">
                    <tr class="group" id="detail-container">
                        <td style="width: 100%" colspan="11">
                            <table id="detail-table">
                                <thead>
                                <tr>
                                    <th scope="col" class="grid-col-1" style="height: fit-content;">Giới tính</th>
                                    <th scope="col" class="grid-col-1">Ngày sinh</th>
                                    <th scope="col" class="grid-col-1">Điện thoại</th>
                                    <th scope="col" class="grid-col-1">Ngày tham gia</th>
                                </tr>
                                </thead>
                                <tbody class="group">
                                <tr class="group">
                                    <td class="grid-col-1 hide edit-tool">
                                        <button class="btn btn-outline-primary">Hủy</button>
                                    </td>
                                    <th class="grid-col-1" >
                                        <span class="gender">1</span>
                                    </th>
                                    <td class="grid-col-1">
                                        <span class="birthday">1</span>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="text" name="phone" value="01324523" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="text" name="dateIn" readonly>
                                    </td>
                                    <td class="grid-col-1 hide edit-tool">
                                        <button class="btn btn-primary">Lưu</button>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <%
                        int stt=1;
                        for(User u : customers) {
                    %>
                            <tr class="group <%=u.isLocked()?"locked":""%>" id="customer<%=u.id%>" onclick="showDetail(event);">
                                <th scope="row" class="grid-col-0_5 text-center" >
                                    <span class="stt"><%=stt++%></span>
<%--                                    <span class="stt">1</span>--%>
                                </th>
                                <td class="grid-col-0_5 text-center user-id">
                                    <span class="id"><%=u.id%></span>
<%--                                    <input class="info-input" type="text" name="id" value="<%=u.id%>" readonly>--%>
                                </td>
                                <td class="grid-col-2">
<%--                                    <span class="name"><%=u.name%></span>--%>
                                    <input class="info-input" type="text" name="name" value="<%=u.name%>" readonly>
                                </td>
                                <td class="grid-col-2">
                                    <span class="email"><%=u.email%></span>
<%--                                    <input class="info-input" type="email" name="email" value="<%=u.email%>" readonly>--%>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <a href="aorder?a=orderof&id=<%=u.id%>">Đơn hàng</a>
                                </td>
                                <td class="grid-col-1" onclick="event.stopPropagation();">
                                    <select name="action" onchange="handleChange(event);" data-default="none">
                                        <option value="none">...</option>
                                        <option class="active-option" value="unlock">Mở khóa</option>
                                        <option class="lock-option" value="lock">Khóa</option>
                                        <option value="pwd">Cấp mật khẩu</option>
                                        <option value="delete">Xóa</option>
                                    </select>
                                </td>
                            </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>

                <div class="flex-roww" style="justify-content: center;margin-top: 20px">
                    <nav aria-label="Page navigation example" id="customer-pagination">
                        <ul class="pagination group" style="align-items: baseline;">
                            <li class="page-item-nav">
                                <a class="page-link" href="#" aria-label="Previous" onclick="previousPage(event)">
                                    <span class="sr-only">Trước</span>
                                </a>
                            </li>

                            <% for (int i = 1; i <= totalPage; i++) {

                            %>
<%--                            <li class="page-item"><a class="page-link" data-value="<%=i%>" href="#"--%>
<%--                                                     onclick="query(<%=i%>)"><%=i%>--%>
<%--                            </a></li>--%>
                            <li class="page-item">
                                <a class="page-link" data-value="<%=i%>" href="acustomer?a=query&page=<%=i%>"><%=i%></a>
                            </li>
                            <%=i == 1 ? "<div class=\"flex-roww\" style=\"align-items: baseline\"><i class=\"bi bi-three-dots etc\" style=\"margin: 0 10px;\"></i></div>\n" : ""%>
                            <%=i == (totalPage - 1) ? "<div class=\"flex-roww\" style=\"align-items: baseline\"><i class=\"bi bi-three-dots etc\" style=\"margin: 0 10px;\"></i></div>\n" : ""%>
                            <%
                                }
                            %>

                            <li class="page-item-nav">
                                <a class="page-link" href="#" aria-label="Next" onclick="nextPage(event)">
                                    <!--              <span aria-hidden="true">&raquo;</span>-->
                                    <span class="sr-only">Tiếp theo</span>
                                </a>
                            </li>
                        </ul>

                    </nav>
                </div>

                <script>
                    setUpPagination('#customer-pagination');
                </script>

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
                    <p class="confirm-content" style="text-align: center">Xác nhận mở khóa </br> <span class="object"></span></p>
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
                    <p class="confirm-content" style="text-align: center">Xác nhận xoá </br> <span class="object"></span></p>
                    <p class="attention">Không thể hoàn tác</p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="deleteCustomer(event);" type="button">Xóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="pwd-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận cấp mật khẩu </br> <span class="object"></span></p>
                    <p class="note">Mật khẩu mới sẽ được tạo tự động và gửi tới email tài khoản</p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="pwd(event);" type="button">Cấp mật khẩu</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="add-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
<%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="acustomer" method="POST" id="addCustomerForm">
                        <h4 class="confirm-content" style="text-align: center">Thêm tài khoản khách hàng mơi</h4>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-7">
                                <label>Tên khách hàng</label>
                                <input type="text" class="form-control" name="name" aria-describedby="emailHelp" placeholder="Nhập tên khách hàng" required="">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-7">
                                <label>Email khách hàng</label>
                                <input type="text" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Nhập email khách hàng" required="">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 15px;">
                            <div class="form-group grid-col-5">
                                <label>Mật khẩu mặc định</label>
                                <input type="text" class="form-control" name="pwd" value="1234">
                            </div>
                        </div>
                        <input type="text" name="a" value="add" hidden>
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="submit">Thêm</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    <div id="acustomer-response"></div>
    </main><!-- End #main -->
</div>
<%=script%>

</body>
</html>