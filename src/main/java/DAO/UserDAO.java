package DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import service.JDBCUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDAO implements IDAO<User> {
    public static UserDAO getInstance(){
        return new UserDAO();
    }
    @Override
    public int insert(User user) { // tra ve id
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into users (email,name,pwd,phone,dob,gender,roles) values(?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.email);
            pst.setString(2, user.name);
            pst.setString(3,user.pwd);
            pst.setString(4,user.phone);
            pst.setObject(5,user.dob);
            pst.setInt(6,user.gender);
            pst.setString(7,user.getRolesJson());
            System.out.println(pst);
            re = pst.executeUpdate();
            if(re==1) {
                sql = "select id from users where email = ? and avai!="+Constant.DELETED+ " order by dateIn desc limit 1;";
                pst = conn.prepareStatement(sql);
                pst.setString(1, user.email);
                System.out.println(pst);
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
    public int update(User user) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set name = ?, phone = ?, dob=?, gender=? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.name);
            pst.setString(2, user.phone);
            pst.setObject(3, user.dob);
            pst.setInt(4, user.gender);
            pst.setInt(5, user.id);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public ArrayList<User> selectAll() {
        ArrayList<User> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from users;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

//                res.add(new User(id,name,email));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User selectById(int idin) {
        User user = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob",LocalDate.class);
                int gender = rs.getInt("gender");
                String rolesJson = rs.getString("roles");
                LocalDate dateIn = rs.getObject("dateIn",LocalDate.class);
                int avai = rs.getInt("avai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> roles = new ArrayList<>();
                if(rolesJson != null) roles = gson.fromJson(rolesJson, type);
                user = new User(id,name,email,null,phone,dob,gender,roles,dateIn,avai);
            }

            JDBCUtil.closeConnection(conn);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User checkLogin(String emailin, String password) {
        User user = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` WHERE email = ? and pwd =?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, emailin);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob", LocalDate.class);
                LocalDate dateIn = rs.getObject("dateIn", LocalDate.class);
                int gender = rs.getInt("gender");
                int avai = rs.getInt("avai");
                String rolesJson = rs.getString("roles");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> roles = new ArrayList<>();
                if (rolesJson != null) roles = gson.fromJson(rolesJson, type);

                user = new User(id, name, email, null, phone, dob, gender, roles, dateIn,avai);
            }

            JDBCUtil.closeConnection(conn);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkEmail(String email) {
        int count =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT count(*) FROM `users` WHERE email = ? and avai !=" +Constant.DELETED;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                count = rs.getInt("count(*)");
            }
            JDBCUtil.closeConnection(conn);
            if(count>0) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updatePassword(int idin, String pwd) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set pwd = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,pwd);
            pst.setInt(2, idin);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int generateNewPassword(String email, String pwd) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set pwd = ? where email = ? " +
                    "and id= (select max(id) from users where email = ?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,pwd);
            pst.setString(2, email);
            pst.setString(3, email);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> selectCustomers(int offset) {
        ArrayList<User> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` WHERE roles IS NULL and avai != " +Constant.DELETED + " order by id desc limit ?," +Constant.NUM_OF_ITEMS_A_PAGE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob",LocalDate.class);
                LocalDate dateIn = rs.getObject("dateIn",LocalDate.class);
                int gender = rs.getInt("gender");
                int avai = rs.getInt("avai");

                res.add(new User(id,name,email,null,phone,dob,gender,null,dateIn,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> selectEmployees(int offset) {
        ArrayList<User> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` WHERE roles IS NOT NULL and avai != "+ Constant.DELETED+ " order by id desc limit ?,"+Constant.NUM_OF_ITEMS_A_PAGE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,offset);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob", LocalDate.class);
                LocalDate dateIn = rs.getObject("dateIn", LocalDate.class);
                int gender = rs.getInt("gender");
                int avai = rs.getInt("avai");
                String rolesJson = rs.getString("roles");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> roles = new ArrayList<>();
                if (rolesJson != null) roles = gson.fromJson(rolesJson, type);

                res.add(new User(id, name, email, null, phone, dob, gender, roles, dateIn,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int lockUser(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set avai = "+ Constant.BLOCKED+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int activeUser(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set avai = "+ Constant.ACTIVE+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            System.out.println(pst);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteUser(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set avai = "+ Constant.DELETED+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateRoles(int idin, ArrayList<String> roles) {
        int re=0;
        Gson gson = new Gson();
        String rolesJson = gson.toJson(roles);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set roles = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,rolesJson);
            pst.setInt(2, idin);
            System.out.println(pst);
            re = pst.executeUpdate();


            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> searchCustomer(String searchInput) {
        ArrayList<User> res = new ArrayList<>();
        String[] tokens = searchInput.split(" ");
        String condition="";
        condition += "(id like '%"+searchInput+"%' or (";
        for(String token : tokens) {
            condition+= "name like '%"+token+"%' and ";
        }
        condition=condition.substring(0, condition.length()-5);
        condition+="))";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` WHERE " +condition +" and roles IS NULL and avai != " +Constant.DELETED + " order by id desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob",LocalDate.class);
                LocalDate dateIn = rs.getObject("dateIn",LocalDate.class);
                int gender = rs.getInt("gender");
                int avai = rs.getInt("avai");

                res.add(new User(id,name,email,null,phone,dob,gender,null,dateIn,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countCustomer() {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select count(*) as total from users where roles IS NULL and avai != " +Constant.DELETED + ";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int total = rs.getInt("total");
                re=total;
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> searchEmployee(String searchInput) {
        ArrayList<User> res = new ArrayList<>();
        String[] tokens = searchInput.split(" ");
        String condition="";
        condition += "(id like '%"+searchInput+"%' or (";
        for(String token : tokens) {
            condition+= "name like '%"+token+"%' and ";
        }
        condition=condition.substring(0, condition.length()-5);
        condition+="))";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM `users` WHERE " +condition +" and roles IS NOT NULL and avai != " +Constant.DELETED + " order by id desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob",LocalDate.class);
                LocalDate dateIn = rs.getObject("dateIn",LocalDate.class);
                int gender = rs.getInt("gender");
                int avai = rs.getInt("avai");

                res.add(new User(id,name,email,null,phone,dob,gender,null,dateIn,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countEmployee() {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select count(*) as total from users where roles IS NOT NULL and avai != " +Constant.DELETED + ";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int total = rs.getInt("total");
                re=total;
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int verifyUser(String email) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update users set avai = "+ Constant.ACTIVE+ " where email = ? " +
                    "and id = max(select max(id) from users where email = ?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,email);
            pst.setString(2,email);
            System.out.println(pst);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
//        ArrayList<User> customers = UserDAO.getInstance().selectCustomers();
//        System.out.println(customers.size());
    }


}
