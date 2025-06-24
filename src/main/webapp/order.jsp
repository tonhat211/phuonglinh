<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý đơn hàng</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/cusOrderManagement.js"></script>
    <script type="text/javascript" src="./assets/js/rating-star.js"></script>


    <!-- Favicons -->

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!--icon-->
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.4.0-web/css/all.min.css">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">

    <!-- Template Main CSS File -->
    <link rel="stylesheet" href="./assets/css/css_bootstrap4/bootstrap.min.css">

    <link href="./assets/css/modal.css" rel="stylesheet">
    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">

    <!-- css tu them   -->
    <link href="./assets/css/cusOrderManagement.css" rel="stylesheet">
    <link href="./assets/css/rating-star.css" rel="stylesheet">

</head>
<body>
<%
    ArrayList<OrderUnit> orderUnits = (ArrayList<OrderUnit>) request.getAttribute("orderUnits");
    int currentStatus = (int) request.getAttribute("status");
    String message = (String) request.getAttribute("message");
    OrderUnit order = (OrderUnit) request.getAttribute("OrderPresentation");

%>
<%
    if(message!=null) {
%>
        <script>
            alert("<%=message%>");
        </script>
<%
    }
%>
<%@ include file="header.jsp" %>
<div class="content-container order-container_mob">
    <div id="toast-2"></div>
    <h3 style="text-align: center">Lịch sử mua hàng</h3>
    <div class="flex-roww" style="align-items: end;">
        <div class="grid-col-8 grid-col-12_mob grid-col-12_tab ">
            <div class="sub-content">
                <ul class="tab-menu flex-roww" style="align-items: end;">
                    <!--            <li class="order-menu-item active"><a href="cus-order?action=all">Tất cả</a></li>-->
                    <li class="<%=currentStatus==Constant.WAITING?"active":""%>"><a href="order?a=status&status=<%=Constant.WAITING%>">Chờ xác nhận</a></li>
                    <li class="<%=currentStatus==Constant.CONFIRMED?"active":""%>"><a href="order?a=status&status=<%=Constant.CONFIRMED%>">Chuẩn bị</a></li>
                    <li class="<%=currentStatus==Constant.DELIVERYING?"active":""%>"><a href="order?a=status&status=<%=Constant.DELIVERYING%>">Vận chuyển</a></li>
                    <li class="<%=currentStatus==Constant.COMPLETED?"active":""%>"><a href="order?a=status&status=<%=Constant.COMPLETED%>">Hoàn thành</a></li>
                    <li class="<%=currentStatus==Constant.CANCELED?"active":""%>"><a href="order?a=status&status=<%=Constant.CANCELED%>">Hủy</a></li>
                    <li class="<%=currentStatus==Constant.NONE_CONDITION?"active":""%>"><a href="order?a=status&status=<%=Constant.NONE_CONDITION%>">Tất cả</a></li>
                </ul>
            </div>
        </div>
        <div class="grid-col-4 hide_mob hide_tab">
        </div>
    </div>
    <div class="flex-roww" style="align-items: start;">
        <div class="grid-col-8 grid-col-12_mob grid-col-12_tab order-field" style="padding-right: 5px;">
            <%
                if(orderUnits.isEmpty()) {
            %>
            <p class="note text-center">Không có đơn hàng nào!</p>
            <%
                }
            %>
            <div class="order-container" style="margin-bottom: 20px">
                <%
                    for(OrderUnit orderUnit : orderUnits) {
                %>
                    <div class="order-item sub-content group">
                        <div class="flex-roww" style="justify-content: space-between;">
                            <p>Mã đơn hàng: <span class="orderID"><%=orderUnit.getID()%></span> </p>
                            <p class="finish-color orderStatus status-<%=orderUnit.getStatus()%>"><%=orderUnit.getStatusName()%></p>

                            <!--                        <span class="orderStatusNumber" style="display: none">trang thai theo so (ẩn)</span>-->
                            <!--                        <span class="orderReceiver" style="display: none">nguoi nhan</span>-->
                            <!--                        <span class="orderReceiverPhone" style="display: none">so dien thoai</span>-->
                            <!--                        <span class="orderAddress" style="display: none">dia chi</span>-->
                        </div>
                        <div class="seperate-horizontal-100"></div>
                        <%
                            ArrayList<OrderDetail> details = orderUnit.details;
                            if(!details.isEmpty()) {
                                OrderDetail firstDe = details.get(0);
                        %>
<%--                                <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">--%>
                                <div class="order-detail grid__row">
                                    <div class="grid__row grid-col-9 " style="justify-content: left;">
                                        <div class="grid-col-3" style="padding: 5px;">
                                            <a href="product?a=detail&id=<%=firstDe.getProductID()%>">
                                                <img src="./assets/img/thumbnail/<%=firstDe.getProductThumbnail()%>" alt="" style="width: 100%;">
                                            </a>
                                        </div>
                                        <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                            <p class="product-name"><%=firstDe.productName%></p>
                                            <p style="font-size: 17px;">Phân loại: <span><%=firstDe.detailClassify%></span></p>
                                            <p style="font-size: 17px;"><span class="product-price"><%=firstDe.getTempPriceFormat()%></span></p>

                                        </div>
                                    </div>
                                    <div class="grid-col-3" style="text-align: end;">
                                        <p style="font-size: 17px;">Số lượng: <span class="product-qty"><%=firstDe.qty%></span></p>
                                    </div>
                                </div>

                                <div class="other-detail hide">
                            <%
                                for(int i=1;i<details.size();i++) {
                            %>
                                    <div class="order-detail grid__row <%=(i%2)!=0?"odd":""%>" onclick="showDetailsOrder.call(this)">
                                        <div class="grid__row grid-col-9" style="justify-content: left;">
                                            <div class="grid-col-3" style="padding: 5px;">
                                                <a href="product?a=detail&id=<%=details.get(i).getProductID()%>">
                                                    <img src="./assets/img/thumbnail/<%=details.get(i).getProductThumbnail()%>" alt="" style="width: 100%;">
                                                </a>
                                            </div>
                                            <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                                <p class="product-name"><%=details.get(i).productName%></p>
                                                <p style="font-size: 17px;">Phân loại: <span><%=details.get(i).detailClassify%></span></p>
                                                <p style="font-size: 17px;"class="product-price"><%=details.get(i).getTempPriceFormat()%></p>

                                            </div>
                                        </div>
                                        <div class="grid-col-3" style="text-align: end;">
                                            <p style="font-size: 17px;">Số lượng: <span class="product-qty"><%=details.get(i).qty%></span></p>
                                        </div>
                                    </div>
                                <%
                                    }
                                %>
                            </div>

                        <%
                            }
                        %>

                        <div class="btn-more-container">
                            <p class="btn-more" onclick="showMoreOrderDetail(event,'.order-item');">Xem thêm <i class="bi bi-chevron-down"></i></p>
                        </div>
                        <!--                <p class="btn-more">Thu gọn <i class="bi bi-chevron-up"></i></p>-->

                        <div class="seperate-horizontal-100"></div>
                        <div class="flex-roww" style="justify-content: space-between;align-items: start">
                            <div class="flex-coll grid-col-6_mob" style="align-items: start; justify-content: space-between;">
                                <%
                                    if(orderUnit.getStatus()<Constant.COMPLETED) {
                                %>
                                    <p>Ngày đặt: </p>
                                    <p class="order-dateSet"><%=orderUnit.getCreateAt()%></p>

                                <%
                                    } else {
                                %>
                                    <p>Ngày hoàn thành: </p>
                                    <p class="order-dateFinish"><%=orderUnit.getUpdateAt()%></p>
                                <%
                                    }
                                %>
                                <div class="hide_mob">
                                    <p>Địa chỉ nhận hàng:</p>
                                    <p><%=orderUnit.getRecieverInfo()%></p>
                                </div>

                            </div>
                            <div class="flex-coll grid-col-6_mob" style="align-items: end; justify-content: space-between;">
                                <p style="margin-bottom: 10px;text-align: end;">Tổng tiền: <span class="order-Price"><%=orderUnit.getTotalMoney()%></span></p>
<%--                                <p style="margin-bottom: 10px;text-align: end;">Tổng tiền trước: <span class="order-Price"><%=orderUnit.getPreMoney()%></span></p>--%>
<%--                                <p style="margin-bottom: 10px;text-align: end;">Tiền ship: <span class="order-Price"><%=orderUnit.getShippingFree()%></span></p>--%>
                                <%
                                    if(orderUnit.getStatus()<=Constant.CONFIRMED) {
                                %>
                                        <a class="btn btn-third btn-status-10" href="#" onclick="confirmCancel(event,<%=orderUnit.getID()%>)">Hủy</a>
                                <%
                                    } else if(orderUnit.getStatus()==Constant.DELIVERYING) {
                                %>
                                    <a class="btn btn-primary" href="#" onclick="confirmComplete(event,<%=orderUnit.getID()%>)">Đã nhận hàng</a>
                                <%
                                    } else if(orderUnit.getStatus()==Constant.COMPLETED && orderUnit.canComment()) {
                                %>
                                <a class="btn btn-primary" href="" onclick="rComment(event,<%=orderUnit.getID()%>)">Đánh giá</a>
                                <%
                                    }
                                %>
                            </div>
                        </div>
<%--                        <a class="hide_mob" href="order?a=detail&id=<%=orderUnit.getID()%>&status=<%=orderUnit.getStatus()%>">Xem chi tiết</a>--%>
                        <a class="hide show_mob" href="order?a=address&id=<%=orderUnit.getID()%>" onclick="showAddress(event)">Xem địa chỉ</a>
                        <div class="hide show_mob">
                            <p class="address-presentation"></p>
                        </div>
                    </div>
                <%
                    }
                %>

            </div>
        </div>

        <div class="grid-col-4 hide_mob feedback-field hide_tab" onclick="">
            <div class="overlay"></div>
            <div class="sub-content feedback-container" style="margin-top: 0">
                <h4>Đánh giá sản phẩm</h4>
                <div class="feedback-list">
<%--                    <div class="feedback-item group">--%>
<%--                        <input type="text" name="orderID" value="${blankComment.prominence}" hidden>--%>
<%--                        <input type="text" name="productID" value="${blankComment.id}" hidden>--%>
<%--                        <div class="flex-roww" style="padding: 6px;">--%>
<%--                            <div class="grid-col-1_5 ">--%>
<%--                                <img class="img-product" src="./assets/img/thumbnail/banner.jpg" alt="" style="width: 100%;">--%>
<%--                            </div>--%>
<%--                            <div class="product-info">--%>
<%--                                <p class="name">Dong trung tuoi</p>--%>
<%--                                <p class="classifies">Phan loai 20g</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div class="rating-main flex-roww" id="rating-presentation">--%>
<%--                            <div class="rating">--%>
<%--                                <span class="star" data-value="1"><i class="bi bi-star"></i></span>--%>
<%--                                <span class="star" data-value="2"><i class="bi bi-star"></i></span>--%>
<%--                                <span class="star" data-value="3"><i class="bi bi-star"></i></span>--%>
<%--                                <span class="star" data-value="4"><i class="bi bi-star"></i></span>--%>
<%--                                <span class="star" data-value="5"><i class="bi bi-star"></i></span>--%>
<%--                            </div>--%>
<%--                            <span class="form-error" style="margin-left: 10px"></span>--%>
<%--                            <input type="text" name="rating-index" value="0" hidden>--%>
<%--                        </div>--%>
<%--                        <textarea contenteditable="true" class="editor" name="content" placeholder="Bình luận..." maxlength="150"></textarea>--%>
<%--                        <div class="flex-roww" style="justify-content: end">--%>
<%--                            <button class="btn btn-primary" type="button" onclick="feedBack(event)">Gửi</button>--%>
<%--                        </div>--%>
<%--                        <script>--%>
<%--                            activeRating('#rating-presentation');--%>
<%--                        </script>--%>
<%--                    </div>--%>
                </div>
            </div>
        </div>
    </div>


</div>
<div class="modall" id="cancel-confirm-modal">
    <div class="modal__overlay group" onclick="closeModal(event);">
        <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
            <h4 class="confirm-content text-center">Xác nhận hủy</h4>
            <p class="text-center">Đơn hàng ID: <span class="object"></span></p>
            <input type="text" class="info-input" name="id" hidden>
            <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                <a class="btn btn-primary btn-status-10" href="order?a=cancel" onclick="cancel(event)">Xác nhận</a>
            </div>
        </div>
    </div>
</div>
<div class="modall" id="complete-confirm-modal">
    <div class="modal__overlay group" onclick="closeModal(event);">
        <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
            <h4 class="confirm-content text-center">Xác nhận đã nhận hàng</h4>
            <p class="text-center">Đơn hàng ID: <span class="object"></span></p>
            <input type="text" class="info-input" name="id" hidden>
            <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                <a class="btn btn-primary btn-status-10" href="order?a=complete" onclick="cancel(event)">Xác nhận</a>
            </div>
        </div>
    </div>
</div>
<div id="cusOrder-response"></div>

<%@ include file="footer.jsp" %>
</body>
</html>