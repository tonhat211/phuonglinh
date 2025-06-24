package DAO;

import model.*;
import partial.ProductAdmin;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ProductDAO implements IDAO<Product> {
    public static ProductDAO getInstance(){
        return new ProductDAO();
    }

    // tra ve id neu them thanh cong
    @Override
    public int insert(Product product) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into products (name,ingredient,category,description,direction,thumbnail,prominence) values (?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,product.name); // dung tam id cua address lam id user
            pst.setString(2,product.ingredient);
            pst.setString(3,product.category);
            pst.setString(4,product.description);
            pst.setString(5,product.direction);
            pst.setString(6,product.thumbnail);
            pst.setInt(7,product.prominence);
            System.out.println(pst);
            re = pst.executeUpdate();
            if(re==1) {
                sql  = "select id from products where name =? " +
                        "and ingredient =? " +
                        "and category =? " +
                        "and description =? " +
                        "and direction =? " +
                        "and thumbnail =? ;";
                pst = conn.prepareStatement(sql);
                pst.setString(1,product.name); // dung tam id cua address lam id user
                pst.setString(2,product.ingredient);
                pst.setString(3,product.category);
                pst.setString(4,product.description);
                pst.setString(5,product.direction);
                pst.setString(6,product.thumbnail);
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
    public int update(Product product) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set name=?, " +
                    "ingredient=?, " +
                    "category=?, " +
                    "description=?, " +
                    "direction=?, " +
                    "prominence=? " +
                    " where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,product.name); // dung tam id cua address lam id user
            pst.setString(2,product.ingredient);
            pst.setString(3,product.category);
            pst.setString(4,product.description);
            pst.setString(5,product.direction);
            pst.setInt(6,product.prominence);
            pst.setInt(7,product.id);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Product product) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set avai =? " +
                    " where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Constant.DELETED);
            pst.setInt(2,product.id);
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Product> selectAll() {
        return null;
    }

    @Override
    public Product selectById(int idin) {
        Product re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from products where id = ? and avai !=" + Constant.DELETED+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String ingredient=rs.getString("ingredient");
                String category=rs.getString("category");
                String description=rs.getString("description");
                String direction=rs.getString("direction");
                String thumbnail=rs.getString("thumbnail");
                int prominence=rs.getInt("prominence");
                int avai=rs.getInt("avai");
                re = new Product(id,name,ingredient,category,description,direction,thumbnail,prominence,avai);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> selectByIDs(ArrayList<Integer> ids) {
        ArrayList<Product> res = new ArrayList<>();
        if(ids.isEmpty()) return res;
        StringBuilder condition = new StringBuilder();
        condition.append("(");
        for(Integer id : ids) {
            condition.append(id+",");
        }
        condition.deleteCharAt(condition.length()-1);
        condition.append(")");
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select id,name,thumbnail from products where id in "+condition.toString()+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String thumbnail=rs.getString("thumbnail");
                res.add(new Product(id,name,thumbnail));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductAdmin> selectByCategoryAdmin(String categoryin, int offset) {
        ArrayList<ProductAdmin> res = new ArrayList<>();
        String categoryCondition= "p.category = '" + categoryin +"' and";
        if(categoryin.equalsIgnoreCase(Constant.ALL_String)) categoryCondition="";
        else if(categoryin.equalsIgnoreCase(Constant.OTHER_CATEGORY)) categoryCondition="p.category not in ('"+Constant.NAM_CATEGORY+"','"+Constant.DONG_TRUNG_CATEGORY+"','"+Constant.RUOU_CATEGORY+"') and";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.category, p.thumbnail,p.prominence,p.avai, count(od.productDetailID) as saledQty\n" +
                    "from products p left join productdetails pd on p.id = pd.productID \n" +
                    " left join orderdetails od on pd.id = od.productDetailID \n" +
                    "where " +categoryCondition +" p.avai != " + Constant.DELETED + " \n"+
                    "group by p.id ORDER BY p.prominence desc, COUNT(od.productDetailID) desc \n" +
                    "limit ?," + Constant.NUM_OF_ITEMS_A_PAGE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String thumbnail=rs.getString("thumbnail");
                int prominence=rs.getInt("prominence");
                int avai = rs.getInt("avai");
                int saledQty = rs.getInt("saledQty");
                Product product = new Product(id, name, category, thumbnail, prominence, avai);
                ProductAdmin productAdmin = new ProductAdmin(product,saledQty);
                res.add(productAdmin);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countByCategory(String categoryin) {
        int re=0;
        String categoryCondition= "category = '" + categoryin +"' and";
        if(categoryin.equalsIgnoreCase(Constant.ALL_String)) categoryCondition="";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT count(*) as count from products where "+categoryCondition+ " avai != " + Constant.DELETED+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re = rs.getInt("count");
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateThumbnail(int id, String filePath) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set thumbnail =? " +
                    "where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,filePath);
            pst.setInt(2,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String selectThumbnail(int id) {
        String re =null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select thumbnail from products " +
                    "where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re=rs.getString("thumbnail");
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateAvai(int id, int avai) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set avai=? " +
                    " where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,avai);
            pst.setInt(2,id);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductAdmin> searchAdminByID(String searchInput) {
        ArrayList<ProductAdmin> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.category, p.thumbnail,p.prominence,p.avai, count(od.productDetailID) as saledQty\n" +
                    "from products p left join productdetails pd on p.id = pd.productID \n" +
                    " left join orderdetails od on pd.id = od.productDetailID \n" +
                    "where p.id like ? and p.avai != " + Constant.DELETED + " \n"+
                    "group by p.id ORDER BY p.prominence desc, COUNT(od.productDetailID) desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,"%"+searchInput+"%");
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String thumbnail=rs.getString("thumbnail");
                int prominence=rs.getInt("prominence");
                int avai = rs.getInt("avai");
                int saledQty = rs.getInt("saledQty");
                Product product = new Product(id, name, category, thumbnail, prominence, avai);
                ProductAdmin productAdmin = new ProductAdmin(product,saledQty);
                res.add(productAdmin);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductAdmin> searchAdminByName(String searchInput) {
        ArrayList<ProductAdmin> res = new ArrayList<>();
        String[] tokens = searchInput.split(" ");
        String condition="";
        for(String token : tokens) {
            condition+= "p.name like '%"+token+"%' and ";
        }
        condition=condition.substring(0, condition.length()-5);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.category, p.thumbnail,p.prominence,p.avai, count(od.productDetailID) as saledQty\n" +
                    "from products p left join productdetails pd on p.id = pd.productID \n" +
                    " left join orderdetails od on pd.id = od.productDetailID \n" +
                    "where " +condition +" and p.avai != " + Constant.DELETED + " \n"+
                    "group by p.id ORDER BY p.prominence desc, COUNT(od.productDetailID) desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String thumbnail=rs.getString("thumbnail");
                int prominence=rs.getInt("prominence");
                int avai = rs.getInt("avai");
                int saledQty = rs.getInt("saledQty");
                Product product = new Product(id, name, category, thumbnail, prominence, avai);
                ProductAdmin productAdmin = new ProductAdmin(product,saledQty);
                res.add(productAdmin);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductAdmin> searchAdminByIngredient(String searchInput) {
        ArrayList<ProductAdmin> res = new ArrayList<>();
        String[] tokens = searchInput.split(" ");
        String condition = "";
        for (String token : tokens) {
            condition += "p.ingredient COLLATE utf8mb4_general_ci\t like '%" + token + "%' and ";
        }
        condition = condition.substring(0, condition.length() - 5);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.category, p.thumbnail,p.prominence,p.avai, count(od.productDetailID) as saledQty\n" +
                    "from products p left join productdetails pd on p.id = pd.productID \n" +
                    " left join orderdetails od on pd.id = od.productDetailID \n" +
                    "where " + condition + " and p.avai != " + Constant.DELETED + " \n" +
                    "group by p.id ORDER BY p.prominence desc, COUNT(od.productDetailID) desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String thumbnail = rs.getString("thumbnail");
                int prominence = rs.getInt("prominence");
                int avai = rs.getInt("avai");
                int saledQty = rs.getInt("saledQty");
                Product product = new Product(id, name, category, thumbnail, prominence, avai);
                ProductAdmin productAdmin = new ProductAdmin(product, saledQty);
                res.add(productAdmin);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateProminence(int id) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set prominence=prominence+1 " +
                    " where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<ProductAdmin> selectProductAdminByIDs(ArrayList<Integer> ids) {
        ArrayList<ProductAdmin> res = new ArrayList<>();
        if(ids.isEmpty()) return res;
        StringBuilder idsCondition = new StringBuilder();
        idsCondition.append("(");
        for(Integer id : ids){
            idsCondition.append(id+", ");
        }
        idsCondition.deleteCharAt(idsCondition.length()-2);
        idsCondition.append(")");
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.category, p.thumbnail,p.prominence,p.avai, count(od.productDetailID) as saledQty\n" +
                    "from products p left join productdetails pd on p.id = pd.productID \n" +
                    " left join orderdetails od on pd.id = od.productDetailID \n" +
                    "where p.id in "+ idsCondition + " and p.avai != " + Constant.DELETED + " \n"+
                    "group by p.id ORDER BY p.prominence desc, COUNT(od.productDetailID) desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String thumbnail=rs.getString("thumbnail");
                int prominence=rs.getInt("prominence");
                int avai = rs.getInt("avai");
                int saledQty = rs.getInt("saledQty");
                Product product = new Product(id, name, category, thumbnail, prominence, avai);
                ProductAdmin productAdmin = new ProductAdmin(product,saledQty);
                res.add(productAdmin);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<ProductAdmin> productAdmins = ProductDAO.getInstance().searchAdminByIngredient("dong trung");
        System.out.println(productAdmins.size());
    }

}
