package DAO;

import model.*;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDetailDAO implements IDAO<ProductDetail>{
    public static ProductDetailDAO getInstance(){
        return new ProductDetailDAO();
    }

    @Override
    public int insert(ProductDetail detail) { // sua lai
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into productdetails (productID,classify,price,prePrice,qty) values (?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,detail.productID);
            pst.setString(2,detail.classify);
            pst.setDouble(3,detail.price);
            pst.setDouble(4,detail.prePrice);
            pst.setInt(5,detail.qty);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(ProductDetail productDetail) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productDetails set classify=?, prePrice=?, price=?,qty=? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, productDetail.classify);
            pst.setDouble(2, productDetail.prePrice);
            pst.setDouble(3, productDetail.price);
            pst.setInt(4, productDetail.qty);
            pst.setInt(5, productDetail.id);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(ProductDetail productDetail) {
        return 0;
    }

    @Override
    public ArrayList<ProductDetail> selectAll() {
        return null;
    }

    @Override
    public ProductDetail selectById(int idin) {
        ProductDetail re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from productdetails where id = ? and avai !=" + Constant.DELETED+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productID = rs.getInt("productID");
                String classify=rs.getString("classify");
                double price=rs.getDouble("price");
                double prePrice=rs.getDouble("prePrice");
                int qty=rs.getInt("qty");
                int avai=rs.getInt("avai");
                re = new ProductDetail(id,productID,classify,price,prePrice,qty,avai);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(ArrayList<ProductDetail> details) {
        if(details.isEmpty()) return 0;
        StringBuilder values =new StringBuilder();
        for(ProductDetail detail : details){
            values.append("("+detail.productID+",'"+detail.classify+"',"+detail.price+","+detail.prePrice+","+detail.qty+"),");
        }
        values.deleteCharAt(values.length()-1);
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into productdetails (productID,classify,price,prePrice,qty) values " + values+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductDetail> selectByProduct(Product product) {
        ArrayList<ProductDetail> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from productdetails where productID = ? and avai !=" + Constant.DELETED+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, product.id);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productID = rs.getInt("productID");
                String classify=rs.getString("classify");
                double price=rs.getDouble("price");
                double prePrice=rs.getDouble("prePrice");
                int qty=rs.getInt("qty");
                int avai=rs.getInt("avai");
                res.add(new ProductDetail(id,productID,classify,price,prePrice,qty,avai));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double[] selectPriceInfo(int id) {
        double[] res = new double[2];
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select price, prePrice from productDetails where id =?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                double price=rs.getDouble("price");
                double prePrice=rs.getDouble("prePrice");
                res[0] = price;
                res[1] = prePrice;
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateSaledQty(ArrayList<CartUnit> orderedCarts) {
        int re=0;
        String newValue ="";
        String condition="(";
        for(CartUnit c : orderedCarts) {
            newValue += "WHEN id = "+c.getProductDetailID()+" THEN qty-" +c.getQty()+"\n";
            condition+= c.getProductDetailID()+",";
        }
        condition = condition.substring(0, condition.length()-1);
        condition += ")";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productdetails set qty = case \n" +
                    newValue +
                    "END\n" +
                    "where id in " + condition+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> selectProductIDs(ArrayList<Integer> ids) {
        ArrayList<Integer> res = new ArrayList<>();
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
            String sql =  "select productID from productDetails where id in " + condition + ";";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int productID = rs.getInt("productID");
                res.add(productID);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductDetail> selectNotComment(int orderIDin) {
        ArrayList<ProductDetail> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select pd.id, pd.productID, pd.classify \n" +
                    "from productdetails pd join orderdetails od on pd.id = od.productDetailID \n" +
                    "where pd.productID not in (select productID from comments where orderID = ?) and od.orderID = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderIDin);
            pst.setInt(2, orderIDin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productID = rs.getInt("productID");
                String classify=rs.getString("classify");
                res.add(new ProductDetail(id,productID,classify));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // neu thanh cong tra ve productID
    public int updateAvai(int id, int newAvai) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productdetails set avai = ? where id =?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,newAvai);
            pst.setInt(2,id);
            re = pst.executeUpdate();
            if(re==1) {
                sql = "select productID from productdetails where id=?;";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,id);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    re = rs.getInt("productID");
                }
                return re;
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductDetail> selectQtyByIDs(ArrayList<Integer> ids) {
        ArrayList<ProductDetail> res = new ArrayList<>();
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
            String sql =  "select id, qty from productDetails where id in " + condition + ";";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int qty = rs.getInt("qty");
                ProductDetail pd = new ProductDetail(id);
                pd.qty = qty;
                res.add(pd);
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
