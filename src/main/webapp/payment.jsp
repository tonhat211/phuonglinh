<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/payment.js"></script>


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


    <!-- css tu them   -->
<%--    <link href="./assets/css/cart.css" rel="stylesheet">--%>
    <link href="./assets/css/payment.css" rel="stylesheet">


</head>
<body>
<jsp:include page="header.jsp" />
<div class="content-container payment-container_mob payment_tab" style="padding: 40px;">
    <%
        ArrayList<CartUnit> carts = (ArrayList<CartUnit>) request.getAttribute("carts");
        ArrayList<AddressItem> addresses = (ArrayList<AddressItem>) request.getAttribute("addresses");
//        SaleProgram saleProgram = (SaleProgram) request.getAttribute("saleProgram");

        double shippingFee = (double) request.getAttribute("shippingFee");
        double totalProductMoney = (double) request.getAttribute("totalProductMoney");
        double totalMoney = (double) request.getAttribute("totalMoney");
    %>

    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;font-size: 20px;">
        <a href="cart">Giỏ hàng </a><p style="margin-bottom: 0;"> / Thanh toán</p>
    </div>
    <form action="order" id="paymentForm" method="post" >
        <input type="text" name="a" value="order" hidden>
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between;">
                <h5 style="margin-bottom: 15px;"><i class="bi bi-geo-alt"></i> Địa chỉ nhận hàng</h5>

                <p class="fake-a btn-change-address" onclick="openAddressModal(event)">Thay đổi</p>

            </div>
            <div class="flex-roww chosen-address flex-coll_mob">
                <%
                    if(!addresses.isEmpty()) {
                        AddressItem address = addresses.get(0);
                %>
                    <p class="grid-col-3 grid-col-12_mob flex-coll_mob">
                        <span class="receiver"><%=address.receiver%></span> <span class="phone"><%=address.phone%></span>
                    </p>
                    <p class="grid-col-9 address grid-col-12_mob"><%=address.getAddress()%></p>
                <%
                    } else {
                %>
                    <p class="grid-col-11">Chưa có địa chỉ nhận hàng</p>
                <%
                    }
                %>
            </div>
            <input type="text" name="address-input" hidden>
        </div>

        <div class="sub-content" style="margin: 20px 0;">
            <h5>Danh sách sản phẩm</h5>
            <table class="" style="width: 100%;">
                <thead class="cart-header">
                <tr>
                    <th scope="col" class="col-base" style="height: fit-content;">STT</th>
                    <th scope="col" class="grid-col-2 hide_mob" style="padding-left: 10px">Hình ảnh</th>
                    <th scope="col" class="grid-col-3 hide_mob">Sản phẩm</th>
                    <th scope="col" class="grid-col-3 hide_mob">Phân loại</th>
                    <th scope="col" class="grid-col-1 hide_mob">Đơn giá</th>
                    <th scope="col" class="grid-col-1 hide_mob">Số lượng</th>
                    <th scope="col" class="grid-col-2 hide_mob">Thành tiền</th>
                    <th scope="col" class="grid-col-12 hide show_mob" style="padding: 0 10px;">Sản phẩm</th>
                </tr>
                </thead>
                <tbody id="cart-body">
                <%
                    int i=0;
                    for(CartUnit c : carts) {
                %>

                    <tr id="de-<%=c.productDetail.id%>">
                        <input type="text" name="cart-id" value="<%=c.getID()%>" hidden>
                        <th scope="row" class="col-base text-center"><%=++i%></th>
                        <td style="height: 100px;padding: 4px" class="grid-col-2 hide_mob"><img src="./assets/img/thumbnail/<%=c.getProductThumbnail()%>" alt="" style="height: 80%"></td>
                        <td class="grid-col-3 hide_mob" style="padding: 0 10px">
                            <p style="font-size: 20px"><%=c.getProductName()%></p>
                            <div class="hide show_mob flex-roww" style="width: 100%;">
                                <div class="grid-col-2">
                                    <img src="./assets/img/thumbnail/<%=c.getProductThumbnail()%>" alt="" style="width: 100%">
                                </div>
                                <div class="grid-col-10">
                                    <p><%=c.getProductName()%></p>
                                    <p><%=c.getDetailClassify()%></p>
                                    <div class="flex-roww">
                                        <p><%=c.getTempPriceFormat()%></p>
                                        <p><%=c.getQty()%></p>
                                    </div>
                                </div>



                            </div>
                        </td>
                        <td class="grid-col-3 hide_mob"><%=c.getDetailClassify()%></td>
                        <td class="grid-col-1 hide_mob"><%=c.getTempPriceFormat()%></td>
                        <td class="col-base hide_mob"><%=c.getQty()%></td>
                        <td class="grid-col-2 temp-money hide_mob"><%=c.getTotalMoneyFormat()%></td>
                        <td class="grid-col-12 hide show_mob">
                            <div class="flex-roww" style="width: 100%">
                                <div class="grid-col-2" style="padding: 0 5px">
                                    <img src="./assets/img/thumbnail/<%=c.getProductThumbnail()%>" alt="" style="width: 100%">
                                </div>
                                <div class="grid-col-10">
                                    <p style="font-size: 17px"><%=c.getProductName()%></p>
                                    <div class="flex-roww" style="justify-content: space-between;font-style: italic; font-size: 13px">
                                        <p>Phân loại: <%=c.getDetailClassify()%></p>
                                        <p>x<%=c.getQty()%></p>
                                    </div>
                                    <div class="flex-roww" style="justify-content: right;">
                                        <p><%=c.getTempPriceFormat()%></p>
                                    </div>
<%--                                    <div class="flex-roww" style="justify-content: end">--%>
<%--                                        <p st>Thành tiền: <%=c.getTotalMoneyFormat()%></p>--%>
<%--                                    </div>--%>
                                </div>
                            </div>
                        </td>
                    </tr>
                <%
                    }
                %>

                </tbody>
            </table>
        </div>
        <div class="sub-content">
            <div class="flex-roww" style="flex-wrap: wrap">
                <div class="grid-col-8 grid-col-12_mob grid-col-6_tab">
                    <h5>Phương thức thanh toán</h5>
                </div>
                <div class="grid-col-4 grid-col-12_mob grid-col-6_tab">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Thanh toán khi nhận hàng</p>
<%--                        <p class="fake-a">Thay đổi</p>--%>
                    </div>
                </div>
            </div>
            <div class="seperate-horizontal"></div>
            <div class="flex-roww payment-result" style="font-size: 15px;color:var(--text-color-2);">
                <div class="grid-col-9 grid-col-1_mob grid-col-7_tab"></div>
                <div class="grid-col-3 grid-col-11_mob grid-col-5_tab">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Tổng tiền hàng: </p>
                        <p style="color: black"><span id="total-money-temp"><%=Constant.formatPrice(totalProductMoney)%></span></p>
                    </div>
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Phí vận chuyển: </p>
                        <p style="color: black"><span id="shipping-fee"><%=Constant.formatPrice(shippingFee)%></span></p>
                        <input type="text" name="shipping-fee-input" hidden value="<%=shippingFee%>">
                    </div>
<%--                    <div class="" style="">--%>
<%--                        <div class="form-group">--%>
<%--                            <div class="flex-roww">--%>
<%--                                <input type="text" class="form-control" name="voucher" placeholder="Nhập mã voucher">--%>
<%--                                <button class="btn btn-primary" type="button" onclick="checkVoucher(null)">Kiểm tra</button>--%>
<%--                            </div>--%>
<%--                          </div>--%>
<%--                        <p style="color: black; margin-bottom: 0;">Giảm: <span id="discount-money">0</span></p>--%>
<%--                        <p class="message-error"></p>--%>
<%--                   </div>--%>
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Tổng thanh toán: </p>
                        <p><span class="total-money" id="total-money"><%=Constant.formatPrice(totalMoney)%></span></p>
                        <input type="text" name="total-money-input" hidden value="<%=totalMoney%>">
                    </div>
                    <div class="flex-roww">
                        <button style="width: 100%" class="btn btn-primary btn-buy" type="submit">Đặt hàng</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div id="modal-container2">
        <!--   cap nhat so dia chi -->
        <div class="modall change-address-modal" onclick="removeModal('#modal-container2');">
            <div class="modall-content" style="width: 40%; background-color: unset;">
                <div class="flex-roww" style="align-items: start;justify-content: space-between;height: 100%;">
                    <div class="sub-content" style="margin-right: 10px;height: 100%; padding-right: 5px;" onclick="event.stopPropagation();">
                        <h3 style="text-align: center;padding-right: 10px;">Sổ địa chỉ</h3>
                        <div class="update-container">
                            <div id="address-container2" style="padding-right: 10px;">
                                <%
                                    for(AddressItem a : addresses) {
                                %>
                                        <div class="address-item" onclick="selectAddressItem(event);">
                                            <div class="flex-roww" style="justify-content: space-between; align-items: start">
                                                <div class="address-info">
                                                    <p><span class="receiver"><%=a.receiver%></span> |   <span class="phone"><%=a.phone%></span></p>
                                                    <p class="id" hidden><%=a.id%></p>
                                                    <p style="margin-top: 5px">Địa chỉ: <span class="address"><%=a.getAddress()%></span></p>
                                                </div>
                                            </div>
                                            <div class="seperate-horizontal-90"></div>
                                        </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="payment-response"></div>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>