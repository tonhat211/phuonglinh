<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý nhân viên</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>
    <script type="text/javascript" src="./assets/js/adminEmployee.js"></script>


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
        ArrayList<User> employees = (ArrayList<User>) request.getAttribute("employees");
        int totalPage = request.getAttribute("totalPage")!=null?(int) request.getAttribute("totalPage"):0;
        String script = (String) request.getAttribute("script");
        if (script==null) script="";
    %>
    <main id="main" class="main grid-col-10 adminMain_tab grid-col-12_tab">
        <div id="toast-2"></div>
        <div class="pagetitle">
            <div class="flex-roww" style="align-items: start;">
                <button class="btn btn-outline-primary menu-btn hide show_tab" onclick="showMenu()"><i class="bi bi-list"></i></button>
                <div>
                    <h1>Quản lý nhân viên</h1>
                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý nhân viên</li>
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
                <p class="fake-a" id="add-btn">Thêm tài khoản</p>

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
                        <th scope="col" class="grid-col-1">Quyền truy cập</th>
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
                                    <th scope="col" class="grid-col-1"><p>Chức vụ</br>Phòng ban</p></th>
                                    <th scope="col" class="grid-col-1">Ngày cập nhật</th>
                                    <th scope="col" class="grid-col-1">Ngày tham gia</th>
<%--                                    <th scope="col" class="grid-col-1_5">Thao tác</th>--%>
                                </tr>
                                </thead>
                                <tbody class="group">
                                <tr class="group">
                                    <th scope="row" class="grid-col-1" >
                                        <input type="text" name="eid" hidden>

                                        <span class="gender">1</span>
                                    </th>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="date" name="birthday" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="text" name="phone" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <div class="flex-coll" style="align-items: start;">
                                            <input class="info-input" type="text" name="area" readonly>
                                            <input class="info-input" type="text" name="position" readonly>

                                        </div>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="datetime-local" name="updateAt" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="datetime-local" name="dateIn" readonly>
                                    </td>
                                    <td class="grid-col-1_5">
                                        <div class="flex-coll" style="align-items: end;">
                                            <button class="btn btn-primary btn-setupupdate" type="button" onclick="setupUpdate(event);" style="background-color: blue; color:white">Cập nhật</button>
                                            <div class="flex-roww secondary-btns-container" style="display: none">
                                                <button class="btn btn-fourth btn-save" style="margin-right: 20px;background-color: blue; color:white" type="button" onclick="saveUpdateDetails(event);">Lưu</button>
                                                <button class="btn btn-fourth btn-cancel" style="" type="button" onclick="closeDetail();">Hủy</button>

                                            </div>
                                        </div>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <%
                        int stt=1;
                        for(User u : employees) {
                    %>
                            <tr class="group <%=u.isLocked()?"locked":""%>" id="employee<%=u.id%>" onclick="showDetail(event);">
                                <th scope="row" class="grid-col-0_5 text-center" >
                                    <span class="stt"><%=stt++%></span>
<%--                                    <span class="stt">1</span>--%>
                                </th>
                                <td class="grid-col-0_5 text-center employee-id">
                                    <span class="id"><%=u.id%></span>
                                </td>
                                <td class="grid-col-2">
                                    <input class="info-input" type="text" name="name" value="<%=u.name%>" readonly>
                                </td>
                                <td class="grid-col-2">
                                    <span class="email"><%=u.email%></span>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <a href="aorder?a=orderof&id=<%=u.id%>">Đơn hàng</a>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <a href="aemployee?a=roles&id=<%=u.id%>" onclick="showRoles(event);">Xem quyền</a>
                                </td>
                                <td class="grid-col-1" onclick="event.stopPropagation();">
                                    <select name="action" onchange="handleChange(event);" data-default="none">
                                        <option value="none">...</option>
                                        <option class="lock-option" value="lock">Khóa</option>
                                        <option class="active-option" value="unlock">Mở khóa</option>
                                        <option value="pwd">Cấp mật khẩu</option>
                                        <option value="delete">Xóa</option>
                                        <option value="update">Cập nhật</option>

                                    </select>
                                </td>
                            </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>

<%--             pagination--%>
                <div class="flex-roww" style="justify-content: center;margin-top: 20px">
                    <nav aria-label="Page navigation example" id="employee-pagination">
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
                                <a class="page-link" data-value="<%=i%>" href="aemployee?a=query&page=<%=i%>"><%=i%></a>
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
                    setUpPagination('#employee-pagination');
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
                    <p class="confirm-content" style="text-align: center">Xác nhận mở khóa </br> <span class="object"> </span></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="active(event);" type="button">Mở khóa</button>
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
        <div class="modall" id="delete-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">

                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận xoá </br> <span class="object"> </span></p>
                    <p class="attention">Không thể hoàn tác</p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="deleteEmployee(event);" type="button">Xóa</button>
                    </div>
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
        <div class="modall" id="roles-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
                <%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll group" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="aemployee" method="post" id="updateRolesForm">
                        <h4 class="confirm-content" style="text-align: center">Quyền truy cập</h4>
                        <p class="text-center"><span class="name"> </span></p>
                        <p class="text-center"><span class="email"> </span></p>
                        <input type="text" name="id" hidden>
                        <input type="text" name="a" value="updateroles" hidden>
                        <div class="flex-roww" style="justify-content: space-around; align-items: start; margin-top: 20px;">
                            <div id="roles">
                                <div class="roles-container">
                                    <div class="form-check">
                                        <%--                                <input class="form-check-input" type="checkbox" id="employee"name="employee" value="employee" <%=roles.contains("employee")?"checked":""%>>--%>
                                        <input class="form-check-input role" type="checkbox" name="employee" value="employee" disabled>
                                        <label class="form-check-label">
                                            Nhân viên
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input role" type="checkbox" name="customer" value="customer" disabled>
                                        <label class="form-check-label">
                                            Khách hàng
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input role" type="checkbox" name="product" value="product" disabled>
                                        <label class="form-check-label">
                                            Sản phẩm
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input role" type="checkbox" name="order" value="order" disabled>
                                        <label class="form-check-label">
                                            Đơn hàng
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input role" type="checkbox" name="interface" value="interface" disabled>
                                        <label class="form-check-label">
                                            Giao diện
                                        </label>
                                    </div>

                                </div>
                                <div class="flex-roww" style="justify-content: end; margin-top: 10px;">
                                </div>
                            </div>
                            <div class="flex-coll" style="align-items: end; justify-content: space-between">
                                <button class="btn btn-primary btn-setup" type="button" onclick="setupUpdateRoles(event);">Cập nhật</button>
                                <button class="btn btn-primary btn-save" type="button" onclick="saveRoles()">Lưu</button>
                                <button class="btn btn-fourth btn-cancel" style="margin-top: 20px;" type="button" onclick="closeModal(event);">Hủy</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    <div id="aemployee-response"></div>
    </main><!-- End #main -->
</div>
<%=script%>

</body>
</html>