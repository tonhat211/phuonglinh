package controller;

import DAO.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        System.out.println("payment");
        String action = req.getParameter("a");
        if(action == null || action.isEmpty()) {
            ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByUserID(userLogging.id);
            req.setAttribute("carts", carts);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/cart.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "BUYNOW": {
                    System.out.println("buy now");
                    int detailID = Integer.parseInt(req.getParameter("id"));
                    CartItem cartItem = new CartItem(Constant.NONE,detailID,1,userLogging.id);
                    CartItemDAO.getInstance().insert(cartItem);
                    CartUnit cartUnit = CartItemDAO.getInstance().selectNewestOf(userLogging.id,detailID);
                    ArrayList<CartUnit> carts = new ArrayList<>();
                    carts.add(cartUnit);
                    double totalProductMoney = 0;
                    double shippingFee = Constant.SHIPPING_FEE_DEFAULT;
                    ArrayList<AddressItem> addresses = AddressItemDAO.getInstance().selectByUserID(userLogging.id);
                    for(CartUnit cart : carts) {
                        totalProductMoney+= (cart.getTotalMoney());
                    }
                    if(totalProductMoney>=50000) shippingFee = 0;
                    double totalMoney = totalProductMoney+ shippingFee;
                    req.setAttribute("carts", carts);
                    req.setAttribute("addresses", addresses);
                    req.setAttribute("shippingFee", shippingFee);
                    req.setAttribute("totalProductMoney", totalProductMoney);
                    req.setAttribute("totalMoney", totalMoney);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/payment.jsp");
                    rd.forward(req, resp);
                    break;
                }
                case "BUY" : {
                    System.out.println("buy: hello");
                    String[] idArr = req.getParameterValues("cart-check");
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(String id : idArr) {
                        System.out.println("cart-id:" + id);
                        ids.add(Integer.parseInt(id));
                    }

                    double totalProductMoney = 0;
                    double shippingFee = Constant.SHIPPING_FEE_DEFAULT;
                    ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByIDs(ids);
                    ArrayList<AddressItem> addresses = AddressItemDAO.getInstance().selectByUserID(userLogging.id);
                    for(CartUnit cart : carts) {
                        totalProductMoney+= (cart.getTotalMoney());
                    }

                    if(totalProductMoney>=50000) shippingFee = 0;
                    double totalMoney = totalProductMoney+ shippingFee;

//                    SaleProgram saleProgram=new SaleProgram(0,"Không có chương trình",0);
                    req.setAttribute("carts", carts);
                    req.setAttribute("addresses", addresses);
//                    req.setAttribute("saleProgram", saleProgram);
                    req.setAttribute("shippingFee", shippingFee);
                    req.setAttribute("totalProductMoney", totalProductMoney);
                    req.setAttribute("totalMoney", totalMoney);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/payment.jsp");
                    rd.forward(req, resp);
                    break;
                }
                case "VOUCHER": {
                    System.out.println("check voucher");
                    String moreAction = req.getParameter("morea");
                    System.out.println("moreAction: "+moreAction);
                    String voucherID = req.getParameter("voucher");
                    System.out.println("voucherID: "+voucherID);
                    Voucher voucher = VoucherDAO.getInstance().selectByVoucherID(voucherID);
                    if(voucher != null) { // kiem tra hop le
                        String idArrJSON = req.getParameter("cartIds");
                        ArrayList<Integer> ids = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
                        if(idArrJSON!=null) ids = gson.fromJson(idArrJSON, type);

                        ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByIDs(ids);
                        ArrayList<Integer> productDetailIDs = new ArrayList<>();
                        ArrayList<Integer> productIDs = new ArrayList<>();
                        for(CartUnit cart : carts) {
                            productDetailIDs.add(cart.getProductDetailID());
                            productIDs.add(cart.getProductID());
                        }
                        // kiem tra, neu co >=2 detail cua cung 1 product thi khong dung duoc voucher
//                        ArrayList productIDs = ProductDetailDAO.getInstance().selectProductIDs(productDetailIDs);
                        if(!hasDuplicateProductID(productIDs)) {
                            if(voucher.checkApplying(userLogging.id, productIDs)) {
                                if(voucher.checkAvai()) {
                                    double totalProductMoney = 0;
                                    double shippingFee = Constant.SHIPPING_FEE_DEFAULT;
                                    for(CartUnit cart : carts) {
                                        totalProductMoney+= (cart.getTotalMoney());
                                    }
                                    if(totalProductMoney>=50000) shippingFee = 0;
                                    double totalMoney = totalProductMoney+ shippingFee;
                                    double discountMoney = totalMoney/100*voucher.discount;
                                    if(voucher.maxMoney>1) { //kiem tra gia tri giam toi da
                                        if(discountMoney>voucher.maxMoney)  totalMoney =totalMoney - voucher.maxMoney;
                                        else totalMoney = totalMoney - discountMoney;
                                    } else {
                                        totalMoney = totalMoney - discountMoney;
                                    }
                                    if(moreAction.equalsIgnoreCase("order")) {
                                        String html = "true";
                                        resp.getWriter().write(html);
                                    } else {
                                        String html = callFunction("applyVoucher('"+Constant.formatPrice(totalMoney)+"','"+Constant.formatPrice(discountMoney)+"');");
                                        resp.getWriter().write(html);
                                    }
                                    return;
                                }  else {
                                    String html = callFunction("cancelVoucher('"+MessageValues.EXPIRED_VOUCHER+"');");
                                    resp.getWriter().write(html);
                                    return;
                                }

                            } else {
                                String html = callFunction("cancelVoucher('"+MessageValues.UNAPPLIED_PRODUCT_LIST+"');");
                                resp.getWriter().write(html);
                                return;
                            }
                        } else {
                            String html = callFunction("cancelVoucher('"+MessageValues.UNAPPLIED_PRODUCT_LIST+"');");
                            resp.getWriter().write(html);
                            return;
                        }
                    } else {
                        String html = callFunction("cancelVoucher('"+MessageValues.UNAVAILABLE_VOUCHER+"');");
                        resp.getWriter().write(html);
                    }
                    break;
                }
                case "QTY": {
                    System.out.println("check qty");
                    String idArrJson = req.getParameter("cart-ids");
                    Gson gson = new Gson();
                    String[] idArr = gson.fromJson(idArrJson.toString(), String[].class);
                    System.out.println("idArr:" + idArr.length);
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(String id : idArr) {
                        ids.add(Integer.parseInt(id));
                    }
                    System.out.println("qua cart-id");
                    ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByIDs(ids);
                    //kiem tra so luong trong kho
                    int exhaustedId = 0;
                    for(CartUnit cart : carts) {
                        if(cart.getQty() > cart.productDetail.qty) {
                            exhaustedId = cart.getProductDetailID();
                            break;
                        }
                    }
                    String json = gson.toJson(exhaustedId);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(json);
                    break;
                }
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

    public boolean hasDuplicateProductID(ArrayList<Integer> productIDs) {
        Set<Integer> seen = new HashSet<>();
        for (int id : productIDs) {
            if (!seen.add(id)) {
                return true;
            }
        }
        return false;
    }

    public String callFunction(String function) {
        return "<script>" + function + "</script>";
    }


}
