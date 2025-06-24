package controller;

import DAO.EmployeeDAO;
import DAO.InterfaceImageDAO;
import DAO.UserDAO;
import com.mysql.cj.Session;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;
import model.Employee;
import model.InterfaceImage;
import model.User;
import service.EmailService;
import values.MessageValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        System.out.println("login controller");
        String action = req.getParameter("a");
        String logo = Constant.DEFAULT_IMG;
        ArrayList<InterfaceImage> logos = InterfaceImageDAO.getInstance().selectByKind(Constant.LOGO_KIND);
        if(!logos.isEmpty()) logo = logos.get(0).url;
        session.setAttribute("logo", logo);


        if(action == null) {
            System.out.println("login action null");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch(action) {
            case "LOGIN": {
                System.out.println("check login");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                password = Constant.hashPassword(password);

//                email = "tominh@gmail.com";
//                password = "81dc9bdb52d04dc20036dbd8313ed055";

                boolean isEmail = UserDAO.getInstance().checkEmail(email);
                if(!isEmail) {
                    System.out.println("mail khong ton tai");
                    String html = callFunction("unexistedEmail();");
                    resp.getWriter().write(html);
                    break;
                }
                User u = UserDAO.getInstance().checkLogin(email, password);
                if(u==null) { // khong thanh cong
                    System.out.println("login failed");
                    String html = callFunction("wrongPwd();");
                    resp.getWriter().write(html);
                } else { // thanh cong
                    if(u.avai==0) {
                        System.out.println("chua xac thuc");
                        String html = callFunction("openModal2('#verify-otp-modal');");
                        resp.getWriter().write(html);
                        return;
                    }
                    System.out.println("login success");
                    String page = req.getParameter("page");
                    if(page==null) page = "product";
                    String html;
                    session.setAttribute("userLogging",u);
                    System.out.println("page: " + page);
                    html =callFunction("window.location.href='product';");
                    String role = u.checkRole();
                    if(role != null) { //admin
                        Employee aLogging = EmployeeDAO.getInstance().selectByUser(u.id);
                        session.setAttribute("aLogging",aLogging);
                        System.out.println("confirm admin");
                        role = "a" + role.toLowerCase();

                        html = callFunction("window.location.href='"+role+"';");
//                        html = callFunction("window.location.href='ainterface';");
                    }
                    resp.getWriter().write(html);
                }
                break;
            }
            case "LOGOUT" :{
                session.removeAttribute("userLogging");
                session.removeAttribute("aLogging");
                resp.sendRedirect("product");
                break;
            }
            case "PWD": {
                String email = req.getParameter("email");
                Random rand = new Random();
                String newPwd ="";
                for(int i=0;i<8; i++) {
                    newPwd+= Constant.EN_CHARS.charAt(rand.nextInt(Constant.EN_CHARS.length()));
                }
                String hashPwd = Constant.hashPassword(newPwd);
                UserDAO.getInstance().generateNewPassword(email,hashPwd);
                EmailService emailService = new EmailService();
                String mailContent = MessageValues.getGENERATE_NEW_PWD(newPwd);
                emailService.sendHTML(email,MessageValues.WEB_NAME, mailContent);
                resp.sendRedirect("login");
                break;
            }
            case "INDEX": {
                System.out.println("index");
                User userLogging = (User)session.getAttribute("userLogging");
                if(userLogging==null) {
                    System.out.println("index user");
                    resp.sendRedirect("product");
                    return;
                }
                    String role = userLogging.checkRole();
                if(role != null) { //admin
                    role = "a" + role.toLowerCase();
                    System.out.println("index admin");
                    resp.sendRedirect(role);
                } else {
                    System.out.println("index user");
                    resp.sendRedirect("product");
                }

                break;

            }
        }




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public String renderHtml(String req, String page) {
        String html = "";
        if(req.equals("WRONGPASSWORD")) {
            html = "   <script>\n" +
                    "      tellWrongPassword();\n" +
                    " </script>";
        } else if(req.equals("SUCCESS")) {
            if(!page.equals("")) {
                html = "   <script>\n" +
                        "      forward(\""+page+"\");\n" +
                        " </script>";
            }
            else  {
                html = "   <script>\n" +
                        "      forward(\"product?action=init&&category=smartphone\");\n" +
                        " </script>";
            }
            System.out.println(html);

        }
        return html;
    }

    public String callFunction(String function) {
        return "   <script>\n" +
                function +
                " </script>";

    }
}
