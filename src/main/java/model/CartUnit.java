package model;

public class CartUnit {
    public CartItem cartItem;
    public Product product;
    public ProductDetail productDetail;
    public SaleProgram saleProgram;

    public CartUnit(CartItem cartItem, Product product, ProductDetail productDetail,SaleProgram saleProgram) {
        this.cartItem = cartItem;
        this.product = product;
        this.productDetail = productDetail;
        this.saleProgram = saleProgram;
    }

    public CartUnit(CartItem cartItem, Product product, ProductDetail productDetail) {
        this.cartItem = cartItem;
        this.product = product;
        this.productDetail = productDetail;
    }

    public int getProductDetailID() {
        return productDetail.id;
    }

    public int getQty() {
        return cartItem.qty;
    }

    public int getSaleProgramID() {
        return saleProgram.id;
    }

    public double getPriceUnit() {
        return productDetail.price;
    }

    public int getProductID() {
        return product.id;
    }

    public String getProductName() {
        return product.name;
    }

    public String getDetailClassify() {
        return productDetail.classify;
    }

    public int getID() {
        return cartItem.id;
    }

    public String getProductThumbnail() {
        return product.thumbnail;
    }

    public String getTempPriceFormat() {
        return Constant.formatPrice(getPriceUnit());
    }

    public double getTotalMoney() {
        return getPriceUnit()*getQty();
    }

    public String getTotalMoneyFormat() {
        return Constant.formatPrice(getPriceUnit()*getQty());
    }
}
