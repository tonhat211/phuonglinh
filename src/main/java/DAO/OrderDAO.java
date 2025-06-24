package DAO;

import model.*;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDAO implements IDAO<Order> {
    public static OrderDAO getInstance(){
        return new OrderDAO();
    }

    @Override
    public int insert(Order order) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into orders (totalMoney,shippingFee,userID,receiverInfo,discountVoucher) values(?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, order.totalMoney);
            pst.setDouble(2, order.shippingFee);
            pst.setInt(3, order.userID);
            pst.setString(4, order.receiverInfo);
            pst.setDouble(5, order.discountVoucher);
            re = pst.executeUpdate();
            if(re==1) {
                sql = "select max(id) as id from orders where userID= ?;";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, order.userID);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    re = rs.getInt("id");
                }
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Order order) {
        return 0;
    }

    @Override
    public int delete(Order order) {
        return 0;
    }

    @Override
    public ArrayList<Order> selectAll() {
        return null;
    }

    @Override
    public Order selectById(int idin) {
        Order re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double totalMoney = rs.getDouble("totalMoney");
                double shippingFee = rs.getDouble("shippingFee");
                double discountVoucher = rs.getDouble("discountVoucher");
                LocalDateTime createAt = rs.getObject("createAt",LocalDateTime.class);
                LocalDateTime updateAt = rs.getObject("updateAt",LocalDateTime.class);
                int userID = rs.getInt("userID");
                String receiverInfo = rs.getString("receiverInfo");
                int isPayed = rs.getInt("isPayed");
                int status = rs.getInt("status");
                re = new Order(id,totalMoney,shippingFee,discountVoucher,createAt,updateAt,userID,receiverInfo,isPayed,status);
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> selectByUser(int userIDin, int statusin) {
        String statusCondition = "and status= " + statusin;
        if(statusin==Constant.NONE_CONDITION) statusCondition = "";
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders where userID= ? " + statusCondition+" order by updateAt desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                double totalMoney = rs.getDouble("totalMoney");
                double shippingFee = rs.getDouble("shippingFee");
                double discountVoucher = rs.getDouble("discountVoucher");
                LocalDateTime createAt = rs.getObject("createAt",LocalDateTime.class);
                LocalDateTime updateAt = rs.getObject("updateAt",LocalDateTime.class);
                int userID = rs.getInt("userID");
                String receiverInfo = rs.getString("receiverInfo");
                int isPayed = rs.getInt("isPayed");
                int status = rs.getInt("status");
                res.add(new Order(id,totalMoney,shippingFee,discountVoucher,createAt,updateAt,userID,receiverInfo,isPayed,status));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int selectStatus(int id) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select status from orders where id = ? ;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                re = rs.getInt("status");
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> selectByStatus(int statusCondition, int offset) {
        ArrayList<Order> res = new ArrayList<>();
        String condition = "where status = '" + statusCondition + "'";
        if(statusCondition==Constant.NONE_CONDITION) condition= "";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " " + condition +
                    "\n   order by updateAt desc, id desc \n" +
                    "limit ?,"+Constant.NUM_OF_ITEMS_A_PAGE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double totalMoney = rs.getDouble("totalMoney");
                double shippingFee = rs.getDouble("shippingFee");
                double discountVoucher = rs.getDouble("discountVoucher");
                LocalDateTime createAt = rs.getObject("createAt",LocalDateTime.class);
                LocalDateTime updateAt = rs.getObject("updateAt",LocalDateTime.class);
                int userID = rs.getInt("userID");
                String receiverInfo = rs.getString("receiverInfo");
                int isPayed = rs.getInt("isPayed");
                int status = rs.getInt("status");
                res.add(new Order(id,totalMoney,shippingFee,discountVoucher,createAt,updateAt,userID,receiverInfo,isPayed,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateStatus(int idin, int newStatus) {
        int re= 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update orders set status = ? where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,newStatus);
            pst.setInt(2,idin);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countByStatus(int statusCondition) {
        int re=0;
        String condition = "" ;
        if(statusCondition!=Constant.NONE_CONDITION) {
            condition= "where status = '" + statusCondition + "'";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select count(*) as count from orders " +
                    " " + condition +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                re = rs.getInt("count");
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> selectByTime(String time) {
        ArrayList<Order> res = new ArrayList<>();
        String condition = "" ;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where updateAt like ? or createAt like ? "+
                    "\n   order by updateAt desc, id desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+time+"%");
            pst.setString(2, "%"+time+"%");
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double totalMoney = rs.getDouble("totalMoney");
                double shippingFee = rs.getDouble("shippingFee");
                double discountVoucher = rs.getDouble("discountVoucher");
                LocalDateTime createAt = rs.getObject("createAt",LocalDateTime.class);
                LocalDateTime updateAt = rs.getObject("updateAt",LocalDateTime.class);
                int userID = rs.getInt("userID");
                String receiverInfo = rs.getString("receiverInfo");
                int isPayed = rs.getInt("isPayed");
                int status = rs.getInt("status");
                res.add(new Order(id,totalMoney,shippingFee,discountVoucher,createAt,updateAt,userID,receiverInfo,isPayed,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Order> selectListByID(String idin) {
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where id like ?"+
                    "\n   order by updateAt desc, id desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+idin+"%");
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double totalMoney = rs.getDouble("totalMoney");
                double shippingFee = rs.getDouble("shippingFee");
                double discountVoucher = rs.getDouble("discountVoucher");
                LocalDateTime createAt = rs.getObject("createAt",LocalDateTime.class);
                LocalDateTime updateAt = rs.getObject("updateAt",LocalDateTime.class);
                int userID = rs.getInt("userID");
                String receiverInfo = rs.getString("receiverInfo");
                int isPayed = rs.getInt("isPayed");
                int status = rs.getInt("status");
                res.add(new Order(id,totalMoney,shippingFee,discountVoucher,createAt,updateAt,userID,receiverInfo,isPayed,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> selectOf(int userIDin) {
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where userID = ? " +
                    "\n   order by updateAt desc, id desc; \n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double totalMoney = rs.getDouble("totalMoney");
                double shippingFee = rs.getDouble("shippingFee");
                double discountVoucher = rs.getDouble("discountVoucher");
                LocalDateTime createAt = rs.getObject("createAt",LocalDateTime.class);
                LocalDateTime updateAt = rs.getObject("updateAt",LocalDateTime.class);
                int userID = rs.getInt("userID");
                String receiverInfo = rs.getString("receiverInfo");
                int isPayed = rs.getInt("isPayed");
                int status = rs.getInt("status");
                res.add(new Order(id,totalMoney,shippingFee,discountVoucher,createAt,updateAt,userID,receiverInfo,isPayed,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String selectReceiverInfo(int id) {
        String re =null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select receiverInfo from orders\n" +
                    " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                String receiverInfo = rs.getString("receiverInfo");
                re = receiverInfo;
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
