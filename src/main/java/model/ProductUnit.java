package model;

import DAO.ProductDetailDAO;
import DAO.ProductUnitDAO;
import com.google.gson.*;
import service.LocalDateTimeAdapter;

import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

public class ProductUnit {
    public Product product;
    public ProductDetail productDetail;
    public double star;
    public int totalComment;
    public SaleProgram saleProgram;
    public ArrayList<ProductDetail> details;

    public ProductUnit(Product product, ProductDetail productDetail, double star, int totalComment, SaleProgram saleProgram) {
        this.product = product;
        this.productDetail = productDetail;
        this.star = star;
        this.totalComment = totalComment;
        this.saleProgram = saleProgram;
    }

    // for detail


    public ProductUnit(Product product, ArrayList<ProductDetail> details) {
        this.product = product;
        this.details = details;
    }

    public String getName() {
        return product.name;
    }

    public int getID() {
        return product.id;
    }

    public String getCategory() {
        return product.category;
    }

//    public String getCategoryName() {
//        return Constant.getCategoryName(product.category);
//    }

    public double getMinPrice() {
        double min = details.get(0).price;
        for (int i = 1; i < details.size(); i++) {
            min = Math.min(min, details.get(i).price);
        }
        return min;
    }

    public double getMinPrePrice() {
        double min = details.get(0).prePrice;
        for (int i = 1; i < details.size(); i++) {
            min = Math.min(min, details.get(i).prePrice);
        }
        return min;
    }

    public String getMinPriceFormat() {
        return Constant.formatPrice(getMinPrice());
    }

    public String getMinPrePriceFormat() {
        return Constant.formatPrice(getMinPrePrice());
    }

    public String getDirection() {
        return product.direction;
    }

    public String getDescription() {
        return product.description;
    }

    public String getThumbnail() {
        return product.thumbnail;
    }

    public String getSaleProgramTitle() {
        return saleProgram.title;
    }

    public String getPriceFormat() {
        return Constant.formatPrice(productDetail.price);
    }

    public String getPrePriceFormat() {
        return Constant.formatPrice(productDetail.prePrice);
    }

    public static void main(String[] args) {
    }

}
