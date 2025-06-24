package model;

import values.MessageValues;
import values.StringValues;

public class OrderDetail {
    public int id;
    public int orderID;
    public ProductDetail productDetail;
//    public int productID;
    public String productName;
    public String detailClassify;
    public int qty;
    public double priceUnit;

    public OrderDetail(int id, int orderID, ProductDetail productDetail, String productName, String detailClassify, int qty, double priceUnit) {
        this.id = id;
        this.orderID = orderID;
        this.productDetail = productDetail;
//        this.productID = productID;
        this.productName = productName;
        this.detailClassify = detailClassify;
        this.qty = qty;
        this.priceUnit = priceUnit;
    }

    public String getProductThumbnail() {
        return productDetail.product.thumbnail;
    }

    public String getTempPriceFormat() {
        return Constant.formatPrice(priceUnit);
    }

    public int getProductID() {
        return productDetail.product.id;
    }

    public String getInfo() {
        return "ID: "+ this.productDetail.id +" - " + this.productName +" (" + this.detailClassify + ") x " + StringValues.QTY+": " + this.qty;
    }
}
