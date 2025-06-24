package DAO;

import model.Image;
import model.InterfaceImage;
import model.Product;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterfaceImageDAO implements IDAO<InterfaceImage>{
    public static InterfaceImageDAO getInstance(){
        return new InterfaceImageDAO();
    }

    @Override
    public int insert(InterfaceImage image) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into interface_images (title,url,kind) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, image.title);
            pst.setString(2, image.url);
            pst.setString(3, image.kind);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(InterfaceImage image) {
        int re=0;
        return re;
//        try {
//            Connection conn = JDBCUtil.getConnection();
//            String sql = "update images set url = ? where productID =?;";
//            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1,image.getUrl());
//            pst.setInt(2,image.getParentID());
//            re = pst.executeUpdate();
//            JDBCUtil.closeConnection(conn);
//            return re;
//
//        } catch (SQLException ex) {
//            // TODO: handle exception
//            throw new RuntimeException(ex);
//        }
    }

    @Override
    public int delete(InterfaceImage image) {
        return 0;
    }

    @Override
    public ArrayList<InterfaceImage> selectAll() {
        ArrayList<InterfaceImage> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select * from interface_images;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                String kind = rs.getString("kind");
                res.add(new InterfaceImage(id,title,url,kind));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InterfaceImage selectById(int id) {
        return null;
    }

    public ArrayList<InterfaceImage> selectByKind(String kindin) {
        ArrayList<InterfaceImage> res = new ArrayList<>();
        try{
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from interface_images where kind=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kindin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                String kind = rs.getString("kind");
                res.add(new InterfaceImage(id,title,url,kind));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAllByKind(String kindin) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from interface_images where kind=?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,kindin);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException ex) {
            // TODO: handle exception
            throw new RuntimeException(ex);
        }
    }

    public int insertList(List<InterfaceImage> images) {
        StringBuilder values = new StringBuilder();
        if (images.isEmpty()) return 0;
        for(InterfaceImage image : images){
            values.append("('"+image.title+"','"+image.url+"','"+image.kind+"'),");
        }
        values.deleteCharAt(values.length()-1);
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into interface_images (title,url,kind) values "+values.toString()+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
//        ArrayList<Image> images = InterfaceImageDAO.getInstance().selectAll();
//        for(Image image : images){
//            System.out.println(image);
//        }
    }

}
