package DAO;

import model.*;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItemDAO implements IDAO<CartItem> {
    public static CartItemDAO getInstance(){
        return new CartItemDAO();
    }

    @Override
    public int insert(CartItem cart) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into cartItems (productDetailID,qty,userID) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, cart.productDetailID);
            pst.setInt(2, cart.qty);
            pst.setInt(3, cart.userId);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(CartItem cart) {
        return 0;
    }

    @Override
    public int delete(CartItem cart) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from cartItems where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,cart.id);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<CartItem> selectAll() {
        return null;
    }

    @Override
    public CartItem selectById(int id) {
      return null;
    }

    public ArrayList<CartUnit> selectByUserID(int userIDin) {
        ArrayList<CartUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT c.id, d.id as productDetailID, d.qty as realQty, p.id as productID, p.name, p.thumbnail, d.classify, d.price,d.prePrice,c.qty \n" +
                    "from products p join productdetails d on p.id = d.productID \n" +
                    "join cartitems c on d.id = c.productDetailID \n" +
                    "where c.userID = ? \n" +
//                    "group by p.id \n" +
                    "order by c.updateTime desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productDetailID = rs.getInt("productDetailID");
                int realQty = rs.getInt("realQty");
                String name = rs.getString("name");
                String thumbnail = rs.getString("thumbnail");
                String classify = rs.getString("classify");
                double price = rs.getDouble("price");
                double prePrice = rs.getDouble("prePrice");
                int qty = rs.getInt("qty");
                int productID = rs.getInt("productID");

                CartItem cartItem = new CartItem(id,productDetailID,qty,userIDin);
                Product product = new Product(productID,name,thumbnail);
                ProductDetail productDetail = new ProductDetail(productDetailID,classify,price,prePrice);
                productDetail.qty = realQty;
                res.add(new CartUnit(cartItem,product,productDetail));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateQty(int id, String operation) {
        int re=0;
        String newQty = "qty-1";
        if(operation.equalsIgnoreCase(Constant.PLUS)){
            newQty= "qty+1";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update cartItems set qty = "+ newQty +" where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re = pst.executeUpdate();

            if(re==1) {
                String sql2 = "select qty from cartItems where id = ?";
                PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,id);
                ResultSet rs = pst2.executeQuery();
                while(rs.next()){
                    re = rs.getInt("qty");
                }
            }

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkExistCart(CartItem cart) {
        int re = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from cartItems where userID = ? and productDetailID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,cart.userId);
            pst.setInt(2,cart.productDetailID);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re = rs.getInt("id");
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CartUnit> selectByIDs(ArrayList<Integer> ids) {
        ArrayList<CartUnit> res = new ArrayList<>();
        StringBuilder condition = new StringBuilder();
        condition.append("(");
        for(Integer id : ids) {
            condition.append(id+",");
        }
        condition.deleteCharAt(condition.length()-1);
        condition.append(")");


        try {
            Connection conn = JDBCUtil.getConnection();
            String sql =  "SELECT c.id, d.id as productDetailID, d.qty as realQty, p.id as productID, p.name, p.thumbnail, " +
                    "d.classify, d.price,d.prePrice," +
                    "c.qty, " +
                    "s.id as sID, s.title, s.discount " +
                    "from products p join productdetails d on p.id = d.productID \n" +
                    "join cartitems c on d.id = c.productDetailID \n" +
                    " left join saleprograms s on JSON_CONTAINS(s.productIDs, JSON_QUOTE(p.id)) and s.avai = " +Constant.ACTIVE +
                    " where c.id in " + condition +
//                    " group by p.id \n" +
                    " order by c.updateTime desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productDetailID = rs.getInt("productDetailID");
                int realQty = rs.getInt("realQty");
                String name = rs.getString("name");
                String thumbnail = rs.getString("thumbnail");
                String classify = rs.getString("classify");
                double price = rs.getDouble("price");
                double prePrice = rs.getDouble("prePrice");
                int qty = rs.getInt("qty");
                int saleProgramID = rs.getInt("sID");
                String saleProgramTitle = rs.getString("title");
                int saleProgramDiscount = rs.getInt("discount");
                int productID = rs.getInt("productID");

                CartItem cartItem = new CartItem(id,productDetailID,qty, Constant.NONE);
                Product product = new Product(productID,name,thumbnail);
                ProductDetail productDetail = new ProductDetail(productDetailID,classify,price,prePrice);
                productDetail.qty= realQty;
                SaleProgram saleProgram = new SaleProgram(saleProgramID,saleProgramTitle,saleProgramDiscount);
                if (saleProgramID==0) saleProgram=null;
                res.add(new CartUnit(cartItem,product,productDetail,saleProgram));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int deleteOrderedCarts(ArrayList<Integer> ids) {
        int re=0;
        StringBuilder condition = new StringBuilder();
        condition.append("(");
        for(Integer id : ids) {
            condition.append(id+",");
        }
        condition.deleteCharAt(condition.length()-1);
        condition.append(")");

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from cartItems \n" +
                    "where id in " + condition +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            re=pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CartUnit selectNewestOf(int userIDin,int productDetailIDin) {
        CartUnit re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT c.id, d.id as productDetailID, p.id as productID, p.name, p.thumbnail, d.classify, d.price,d.prePrice,c.qty \n" +
                    "from products p join productdetails d on p.id = d.productID \n" +
                    "join cartitems c on d.id = c.productDetailID \n" +
                    "where c.userID = ? and c.productDetailID =? and c.qty =1 \n" +
//                    "group by p.id \n" +
                    "order by c.updateTime desc limit 0,1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            pst.setInt(2,productDetailIDin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productDetailID = rs.getInt("productDetailID");
                String name = rs.getString("name");
                String thumbnail = rs.getString("thumbnail");
                String classify = rs.getString("classify");
                double price = rs.getDouble("price");
                double prePrice = rs.getDouble("prePrice");
                int qty = rs.getInt("qty");
                int productID = rs.getInt("productID");

                CartItem cartItem = new CartItem(id,productDetailID,qty,userIDin);
                Product product = new Product(productID,name,thumbnail);
                ProductDetail productDetail = new ProductDetail(productDetailID,classify,price,prePrice);
                re = new CartUnit(cartItem,product,productDetail);

            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(14);
        ids.add(15);
        ids.add(16);
        ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByIDs(ids);
        System.out.println(carts.size());

    }


}
