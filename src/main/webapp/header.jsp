<%@ page import="model.User" %>
<%@ page import="model.Constant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thietbididong.com</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/header.js"></script>
<%--    <script type="text/javascript" src="./assets/js/login.js"></script>--%>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/loading.js"></script>

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
    <link href="./assets/css/header.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">
    <link href="./assets/css/loading.css" rel="stylesheet">


    <!-- css tu them   -->
    <link href="./assets/css/smartphone.css" rel="stylesheet">
    <link href="./assets/css/index.css" rel="stylesheet">
    <link href="./assets/css/login.css" rel="stylesheet">

</head>
<body>
<%
    User userLogging = (User) session.getAttribute("userLogging");
//    userLogging = new User(1,"Minh Nhat","minhnhat@gmail.com");
    String currentCategory= (String) session.getAttribute("category");
    if (currentCategory==null) currentCategory = Constant.NAM_CATEGORY;
    String logo = (String) session.getAttribute("logo");
%>
<div class="header pd10-20 header_mob header_tab">
    <div id="toast-header" class="toast">
    </div>
    <div id="toast-2">

    </div>
    <div class="header_top flex-roww">
        <a href="login?a=index" class="logo grid-col-2 pd0 grid-col-5_mob">
            <img src="./assets/img/logo/<%=logo%>" alt="PhuongLinh" style="color:black;">
        </a>
        <div class="flex-roww grid-col-10 grid-col-12_mob" style="justify-content: space-between;">
            <button class="menu-btn_mob" onclick="showMenu()"><i class="bi bi-list"></i></button>
            <form action="product" class="search-bar grid-col-4 grid-col-8_mob grid-col-7_tab">
                <button style="border: none;background: none;"><i class="fa-solid fa-magnifying-glass"></i></button>
                <input type="text" name="a" value="search" hidden>
                <input type="text" placeholder="Bạn tìm gì..." class="input-search" name="search-input">
            </form>
            <div class="user_part flex-roww grid-col-3">
                <%
                    if(userLogging==null) {
                %>
                <div class="cart flex-roww" href="" onclick="openHeaderModal('.login-require-modal');">
                    <i class="bi bi-cart3"></i>
                    <p>Giỏ hàng</p>
                </div>
                <%
                } else {
                %>
                <a class="cart flex-roww" href="cart">
                    <i class="bi bi-cart3"></i>
                    <p>Giỏ hàng</p>
                </a>
                <%
                    }
                %>
                <script>
                    // function openModal(event, modal) {
                    //     event
                    // }
                </script>
                <%
                    if(userLogging==null) {
                %>
                    <a class="user flex-roww" href="login">
                        <i class="bi bi-person"></i>
                        <p>Đăng nhập</p>
                    </a>
                <%
                    } else  {
                %>
                    <div class="user flex-roww">
                        <i class="bi bi-person"></i>
                        <p><%=userLogging.name%></p>
                        <ul class="sub-menu">
                            <li>
                                <a href="order" class="li-order">Đơn hàng</a>
                            </li>
                            <li>
                                <a href="profile?a=address" class="li-address" onclick="manageAddress(event);">Sổ địa chỉ</a>
                            </li>
                            <li>
                                <a href="profile?a=info" class="li-profile" onclick="manageProfile(event);">Quản lý</a>
                            </li>
                            <li>
                                <a href="login?a=logout" class="li-logout">Đăng xuất</a>
                            </li>
                        </ul>
                    </div>
                    <%
                        }
                    %>

            </div>
        </div>

    </div>
    <div class="header_bottom">
        <div class="overlay_mob" onclick="hideMenu()"></div>
        <ul class="main-menu flex-roww grid-col-5_mob" id="header-main-menu">
            <li>
                <a class="<%=currentCategory.equalsIgnoreCase(Constant.NAM_CATEGORY)?"active":""%>" href="product?a=query&c=nam" data-value="nam"><i class="bi bi-phone"></i>
                    Nấm</a>
            </li>
            <li>
                <a class="<%=currentCategory.equalsIgnoreCase(Constant.DONG_TRUNG_CATEGORY)?"active":""%>" href="product?a=query&c=dongtrung" data-value="dongtrung"><i class="bi bi-tablet-landscape"></i>
                    Đông Trùng</a>
            </li>
            <li>
                <a class="<%=currentCategory.equalsIgnoreCase(Constant.RUOU_CATEGORY)?"active":""%>" href="product?a=query&c=ruou" data-value="ruou"><i class="bi bi-tablet-landscape"></i>
                    Rượu</a>
            </li>
            <li>
                <a class="<%=currentCategory.equalsIgnoreCase(Constant.OTHER_CATEGORY)?"active":""%>" href="product?a=query&c=other" data-value="other"><i class="bi bi-tablet-landscape"></i>
                    Khác</a>
            </li>
        </ul>
    </div>
    <div id="modal-container">
        <!--   cap nhat so dia chi -->
        <div class="modall address-modal address-modal_mob address-modal_tab" onclick="removeModal('#modal-container');">
            <div class="modall-content" style="width: 80%; background-color: unset;" onclick="event.stopPropagation()">
                <div class="flex-roww" style="align-items: start;justify-content: space-between;height: 100%;">
                    <div class="sub-content" style="margin-right: 10px;height: 100%; padding-right: 5px;position: relative" onclick="event.stopPropagation();">
                        <h3 style="text-align: center;padding-right: 10px;">Sổ địa chỉ</h3>
                        <i class="bi bi-plus-square btn-add-address_mob btn-add-address_tab" onclick="initAddAddress('#editAddressForm');"></i>
                        <div class="update-container">
                            <div id="address-container" style="padding-right: 10px;">
                                <div class="address-item">
                                    <div class="flex-roww" style="justify-content: space-between; align-items: start">
                                        <div class="address-info">
                                            <p><span class="receiver">Hung Dong Nguyen</span> |   <span class="phone">092413211</span></p>
                                            <p class="id" hidden="">5</p>
                                            <p style="margin-top: 5px">Địa chỉ:</p>
                                            <div>
                                                <p class="address" style="margin-left: 10px;"><span class="street">510 quốc lộ 13</span> - <span class="village">Hiệp Bình Chánh</span> - <span class="district">Thủ Đức</span> - <span class="province">Hồ Chí Minh</span></p>
                                            </div>
                                        </div>
                                        <div class="flex-coll update-action">
                                            <i class="bi bi-pencil-square btn-edit" onclick="initUpdateAddress(event,'.address-item','#editAddressForm')"></i>
                                            <i class="bi bi-trash btn-delete" onclick="initDeleteAddress(event,'.address-item','.modall','.address-confirm')"></i>
                                            <!--                                <i class="fa-solid fa-ellipsis editBtn" onclick="showEditAddress.call(this,event)"></i>-->
                                        </div>
                                    </div>
                                    <div class="seperate-horizontal-90"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="sub-content add-address-content_mob add-address-content_tab" id="address-form-container" style="margin-left: 10px;" onclick="event.stopPropagation();">
                        <form id="editAddressForm" action="profile">
                            <p class="bold-text-6 edit-title" style="text-align: center">Thêm địa chỉ</p>
                            <div class="flex-roww" style="margin-top: 10px">
                                <input type="text"  class="form-control w-80" id="receiver" name="receiver"  aria-describedby="" placeholder="Tên người nhận" value="">
                                <div style="margin:0 10px"></div>
                                <input type="text" class="form-control w-80" id="phone" name="phone"  aria-describedby="" placeholder="Số điện thoại nhận hàng" value="">
                            </div>
                            <p>Địa chỉ:</p>
                            <div style="margin-left:10px">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="province" name="province"  aria-describedby="" placeholder="Thành phố/Tỉnh" value="">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="district" name="district"  aria-describedby="" placeholder="Quận/Huyện" value="">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="village" name="village"  aria-describedby="" placeholder="Phường/Xã" value="">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="street" name="street"  aria-describedby="" placeholder="Chi tiết" value="">
                            </div>
                            <input type="text" class="form-control w-80" id="id" name="id"  aria-describedby="" placeholder="ID account" value="" hidden>
                            <input type="text" class="form-control w-80" name="action"  aria-describedby="" placeholder="ID account" value="addAddress" hidden>
                            <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                                <button class="btn btn-outline-primary" type="button" onclick="cancelEdit('#editAddressForm');">Hủy</button>
                                <button class="btn btn-primary btn-edit" type="submit">Lưu</button>
                            </div>
                        </form>
                        <script>
                            const updateAddressForm = document.querySelector('#editAddressForm');
                            updateAddressForm.addEventListener('submit', function(e) {
                                console.log("call submit");
                                e.preventDefault();
                                var formData = new FormData(this);
                                let id = formData.get('id');
                                let receiver = formData.get('receiver');
                                let phone = formData.get("phone");
                                let street = formData.get("street");
                                let village = formData.get("village");
                                let district = formData.get("district");
                                let province = formData.get("province");
                                let action = formData.get("action");
                                if(action=="updateAddress") {
                                    updateAddressAjax(action,id,receiver,phone,street,village,district,province).then(function(addresses) {
                                        renderAddressList(addresses); // Gọi render khi dữ liệu đã được lấy về
                                    }).catch(function(error) {
                                        console.error("Có lỗi khi lấy danh sách địa chỉ:", error);
                                        $('#address-container').html('<p>Không thể tải danh sách địa chỉ.</p>'); // Hiển thị thông báo lỗi nếu có
                                    });
                                } else {
                                    addAddressAjax(action,id,receiver,phone,street,village,district,province).then(function(addresses) {
                                        renderAddressList(addresses); // Gọi render khi dữ liệu đã được lấy về
                                    }).catch(function(error) {
                                        console.error("Có lỗi khi lấy danh sách địa chỉ:", error);
                                        $('#address-container').html('<p>Không thể tải danh sách địa chỉ.</p>'); // Hiển thị thông báo lỗi nếu có
                                    });
                                }
                            });
                        </script>
                    </div>
                </div>

            </div>
            <div class="modal-confirm sub-content address-confirm grid-col-10_mob grid-col-10_tab" onclick="event.stopPropagation();" >
                <p class="bold-text-6 edit-title" style="text-align: center">Xác nhận xóa địa chỉ</p>
                <div class="address-item">
                    <div class="flex-roww" style="justify-content: space-between; align-items: start">
                        <div class="address-info">
                            <p>Người nhận: <span class="receiver">nhat</span></p>
                            <p>Số điện thoại: <span class="phone">091250154</span></p>
                            <p class="id" hidden>0</p>
                            <p>Địa chỉ: <span class="address">nong lam</span></p>
                        </div>
                    </div>
                </div>
                <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                    <button class="btn btn-third" type="button" onclick="cancelDelete(event,'.address-confirm');">Hủy</button>
                    <button class="btn btn-primary btn-confirm-delete" onclick="deleteAddress()" type="button">Xóa</button>
                </div>
                <script>
                    function deleteAddress() {
                        let id =document.querySelector('.address-confirm .id').innerText;
                        deleteAddressAjax(id).then(function(addresses) {
                            renderAddressList(addresses); // Gọi render khi dữ liệu đã được lấy về
                        }).catch(function(error) {
                            console.error("Có lỗi khi lấy danh sách địa chỉ:", error);
                            $('#address-container').html('<p>Không thể tải danh sách địa chỉ.</p>'); // Hiển thị thông báo lỗi nếu có
                        });
                    }

                </script>
            </div>
        </div>

        <div class="modall info-modal info-modal_mob info-modal_tab" onclick="removeModal('#modal-container');">
            <div class="modall-content" style="width: 80%; background-color: unset;top: 35%;" onclick="event.stopPropagation()">
                <div class="flex-roww" style="align-items: start;justify-content: center;height: 100%;">
                    <div class="sub-content grid-col-12_mob" style="width: 50%;margin-top: 0" onclick="event.stopPropagation();">
                        <form id="editInfoForm" action="profile" method="post">
                        	<input type="text" name="a" value="updateInfo" hidden>
                            <h3 style="text-align: center;padding-right: 10px;">Thông tin cá nhân</h3>
                            <div class="flex-roww update-action" style="justify-content: space-between;">
<%--                                <div class="flex-roww" style="font-size: 13px; font-style: italic; color: #ccc">--%>
<%--                                    <p>Ngày tham gia</p>--%>
<%--                                    <input type="text" class="presentation-input" style="margin-bottom: 0; margin-left: 10px;font-style: italic; color: #ccc" name="dateIn" value="" readonly>--%>
<%--                                </div>--%>
                                <i class="bi bi-pencil-square btn-edit" onclick="initUpdateInfo(event,'#editInfoForm');"></i>
                            </div>
                            <p class="bold-text-6 edit-title" style="text-align: center;color: var(--bold-color);display: none;">Cập nhật thông tin</p>
							<div class="flex-roww" style="justify-content: space-between;">
                                <div class="form-group">
                                    <label>Họ tên</label>
                                    <input type="text" class="form-control" name="name" placeholder="Nhập họ và tên" required readonly>
                                </div>
							</div>
                            
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Nhập email" required readonly>
                            </div>
                            <div class="form-group">
                                <label>Số điện thoại</label>
                                <input type="text" class="form-control" name="phone" placeholder="Nhập số điện thoại" required readonly>
                            </div>
                            <div class="form-group" style="margin-left: 10px;">
                                <label>Ngày sinh</label>
                                <input type="date" class="form-control" name="birthday" placeholder="Nhập ngày sinh" required readonly>
                            </div>

                            <p>Giới tính:</p>
                            <div class="flex-roww" style="justify-content: space-around;margin-bottom: 10px;">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="exampleRadios1" value="1" checked disabled>
                                    <label class="form-check-label" for="exampleRadios1">
                                        Nam
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="exampleRadios2" value="2" disabled>
                                    <label class="form-check-label" for="exampleRadios2">
                                        Nữ
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="exampleRadios3" value="0" disabled>
                                    <label class="form-check-label" for="exampleRadios3">
                                        Không muốn tiết lộ
                                    </label>
                                </div>
                            </div>

                            <a href="" onclick="initChangePwd(event,'#edit-pwd-form-container')">Đổi mật khẩu</a>
                            <div class="flex-roww btn-edit-container" style="margin-top:20px; justify-content: space-around; display: none;">
                                <button class="btn btn-outline-primary" type="button" onclick="cancelEditInfo('#editInfoForm');">Hủy</button>
                                <button class="btn btn-primary btn-edit" type="submit">Lưu</button>
                            </div>
                        </form>
                    </div>
                    <div class="sub-content grid-col-12_mob" id="edit-pwd-form-container"  style="margin-left: 10px;width: 50%;margin-top: 0;" onclick="event.stopPropagation();">
                        <form id="editPasswordForm">
                            <input type="text" name="action" value="updatePassword" hidden>
                            <p class="bold-text-6 edit-title" style="text-align: center;color:var(--bold-color);">Đổi mật khẩu</p>
                            <div class="form-group">
                                <label for="current-password">Mật khẩu hiện tại</label>
                                <div class="flex-roww">
                                    <input type="password" class="form-control btrr0 bbrr0" id="current-password" name="currentPassword" placeholder="Nhập mật khẩu hiện tại" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                                </div>
                                <span class="form-error pwd-error"></span>
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu mới</label>
                                <div class="flex-roww">
                                    <input type="password" class="form-control btrr0 bbrr0" id="password" name="password" placeholder="Nhập mật khẩu hiện mới" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                                </div>
                                <span class="form-error new-pwd-error"></span>
                            </div>
                            <div class="form-group">
                                <label for="password">Nhập lại mật khẩu mới</label>
                                <div class="flex-roww">
                                    <input type="password" class="form-control btrr0 bbrr0" id="repassword" name="repassword" placeholder="Nhập lại mật khẩu hiện mới" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                                </div>
                                <span class="form-error re-new-pwd-error"></span>

                            </div>
                            <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                                <button class="btn btn-outline-primary" type="button" onclick="cancelChangePwd('#edit-pwd-form-container');">Hủy</button>
                                <button class="btn btn-primary btn-edit" type="submit">Lưu</button>
                            </div>
                        </form>
                    </div>

                </div>

            </div>
        </div>
        <div class="modall login-require-modal" onclick="removeModal('#modal-container');">
            <div class="modall-content" style="width: 80%; background-color: unset;">
                <div style="width: 100%; background-color: white;border-radius: 10px;padding: 20px;">
                    <div class="flex-roww" style="justify-content: center;margin:22px 0;font-size: 19px">
                        <p>Bạn chưa đăng nhập, vui lòng đăng nhập</p>
                    </div>
                    <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                        <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                        <a href="login" class="btn btn-primary btn-filter" style="color: white;">Đăng nhập</a>
                    </div>
                </div>


            </div>
        </div>

    </div>
    <div id="header-response"></div>
    <div class="loading-container" id="loading">
        <div class="loading-content">
            <div class="loading-element">
                <p class="loading-item">N</p>
                <p class="loading-item">a</p>
                <p class="loading-item">m</p>
                <p class="loading-item">P</p>
                <p class="loading-item">h</p>
                <p class="loading-item">u</p>
                <p class="loading-item">o</p>
                <p class="loading-item">n</p>
                <p class="loading-item">g</p>
                <p class="loading-item">L</p>
                <p class="loading-item">i</p>
                <p class="loading-item">n</p>
                <p class="loading-item">h</p>
            </div>
        </div>
        <script>


            // isLoading(true);
            // setTimeout(() => {
            //     console.log("timeout");
            //     isLoading(false);
            // }, 4000);

        </script>

    </div>
</div>

</body>
</html>