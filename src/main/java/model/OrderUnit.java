package model;

import values.StringValues;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class OrderUnit {
    public Order order;
    public ArrayList<OrderDetail> details;
    public OrderUnit() {
        this.details = new ArrayList<>();
    }

    public OrderUnit(Order order) {
        this.order = order;
        this.details = new ArrayList<>();
    }

    public OrderUnit(Order order,ArrayList<OrderDetail> details) {
        this.order = order;
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderUnit orderUnit = (OrderUnit) o;
        return Objects.equals(order, orderUnit.order);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(order);
    }

    public String toString() {
        String str = order.toString();
        for(OrderDetail detail : details) {
            str+="\t"+detail.toString()+"\n";
        }
        return str;
    }

    public int getID() {
        return order.id;
    }

    public int getStatus() {
        return order.status;
    }

    public String getStatusName() {
        return Constant.getOrderStatusName(order.status);
    }

    public String getCreateAt() {
        return Constant.formatLocalDateTime(order.createAt);
    }

    public String getUpdateAt() {
        return Constant.formatLocalDateTime(order.updateAt);
    }

    public String getTotalMoney() {
        return Constant.formatPrice(order.totalMoney);
    }

    public String getShippingFree() {
        return Constant.formatPrice(order.shippingFee);
    }
    public String getDiscountVoucher() {
        return Constant.formatPrice(order.discountVoucher);
    }

    public String getRecieverInfo() {
        return order.receiverInfo;
    }

    public boolean canComment() {
        if(order.status!=Constant.COMPLETED) return false;
        long days = ChronoUnit.DAYS.between(order.updateAt,LocalDateTime.now());
        if(days>7) return false;
        return true;
    }

    public ArrayList<OrderDetail> getDetails() {
        return details;
    }

    public String getProductsInfo() {
        StringBuilder sb = new StringBuilder();
        for(OrderDetail detail : details) {
            sb.append(detail.getInfo()+"\n");
        }
        return sb.toString();
    }

    public int getNextStatus() {
        if(this.order.status>=0 && this.order.status<3) return this.order.status+1;
        return -1;
    }

    public String getNextStatusName() {
        return Constant.getActionStatusName(getNextStatus());
    }

    public String getPaymentInfo() {
        if(order.isPayed==Constant.PAYED) return StringValues.PAYED;
        return StringValues.NONE_PAYED;
    }

    public static void main(String[] args) {

    }
}
