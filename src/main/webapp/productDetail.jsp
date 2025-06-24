<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%
        ProductUnit productUnit = (ProductUnit) request.getAttribute("productUnit");
        ArrayList<Image> images = (ArrayList<Image>) request.getAttribute("images");
        SaleProgram saleProgram = (SaleProgram) request.getAttribute("saleProgram");
        ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
        double avgRating = (double) request.getAttribute("avgRating");
        User userLoggingProductDetail = (User) session.getAttribute("userLogging");
    %>

    <title><%=productUnit.getName()%></title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/rating-star.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/productDetail.js"></script>
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
    <link href="./assets/css/productDetail.css" rel="stylesheet">
    <link href="./assets/css/rating-star.css" rel="stylesheet">
    <style>
        .carousel-indicators {
            position: relative;
        }
        .carousel-nav li {
            width: 8.33%;
            height: unset;
        }



    </style>

</head>
<body>
<%@ include file="header.jsp" %>
<div class="content-container product-details-container productDetail_mob productDetail_tab">
    <div id="toast"></div>
    <div class="filter-options modal-filter-details">
        <div class="filter-details modall" style="width: 600px;height: 150px;">
            <div class="flex-roww" style="justify-content: center;margin:22px 0;font-size: 19px">
                <p>Bạn chưa đăng nhập, vui lòng đăng nhập để sử dụng giỏ hàng</p>
            </div>
            <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                <a href="login?action=require&page=product&pageAction=detail&pageProductID=<%=productUnit.getID()%>" class="btn btn-primary btn-filter" style="color: white;">Đăng nhập</a>

            </div>
        </div>
        <div class="overlay" onclick="removeModal('.modal-filter-details')"></div>
    </div>
    <div class="productDetail-main-content">
        <div class="sub-content">
            <div class="p-title flex-roww flex-coll_mob">
                <h4 class="p-name"><%=productUnit.getName()%></h4>
                <div class="rate">
                    <p><i class="bi bi-star-fill"></i><span class="rate-figure"><%=avgRating%></span>(<span class="total-estimate"><%=comments.size()%></span>)</p>
                </div>
            </div>
        </div>
        <div class="flex-roww" style="align-items: start;">
            <div class="grid-col-8 grid-col-12_mob grid-col-12_tab">
                <div class="p-img-container sub-content">
                    <div id="carouselExampleIndicators" class="carousel slide img-presentation " data-ride="carousel">
                        <div class="carousel-inner">
                        <%
                            for(Image image : images) {
                        %>
                            <div class="carousel-item">
                                <img class="d-block w-100" src="./assets/img/detail/<%=image.url%>" alt="<%=image.title%>">
                            </div>

                        <%
                            }
                        %>
                            <script>
                                document.querySelector('.carousel-item').classList.add('active');
                            </script>

                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                        <ol class="carousel-indicators carousel-nav">
                        <%
                            for(int i=0;i< images.size();i++) {
                        %>
                            <li data-target="#carouselExampleIndicators" data-slide-to="<%=i%>" class="indicator-item grid-col-1">
                                <img class="d-block w-100" src="./assets/img/detail/<%=images.get(i).url%>" alt="<%=images.get(i).title%>">
                            </li>

                        <%
                            }
                        %>
                        <script>
                            document.querySelector('.indicator-item').classList.add('active');
                        </script>

                        </ol>

                    </div>
                </div>

                <div class="sub-content hide info_mob info-tab">
                    <div class="price flex-roww flex-coll_mob" style="padding: 0">
                        <div><p class="cur-price"><span class="cur-price product-price"><%=productUnit.getMinPriceFormat()%></span></p> </div>
                        <div><p class="pre-price" style="padding-left: 10px"><%=productUnit.getMinPrePriceFormat()%></p> </div>
                    </div>
                    <div class="grid__row option-selector group">
                        <%
                            for(ProductDetail detail : productUnit.details) {
                        %>
                        <button class="grid-col-3_6 btn btn-outline-primary option-item " <%=detail.isExhausted()?"disabled":""%> onclick="checkClassify(<%=detail.id%>);"><%=detail.classify%></button>
                        <%
                            }
                        %>
                    </div>

                    <div class="promotion-container">
                        <%
                            if(saleProgram!=null) {
                        %>
                        <div class="promotion__header">
                            <p class="bold-text-6"><%=saleProgram.title%> (<%=saleProgram.discount%>%)</p>
                            <p style="font-size: 12px; color: var(--text-bold-color);">Thời gian áp dụng từ <span class="promotion-end-date"><%=saleProgram.getStartTimeFormat()%></span>
                                đến hết <span class="promotion-end-date"><%=saleProgram.getEndTimeFormat()%></span></p>
                        </div>
                        <div class="promotion__content">
<%--                            <p class="promotion__content-item"><span class="ordinal">1</span> giam 10k</p>--%>
                            <p class="promotion__content-item"><%=saleProgram.content%></p>
                        </div>
                        <%
                            }
                        %>

                    </div>
                </div>



                <div class="sub-content">
                    <div class="flex-roww" style="justify-content: center;">
                        <div class="flex-roww group " style="justify-content: space-evenly;width: fit-content">
                            <button class="btn btn-outline-primary active btn-show" onclick="getDirection()">Hướng dẫn sử dụng</button>
                            <button class="btn btn-outline-primary btn-show" onclick="getDescription()">Mô tả</button>
                        </div>
                    </div>
                    <div class="seperate-horizontal-90" style="margin: 20px auto;"></div>
                    <div id="direction-show" class="p-des active">
                        <%=productUnit.getDirection()%>
                    </div>
                    <div id="description-show" class="p-des">
                        <%=productUnit.getDescription()%>
                    </div>
                </div>

                <div class="sub-content">
                    <div class="rating-product">
                        <p class="bold-text-6">Đánh giá sản phẩm</p>
                    </div>
                    <div class="rating-main flex-roww" id="rating-presentation">
                        <p class="rating-figure bold-text-6"><%=avgRating%></p>
                        <div class="rating">
                            <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                        </div>
                        <p style="color: var(--bold-color);font-weight: 600"><span class="total-rating"><%=comments.size()%></span> đánh giá</p>
                    </div>
                    <div class="comment-container">
                        <%
                            for(Comment cmt : comments) {
                        %>
                            <div class="comment-item" id="cmt<%=cmt.id%>">
                                <div class="comment-header flex-roww">
                                    <p class="comment-owner"><%=cmt.getUserName()%></p>
                                    <p class="buy-check"><i class="bi bi-check-circle"></i> Đã trải nghiệm sản phẩm</p>
                                </div>
                                <div class="rating">
                                    <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                                    <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                                    <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                                    <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                                    <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                                </div>
                                <script>
                                    initRating('#cmt<%=cmt.id%>',<%=cmt.star%>);
                                </script>
                                <div class="comment-content">
                                    <p><%=cmt.content%></p>
                                </div>
                                <div class="seperate-horizontal-90" style="margin: 20px 0;"></div>
                            </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <div class="grid-col-4 buy_mob buy_tab" style="padding-left: 20px;">
                <div> <span class="id-product-detail"></span></div>
                <div class="sub-content">
                    <div class="info-desk">
                        <div class="grid__row option-selector group">
                            <%
                                for(ProductDetail detail : productUnit.details) {
                            %>
                            <button class="grid-col-3_6 btn btn-outline-primary option-item " <%=detail.isExhausted()?"disabled":""%> onclick="checkClassify(<%=detail.id%>);"><%=detail.classify%></button>
                            <%
                                }
                            %>

                        </div>
                        <div class="price flex-roww" style="padding: 0">
                            <input type="text" name="chosen-detailID" hidden>

                            <div><p class="cur-price"><%=productUnit.getMinPriceFormat()%></p></div>
                            <div><p class="pre-price" style="padding-left: 10px"><%=productUnit.getMinPrePriceFormat()%></p></div>
                        </div>

                    </div>

                    <%
                        if(userLoggingProductDetail!=null) {
                    %>
                            <div class="grid__row" style="margin: 15px 0;">
                                <div class="grid-col-6" style="padding-right: 5px">
                                    <button class="btn btn-outline-primary btn-add-to-cart" onclick="addToCart()"><i class="bi bi-cart-plus"></i>Thêm vào giỏ<span class="flex-coll"></span></button>
                                </div>
                                <div class="grid-col-6" style="padding-left: 5px">
                                    <a class="btn btn-primary btn-buy" href="payment?a=buynow" onclick="buy(event)">Mua ngay</a>
                                </div>
                            </div>
                    <%
                        } else {
                    %>
                        <a class="note" href="login">Hãy đăng nhập để mua hàng/dùng giỏ hàng</a>
                    <%
                        }
                    %>


                </div>
<%--                <div class="sub-content">--%>
<%--                    <div id="direction-show1" class="p-des active">--%>
<%--                        <h3>Hướng dẫn sử dụng</h3>--%>
<%--                        <%=productUnit.getDirection()%>--%>
<%--                    </div>--%>
<%--                </div>--%>
            </div>
        </div>

        <div id="productDetail-response"></div>
    </div>
    <script>
        $('.carousel').carousel();

        radioElements('.option-item');
        initRating('#rating-presentation',<%=avgRating%>);
        // activeRating('#rating-presentation');
        radioElements('.btn-show');

    </script>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>