package DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Constant;
import model.SaleProgram;
import service.JDBCUtil;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaleProgramDAO implements IDAO<SaleProgram> {
    public static SaleProgramDAO getInstance(){
        return new SaleProgramDAO();
    }

    @Override
    public int insert(SaleProgram saleProgram) {
        return 0;
    }

    @Override
    public int update(SaleProgram saleProgram) {
        return 0;
    }

    @Override
    public int delete(SaleProgram saleProgram) {
        return 0;
    }

    @Override
    public ArrayList<SaleProgram> selectAll() {
        return null;
    }

    @Override
    public SaleProgram selectById(int idin) {
        SaleProgram saleProgram = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from saleprograms where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String productIDsJson = rs.getString("productIDs");
                String title = rs.getString("title");
                String content = rs.getString("content");
                double discount = rs.getDouble("discount");
                int kind = rs.getInt("kind");
                LocalDateTime startTime =rs.getObject("startTime",LocalDateTime.class);
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                int avai = rs.getInt("avai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
                ArrayList<Integer> productIDs = new ArrayList<>();
                if(productIDsJson!=null) productIDs = gson.fromJson(productIDsJson, type);

                saleProgram = new SaleProgram(id, productIDs, title, content, discount,kind, startTime, endTime, avai);

            }

            JDBCUtil.closeConnection(conn);
            return saleProgram;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDateTime getFlashSaleEndTime() {
        LocalDateTime re = LocalDateTime.parse("2003-10-23T14:30:00");
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "select endTime from saleprograms where main = ? and avai = " + Constant.ACTIVE;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Constant.FLASH);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re = rs.getObject("endTime",LocalDateTime.class);
            }

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SaleProgram selectByProduct(int idin) {
        SaleProgram saleProgram = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * \n" +
                    "FROM `saleprograms` \n" +
                    "WHERE JSON_CONTAINS(productIDs, ?, '$') AND avai = "+Constant.ACTIVE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, String.valueOf(idin));
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String productIDsJson = rs.getString("productIDs");
                String title = rs.getString("title");
                String content = rs.getString("content");
                double discount = rs.getDouble("discount");
                int kind = rs.getInt("kind");
                LocalDateTime startTime =rs.getObject("startTime",LocalDateTime.class);
                LocalDateTime endTime =rs.getObject("endTime",LocalDateTime.class);
                int avai = rs.getInt("avai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
                ArrayList<Integer> productIDs = new ArrayList<>();
                if(productIDsJson!=null) productIDs = gson.fromJson(productIDsJson, type);

                saleProgram = new SaleProgram(id, productIDs, title, content, discount,kind, startTime, endTime, avai);

            }

            JDBCUtil.closeConnection(conn);
            return saleProgram;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
