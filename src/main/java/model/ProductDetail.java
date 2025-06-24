package model;

import java.sql.Date;
import java.util.List;

public class ProductDetail {
    public int id;
    public int productID;
    public String classify;
    public double price;
    public double prePrice;
    public int qty;
    public int avai;
    public Product product;

    public ProductDetail(int id, int productID, String classify, double price, double prePrice, int qty, int avai) {
        this.id = id;
        this.productID = productID;
        this.classify = classify;
        this.price = price;
        this.prePrice = prePrice;
        this.qty = qty;
        this.avai = avai;
    }

    public ProductDetail(int id) {
        this.id = id;
    }

    // cartUnit
    public ProductDetail(int id, String classify, double price, double prePrice) {
        this.id = id;
        this.classify = classify;
        this.price = price;
        this.prePrice = prePrice;
    }

    //order detail
    public ProductDetail(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    // comment
    public ProductDetail(int id, int productID, String classify) {
        this.id = id;
        this.productID = productID;
        this.classify = classify;
    }

    public boolean isExhausted() {
        if(this.qty>0) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", productID=" + productID +
                ", classify='" + classify + '\'' +
                ", price=" + price +
                ", prePrice=" + prePrice +
                ", qty=" + qty +
                ", avai=" + avai +
                ", product=" + product +
                '}';
    }
}
