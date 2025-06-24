package controller;

import DAO.OrderDAO;
import DAO.OrderUnitDAO;
import DAO.ProductDAO;
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
import model.Constant;
import model.Order;
import model.OrderUnit;
import model.User;
import partial.ProductAdmin;
import service.LocalDateTimeAdapter;
import values.MessageValues;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet("/aorder")
public class AdminOrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        System.out.println("admin order");
        if(userLogging==null || !userLogging.hasRole("product")) {
            String script = Constant.callFunction("changeToProductUrl();" +
                    "showErrorToast2('"+ MessageValues.NOT_ROLE+"','none');");

            req.setAttribute("script", script);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product");
            dispatcher.forward(req, resp);
            return;
        }
        String action = req.getParameter("a");
        if(action==null) {
            System.out.println("a null: -> init");
            ArrayList<OrderUnit> orderUnits = OrderUnitDAO.getInstance().selectByStatus(Constant.NONE_CONDITION,0);
            int total = OrderDAO.getInstance().countByStatus(Constant.NONE_CONDITION);
            int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
            if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                totalPage++;
            }
            req.setAttribute("orderUnits", orderUnits);
            req.setAttribute("totalPage", totalPage);
            session.setAttribute("amenu", "order");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "SEARCH": {
                String searchInput = req.getParameter("search");
                if("".equals(searchInput)) {
                    resp.sendRedirect("aorder");
                    break;
                }
                ArrayList<OrderUnit> orderUnits =  new ArrayList<>();
                if(searchInput.contains(":") || searchInput.contains("-")) {
                    orderUnits = OrderUnitDAO.getInstance().selectByTime(searchInput);
                } else {
                    orderUnits = OrderUnitDAO.getInstance().selectListByID(searchInput);
                }
                req.setAttribute("orderUnits", orderUnits);
                String script = Constant.callFunction("setSearchValue('#search-order-form input[name=\"search\"]','"+searchInput+"');");
                req.setAttribute("script", script);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "ORDEROF": {
                System.out.println("order of");
                int id = Integer.parseInt(req.getParameter("id"));

                ArrayList<OrderUnit> orderUnits =  OrderUnitDAO.getInstance().selectOf(id);
                req.setAttribute("orderUnits", orderUnits);
                String script = Constant.callFunction("setTitle('Đơn hàng của ID: "+id+"');");
                req.setAttribute("script", script);
                session.setAttribute("amenu", "order");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "QUERY": {
                System.out.println("query");
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                System.out.println("page: " +page);
                int status = Integer.parseInt(req.getParameter("status"));
                ArrayList<OrderUnit> orderUnits = new ArrayList<>();
                int total = 0;
                String script="";
                orderUnits = OrderUnitDAO.getInstance().selectByStatus(status,offset);
                total = OrderDAO.getInstance().countByStatus(status);
                script = Constant.callFunction("setSelectedOption('byStatus','"+status+"');");
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                script += Constant.callFunction("updatePagination('#aorder-pagination',"+page+");");
                req.setAttribute("script", script);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("totalPage", totalPage);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "UPDATE": {
                System.out.println("update ");
                int id = Integer.parseInt(req.getParameter("id"));
                int status = Integer.parseInt(req.getParameter("status"));
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .create();
                // Kiem tra new status hop le khong
                int currentStatus = OrderDAO.getInstance().selectStatus(id);
                if((status <= currentStatus) || status<0) { // khong hop le
                    Order order = new Order(0);
                    OrderUnit orderUnit = new OrderUnit(order);
                    String json = gson.toJson(orderUnit);
                    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                    jsonObject.addProperty("script",Constant.callFunction("showErrorToast2('"+MessageValues.UPDATE_FAIL+": "+MessageValues.INVALID_STATUS+"!','none');"));
                    json = gson.toJson(jsonObject);
                    resp.getWriter().write(json);
                } else { //hop le
                    int re = OrderDAO.getInstance().updateStatus(id, status);
                    if (re == 1) { // thanh cong
                        OrderUnit orderUnit = OrderUnitDAO.getInstance().selectById(id);
                        String json = gson.toJson(orderUnit);
                        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                        jsonObject.addProperty("script",Constant.callFunction("showSuccessToast2('"+MessageValues.UPDATE_SUCCESS+"','none');"));
                        jsonObject.addProperty("statusName",orderUnit.getStatusName());
                        jsonObject.addProperty("nextStatus",orderUnit.getNextStatus());
                        jsonObject.addProperty("nextStatusName",orderUnit.getNextStatusName());
                        jsonObject.addProperty("updateAt",orderUnit.getUpdateAt());
                        json = gson.toJson(jsonObject);
                        resp.getWriter().write(json);
                    } else { // loi
                        Order order = new Order(0);
                        OrderUnit orderUnit = new OrderUnit(order);
                        String json = gson.toJson(orderUnit);
                        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                        jsonObject.addProperty("script",Constant.callFunction("showErrorToast2('"+MessageValues.UPDATE_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');"));
                        json = gson.toJson(jsonObject);
                        resp.getWriter().write(json);
                    }
                }
                break;
            }
            case "DETAIL": {
                System.out.println("detail");
                int id = Integer.parseInt(req.getParameter("id"));
                OrderUnit orderUnit = OrderUnitDAO.getInstance().selectById(id);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .create();
                String json = gson.toJson(orderUnit);
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                jsonObject.addProperty("createAt",orderUnit.getCreateAt());
                jsonObject.addProperty("productList",orderUnit.getProductsInfo());
                jsonObject.addProperty("paymentInfo",orderUnit.getPaymentInfo());
                jsonObject.addProperty("discountVoucher",orderUnit.getDiscountVoucher());
                jsonObject.addProperty("shippingFee",orderUnit.getShippingFree());
                jsonObject.addProperty("totalMoney",orderUnit.getTotalMoney());
                json = gson.toJson(jsonObject);
                resp.getWriter().write(json);
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


}
