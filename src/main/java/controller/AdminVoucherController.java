package controller;

import DAO.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import partial.ProductAdmin;
import service.LocalDateTimeAdapter;
import values.MessageValues;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/avoucher")
public class AdminVoucherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        System.out.println("admin voucher");
        User userLogging = (User) session.getAttribute("userLogging");
        if(userLogging==null || !userLogging.hasRole("voucher")) {
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
            ArrayList<Voucher> vouchers = VoucherDAO.getInstance().selectAll();
            req.setAttribute("vouchers", vouchers);
            session.setAttribute("amenu", "voucher");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminVoucher.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "LOCK": {
                System.out.println("lock voucher");
                String id = req.getParameter("id");
                int re = VoucherDAO.getInstance().lockVoucher(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("addClassLocked('#voucher"+id+"');");
                    html+= Constant.callFunction("showSuccessToast2('"+ MessageValues.getLock_Success(1)+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getLock_Fail(1)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVE": {
                System.out.println("active voucher");
                String id = req.getParameter("id");
                int re = VoucherDAO.getInstance().activeVoucher(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("removeClassLocked('#voucher"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getActive_Success(1)+"','none');");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getActive_Fail(1)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETE": {
                System.out.println("delete voucher");
                String id = req.getParameter("id");
                int re = VoucherDAO.getInstance().deleteVoucher(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("hide('#voucher"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getDelete_Success(1)+"','none');");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getDelete_Fail(1)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "UPDATE": {
                System.out.println("update info");
                String id = req.getParameter("id");
                int discount = Integer.parseInt(req.getParameter("discount"));
                double maxMoney = Double.parseDouble(req.getParameter("maxMoney"));
                int qty = Integer.parseInt(req.getParameter("qty"));
                System.out.println("startTime: " + "".equals(req.getParameter("startTime")));
                String startTimeString = req.getParameter("startTime");
                String endTimeString = req.getParameter("endTime");
                LocalDateTime startTime = null;
                LocalDateTime endTime = null;
                if(!"".equals(startTimeString)) {
                    startTime = LocalDateTime.parse(startTimeString);
                }
                if(!"".equals(endTimeString)) {
                    endTime = LocalDateTime.parse(endTimeString);
                }
                System.out.println("endTime:" + endTime);

                Voucher voucher = new Voucher(id,null,null,discount,maxMoney,qty,startTime,endTime,null,1);
                int re = VoucherDAO.getInstance().updateBase(voucher);
                if(re==1) {
                    voucher = VoucherDAO.getInstance().selectByVoucherID(id);
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                            .create();
                    String json = gson.toJson(voucher);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(json);
                }

                break;
            }
            case "DESCRIPTION": {
                System.out.println("DESCRIPTION");
                String id = req.getParameter("id");
                Voucher voucher = VoucherDAO.getInstance().selectByVoucherID(id);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .create();
                String json = gson.toJson(voucher);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "UPDATEDESCRIPTION": {
                System.out.println("DESCRIPTION");
                String id = req.getParameter("id");
                String description = req.getParameter("description");
                int re = VoucherDAO.getInstance().updateDescription(id,description);
                if(re==1) resp.getWriter().write("true");
                else resp.getWriter().write("false");
                break;
            }
            case "APPLIEDPRODUCTS": {
                System.out.println("PRODUCTS");
                String id = req.getParameter("id");
                Voucher voucher = VoucherDAO.getInstance().selectByVoucherID(id);
                ArrayList<ProductAdmin> productAdmins = ProductDAO.getInstance().selectProductAdminByIDs(voucher.productIDs);
                Gson gson = new Gson();
                String json = gson.toJson(productAdmins);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
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
