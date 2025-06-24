package values;

public class MessageValues {
    //voucher
    public static final String UNAVAILABLE_VOUCHER = "Voucher không hợp lệ!";
    public static final String UNAPPLIED_PRODUCT_LIST = "Kiểm tra lại danh sách sản phẩm được áp dụng!";
    public static final String EXPIRED_VOUCHER = "Voucher hết hạn!";
    public static final String CHANGE_PWD_SUCCESS = "Đổi mật khẩu thành công";
    public static final String PWD_RULE = "* Mật khẩu phải ít nhất 8 ký tự";
    public static final String SAME_PWD = "Mật khẩu không trùng khớp";
    public static final String CMT_SUCCESS = "Đánh giá thành công";
    public static final String EXIST_EMAIL = "Email đã tồn tại!";
    public static final String FAKE_EMAIL = "Email không có thật!";
    public static String getOTP_VERIFY_ACCOUNT_MESSAGE(String otp) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Mail</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center; font-family: 'Roboto', sans-serif;\">\n" +
                "    <h1 style=\"color: #ffc021\">"+WEB_NAME+"</h1>\n" +
                "    <p>Xin chào, mã xác minh tài khoản "+WEB_NAME+" của bạn là</p>\n" +
                "    <h2 style=\"color: #ff623d\">"+otp+"</h2>\n" +
                "    <p>Có hiệu lực trong vòng 5 phút, KHÔNG chia sẻ mã này với bất kì ai, kể cả nhân viên "+WEB_NAME+"</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

    }
    public static String getGENERATE_NEW_PWD(String newPwd) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Mail</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center; font-family: 'Roboto', sans-serif;\">\n" +
                "    <h1 style=\"color: #ffc021\">"+WEB_NAME+"</h1>\n" +
                "    <p>Xin chào, bạn đã chọn Quên mật khẩu </p>\n" +
                "<p>Mật khẩu mới tài khoản "+WEB_NAME+" của bạn là</p>\n" +
                "    <h2 style=\"color: #ff623d\">"+newPwd+"</h2>\n" +
                "    <p>Hãy thiết lập mật khẩu mới sau khi đăng nhập</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

    }

    public static String getGENERATE_NEW_PWD_FROM_ADMIN(String newPwd) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Mail</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center; font-family: 'Roboto', sans-serif;\">\n" +
                "    <h1 style=\"color: #ffc021\">"+WEB_NAME+"</h1>\n" +
                "    <p>Xin chào, đây là mail từ " + WEB_NAME +"</p>\n" +
                "<p>Mật khẩu mới tài khoản "+WEB_NAME+" của bạn là</p>\n" +
                "    <h2 style=\"color: #ff623d\">"+newPwd+"</h2>\n" +
                "    <p>Hãy thiết lập mật khẩu mới sau khi đăng nhập</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

    }

    public static String getNEW_ACCOUNT_FROM_ADMIN(String email,String pwd) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Mail</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center; font-family: 'Roboto', sans-serif;\">\n" +
                "    <h1 style=\"color: #ffc021\">"+WEB_NAME+"</h1>\n" +
                "    <p>Xin chào, đây là mail từ " + WEB_NAME +"</p>\n" +
                "<p>Thông tin tài khoản "+WEB_NAME+" mới của bạn là</p>\n" +
                "    <h2>Email đăng nhập: <span style=\"color: #ff623d\">"+email+"</span></h2>\n" +
                "    <h2>Mật khẩu đăng nhập: <span style=\"color: #ff623d\">"+pwd+"</span></h2>\n" +
                "    <p>Hãy thiết lập mật khẩu mới sau khi đăng nhập</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

    }
    public static String WEB_NAME = "NamPhuongLinh";
    public static String WRONG_OTP = "Mã OTP sai!";
    public static String getLock_Success(int id) {
        return "Đã khóa ID: " + id +" thành công!";
    }
    public static String getLock_Fail(int id) {
        return "Khóa ID: " + id +" thất bại!";
    }
    public static String getActive_Success(int id) {
        return "Mở khóa ID: " + id +" thành công!";
    }
    public static String getActive_Fail(int id) {
        return "Mở khóa ID: " + id +" thất bại!";
    }
    public static String getDelete_Success(int id) {
        return "Xóa ID: " + id +" thành công!";
    }
    public static String getDelete_Fail(int id) {
        return "Xóa ID: " + id +" thất bại!";
    }
    public static String getIssuePwd_Success(int id) {
        return "Cung cấp mật khẩu mới ID: " + id +" thành công!";
    }
    public static String getIssuePwd_Fail(int id) {
        return "Cung cấp mật khẩu mới ID: " + id +" thất bại!";
    }
    public static String getFind_Fail(int id) {
        return "Không tìm thấy ID: " + id +"!";
    }

    public static String UPDATE_SUCCESS= "Cập nhật thành công.";
    public static String UPDATE_FAIL= "Cập nhật thất bại!";
    public static String ADD_ACCOUNT_SUCCESS= "Thêm tài khoản thành công.";
    public static String ADD_ACCOUNT_FAIL= "Thêm tài khoản thất bại!";
    public static String NOT_ROLE= "Bạn không có quyền truy cập!";
    public static String DATABASE_ERROR= "Lỗi Database!";
    public static String ADD_SUCCESS= "Thêm thành công.";
    public static String ADD_FAIL= "Thêm thất bại!";
    public static String LOCK_SUCCESS= "Khóa thành công.";
    public static String LOCK_FAIL= "Khóa thất bại!";
    public static String ACTIVE_SUCCESS= "Kích hoạt thành công.";
    public static String ACTIVE_FAIL= "Kích hoạt thất bại!";
    public static String DELETE_SUCCESS= "Xóa thành công.";
    public static String DELETE_FAIL= "Xóa thất bại!";
    public static String INVALID_STATUS= "Trạng thái không hợp lệ!";
    public static String NOT_LOGIN= "Chưa đăng nhập!";
    public static String UPDATE_QTY_SUCCESS= "Cập nhật số lượng thành công!";
    public static String ADD_TO_CART_SUCCESS= "Thêm vào giỏ hàng thành công.";
    public static String DELETE_FROM_CART_SUCCESS= "Đã xóa khỏi giỏ hàng!";



}
