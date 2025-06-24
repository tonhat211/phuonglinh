<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ProductUnit" %>
<%@ page import="model.InterfaceImage" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%
        String productScript = (String) request.getAttribute("script");
        if (productScript==null) productScript="";

    %>
    <title>Sản phẩm</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/product.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>

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
    <link href="./assets/css/smartphone.css" rel="stylesheet">

</head>
<%
    ArrayList<ProductUnit> productUnits = (ArrayList<ProductUnit>) request.getAttribute("productUnits");
    String script = (String) request.getAttribute("script");
    ArrayList<InterfaceImage> banners = (ArrayList<InterfaceImage>) session.getAttribute("banners");
    if(banners==null) banners = new ArrayList<>();
%>
<body>
<%@ include file="header.jsp" %>
<div class="content-container product-container_mob">
    <div id="toast-2"></div>
<%--    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;">--%>
<%--        <a href="#">Trang chủ </a><p style=";margin-bottom: 0;"> / <span class="total-phone" style="margin-bottom: 0">1000</span> điện thoại</p>--%>
<%--    </div>--%>
    <%
        if(!banners.isEmpty()) {
    %>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="border-radius: 10px; overflow: hidden;">
        <ol class="carousel-indicators" style="bottom:-30px;z-index: 0;">
            <%
                for(int i=0;i< banners.size();i++) {
            %>
            <li data-target="#carouselExampleIndicators" data-slide-to="<%=i%>" class="<%=i==0?"active":""%>"></li>
            <%
                }
            %>
        </ol>
        <div class="carousel-inner">
            <%--            1344 x 224--%>
            <%
                for(InterfaceImage banner : banners) {
            %>
            <div class="carousel-item">
                <div class="row">
                    <div class="grid-col-12">
                        <img class="d-block w-100" src="./assets/img/banner/<%=banner.url%>" alt="<%=banner.url%>">
                    </div>
                    <%--                        <div class="col-6">--%>
                    <%--                            <img class="d-block w-100" src="./assets/img/banner/<%=banner.url%>" alt="<%=banner.url%>">--%>
                    <%--                        </div>--%>
                </div>
            </div>
            <%
                }
            %>
            <script>
                document.querySelector('.carousel-item').classList.add('active');
            </script>

        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev" style="justify-content: start">
            <!--            <span class="carousel-control-prev-icon" aria-hidden="true"></span>-->
            <i class="bi bi-arrow-left-circle-fill" style="font-size: 40px; margin: 20px;"></i>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next" style="justify-content: end">
            <!--            <span class="carousel-control-next-icon" aria-hidden="true"></span>-->
            <i class="bi bi-arrow-right-circle-fill" style="font-size: 40px; margin: 20px;"></i>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <%
        }
    %>
    <div class="main-content">
        <div class="product-container">
            <%
                if(productUnits.isEmpty()) {
            %>
            <p class="note text-center">Không có sản phẩm!</p>
            <%
                }
            %>
            <div class="grid" style="min-width: unset">
                <div class="grid__row" id="product-list">
                <%
                    for (ProductUnit productUnit : productUnits) {
                %>
<%--                    mot san pham --%>
                        <div class="grid-col-2_4 product-item-container grid-col-4_tab grid-col-6_mob">
                            <div class="product-item product-item_mob">
                                <a href="product?a=detail&id=<%=productUnit.getID()%>">
                                    <div class="img-container flex-roww" style="justify-content: center;">
                                        <img class="img-product" src="./assets/img/thumbnail/<%=productUnit.getThumbnail()%>" alt="<%=productUnit.getName()%>">
                                    </div>
                                    <%
                                        if(productUnit.getSaleProgramTitle()!=null) {
                                    %>
                                            <div class="info promotion">
                                                <%--                                    <p class="promotion-item"><i class="bi bi-gift"></i><%=p.getPromotion()%></p>--%>
                                                <p class="promotion-item"><%=productUnit.getSaleProgramTitle()%></p>
                                            </div>
                                    <%
                                        }
                                    %>

                                    <div class="info name info-name_mob">
                                        <p><%=productUnit.getName()%></p>
                                    </div>
                                    <div class="price" style="margin-top: 0;">
                                        <div><p class="cur-price"><%=productUnit.getPriceFormat()%></p> </div>
                                        <div class="flex-roww flex-coll_mob" style="justify-content: space-between;">
                                            <p class="pre-price"><%=productUnit.getPrePriceFormat()%></p>
                                            <div class="info rate">
                                                <p><i class="bi bi-star-fill"></i><span class="rate-figure"><%=productUnit.star%></span>(<span class="total-estimate"><%=productUnit.totalComment%></span>)</p>
                                            </div>
                                        </div>
                                    </div>
                                </a>


                            </div>

                        </div>
                <%
                    }
                %>

                </div>
            </div>
        </div>
    </div>
    <div class="flex-roww" style="margin: 10px 0;justify-content: center">
        <a class="btn btn-outline-primary" id="btn-more-product" href="product?a=more" data-value="2" onclick="moreProduct(event)">Xem thêm sản phẩm <i class="bi bi-chevron-down"></i></a>
    </div>
    <script>
        $('.carousel').carousel();
    </script>

</div>
<div id="product-response"></div>
<%=productScript%>


<%@ include file="footer.jsp" %>
</body>
</html>