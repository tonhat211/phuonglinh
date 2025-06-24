package DAO;

import model.*;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAO implements IDAO<OrderDetail> {
    public static OrderDetailDAO getInstance(){
        return new OrderDetailDAO();
    }
    @Override
    public int insert(OrderDetail orderDetail) {
        return 0;
    }

    @Override
    public int update(OrderDetail orderDetail) {
        return 0;
    }

    @Override
    public int delete(OrderDetail orderDetail) {
        return 0;
    }

    @Override
    public ArrayList<OrderDetail> selectAll() {
        return null;
    }

    @Override
    public OrderDetail selectById(int id) {
        return null;
    }

    public int insertOrderDetails(int orderID,ArrayList<CartUnit> carts) {
        int re=0;
        StringBuilder values = new StringBuilder();
        for(CartUnit c : carts){
            values.append("(" + orderID +","+c.getProductDetailID()+",'"+c.getProductName()+"','"+c.getDetailClassify()+"',"+c.getQty()+","+c.getPriceUnit()+"),");
        }
        values.deleteCharAt(values.length()-1);

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into orderDetails (orderID,productDetailID,productName, detailClassify,qty,priceUnit) values "+ values+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderDetail> selectByOrders(ArrayList<Order> orders) {
        if(orders.isEmpty()) return new ArrayList<>();
        StringBuilder orderCondition = new StringBuilder();
        orderCondition.append("(");
        for(Order order : orders) {
            orderCondition.append(order.id+",");
        }
        orderCondition.deleteCharAt(orderCondition.length()-1);
        orderCondition.append(")");
        ArrayList<OrderDetail> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT od.id as id, od.orderID as orderID, od.productDetailID,od.productName, od.detailClassify, od.qty, od.priceUnit, \n" +
                    "pd.productID, \n" +
                    "p.thumbnail \n" +
                    "FROM orderdetails od join productdetails pd on od.productDetailID = pd.id \n" +
                    "join products p on p.id = pd.productID WHERE orderID in " + orderCondition +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int orderID = rs.getInt("orderID");
                int productDetailID = rs.getInt("productDetailID");
                String productName = rs.getString("productName");
                String detailClassify = rs.getString("detailClassify");
                int qty = rs.getInt("qty");
                double priceUnit = rs.getDouble("priceUnit");
                // product detail
                int productID = rs.getInt("productID");
//                String classify = rs.getString("classify");
                // product
//                String name = rs.getString("name");
                String thumbnail = rs.getString("thumbnail");
                Product product = new Product(productID, thumbnail);
                ProductDetail productDetail = new ProductDetail(productDetailID, product);

                res.add(new OrderDetail(id,orderID,productDetail,productName,detailClassify,qty,priceUnit));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
