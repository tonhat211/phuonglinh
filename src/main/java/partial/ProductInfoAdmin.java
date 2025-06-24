package partial;

import model.Image;
import model.Product;

import java.util.ArrayList;

public class ProductInfoAdmin {
    public Product product;
    public ArrayList<Image> images;
    public ProductInfoAdmin(Product product, ArrayList<Image> images) {
        this.images = images;
        this.product = product;
    }
}
