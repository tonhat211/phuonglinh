package controller;

import DAO.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import service.LocalDateTypeAdapter;
import values.MessageValues;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        String action = req.getParameter("a");
        if(action==null) {
            ArrayList<OrderUnit> orderUnits = OrderUnitDAO.getInstance().selectByUser(userLogging.id,Constant.WAITING);
            req.setAttribute("orderUnits", orderUnits);
            req.setAttribute("status", Constant.WAITING);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
            rd.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "ORDER" : {
                System.out.println("A: order");
                String[] idArr = req.getParameterValues("cart-id");
                ArrayList<Integer> ids = new ArrayList<>();
                for(String id : idArr) {
                    ids.add(Integer.parseInt(id));
                }
                ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByIDs(ids);
                String receiverInfo = req.getParameter("address-input");
                double totalProductMoney = 0;
                double shippingFee = Constant.SHIPPING_FEE_DEFAULT;
                for(CartUnit cart : carts) {
                    totalProductMoney+= (cart.getTotalMoney());
                }
                if(totalProductMoney>=50000) shippingFee = 0;
                double totalMoney = totalProductMoney+ shippingFee;
                double discountMoney=0;
//                    kiem tra voucher:
                String voucherID = req.getParameter("voucher");
                System.out.println("voucherID:" + voucherID);
                if(voucherID!=null && !voucherID.trim().equals("")) {
                    System.out.println("co voucher");
                    Voucher voucher = VoucherDAO.getInstance().selectByVoucherID(voucherID);
                    discountMoney = totalMoney/100*voucher.discount;
                    if(voucher.maxMoney>1) { //kiem tra gia tri giam toi da
                        if(discountMoney>voucher.maxMoney)  totalMoney =totalMoney - voucher.maxMoney;
                        else totalMoney = totalMoney - discountMoney;
                    } else {
                        totalMoney = totalMoney - discountMoney;
                    }

                    // huy voucher
                    if(voucher.userIDs.isEmpty()) {
                        VoucherDAO.getInstance().decreaseQty(voucherID);
                    } else {
                        VoucherDAO.getInstance().rejectUser(userLogging.id,voucherID);
                    }

                }

                // tien hanh dat hang
                Order order = new Order(totalMoney,shippingFee,discountMoney,userLogging.id, receiverInfo);
                int orderID = OrderDAO.getInstance().insert(order);
                int re = OrderDetailDAO.getInstance().insertOrderDetails(orderID,carts);

//                  giam so luong trong kho
                ProductDetailDAO.getInstance().updateSaledQty(carts);

//                  xoa khoi gio hang
                CartItemDAO.getInstance().deleteOrderedCarts(ids);
                if(re!=0) {
                    ArrayList<OrderUnit> orderUnits = OrderUnitDAO.getInstance().selectByUser(userLogging.id,Constant.WAITING);
                    req.setAttribute("orderUnits", orderUnits);
                    req.setAttribute("status", Constant.WAITING);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                    rd.forward(req, resp);
                }
                break;
            }
            case "STATUS": {
                int status = Integer.parseInt(req.getParameter("status"));
                ArrayList<OrderUnit> orderUnits =  OrderUnitDAO.getInstance().selectByUser(userLogging.id, status);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("status", status);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                rd.forward(req, resp);
                break;
            }
            case "CANCEL": {
                int id = Integer.parseInt(req.getParameter("id"));
                int re = OrderDAO.getInstance().updateStatus(id, Constant.CANCELED);
                ArrayList<OrderUnit> orderUnits =   OrderUnitDAO.getInstance().selectByUser(userLogging.id, Constant.CANCELED);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("status", Constant.CANCELED);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                rd.forward(req, resp);
                break;
            }
            case "COMPLETE": {
                int id = Integer.parseInt(req.getParameter("id"));
                int re = OrderDAO.getInstance().updateStatus(id, Constant.COMPLETED);
                ArrayList<OrderUnit> orderUnits =   OrderUnitDAO.getInstance().selectByUser(userLogging.id, Constant.COMPLETED);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("status", Constant.COMPLETED);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                rd.forward(req, resp);
                break;
            }
            case "RCOMMENT": {
                System.out.println("rcomment");
                int orderID = Integer.parseInt(req.getParameter("id"));
                System.out.println("lay product details");
                ArrayList<ProductDetail> productDetails = ProductDetailDAO.getInstance().selectNotComment(orderID);
                Map<Integer,String> map = new LinkedHashMap<>();
                ArrayList<Integer> productIDs = new ArrayList<>();
                for(ProductDetail productDetail : productDetails) {
                    if(!map.containsKey(productDetail.productID)) {
                        map.put(productDetail.productID, productDetail.classify);
                        productIDs.add(productDetail.productID);
                    }
                    else {
                        String currentValue = map.get(productDetail.productID);
                        String newValue = currentValue + " - " + productDetail.classify;
                        map.put(productDetail.productID, newValue);
                    }
                }
                ArrayList<Product> products = ProductDAO.getInstance().selectByIDs(productIDs);
                // dung tam description lam classifies, va prominece lam orderID
                for(Product product : products) {
                    product.description = map.get(product.id);
                    product.prominence = orderID;
                }
                Gson gson = new Gson();
                String productsJson = gson.toJson(products);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(productsJson);
                break;

            }
            case "COMMENT": {
                int productID = Integer.parseInt(req.getParameter("productID"));
                int ratingIndex = Integer.parseInt(req.getParameter("ratingIndex"));
                String classifies = req.getParameter("classifies");
                String content = req.getParameter("content");
                int orderID = Integer.parseInt(req.getParameter("orderID"));
                Comment comment = new Comment(Constant.NONE,productID,ratingIndex,new User(userLogging.id),classifies+"\n"+content,null,orderID,Constant.NONE);
                int re = CommentDAO.getInstance().insert(comment);
                if(re==1) {
                    String html = callFunction("showSuccessToast2('"+MessageValues.CMT_SUCCESS+"','none');");
                    resp.getWriter().write(html);
                    return;
                }
                break;
            }
            case "ADDRESS": {
                int id = Integer.parseInt(req.getParameter("id"));
                String re = OrderDAO.getInstance().selectReceiverInfo(id);
                resp.getWriter().write(re);
                break;
            }
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }

    public String callFunction(String function) {
        return "<script>" + function + "</script>";
    }

}
