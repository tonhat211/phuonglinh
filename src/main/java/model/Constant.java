package model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constant {
    public final static String EN_CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789" +
                    ".,;!?()[]{}:<>-_=/+*^%$#@&";

    public static final int NUM_OF_ITEMS_A_PAGE = 5;

    //avai
    public static final int UNACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int BLOCKED = -1;
    public static final int DELETED = -99;

    public static final int YES = 1;
    public static final int NO = 0;

    //order status
    public static final int ALL_STATUS = -1;
    public static final int WAITING = 0;
    public static final int CONFIRMED = 1;
    public static final int DELIVERYING = 2;
    public static final int COMPLETED = 3;
    public static final int CANCELED = 11;
    public static final int BACKING = 12;
    public static final int COMPLETE_BACKED = 13;
    public static String getOrderStatusName(int status) {
        switch (status) {
            case WAITING: return "Chờ xác nhận";
            case CONFIRMED: return "Đã xác nhận";
            case DELIVERYING: return "Vận chuyển";
            case COMPLETED: return "Đã hoàn thành";
            case CANCELED: return "Đã hủy";
        }
        return UNKNOWN;
    }
    public static String getActionStatusName(int status) {
        switch (status) {
            case WAITING: return "Chờ xác nhận";
            case CONFIRMED: return "Xác nhận";
            case DELIVERYING: return "Vận chuyển";
            case COMPLETED: return "Hoàn thành";
            case CANCELED: return "Hủy";
        }
        return UNKNOWN;
    }

    // sale program kind
    public static final int MAIN = 1;
    public static final int FLASH = 2;

    // log level
    public static final int NORMAL = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    // gender
    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final int UNDEFINED = 0;

    public static String getGenderString(int gender) {
        switch (gender) {
            case MALE: return "Nam";
            case FEMALE: return "Nữ";
        }
        return "Không xác định";
    }

    //menu
    public static final int NAM_CATEGORY_ID = 1;
    public static final int DONG_TRUNG_CATEGORY_ID = 2;
    public static final int RUOU_CATEGORY_ID = 3;
    public static final String NAM_CATEGORY = "nam";
    public static final String DONG_TRUNG_CATEGORY = "dongtrung";
    public static final String RUOU_CATEGORY = "ruou";
    public static final String ALL_String = "ALL";
    public static final String OTHER_CATEGORY = "OTHER";

    public static final String NAME_FILTER = "NAME";
    public static final String ID_FILTER = "ID";
    public static final String INGREDIENT_FILTER = "INGREDIENT";



    public static final int NONE=0;
    public static final int NONE_CONDITION =-1000;
    public static final String UNKNOWN= "unknown";

    public static String formatPrice(double price) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(price);
        return formattedPrice;
    }

    public static String formatLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = time.format(formatter);
        return formattedDate;
    }

    // hanh dong
    public static final String PLUS = "PLUS";

    // hash password
    public static String hashPassword(String password) {
        HashAlgorism hashAlgorism = new HashAlgorism();
        try {
            return hashAlgorism.hash(password);
        } catch (Exception e) {
            return "null";
        }
    }

    // payment
    public static final double SHIPPING_FEE_DEFAULT = 50000;

    public static String callFunction(String method) {
        String re ="  <script>" +
                method +
                "     </script>";
        return re;
    }

    public static final String THUMBNAIL_DIR = "/assets/img/thumbnail"; // Relative path
    public static final String PRODUCT_DETAIL_IMG_DIR = "/assets/img/detail";
    public static final String BANNER_IMG_DIR = "/assets/img/banner";
    public static final String LOGO_IMG_DIR = "/assets/img/logo";
    public static final String DEFAULT_IMG = "default.jpg";

    public static int PAYED=1;
    public static int NON_PAYED=0;

    public static final String BANNER_KIND = "BANNER";
    public static final String LOGO_KIND = "LOGO";

    public static final String DES_ID = "DES";
    public static final String CBC = "CBC";
    public static final String PKCS5Padding = "PKCS5Padding";
    public static final String DES_KEY = "-X*]bAEZ";
}
