package controller;

import DAO.EmployeeDAO;
import DAO.ProductUnitDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@WebServlet("/aemployee")
public class AdminEmployeeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        System.out.println("admin employee");
        User userLogging = (User) session.getAttribute("userLogging");
        if(userLogging==null || !userLogging.hasRole("employee")) {
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
            ArrayList<User> employees = UserDAO.getInstance().selectEmployees(0);
            int total = UserDAO.getInstance().countEmployee();
            int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
            if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                totalPage++;
            }

            //fake totalPage
//            totalPage=10;

            req.setAttribute("employees", employees);
            req.setAttribute("totalPage", totalPage);
            session.setAttribute("amenu", "employee");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminEmployee.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        action= action.toUpperCase();
        switch (action) {
            case "SEARCH": {
                String searchInput = req.getParameter("search");
                ArrayList<User> employees = UserDAO.getInstance().searchEmployee(searchInput);
                req.setAttribute("employees", employees);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminEmployee.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "QUERY": {
                System.out.println("query");
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                System.out.println("page: " +page);
                ArrayList<User> employees = UserDAO.getInstance().selectEmployees(offset);
                int total = UserDAO.getInstance().countEmployee();
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                String script = Constant.callFunction("updatePagination('#employee-pagination',"+page+");");
                req.setAttribute("script", script);
                req.setAttribute("employees", employees);
                req.setAttribute("totalPage", totalPage);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminEmployee.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "INFO" : {
                System.out.println("r update");
                int id = Integer.parseInt(req.getParameter("id"));
                Employee employee  = EmployeeDAO.getInstance().selectByUser(id);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())  // Đăng ký TypeAdapter
                        .create();
                String employeeJson = gson.toJson(employee);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(employeeJson);
                break;
            }
            case "LOCK": {
                System.out.println("lock employee");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = UserDAO.getInstance().lockUser(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("addClassLocked('#employee"+id+"');");
                    html+= Constant.callFunction("showSuccessToast2('"+ MessageValues.getLock_Success(id)+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getLock_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVE": {
                System.out.println("active employee");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = UserDAO.getInstance().activeUser(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("removeClassLocked('#employee"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getActive_Success(id)+"','none');");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getActive_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "PWD": {
                System.out.println("issue employee password");
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
//                    html =Constant.callFunction("hide('#employee"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getIssuePwd_Success(id)+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getIssuePwd_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETE": {
                System.out.println("delete employee");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = UserDAO.getInstance().deleteUser(id);
                String html="";
                if(re==1) {
                    html =Constant.callFunction("hide('#employee"+id+"');");
                    html += Constant.callFunction("showSuccessToast2('"+ MessageValues.getDelete_Success(id)+"','none');");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.getDelete_Fail(id)+"','none');");
                }
                resp.getWriter().write(html);
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
                String position = req.getParameter("position");
                String area = req.getParameter("area");
                User user = new User(id, name, null, null, phone, dob, gender, null, null, Constant.NONE);
                int re = UserDAO.getInstance().update(user);

                Employee employee = EmployeeDAO.getInstance().selectByUser(user.id);
                EmployeeDAO.getInstance().delete(employee);
                employee = new Employee(user, position, area);
                re = EmployeeDAO.getInstance().insert(employee);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("showSuccessToast2('"+ MessageValues.UPDATE_SUCCESS+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+ MessageValues.UPDATE_FAIL+"','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ADD": {
                System.out.println("add");
                String name = req.getParameter("name");
                String email = req.getParameter("email");

                String pwd = req.getParameter("pwd");
                String position = req.getParameter("position");
                String area = req.getParameter("area");

                String hashedPassword = Constant.hashPassword(pwd);
                User user = new User(name, email, hashedPassword);
                boolean isExistEmail = UserDAO.getInstance().checkEmail(email);
                if(isExistEmail) {
                    String html = Constant.callFunction("errorEmail('"+ MessageValues.EXIST_EMAIL+"');");
                    resp.getWriter().write(html);
                    return;
                }
                user.roles=new ArrayList<>();
                int newUserID = UserDAO.getInstance().insert(user);
                UserDAO.getInstance().activeUser(newUserID); // active user
                Employee employee = new Employee(new User(newUserID), position, area);
                int re = EmployeeDAO.getInstance().insert(employee);
                System.out.println("new user id: " + newUserID);
                if(re==1) {
                    //gui mail thong bao toi email
                    EmailService emailService = new EmailService();
                    String mailContent = MessageValues.getNEW_ACCOUNT_FROM_ADMIN(email,pwd);
                    boolean sendSuccess = emailService.sendHTML(email,MessageValues.WEB_NAME, mailContent);
                    if(sendSuccess==true) {
                        String html = Constant.callFunction("showSuccessToast2('"+ MessageValues.ADD_ACCOUNT_SUCCESS+"','none');");
                        html+= Constant.callFunction("window.location.href='aemployee'");
                        resp.getWriter().write(html);
                    } else {
                        System.out.println("fake email");
                        UserDAO.getInstance().deleteUser(newUserID);
                        EmployeeDAO.getInstance().deleteByUser(new User(newUserID));
                        String html = Constant.callFunction("errorEmail('"+ MessageValues.FAKE_EMAIL+"');");
                        resp.getWriter().write(html);
                    }

                } else {
                    UserDAO.getInstance().deleteUser(newUserID);
                    EmployeeDAO.getInstance().deleteByUser(new User(newUserID));
                    String html = Constant.callFunction("showErrorToast2('"+ MessageValues.ADD_ACCOUNT_FAIL+"</br>"+
                            MessageValues.DATABASE_ERROR+"','none');");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "ROLES" : {
                System.out.println("roles");
                int id = Integer.parseInt(req.getParameter("id"));
                User selectedUser = UserDAO.getInstance().selectById(id);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();  // Đăng ký TypeAdapter;
                String json = gson.toJson(selectedUser);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "UPDATEROLES" : {
                System.out.println("update roles");
                int id = Integer.parseInt(req.getParameter("id"));
                String customer = req.getParameter("customer");
                String employee = req.getParameter("employee");
                String product = req.getParameter("product");
                String order = req.getParameter("order");
                String interfacee = req.getParameter("interface");
                ArrayList<String> roles = new ArrayList<>();
                if(customer!=null && !"".equalsIgnoreCase(customer)) roles.add(customer);
                if(employee!=null && !"".equalsIgnoreCase(employee)) roles.add(employee);
                if(product!=null && !"".equalsIgnoreCase(product)) roles.add(product);
                if(order!=null && !"".equalsIgnoreCase(order)) roles.add(order);
                if(interfacee!=null && !"".equalsIgnoreCase(interfacee)) roles.add(interfacee);

                int re = UserDAO.getInstance().updateRoles(id, roles);
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
