<%@ page import="model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->


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



    <link href="./assets/css/adminBase.css" rel="stylesheet">
    <link href="./assets/css/adminOrder.css" rel="stylesheet">
</head>
<body>
<%
    User userLoggingMenu = (User) session.getAttribute("userLogging");
    String adminMenu = (String) session.getAttribute("amenu");
    if(adminMenu==null) adminMenu="";
%>
<script>
    function showMenu() {
        document.querySelector('#sidebar').classList.add('active');
        document.querySelector('#menu_overlay').classList.add('active');
    }

    function hideMenu() {
        document.querySelector('#sidebar').classList.remove('active');
        document.querySelector('#menu_overlay').classList.remove('active');
    }
</script>
<div id="menu_overlay" onclick="hideMenu()"></div>
<aside id="sidebar" class="sidebar grid-col-2 grid-col-4_tab">
    <ul class="sidebar-nav" id="sidebar-nav">
        <h3 style="text-align: center">[Admin]</h3>
        <%if(userLoggingMenu.hasRole("CUSTOMER")) {%>
            <li class="nav-item">
                <a class="nav-link <%=adminMenu.equalsIgnoreCase("customer")?"":"collapsed"%>" href="acustomer">
                    <span>Quản lý khách hàng</span>
                </a>
            </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("EMPLOYEE")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("employee")?"":"collapsed"%>" href="aemployee">
                <span>Quản lý nhân viên</span>
            </a>
        </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("PRODUCT")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("product")?"":"collapsed"%>" href="aproduct">
                <span>Quản lý sản phẩm</span>
            </a>
        </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("ORDER")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("order")?"":"collapsed"%>" href="aorder">
                <span>Quản lý đơn hàng</span>
            </a>
        </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("INTERFACE")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("interface")?"":"collapsed"%>" href="ainterface">
                <span>Quản lý giao diện</span>
            </a>
        </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("VOUCHER")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("voucher")?"":"collapsed"%>" href="avoucher">
                <span>Quản lý Voucher</span>
            </a>
        </li>
        <%
            }
        %>
<%--        <%if(userLoggingMenu.hasAnyRole()) {%>--%>
<%--        <li class="nav-item ">--%>
<%--            <a class="nav-link <%=adminMenu.equalsIgnoreCase("log")?"":"collapsed"%>" href="alog">--%>
<%--                <span>Quản lý Log</span>--%>
<%--            </a>--%>
<%--        </li>--%>
<%--        <%--%>
<%--            }--%>
<%--        %>--%>
    </ul>
</aside><!-- End Sidebar-->
</body>
</html>