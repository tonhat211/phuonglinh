package model;

public class AddressItem {
    public int id;
    public int userID;
    public String receiver;
    public String phone;
    public String province;
    public String district;
    public String village;
    public String detail;

    public AddressItem(int id, int userID, String receiver, String phone, String province, String district, String village, String detail) {
        this.id = id;
        this.userID = userID;
        this.receiver = receiver;
        this.phone = phone;
        this.province = province;
        this.district = district;
        this.village = village;
        this.detail = detail;
    }

    public AddressItem(int id) {
        this.id = id;
    }

    public String getAddress() {
        return province +", "+ district+", " + village+", " + detail;
    }


}
