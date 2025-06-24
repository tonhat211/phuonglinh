package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    public int id;
    public double totalMoney;
    public double shippingFee;
    public double discountVoucher;
    public LocalDateTime createAt;
    public LocalDateTime updateAt;
    public int userID;
    public String receiverInfo;
    public int isPayed;
    public int status;

    public Order(int id, double totalMoney, double shippingFee, double discountVoucher, LocalDateTime createAt, LocalDateTime updateAt, int userID, String receiverInfo, int isPayed, int status) {
        this.id = id;
        this.totalMoney = totalMoney;
        this.shippingFee = shippingFee;
        this.discountVoucher = discountVoucher;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.userID = userID;
        this.receiverInfo = receiverInfo;
        this.isPayed = isPayed;
        this.status = status;
    }

    public Order(int id) {
        this.id = id;
    }

    // order: insert


    public Order(double totalMoney, double shippingFee, double discountVoucher, int userID, String receiverInfo) {
        this.totalMoney = totalMoney;
        this.shippingFee = shippingFee;
        this.discountVoucher = discountVoucher;
        this.userID = userID;
        this.receiverInfo = receiverInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
