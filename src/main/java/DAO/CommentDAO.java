package DAO;

import model.Comment;
import model.Constant;
import model.User;
import service.JDBCUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentDAO implements IDAO<Comment> {
    public static CommentDAO getInstance(){
        return new CommentDAO();
    }

    @Override
    public int insert(Comment comment) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into comments (productID,star,userID,content,orderID) values(?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, comment.productID);
            pst.setInt(2, comment.star);
            pst.setInt(3,comment.user.id);
            pst.setString(4,comment.content);
            pst.setInt(5,comment.orderID);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Comment comment) {
        return 0;
    }

    @Override
    public int delete(Comment comment) {
        return 0;
    }

    @Override
    public ArrayList<Comment> selectAll() {
        return null;
    }

    @Override
    public Comment selectById(int id) {
        return null;
    }

    public ArrayList<Comment> selectByProductID(int productID) {
        ArrayList<Comment> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select c.id as cmtID, c.content,c.star,u.id as userID, u.name as userName, c.time " +
                    "from comments c join users u on c.userID = u.id " +
                    "where c.productID = ? and c.avai != "+ Constant.DELETED+ " " +
                    "order by c.id desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productID);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("cmtID");
                String content = rs.getString("content");
                int star = rs.getInt("star");
                int userID = rs.getInt("userID");
                String userName = rs.getString("userName");
                LocalDateTime time = rs.getObject("time", LocalDateTime.class);
                res.add(new Comment(id,productID,star,new User(userID,userName),content,time,Constant.NONE,Constant.NONE));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
