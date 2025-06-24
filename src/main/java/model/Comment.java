package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {
    public int id;
    public int productID;
    public int star;
    public User user;
    public String content;
    public LocalDateTime time;
    public int orderID;
    public int avai;

    public Comment(int id, int productID, int star, User user, String content, LocalDateTime time, int orderID, int avai) {
        this.id = id;
        this.productID = productID;
        this.star = star;
        this.user = user;
        this.content = content;
        this.time = time;
        this.orderID = orderID;
        this.avai = avai;
    }

    public String getUserName() {
        return user.name;
    }
}
