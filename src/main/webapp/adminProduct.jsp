<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="partial.ProductAdmin" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý sản phẩm</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>
    <script type="text/javascript" src="./assets/js/adminProduct.js"></script>


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
    <link href="./assets/css/adminProduct.css" rel="stylesheet">

</head>
<body>
<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<ProductAdmin> productAdmins = (ArrayList<ProductAdmin>) request.getAttribute("productAdmins");
        int totalPage = request.getAttribute("totalPage")!=null?(int) request.getAttribute("totalPage"):0;
        String script = (String) request.getAttribute("script");
        if (script==null) script="";
    %>
    <main id="main" class="main grid-col-10 adminMain_tab grid-col-12_tab">
        <div id="toast-2"></div>
        <div id="toast"></div>
        <div class="pagetitle">
            <div class="flex-roww" style="align-items: start;">
                <button class="btn btn-outline-primary menu-btn hide show_tab" onclick="showMenu()"><i class="bi bi-list"></i></button>
                <div>
                    <h1>Quản lý sản phẩm</h1>
                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý sản phẩm</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between">
                <div class="flex-roww grid-col-6">
                    <form action="aproduct" method="get" class="search-bar grid-col-6 ">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" class="input-search" name="search" placeholder="Bạn tìm gì...">
                        <input type="text" name="a" value="search" hidden>
                        <input type="submit" hidden>
                        <input type="text" name="filterSearch" value="name" hidden>
                    </form>
                    <div class="flex-roww" style="margin-left: auto">
                        <p style="margin: 0 10px;min-width: fit-content">Tìm theo: </p>
                        <select name="filterSearch" id="filterSẻarch" onchange="setFilterSearch(event);" >
                            <option value="<%=Constant.NAME_FILTER%>" selected>Tên</option>
                            <option value="<%=Constant.ID_FILTER%>">ID</option>
                            <option value="<%=Constant.INGREDIENT_FILTER%>">Thành phần chính</option>
                        </select>
                    </div>


                </div>
                <p class="fake-a" id="add-product-btn">Thêm sản phẩm</p>


            </div>
            <div class="flex-roww" style="margin-top: 20px;">
                <p style="margin-right: 10px">Xem theo phân loại: </p>
                <select name="byCategory" id="byCategory" onchange="queryBy(event);" >
                    <option value="<%=Constant.ALL_String%>" selected>Tất cả</option>
                    <option value="<%=Constant.NAM_CATEGORY%>">Nấm</option>
                    <option value="<%=Constant.DONG_TRUNG_CATEGORY%>">Đông trùng</option>
                    <option value="<%=Constant.RUOU_CATEGORY%>">Rượu</option>
                    <option value="<%=Constant.OTHER_CATEGORY%>">Khác</option>
                </select>
            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                        <th scope="col" class="grid-col-0_5 text-center">ID</th>
                        <th scope="col" class="grid-col-0_5 text-center">Ảnh</th>
                        <th scope="col" class="grid-col-1_5">Tên</th>
                        <th scope="col" class="grid-col-1">Phân loại</th>
                        <th scope="col" class="grid-col-1">Lượng bán</th>
                        <th scope="col" class="grid-col-1">Lượt truy cập</th>
                        <th scope="col" class="grid-col-1">Feedback</th>
                        <th scope="col" class="grid-col-1">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody class="group">
                    <tr class="group" id="detail-container">
                        <td style="width: 100%" colspan="11">
                            <input type="submit" hidden>
                            <table id="detail-table">
                                <thead>
                                <tr>
                                    <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                                    <th scope="col" class="grid-col-0_5 text-center">ID</th>
                                    <th scope="col" class="grid-col-1">Phân loại</th>
                                    <th scope="col" class="grid-col-1 text-center">Giá trước giảm</th>
                                    <th scope="col" class="grid-col-1 text-center">Giá sau giảm</th>
                                    <th scope="col" class="grid-col-1 text-center">Số lượng</th>
                                    <th scope="col" class="grid-col-1">Thao tác</th>
                                </tr>
                                </thead>
                                <tbody class="group">
                                <tr class="group locked">
                                    <th scope="row" class="grid-col-0_5 text-center" >
                                        <span class="stt">1</span>
                                    </th>
                                    <td class="grid-col-0_5 text-center">
                                        <span class="id">1</span>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="text" name="classify" value="Xanh" readonly>
                                    </td>
                                    <td class="grid-col-1 text-center">
                                        <input class="info-input text-center" type="number" name="prePrice" value="1000000" readonly>
                                    </td>
                                    <td class="grid-col-1 text-center">
                                        <input class="info-input text-center" type="number" name="price" value="8900000" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input text-center" type="number" name="qty" value="3" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <div class="flex-row">
                                            <select name="action" onchange="handleChange(event);" data-default="none">
                                                <option value="none">...</option>
                                                <option value="updatedetail">Cập nhật</option>
                                                <option class="lock-option" value="lockdetail">Khóa</option>
                                                <option class="unlock-option" value="unlockdetail">Mở khóa</option>
                                                <option value="deletedetail">Xóa</option>
                                            </select>
                                            <button class="btn-update-detail active" onclick="updateDetail(event);"><i class="bi bi-floppy"></i></button>

                                        </div>
                                       </td>

                                </tr>

                                </tbody>
                            </table>
<%--                            </form>--%>

                        </td>
                    </tr>
                    <%
                        int stt=1;
                        for(ProductAdmin p : productAdmins) {
                    %>
                        <tr class="group <%=p.isLocked()?"locked":""%>" id="product<%=p.getProductID()%>" onclick="showDetail(event);">
                            <th scope="row" class="grid-col-0_5 text-center" >
                                <span class="stt"><%=stt++%></span>
                            </th>
                            <td class="grid-col-0_5 text-center product-id">
                                <span class="id"><%=p.getProductID()%></span>
                            </td>
                            <td class="grid-col-0_5">
                                <div class="img-thumbnail-container">
                                    <img src="./assets/img/thumbnail/<%=p.getThumbnail()%>" alt="">
                                </div>
                            </td>
                            <td class="grid-col-1_5">
                                <span class="name"><%=p.getName()%></span>
                            </td>
                            <td class="grid-col-1">
                                <span class="category"><%=p.getCategory()%></span>
                            </td>
                            <td class="grid-col-1">
                                <%=p.saledQty%>
                            </td>
                            <td class="grid-col-1">
                                <span class="prominence"><%=p.getProminenece()%></span>
                            </td>
                            <td class="grid-col-1">
                                <a href="" class="btn-feedBack">Feedback</a>
                            </td>

                            <td class="grid-col-1" onclick="event.stopPropagation();">
                                <select name="action" onchange="handleChange(event);" data-default="none">
                                    <option value="none">...</option>
                                    <option value="update">Cập nhật</option>
                                    <option class="lock-option" value="lock">Khóa</option>
                                    <option class="active-option" value="unlock">Mở khóa</option>
                                    <option value="info">Thông tin</option>
                                    <option value="delete">Xóa</option>
                                </select>
                            </td>
                        </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>
                <div class="temp" style="display: none;">

                </div>

                <%--             pagination--%>
                <div class="flex-roww" style="justify-content: center;margin-top: 20px">
                    <nav aria-label="Page navigation example" id="product-pagination">
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
                                <a class="page-link" data-value="<%=i%>" href="aproduct?a=query&page=<%=i%>" onclick="query(event);"><%=i%></a>
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
                    setUpPagination('#product-pagination');
                </script>

            </div>

        </div>
        <div class="modall" id="lock-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <h3 class="confirm-content" style="text-align: center">Xác nhận khóa </h3>
                    <p class="object text-center">Nhật</p>
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
                    <h3 class="confirm-content" style="text-align: center">Xác nhận mở khóa </h3>>
                    <p class="object text-center"></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="active(event);" type="button">Kích hoạt</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="delete-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <h3 class="confirm-content" style="text-align: center">Xác nhận xoá </h3>
                    <p class="object text-center"></p>
                    <input type="text" name="id" hidden>
                    <input type="text" name="action" value="delete" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="deleteProduct(event);" type="button">Xóa</button>
                    </div>
                </div>


            </div>
        </div>
        <div class="modall parent-primary" id="update-info-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
                <div class="modall-content  grid-col-6 custom-scroll grid-col-10_tab" onclick="event.stopPropagation();">
                    <form action="adminproduct" method="POST" id="updateInfoForm" enctype="multipart/form-data">
                        <h4 class="title" style="text-align: center">Cập nhật thông tin sản phẩm</h4>
                        <div class="flex-roww" style="margin-top: 20px; align-items: baseline">
                            <div class="form-group grid-col-6">
                                <label for="name">Tên</label>
                                <input type="text" class="form-control" id="name" name="name" aria-describedby="emailHelp" placeholder="Nhập tên sản phẩm" required>
                            </div>
                            <div class="form-group flex-roww grid-col-4" style="margin-left: 40px;">
                                <label>ID:</label>
                                <input type="text" class="info-input exception" name="id" readonly>
                            </div>
                        </div>
                        <div class="flex-roww grid-col-4 group" style="margin-top: 15px;">
                            <div>
                                <label>Phân loại</label>
                                <select name="category" style="margin-left: 10px;" onchange="handleChange(event);">
                                    <option value="dongtrung" selected>Đông trùng</option>
                                    <option value="nam">Nấm</option>
                                    <option value="ruou">Rượu</option>
                                    <option value="other-category">Khác</option>
                                </select>
                            </div>
                            <div>
                                <input type="text" class="form-control other-category-input" name="other-category" required placeholder="Nhập phân loại" value="" style="margin-left: 10px; min-width: fit-content;">
                            </div>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Thành phần chính</label>
                            <textarea contenteditable="true" class="editor" name="ingredient" placeholder="Thành phần..." maxlength="150" style="height: 50px;"></textarea>
                        </div>
                        <div class="flex-roww" style="margin-top: 15px;align-items: start">
                            <div class="flex-roww grid-col-6">
                                <div class="form-group group">
                                    <div class="flex-roww">
                                        <label style="min-width: fit-content;margin-right: 10px">Ảnh đại diện</label>
                                        <input class="thumbnailInput" type="file" name="thumbnail" accept=".jpg, .jpeg, .png"  onchange="previewImage(event)" />
                                    </div>
                                   <div class="thumbnail-img-container grid-col-3" style="margin-top: 15px;position: relative;">
                                        <img src="./assets/img/thumbnail/banner.jpg" alt="" class="thumbnailPreview" data-file="" style="width:100%">
                                        <input type="text" value="" name="thumbnailInput" hidden class="exception">
                                        <div class="upload__img-close" onclick="removeThumbnail(event)"></div>
                                   </div>
                                </div>
                            </div>

                        </div>
                        <div class="flex-roww" style="margin-top: 15px;" >
                            <div class="form-group group">
                                <div class="flex-roww">
                                    <label style="min-width: fit-content;margin-right: 10px">Ảnh chi tiết</label>
                                    <input type="file" multiple data-max_length="20" name="imgs" class="upload__inputfile" accept=".jpg, .jpeg, .png" onchange="previewImages(event);">
                                </div>
                                <div class="upload__box">
                                    <div class="upload__img-wrap">

                                    </div>
                                    <input type="text" name="imgUrls" value="" hidden class="exception">
                                </div>
                            </div>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Mô tả</label>
                            <textarea contenteditable="true" class="editor" name="description" placeholder="Mô tả..." style="height: 100px;"></textarea>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Hướng dẫn sử dụng</label>
                            <textarea contenteditable="true" class="editor" name="direction" placeholder="Hướng dẫn ..." style="height: 100px;"></textarea>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Lượt truy cập</label>
                            <input type="number" class="form-control grid-col-4" name="prominence" value="99" placeholder="Nhập lượt truy cập" required>
                        </div>
                        <div class="flex-roww action-tool" style="margin-top:20px; justify-content: space-around">
                            <input type="text" name="action" value="update" hidden>
                            <button class="btn btn-fourth btn-cancel child-primary" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm child-primary" onclick="saveUpdate(event,'update')" type="button">Lưu</button>
                            <button class="btn btn-primary btn-setup child-secondary" type="button">Cập nhật</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modall" id="add-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
<%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll grid-col-10_tab" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="aproduct" method="POST" id="addProductForm" enctype="multipart/form-data">
                        <h4 class="confirm-content" style="text-align: center">Thêm sản phẩm mới</h4>

                        <div class="flex-roww" style="margin-top: 20px; align-items: baseline">
                            <div class="form-group grid-col-6">
                                <label>Tên</label>
                                <input type="text" class="form-control" name="name" aria-describedby="emailHelp" placeholder="Nhập tên sản phẩm" required>
                            </div>

                        </div>
                        <div class="flex-roww grid-col-4 group" style="margin-top: 15px;">
                            <div>
                                <label>Phân loại</label>
                                <select name="category" style="margin-left: 10px;" onchange="handleChange(event);">
                                    <option value="dongtrung" selected>Đông trùng</option>
                                    <option value="nam">Nấm</option>
                                    <option value="ruou">Rượu</option>
                                    <option value="other-category">Khác</option>
                                </select>
                            </div>
                            <div>
                                <input type="text" class="form-control other-category-input" name="other-category" placeholder="Nhập phân loại" value="" style="margin-left: 10px; min-width: fit-content;">
                            </div>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Thành phần chính</label>
                            <textarea contenteditable="true" class="editor" name="ingredient" placeholder="Thành phần..." maxlength="150" style="height: 50px;"></textarea>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Phân loại của sản phẩm:</label>
                            <div class="group">
                                <div class="classify-input-container group">
                                    <div class="flex-roww group classify-item" style="justify-content: space-between;margin-top: 10px">
                                        <input class="form-control grid-col-2" type="text" name="classify" placeholder="Phân loại" required>
                                        <input class="form-control grid-col-2" type="number" name="prePrice" placeholder="Giá trước giảm" required>
                                        <input class="form-control grid-col-2" type="number" name="price" placeholder="Giá sau giảm" required>
                                        <input class="form-control grid-col-2" type="number" name="qty" placeholder="Số lượng" required>
                                        <i class="bi bi-plus-circle-fill btn-add-classify active" onclick="addMoreClassify(event)"></i>
                                        <i class="bi bi-dash-circle-fill btn-remove-classify" onclick="removeClassify(event)"></i>
                                    </div>


                                </div>
                            </div>
                            <input type="text" name="classifies" hidden>
                        </div>
                        <div class="flex-roww" style="margin-top: 15px;align-items: start">
                            <div class="flex-roww grid-col-6">
                                <div class="form-group group">
                                    <div class="flex-roww">
                                        <label style="min-width: fit-content;margin-right: 10px">Ảnh đại diện</label>
                                        <input class="thumbnailInput" type="file" name="thumbnail" accept=".jpg, .jpeg, .png"  onchange="previewImage(event)" />
                                    </div>
                                    <div class="thumbnail-img-container grid-col-3" style="margin-top: 15px;position: relative;">
                                        <img src="./assets/img/thumbnail/banner.jpg" alt="" class="thumbnailPreview" data-file="" style="width:100%">
                                        <input type="text" value="" name="thumbnailInput" hidden class="exception">
                                        <div class="upload__img-close" onclick="removeThumbnail(event)"></div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="flex-roww" style="margin-top: 15px;" >
                            <div class="form-group group">
                                <div class="flex-roww">
                                    <label style="min-width: fit-content;margin-right: 10px">Ảnh chi tiết</label>
                                    <input type="file" multiple data-max_length="20" name="imgs" class="upload__inputfile" accept=".jpg, .jpeg, .png" onchange="previewImages(event);">
                                </div>
                                <div class="upload__box">
                                    <div class="upload__img-wrap">

                                    </div>
                                    <input type="text" name="imgUrls" value=""  hidden class="exception">
                                </div>
                            </div>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Mô tả</label>
                            <textarea contenteditable="true" class="editor" name="description" placeholder="Mô tả..." style="height: 100px;"></textarea>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Hướng dẫn sử dụng</label>
                            <textarea contenteditable="true" class="editor" name="direction" placeholder="Hướng dẫn ..." style="height: 100px;"></textarea>
                        </div>
                        <div style="margin-top: 15px;">
                            <label>Lượt truy cập</label>
                            <input type="number" class="form-control grid-col-4" name="prominence" value="0" placeholder="Nhập lượt truy cập" required hidden>
                        </div>
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <input type="text" name="action" value="add" hidden>
                            <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="button" onclick="saveAdd(event)">Thêm</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <div class="modall" id="lock-detail-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <h3 class="confirm-content" style="text-align: center">Xác nhận khóa </h3>
                    <p class="object text-center"></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="lockDetail(event);" type="button">Khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="unlock-detail-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <h3 class="confirm-content" style="text-align: center">Xác nhận mở khóa </h3>
                    <p class="object text-center"></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="activeDetail(event);" type="button">Mở khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="delete-detail-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">

                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <h3 class="confirm-content" style="text-align: center">Xác nhận xoá </h3>
                    <p class="object text-center"></p>
                    <input type="text" name="id" hidden>
                    <input type="text" name="action" value="deleteDetail" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" type="button" onclick="deleteDetail(event)">Xóa</button>
                    </div>
                </div>


            </div>
        </div>


    <div id="aproduct-response"></div>
    </main><!-- End #main -->
</div>
<%=script%>

</body>
</html>