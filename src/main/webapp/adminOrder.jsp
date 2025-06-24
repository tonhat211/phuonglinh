<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý đơn hàng</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>
    <script type="text/javascript" src="./assets/js/adminOrder.js"></script>


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
    <link href="./assets/css/adminOrder.css" rel="stylesheet">
</head>
<body>
<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<OrderUnit> orderunits = (ArrayList<OrderUnit>) request.getAttribute("orderUnits");
        int totalPage = request.getAttribute("totalPage")!=null?(int) request.getAttribute("totalPage"):0;
        String script = (String) request.getAttribute("script");
        if (script==null) script="";

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
                        <h1>Quản lý đơn hàng</h1>

                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index.jsp">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý đơn hàng</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between">
                <form action="aorder" method="get" class="search-bar grid-col-4" id="search-order-form">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" class="input-search" name="search" placeholder="Tìm theo ID ...">
                    <input type="submit" hidden>
                    <input type="text" name="a" value="search" hidden>
                </form>
                <div class="flex-roww">
                    <p style="margin-right: 10px">Xem theo trạng thái: </p>
                    <select name="byStatus" id="byStatus" onchange="queryBy(event);">
                        <option value="<%=Constant.NONE_CONDITION%>" selected>Tất cả</option>
                        <option value="<%=Constant.WAITING%>">Chờ xác nhận</option>
                        <option value="<%=Constant.CONFIRMED%>">Xác nhận</option>
                        <option value="<%=Constant.DELIVERYING%>">Vận chuyển</option>
                        <option  value="<%=Constant.COMPLETED%>">Thành công</option>
                        <option value="<%=Constant.CANCELED%>">Hủy</option>
                    </select>
                </div>

            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">ID</th>
                        <th scope="col" class="grid-col-1">Tạo lúc</th>
                        <th scope="col" class="grid-col-3_5">Danh sách sản phẩm</th>
                        <th scope="col" class="grid-col-1_5">Tổng tiền</th>
<%--                        <th scope="col" class="grid-col-1">Địa chỉ</th>--%>
                        <th scope="col" class="grid-col-1">Trạng thái</th>
                        <th scope="col" class="grid-col-1">Cập nhật vào</th>
                        <th scope="col" class="grid-col-1">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="order-list-container">
                    <%
                        for(OrderUnit orderUnit : orderunits) {
                    %>
                            <tr class="group" id="order<%=orderUnit.getID()%>">
                                <th scope="row" class="grid-col-0_5 text-center id" style="height: fit-content;"><%=orderUnit.getID()%></th>
                                <td class="grid-col-1 text-center">
                                    <span class="grid__row time"><%=orderUnit.getCreateAt()%></span>
                                </td>
                                <td class="grid-col-3_5">
                                    <%=orderUnit.getProductsInfo().replace("\n", "<br>")%>

                                </td>
                                <td class="grid-col-1_5"><%=orderUnit.getTotalMoney()%></td>
                                <td class="grid-col-1"><span class="current-status status-<%=orderUnit.getStatus()%>"><%=orderUnit.getStatusName()%></span></td>
                                <td class="grid-col-1 text-center"><span class="updateTime time"><%=orderUnit.getUpdateAt()%></span></td>
                                <td class="grid-col-1">
                                    <button class="btn btn-next-status btn-status-<%=orderUnit.getNextStatus()%>" type="button" onclick="updateStatus('<%=orderUnit.getID()%>','<%=orderUnit.getNextStatus()%>')"><%=orderUnit.getNextStatusName()%></button>
                                    <select name="action" onchange="handleChange(event);" data-default="none" style="margin-bottom: 15px;" >
                                        <option value="none">...</option>
                                        <option value="<%=Constant.CONFIRMED%>">Xác nhận</option>
                                        <option class="" value="<%=Constant.DELIVERYING%>">Bàn giao</option>
                                        <option class="" value="<%=Constant.COMPLETED%>">Thành công</option>
                                        <option value="<%=Constant.CANCELED%>">Hủy</option>
                                    </select>
                                    <a class="btn-show-detail" href="" onclick="showDetail(event,<%=orderUnit.getID()%>)">Chi tiết</a>
<%--                                    <div style="position: relative;">--%>
<%--                                        <div class="address-container sub-content">--%>
<%--                                            <p><%=orderUnit.getRecieverInfo()%></p>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
                                </td>
                            </tr>
                    <%
                        }
                    %>

                    </tbody>
                </table>
                <%--             pagination--%>
                <div class="flex-roww" style="justify-content: center;margin-top: 20px">
                    <nav aria-label="Page navigation example" id="aorder-pagination">
                        <ul class="pagination group" style="align-items: baseline;">
                            <li class="page-item-nav">
                                <a class="page-link" href="#" aria-label="Previous" onclick="previousPage(event)">
                                    <span class="sr-only">Trước</span>
                                </a>
                            </li>
                        <% for (int i = 1; i <= totalPage; i++) {%>
                            <li class="page-item">
                                <a class="page-link" data-value="<%=i%>" href="aorder?a=query&page=<%=i%>" onclick="query(event);"><%=i%></a>
                            </li>
                            <%=i == 1 ? "<div class=\"flex-roww\" style=\"align-items: baseline\"><i class=\"bi bi-three-dots etc\" style=\"margin: 0 10px;\"></i></div>\n" : ""%>
                            <%=i == (totalPage - 1) ? "<div class=\"flex-roww\" style=\"align-items: baseline\"><i class=\"bi bi-three-dots etc\" style=\"margin: 0 10px;\"></i></div>\n" : ""%>
                        <% } %>

                            <li class="page-item-nav">
                                <a class="page-link" href="#" aria-label="Next" onclick="nextPage(event)">
                                    <span class="sr-only">Tiếp theo</span>
                                </a>
                            </li>
                        </ul>

                    </nav>
                </div>
                <script>
                    setUpPagination('#aorder-pagination');
                </script>
                <%--            end pagination--%>
            </div>

        </div>
        <div class="modall" id="detail-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
                <div class="modall-content  grid-col-6 custom-scroll grid-col-10_tab group" onclick="event.stopPropagation();">
                    <h4 class="title" style="text-align: center">Thông tin đơn hàng</h4>
                    <div class="flex-roww" style="margin-top: 20px; align-items: baseline">
                        <div class="flex-roww grid-col-6" style="margin-left: 40px;">
                            <label class="grid-col-4">ID:</label>
                            <p class="id"></p>
                        </div>
                        <div class="flex-roww grid-col-6">
                            <label class="grid-col-4">Ngày đặt</label>
                            <p class="createAt"></p>
                        </div>
                    </div>
                    <div class="flex-roww grid-col-12" style="margin-top: 15px;">
                        <label class="grid-col-4">Thông tin nhận hàng</label>
                        <p class="receiverInfo"></p>
                    </div>
                    <div style="margin-top: 15px;">
                        <label class="grid-col-4">Danh sách sản phẩm</label>
                        <p class="product-list" style="margin-left: 20px;">san pham 1</p>
                    </div>
                    <div class="flex-roww grid-col-12 no-print" style="margin-top: 15px;">
                        <label class="grid-col-4">Thông tin thanh toán</label>
                        <p class="paymentInfo"></p>
                    </div>
                    <div class="flex-roww grid-col-12 no-print" style="margin-top: 15px;">
                        <label class="grid-col-4">Phí vận chuyển</label>
                        <p class="shippingFee"></p>
                    </div>
                    <div class="flex-roww grid-col-12 no-print" style="margin-top: 15px;">
                        <label class="grid-col-4">Voucher giảm</label>
                        <p class="discountVoucher"></p>
                    </div>
                    <div class="flex-roww grid-col-12 total-money-container" style="margin-top: 15px;">
                        <label class="grid-col-4">Thành tiền</label>
                        <p class="totalMoney"></p>
                    </div>
                    <div class="flex-roww no-print-sure no-print" style="margin-top:20px; justify-content: space-around">
                        <button class="btn btn-outline-primary btn-print-detail" type="button" onclick="printDetail();">In chi tiết</button>
                        <button class="btn btn-primary btn-print-base" onclick="printBase()" type="button">In cơ bản</button>
                    </div>
                </div>
            </div>
        </div>


    <div id="aorder-response"></div>
    </main><!-- End #main -->

</div>
<%=script%>

</body>
</html>