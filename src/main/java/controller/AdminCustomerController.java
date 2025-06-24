package controller;

import DAO.UserDAO;
import com.google.gson.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.EmailService;
import service.LocalDateTypeAdapter;
import values.MessageValues;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/acustomer")
public class AdminCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        if(userLogging==null || !userLogging.hasRole("customer")) {
            String script = Constant.callFunction("changeToProductUrl();" +
                    "showErrorToast2('"+ MessageValues.NOT_ROLE+"','none');");

            req.setAttribute("script", script);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product");
            dispatcher.forward(req, resp);
            return;
        }
        System.out.println("admin customer");
        String action = req.getParameter("a");
        if(action==null) {
            System.out.println("a null: -> init");
            ArrayList<User> customers = UserDAO.getInstance().selectCustomers(0);
            int total = UserDAO.getInstance().countCustomer();
            int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
            if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                totalPage++;
            }

            //fake totalPage
//            totalPage=10;

            req.setAttribute("customers", customers);
            req.setAttribute("totalPage", totalPage);
            session.setAttribute("amenu", "customer");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "SEARCH": {
                String searchInput = req.getParameter("search");
                ArrayList<User> customers = UserDAO.getInstance().searchCustomer(searchInput);
//                req.setAttribute("numOfPages", 1);
//                req.setAttribute("productUnits", productUnits);
                req.setAttribute("customers", customers);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "QUERY": {
                System.out.println("query");
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                System.out.println("page: " +page);
                ArrayList<User> customers = UserDAO.getInstance().selectCustomers(offset);
//                Gson gson = new GsonBuilder()
//                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())  // Đăng ký TypeAdapter
//                        .create();
//                String userJson = gson.toJson(customers);
//                resp.setContentType("application/json");
//                resp.setCharacterEncoding("UTF-8");
//                resp.getWriter().write(userJson);
//                break;
                int total = UserDAO.getInstance().countCustomer();
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                String script = Constant.callFunction("updatePagination('#customer-pagination',"+page+");");
                req.setAttribute("script", script);
                req.setAttribute("customers", customers);
                req.setAttribute("totalPage", totalPage);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "INFO" : {
                System.out.println("r update");
                int id = Integer.parseInt(req.getParameter("id"));
                User user  = UserDAO.getInstance().selectById(id);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())  // Đăng ký TypeAdapter
                        .create();
                String userJson = gson.toJson(user);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(userJson);
                break;

            }
            case "LOCK": {
                System.out.println("lock customer");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = UserDAO.getInstance().lockUser(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("addClassLocked('#customer"+id+"');");
                    html+= Constant.callFunction("showSuccessToast2('"+ MessageValues.getLock_Success(id)+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getLock_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVE": {
                System.out.println("active customer");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = UserDAO.getInstance().activeUser(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("removeClassLocked('#customer"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getActive_Success(id)+"','none');");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getActive_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETE": {
                System.out.println("delete customer");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = UserDAO.getInstance().deleteUser(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("hide('#customer"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getDelete_Success(id)+"','none');");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getDelete_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ORDER": {
                System.out.println("cutomer: order");
                int id = Integer.parseInt(req.getParameter("id"));
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/aorder?action=orderOf&id="+id);
                dispatcher.forward(req, resp);
                break;
            }
            case "PWD": {
                System.out.println("issue customer password");
                int id = Integer.parseInt(req.getParameter("id"));
                User user= UserDAO.getInstance().selectById(id);
                if(user==null) {
                    String html = Constant.callFunction("showErrorToast2('"+ MessageValues.getFind_Fail(id)+"','none');");
                    resp.getWriter().write(html);
                    break;
                }

                Random rand = new Random();
                String newPwd ="";
                for(int i=0;i<8; i++) {
                    newPwd+= Constant.EN_CHARS.charAt(rand.nextInt(Constant.EN_CHARS.length()));
                }
                String hashPwd = Constant.hashPassword(newPwd);
                int re = UserDAO.getInstance().generateNewPassword(user.email, hashPwd);
                EmailService emailService = new EmailService();
                String mailContent = MessageValues.getGENERATE_NEW_PWD_FROM_ADMIN(newPwd);
                emailService.sendHTML(user.email,MessageValues.WEB_NAME, mailContent);
                String html="";
                if(re==1) {
//                    html =Constant.callFunction("hide('#customer"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getIssuePwd_Success(id)+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getIssuePwd_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ADD": {
                System.out.println("add");
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String pwd = req.getParameter("pwd");

                String hashedPassword = Constant.hashPassword(pwd);
                User user = new User(name, email, hashedPassword);
                boolean isExistEmail = UserDAO.getInstance().checkEmail(email);
                if(isExistEmail) {
                    ArrayList<User> customers = UserDAO.getInstance().selectCustomers(0);
                    String html = Constant.callFunction("showErrorToast2('"+ MessageValues.EXIST_EMAIL+"','none');");
                    req.setAttribute("script", html);
                    req.setAttribute("customers", customers);
                    session.setAttribute("amenu", "customer");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
                    dispatcher.forward(req, resp);
                    return;
                }
                int re = UserDAO.getInstance().insert(user);
                re = UserDAO.getInstance().activeUser(re); // active user
                if(re==1) {
                    //gui mail thong bao khach hang
                    EmailService emailService = new EmailService();
                    String mailContent = MessageValues.getNEW_ACCOUNT_FROM_ADMIN(email,pwd);
                    emailService.sendHTML(email,MessageValues.WEB_NAME, mailContent);

                    ArrayList<User> customers = UserDAO.getInstance().selectCustomers(0);
                    String html = Constant.callFunction("showSuccessToast2('"+ MessageValues.ADD_ACCOUNT_SUCCESS+"','none');");
                    req.setAttribute("script", html);
                    req.setAttribute("customers", customers);
                    session.setAttribute("amenu", "customer");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
                    dispatcher.forward(req, resp);

                } else {
                    ArrayList<User> customers = UserDAO.getInstance().selectCustomers(0);
                    String html = Constant.callFunction("showErrorToast2('"+ MessageValues.ADD_ACCOUNT_FAIL+"','none');");
                    req.setAttribute("script", html);
                    req.setAttribute("customers", customers);
                    session.setAttribute("amenu", "customer");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminCustomer.jsp");
                    dispatcher.forward(req, resp);
                }
                break;
            }
            case "UPDATE": {
                System.out.println("update");
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                int gender = Integer.parseInt(req.getParameter("gender"));
                String dobString =  req.getParameter("dob");
                LocalDate dob;
                if(dobString==null || dobString.equals("")) {
                    dob =null;
                } else {
                    dob = LocalDate.parse(dobString);
                }


                String phone =req.getParameter("phone");
                User user = new User(id, name, null, null, phone, dob, gender, null, null, Constant.NONE);
                int re = UserDAO.getInstance().update(user);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("showSuccessToast2('"+ MessageValues.UPDATE_SUCCESS+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.UPDATE_FAIL+"','none');");
                }
                resp.getWriter().write(html);
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
