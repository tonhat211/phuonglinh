package model;

import DAO.VoucherDAO;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Voucher {
    public String id;
    public ArrayList<Integer> productIDs;
    public ArrayList<Integer> userIDs;
    public int discount;
    public double maxMoney;
    public int qty;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String description;
    public int avai;

    public Voucher(String id, ArrayList<Integer> productIDs, ArrayList<Integer> userIDs, int discount, double maxMoney, int qty, LocalDateTime startTime, LocalDateTime endTime, String description, int avai) {
        this.id = id;
        this.productIDs = productIDs;
        this.userIDs = userIDs;
        this.discount = discount;
        this.maxMoney = maxMoney;
        this.qty = qty;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.avai = avai;
    }



    public String getProductIDsJSON() {
        Gson gson = new Gson();
        String json = gson.toJson(productIDs);
        return json;
    }

    public String getUserIDsJSON() {
        Gson gson = new Gson();
        String json = gson.toJson(userIDs);
        return json;
    }

    public boolean checkAvai() {
        if (avai!=Constant.ACTIVE) return false;
        if(startTime ==null || endTime==null) return true;
        if (!LocalDateTime.now().isAfter(startTime) || !LocalDateTime.now().isBefore(endTime)) return false;
        return true;
    }

    public boolean checkAppliedProductIDs(ArrayList<Integer> productIDsin) {
        return productIDs.containsAll(productIDsin);
    }

    public boolean checkAppliedUserIDs(int userID) {
        return userIDs.contains(userID);
    }

    public boolean checkApplying(int userID,ArrayList<Integer> productIDsin) {
//        if(checkAvai()) {
//
//        } else return false;
        if(!userIDs.isEmpty()) {
            if(!checkAppliedUserIDs(userID)) return false;
        }
        if(!productIDs.isEmpty()) {
            if(!checkAppliedProductIDs(productIDsin)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id='" + id + '\'' +
                ", productIDs=" + productIDs +
                ", userIDs=" + userIDs +
                ", discount=" + discount +
                ", maxMoney=" + maxMoney +
                ", qty=" + qty +
                ", starTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", avai=" + avai +
                '}';
    }
//
//    public void isActive() {
//        if(this.checkAvai())
//    }

    public static void main(String[] args) {
        Voucher voucher = VoucherDAO.getInstance().selectByVoucherID("max100");
//
//        System.out.println(LocalDateTime.now());
//        System.out.println(voucher.endTime);
//        System.out.println(LocalDateTime.now().isBefore(voucher.endTime));
//        System.out.println(voucher.check(1,new ArrayList<>()));

    }
}
