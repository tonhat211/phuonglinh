package model;

public class CartItem {
    public int id;
    public int productDetailID;
    public int qty;
    public int userId;

    public CartItem(int id, int productDetailID, int qty, int userId) {
        this.id = id;
        this.productDetailID = productDetailID;
        this.qty = qty;
        this.userId = userId;
    }

    // dung khi truyen tham so vao database
    public CartItem(int id) {
        this.id = id;
    }
}
