package DAO;

import model.Constant;
import model.VerifyCode;
import service.JDBCUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class VerifyCodeDAO implements IDAO<VerifyCode> {
    public static VerifyCodeDAO getInstance(){
        return new VerifyCodeDAO();
    }

    @Override
    public int insert(VerifyCode verifyCode) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into verifycodes (code,email) values (?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, verifyCode.getCode());
            pst.setString(2, verifyCode.getEmail());
            System.out.println(pst);
//            pst.setInt(3, Constant.ACTIVE);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(VerifyCode verifyCode) {
        return 0;
    }

    @Override
    public int delete(VerifyCode verifyCode) {
        return 0;
    }

    @Override
    public ArrayList<VerifyCode> selectAll() {
        return null;
    }

    @Override
    public VerifyCode selectById(int id) {
        return null;
    }

    public VerifyCode selectTheLast(String emailin) {
        VerifyCode re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from verifycodes where email = ? ORDER by id desc LIMIT 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, emailin);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String code = rs.getString("code");
                String email = rs.getString("email");
                LocalDateTime time = rs.getObject("time", LocalDateTime.class);
                int isVerify = rs.getInt("isVerify");
                re = new VerifyCode(code, email, time, isVerify);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int disableCode(String code) {
        int re = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update verifycodes set isVerify = "+Constant.UNACTIVE+ " where code = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, code);
            re = pst.executeUpdate();
            return re;

        } catch (SQLException e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {

    }
}
