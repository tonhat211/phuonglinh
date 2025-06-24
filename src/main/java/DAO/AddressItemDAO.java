package DAO;

import model.AddressItem;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressItemDAO implements IDAO<AddressItem>{
    public static AddressItemDAO getInstance(){
        return new AddressItemDAO();
    }
    @Override
    public int insert(AddressItem a) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into addressItems (userID,receiver,phone,province,district,village,detail) values (?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,a.userID); // dung tam id cua address lam id user
            pst.setString(2,a.receiver);
            pst.setString(3,a.phone);
            pst.setString(4,a.province);
            pst.setString(5,a.district);
            pst.setString(6,a.village);
            pst.setString(7,a.detail);

            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(AddressItem a) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update addressItems set receiver=?, " +
                    "phone=?, " +
                    "province=?, " +
                    "district=?, " +
                    "village=?, " +
                    "detail=? " +
                    " where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,a.receiver);
            pst.setString(2,a.phone);
            pst.setString(3,a.province);
            pst.setString(4,a.district);
            pst.setString(5,a.village);
            pst.setString(6,a.detail);
            pst.setInt(7,a.id);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(AddressItem address) {
        int re = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from addressItems where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,address.id);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<AddressItem> selectAll() {
        return null;
    }

    @Override
    public AddressItem selectById(int idin) {
        AddressItem re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from addressItems where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int userID = rs.getInt("userID");
                String receiver = rs.getString("receiver");
                String phone = rs.getString("phone");
                String province = rs.getString("province");
                String district = rs.getString("district");
                String village = rs.getString("village");
                String detail = rs.getString("detail");
                re = new AddressItem(id, userID,receiver, phone, province, district, village, detail);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<AddressItem> selectByUserID(int userIDin) {
        ArrayList<AddressItem> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from addressItems where userID = ? order by updateAt desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int userID = rs.getInt("userID");
                String receiver = rs.getString("receiver");
                String phone = rs.getString("phone");
                String province = rs.getString("province");
                String district = rs.getString("district");
                String village = rs.getString("village");
                String detail = rs.getString("detail");
                res.add(new AddressItem(id, userID,receiver, phone, province, district, village, detail));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
