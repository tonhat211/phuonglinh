package DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Constant;
import model.Employee;
import model.User;
import service.JDBCUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmployeeDAO implements  IDAO<Employee> {
    public static EmployeeDAO getInstance(){
        return new EmployeeDAO();
    }

    @Override
    public int insert(Employee employee) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into employees (userID,position,area) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,employee.getUserID());
            pst.setString(2, employee.position);
            pst.setString(3,employee.area);
            System.out.println(pst);
            re = pst.executeUpdate();
//            if(re==1) {
//                sql = "select id from users where email = ? order by dateIn desc limit 1;";
//                pst = conn.prepareStatement(sql);
//                pst.setString(1, user.email);
//                ResultSet rs = pst.executeQuery();
//                while(rs.next()) {
//                    re = rs.getInt("id");
//                }
//            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update employees set avai = "+Constant.DELETED+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, employee.id);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Employee employee) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update employees set avai = "+Constant.DELETED+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, employee.id);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Employee> selectAll() {
        ArrayList<Employee> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT e.id as eid, e.userID, e.position, e.area, e.updateAt as eupdateAt,\n" +
                    "e.avai as eavai, \n" +
                    "u.name, u.email, u.phone, u.dob, u.gender, u.roles, u.dateIn as udateIn, u.avai as uavai\n" +
                    "FROM `employees` e left join users u on e.userID = u.id  " +
                    "and u.avai!=" + Constant.DELETED + " and e.avai !="+ Constant.DELETED +
                    " where u.roles Is not null and e.avai != " + Constant.DELETED +" and u.avai !=" + Constant.DELETED+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int eid = rs.getInt("eid");
                int userID = rs.getInt("userID");
                String position = rs.getString("position");
                String area = rs.getString("area");
                LocalDate updateAt = rs.getObject("eupdateAt", LocalDate.class);
                int eavai = rs.getInt("eavai");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob",LocalDate.class);
                int gender = rs.getInt("gender");
                String rolesJson = rs.getString("roles");
                LocalDate dateIn = rs.getObject("udateIn",LocalDate.class);
                int uavai = rs.getInt("uavai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> roles = new ArrayList<>();
                if(rolesJson != null) roles = gson.fromJson(rolesJson, type);
                User user = new User(userID,name,email,null,phone,dob,gender,roles,dateIn,uavai);
                res.add(new Employee(eid,user,position,area, updateAt,eavai));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee selectById(int id) {
        Employee re = null;
        return re;
//        try {
//            Connection conn = JDBCUtil.getConnection();
//            String sql =  "SELECT e.id as eid, e.userID, e.position, e.area, e.updateAt as eupdateAt,\n" +
//                    "e.avai as eavai, \n" +
//                    "u.name, u.email, u.phone, u.dob, u.gender, u.roles, u.dateIn as udateIn, u.avai as uavai\n" +
//                    "FROM users u left join employees e on e.userID = u.id and e.avai !="+ Constant.DELETED +
//                    " where u.id = ? and u.avai !=" + Constant.DELETED+";";
//            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setInt(1,id);
//            System.out.println(pst);
//            ResultSet rs = pst.executeQuery();
//            while(rs.next()){
//                int eid = rs.getInt("eid");
//                int userID = rs.getInt("userID");
//                String position = rs.getString("position");
//                String area = rs.getString("area");
//                LocalDate updateAt = rs.getObject("eupdateAt", LocalDate.class);
//                int eavai = rs.getInt("eavai");
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                String phone = rs.getString("phone");
//                LocalDate dob = rs.getObject("dob",LocalDate.class);
//                int gender = rs.getInt("gender");
//                String rolesJson = rs.getString("roles");
//                LocalDate dateIn = rs.getObject("udateIn",LocalDate.class);
//                int uavai = rs.getInt("uavai");
//                Gson gson = new Gson();
//                Type type = new TypeToken<ArrayList<String>>() {}.getType();
//                ArrayList<String> roles = new ArrayList<>();
//                if(rolesJson != null) roles = gson.fromJson(rolesJson, type);
//                User user = new User(userID,name,email,null,phone,dob,gender,roles,dateIn,uavai);
//                re = new Employee(eid,user,position,area, updateAt,eavai);
//            }
//            JDBCUtil.closeConnection(conn);
//            return re;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Employee selectByUser(int userIDin) {
        Employee re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT e.id as eid, e.userID, e.position, e.area, e.updateAt as eupdateAt,\n" +
                    "e.avai as eavai, \n" +
                    "u.name, u.email, u.phone, u.dob, u.gender, u.roles, u.dateIn as udateIn, u.avai as uavai\n" +
                    "FROM users u left join employees e on e.userID = u.id and e.avai !="+ Constant.DELETED +
                    " where u.id = ? and u.avai !=" + Constant.DELETED+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int eid = rs.getInt("eid");
                int userID = rs.getInt("userID");
                String position = rs.getString("position");
                String area = rs.getString("area");
                LocalDate updateAt = rs.getObject("eupdateAt", LocalDate.class);
                int eavai = rs.getInt("eavai");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dob = rs.getObject("dob",LocalDate.class);
                int gender = rs.getInt("gender");
                String rolesJson = rs.getString("roles");
                LocalDate dateIn = rs.getObject("udateIn",LocalDate.class);
                int uavai = rs.getInt("uavai");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> roles = new ArrayList<>();
                if(rolesJson != null) roles = gson.fromJson(rolesJson, type);
                User user = new User(userID,name,email,null,phone,dob,gender,roles,dateIn,uavai);
                re = new Employee(eid,user,position,area, updateAt,eavai);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteByUser(User user) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update employees set avai = "+Constant.DELETED+ " where userId = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, user.id);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Employee employee = EmployeeDAO.getInstance().selectByUser(1);
        System.out.println(employee);
    }
}
