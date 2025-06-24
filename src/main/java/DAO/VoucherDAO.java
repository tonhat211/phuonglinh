package DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Constant;
import model.Voucher;
import service.JDBCUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VoucherDAO implements IDAO<Voucher> {
    public static VoucherDAO getInstance(){
        return new VoucherDAO();
    }
    @Override
    public int insert(Voucher voucher) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into vouchers (id,productIDs,discount,qty,startTime,endTime,description,avai,maxMoney,userIDs) values(?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, voucher.id);
            pst.setString(2, voucher.getProductIDsJSON());
            pst.setInt(3, voucher.discount);
            pst.setInt(4, voucher.qty);
            pst.setObject(5, voucher.startTime);
            pst.setObject(6, voucher.endTime);
            pst.setString(6, voucher.description);
            pst.setInt(8, voucher.avai);
            pst.setDouble(9, voucher.maxMoney);
            pst.setString(10, voucher.getUserIDsJSON());
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Voucher voucher) {
        return 0;
    }

    @Override
    public int delete(Voucher voucher) {
        return 0;
    }

    @Override
    public ArrayList<Voucher> selectAll() {
        ArrayList<Voucher> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from vouchers where avai !=" + Constant.DELETED + " order by createAt desc";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String productIDsJson = rs.getString("productIDs");
                String userIDsJson = rs.getString("userIDs");
                int discount = rs.getInt("discount");
                Double maxMoney = rs.getDouble("maxMoney");
                int qty = rs.getInt("qty");
                LocalDateTime startTime =rs.getObject("startTime",LocalDateTime.class);
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                String description = rs.getString("description");
                int avai = rs.getInt("avai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
                ArrayList<Integer> productIDs = new ArrayList<>();
                if(productIDsJson!=null) productIDs = gson.fromJson(productIDsJson, type);
                ArrayList<Integer> userIDs = new ArrayList<>();
                if(userIDsJson!=null) userIDs = gson.fromJson(userIDsJson, type);
                res.add(new Voucher(id,productIDs,userIDs,discount,maxMoney,qty,startTime,endTime,description,avai));

            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Voucher selectById(int idin) {
        Voucher voucher = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from saleprograms where id = ? and avai !=" + Constant.DELETED;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String productIDsJson = rs.getString("productIDs");
                String userIDsJson = rs.getString("userIDs");
                int discount = rs.getInt("discount");
                Double maxMoney = rs.getDouble("maxMoney");
                int qty = rs.getInt("qty");
                LocalDateTime startTime =rs.getObject("startTime",LocalDateTime.class);
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                String description = rs.getString("description");
                int avai = rs.getInt("avai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
                ArrayList<Integer> productIDs = new ArrayList<>();
                if(productIDsJson!=null) productIDs = gson.fromJson(productIDsJson, type);
                ArrayList<Integer> userIDs = new ArrayList<>();
                if(userIDsJson!=null) userIDs = gson.fromJson(userIDsJson, type);
                voucher = new Voucher(id,productIDs,userIDs,discount,maxMoney,qty,startTime,endTime,description,avai);

            }

            JDBCUtil.closeConnection(conn);
            return voucher;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Voucher selectByVoucherID(String voucherID) {
        Voucher voucher = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from vouchers where id = ? and avai !=" + Constant.DELETED;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, voucherID);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String productIDsJson = rs.getString("productIDs");
                String userIDsJson = rs.getString("userIDs");
                int discount = rs.getInt("discount");
                Double maxMoney = rs.getDouble("maxMoney");
                int qty = rs.getInt("qty");
                LocalDateTime startTime =rs.getObject("startTime",LocalDateTime.class);
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                String description = rs.getString("description");
                int avai = rs.getInt("avai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
                ArrayList<Integer> productIDs = new ArrayList<>();
                if(productIDsJson!=null) productIDs = gson.fromJson(productIDsJson, type);
                ArrayList<Integer> userIDs = new ArrayList<>();
                if(userIDsJson!=null) userIDs = gson.fromJson(userIDsJson, type);
                voucher = new Voucher(id,productIDs,userIDs,discount,maxMoney,qty,startTime,endTime,description,avai);

            }

            JDBCUtil.closeConnection(conn);
            return voucher;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int rejectUser(int userID, String voucherID) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "UPDATE vouchers\n" +
                    "SET userIDs = JSON_REMOVE(userIDs, JSON_UNQUOTE(JSON_SEARCH(userIDs, 'one', ?)))\n" +
                    "WHERE JSON_CONTAINS(userIDs, JSON_ARRAY(?)) and id = ?; ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, userID);
            pst.setString(3, voucherID);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int decreaseQty(String voucherID) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update vouchers set qty = qty-1 where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, voucherID);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int lockVoucher(String voucherID) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update vouchers set avai = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Constant.BLOCKED);
            pst.setString(2, voucherID);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int activeVoucher(String voucherID) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update vouchers set avai = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Constant.ACTIVE);
            pst.setString(2, voucherID);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int deleteVoucher(String voucherID) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update vouchers set avai = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Constant.DELETED);
            pst.setString(2, voucherID);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int updateBase(Voucher voucher) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update vouchers set discount = ?, maxMoney=?, qty=?, startTime=?, endTime=? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, voucher.discount);
            pst.setDouble(2, voucher.maxMoney);
            pst.setInt(3, voucher.qty);
            pst.setObject(4, voucher.startTime);
            pst.setObject(5, voucher.endTime);
            pst.setString(6, voucher.id);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int updateDescription(String id, String description) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update vouchers set description = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, description);
            pst.setString(2, id);
            System.out.println(pst);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
