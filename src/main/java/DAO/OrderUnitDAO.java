package DAO;

import model.Constant;
import model.Order;
import model.OrderDetail;
import model.OrderUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderUnitDAO implements IDAO<OrderUnit> {
    public static OrderUnitDAO getInstance(){
        return new OrderUnitDAO();
    }
    @Override
    public int insert(OrderUnit orderUnit) {
        return 0;
    }

    @Override
    public int update(OrderUnit orderUnit) {
        return 0;
    }

    @Override
    public int delete(OrderUnit orderUnit) {
        return 0;
    }

    @Override
    public ArrayList<OrderUnit> selectAll() {
        return null;
    }

    @Override
    public OrderUnit selectById(int id) {
        OrderUnit orderUnit = null;
        Order order = OrderDAO.getInstance().selectById(id);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        ArrayList<OrderDetail> details = OrderDetailDAO.getInstance().selectByOrders(orders);
        orderUnit = new OrderUnit(order,details);
        return orderUnit;

    }

    public ArrayList<OrderUnit> selectByUser(int userIDin, int statusin) {
        ArrayList<Order> orders = OrderDAO.getInstance().selectByUser(userIDin, statusin);
        Map<Integer,OrderUnit> map = new LinkedHashMap<>();
        for (Order order : orders) {
            map.put(order.id,new OrderUnit(order));
        }
        ArrayList<OrderDetail> details = OrderDetailDAO.getInstance().selectByOrders(orders);
        for(OrderDetail orderDetail : details){
            int orderID = orderDetail.orderID;
            map.get(orderID).details.add(orderDetail);
        }
        ArrayList<OrderUnit> orderUnits = new ArrayList<>();
        for(OrderUnit orderUnit : map.values()){
            orderUnits.add(orderUnit);
        }
        return orderUnits;
    }

    public ArrayList<OrderUnit> selectByStatus(int statusCondition, int offset) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        ArrayList<Order> orders = OrderDAO.getInstance().selectByStatus(statusCondition, offset);
        ArrayList<OrderDetail> details = OrderDetailDAO.getInstance().selectByOrders(orders);
        Map<Integer,OrderUnit> map = new LinkedHashMap<>();
        for (Order order : orders) {
            map.put(order.id,new OrderUnit(order));
        }
        for(OrderDetail orderDetail : details){
            int orderID = orderDetail.orderID;
            map.get(orderID).details.add(orderDetail);
        }
        for(OrderUnit orderUnit : map.values()){
            res.add(orderUnit);
        }
        return res;

    }

    public ArrayList<OrderUnit> selectByTime(String time) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        ArrayList<Order> orders = OrderDAO.getInstance().selectByTime(time);
        ArrayList<OrderDetail> details = OrderDetailDAO.getInstance().selectByOrders(orders);
        Map<Integer,OrderUnit> map = new LinkedHashMap<>();
        for (Order order : orders) {
            map.put(order.id,new OrderUnit(order));
        }
        for(OrderDetail orderDetail : details){
            int orderID = orderDetail.orderID;
            map.get(orderID).details.add(orderDetail);
        }
        for(OrderUnit orderUnit : map.values()){
            res.add(orderUnit);
        }
        return res;

    }

    public ArrayList<OrderUnit> selectListByID(String id) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        ArrayList<Order> orders = OrderDAO.getInstance().selectListByID(id);
        ArrayList<OrderDetail> details = OrderDetailDAO.getInstance().selectByOrders(orders);
        Map<Integer,OrderUnit> map = new LinkedHashMap<>();
        for (Order order : orders) {
            map.put(order.id,new OrderUnit(order));
        }
        for(OrderDetail orderDetail : details){
            int orderID = orderDetail.orderID;
            map.get(orderID).details.add(orderDetail);
        }
        for(OrderUnit orderUnit : map.values()){
            res.add(orderUnit);
        }
        return res;

    }

    public ArrayList<OrderUnit> selectOf(int id) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        ArrayList<Order> orders = OrderDAO.getInstance().selectOf(id);
        ArrayList<OrderDetail> details = OrderDetailDAO.getInstance().selectByOrders(orders);
        Map<Integer,OrderUnit> map = new LinkedHashMap<>();
        for (Order order : orders) {
            map.put(order.id,new OrderUnit(order));
        }
        for(OrderDetail orderDetail : details){
            int orderID = orderDetail.orderID;
            map.get(orderID).details.add(orderDetail);
        }
        for(OrderUnit orderUnit : map.values()){
            res.add(orderUnit);
        }
        return res;

    }


    public static void main(String[] args) {
        ArrayList<OrderUnit> orderUnits = OrderUnitDAO.getInstance().selectByStatus(Constant.NONE_CONDITION, 0);
        System.out.println(orderUnits.size());

    }


}
