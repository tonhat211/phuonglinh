package partial;

import model.Constant;
import model.Product;

public class ProductAdmin {
    public Product product;
    public int saledQty;

    public ProductAdmin(Product product, int saledQty) {
        this.product = product;
        this.saledQty = saledQty;
    }

    public boolean isLocked() {
        if(this.product.avai!= Constant.ACTIVE) return true;
        else return false;
    }

    public int getProductID() {
        return product.id;
    }

    public String getThumbnail() {
        return product.thumbnail;
    }

    public String getName() {
        return product.name;
    }

    public int getProminenece() {
        return product.prominence;
    }

    public String getCategory() {
        return product.category;
    }
}
