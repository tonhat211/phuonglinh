package DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.MailConfig;
import model.User;
import service.JDBCUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class MailConfigDAO implements IDAO<MailConfig>{
    public static MailConfigDAO getInstance(){
        return new MailConfigDAO();
    }


    @Override
    public int insert(MailConfig mailConfig) {
        return 0;
    }

    @Override
    public int update(MailConfig mailConfig) {
        return 0;
    }

    @Override
    public int delete(MailConfig mailConfig) {
        return 0;
    }

    @Override
    public ArrayList<MailConfig> selectAll() {
        return null;
    }

    @Override
    public MailConfig selectById(int idin) {
        MailConfig re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM mail_configs where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String pwd = rs.getString("pwd");
                int avai = rs.getInt("avai");
                re = new MailConfig(id, email, pwd, avai);
            }

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
