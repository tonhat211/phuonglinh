<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PhuongLinh</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>

    <!-- Favicons -->

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!--icon-->
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.4.0-web/css/all.min.css">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">

    <!-- Template Main CSS File -->
    <link rel="stylesheet" href="./assets/css/css_bootstrap4/bootstrap.min.css">

    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">

    <!-- css tu them   -->
    <link href="./assets/css/smartphone.css" rel="stylesheet">

</head>
<body>
<%@ include file="header.jsp" %>
<div class="content-container">
    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;">
        <a href="#">Trang chủ </a><p style=";margin-bottom: 0;"> / <span class="total-phone" style="margin-bottom: 0">1000</span> điện thoại</p>
    </div>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="border-radius: 10px; overflow: hidden;">
        <ol class="carousel-indicators" style="bottom:-30px;">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <div class="row">
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner1.png" alt="First slide">
                    </div>
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner2.png" alt="Second slide">
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner3.jpg" alt="First slide">
                    </div>
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner4.png" alt="Second slide">
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner5.jpg" alt="First slide">
                    </div>
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner6.png" alt="Second slide">
                    </div>
                </div>
            </div>

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
    <div class="main-content">
        <div class="filter-container flex-roww">
            <div class="filter-brand flex-roww">
                <button class="btn filter-btn ml-0">Tất cả</button>
                <div>
                    <button class="btn btn-brand filter-btn">Samsung</button>
                    <button class="btn btn-brand filter-btn">Apple</button>
                    <button class="btn btn-brand filter-btn">Xiaomi</button>
                    <button class="btn btn-brand filter-btn">Realme</button>
                </div>
            </div>
            <div class="filter-options modal-filter-details">
                <button class="btn filter-btn mr-0 not-active" onclick="openModal('.modal-filter-details')"><i class="bi bi-funnel"></i> Lọc</button>
                <div class="filter-details modall">
                    <div class="os-filter">
                        <p>Hệ điều hành</p>
                        <div class="flex-roww">
                            <button class="btn btn-brand filter-btn">Android</button>
                            <button class="btn btn-brand filter-btn">IOS</button>
                            <button class="btn btn-brand filter-btn">Window</button>
                            <button class="btn btn-brand filter-btn">Harmony OS</button>
                        </div>

                    </div>
                    <div class="seperate-horizontal-90" style="margin: 5px 0"></div>
                    <div class="price-filter group">
                        <p>Giá</p>
                        <div class="flex-roww grid__row">
                            <button class="btn filter-btn">Dưới 2 triệu</button>
                            <button class="btn filter-btn">Từ 2 - 4 triệu</button>
                            <button class="btn filter-btn">Từ 4 - 7 triệu</button>
                            <button class="btn filter-btn ">Từ 7 - 13 triệu</button>
                            <button class="btn filter-btn ">Từ 13 - 20 triệu</button>
                            <button class="btn filter-btn ">Trên 20 triệu</button>
                        </div>


                    </div>
                    <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                        <button class="btn btn-outline-primary" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                        <button class="btn btn-primary"><i class="bi bi-check-lg"></i> Lọc</button>
                    </div>
                </div>
                <div class="overlay" onclick="removeModal('.modal-filter-details')"></div>
            </div>
        </div>
        <div class="sort-container flex-roww group">
            <p>Sắp xếp theo: </p>
            <a href="" class="sort-option active">Nổi bật</a>
            <a href="" class="sort-option">Mới</a>
            <a href="" class="sort-option">Bán chạy</a>
            <div class="parent-option">
                <a href="" class="sort-option ">Giá <i class="bi bi-chevron-down"></i></a>
                <div class="sub-option flex-coll">
                    <a href="" class="sort-option">Thấp đến cao <i class="bi bi-arrow-up-right"></i></a>
                    <div class="seperate-horizontal-90"></div>
                    <a href="" class="sort-option">Cao đến thấp <i class="bi bi-arrow-down-right"></i></a>
                </div>
            </div>

        </div>
        <div class="product-container">
            <div class="grid" style="min-width: unset">
                <div class="grid__row">
                    <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <div class="info flex-roww status">
                                <p class="status-item">Mẫu mới</p>
                            </div>
                            <div class="img-container flex-roww">
                                <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                            </div>
                            <div class="info promotion">
                                <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                            </div>
                            <div class="info features flex-roww">
                                <p class="feature-item">Man hinh 4k</p>
                                <p class="feature-item">sac 5p day</p>
                            </div>
                            <div class="version flex-roww group">
                                <div class="version-item active">
                                    <p>64gb</p>
                                </div>
                                <div class="version-item">
                                    <p>128gb</p>
                                </div>
                            </div>
                            <div class="price">
                                <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                                <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                            </div>
                            <div class="info rate">
                                <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                            </div>
                        </div>

                    </div>
                    <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <div class="info flex-roww status">
                                <p class="status-item">Mẫu mới</p>
                            </div>
                            <div class="img-container flex-roww">
                                <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                            </div>
                            <div class="info promotion">
                                <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                            </div>
                            <div class="info features flex-roww">
                                <p class="feature-item">Man hinh 4k</p>
                                <p class="feature-item">sac 5p day</p>
                            </div>
                            <div class="version flex-roww group">
                                <div class="version-item active">
                                    <p>64gb</p>
                                </div>
                                <div class="version-item">
                                    <p>128gb</p>
                                </div>
                            </div>
                            <div class="price">
                                <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                                <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                            </div>
                            <div class="info rate">
                                <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                            </div>
                        </div>

                    </div>
                    <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <div class="info flex-roww status">
                                <p class="status-item">Mẫu mới</p>
                            </div>
                            <div class="img-container flex-roww">
                                <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                            </div>
                            <div class="info promotion">
                                <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                            </div>
                            <div class="info features flex-roww">
                                <p class="feature-item">Man hinh 4k</p>
                                <p class="feature-item">sac 5p day</p>
                            </div>
                            <div class="version flex-roww group">
                                <div class="version-item active">
                                    <p>64gb</p>
                                </div>
                                <div class="version-item">
                                    <p>128gb</p>
                                </div>
                            </div>
                            <div class="price">
                                <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                                <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                            </div>
                            <div class="info rate">
                                <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                            </div>
                        </div>

                    </div>
                    <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <div class="info flex-roww status">
                                <p class="status-item">Mẫu mới</p>
                            </div>
                            <div class="img-container flex-roww">
                                <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                            </div>
                            <div class="info promotion">
                                <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                            </div>
                            <div class="info features flex-roww">
                                <p class="feature-item">Man hinh 4k</p>
                                <p class="feature-item">sac 5p day</p>
                            </div>
                            <div class="version flex-roww group">
                                <div class="version-item active">
                                    <p>64gb</p>
                                </div>
                                <div class="version-item">
                                    <p>128gb</p>
                                </div>
                            </div>
                            <div class="price">
                                <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                                <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                            </div>
                            <div class="info rate">
                                <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                            </div>
                        </div>

                    </div>
                    <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <div class="info flex-roww status">
                                <p class="status-item">Mẫu mới</p>
                            </div>
                            <div class="img-container flex-roww">
                                <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                            </div>
                            <div class="info promotion">
                                <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                            </div>
                            <div class="info features flex-roww">
                                <p class="feature-item">Man hinh 4k</p>
                                <p class="feature-item">sac 5p day</p>
                            </div>
                            <div class="version flex-roww group">
                                <div class="version-item active">
                                    <p>64gb</p>
                                </div>
                                <div class="version-item">
                                    <p>128gb</p>
                                </div>
                            </div>
                            <div class="price">
                                <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                                <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                            </div>
                            <div class="info rate">
                                <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                            </div>
                        </div>

                    </div>
                    <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <div class="info flex-roww status">
                                <p class="status-item">Mẫu mới</p>
                            </div>
                            <div class="img-container flex-roww">
                                <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                            </div>
                            <div class="info promotion">
                                <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                            </div>
                            <div class="info features flex-roww">
                                <p class="feature-item">Man hinh 4k</p>
                                <p class="feature-item">sac 5p day</p>
                            </div>
                            <div class="version flex-roww group">
                                <div class="version-item active">
                                    <p>64gb</p>
                                </div>
                                <div class="version-item">
                                    <p>128gb</p>
                                </div>
                            </div>
                            <div class="price">
                                <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                                <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                            </div>
                            <div class="info rate">
                                <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                            </div>
                        </div>

                    </div>


                </div>
            </div>
        </div>
    </div>
    <div class="flex-roww" style="margin: 10px 0;justify-content: center">
        <button class="btn btn-outline-primary more">Xem thêm sản phẩm <i class="bi bi-chevron-down"></i></button>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>