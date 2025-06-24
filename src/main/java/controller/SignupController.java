package controller;

import DAO.InterfaceImageDAO;
import DAO.UserDAO;
import DAO.VerifyCodeDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;
import model.InterfaceImage;
import model.User;
import model.VerifyCode;
import service.EmailService;
import values.MessageValues;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String logo = Constant.DEFAULT_IMG;
        ArrayList<InterfaceImage> logos = InterfaceImageDAO.getInstance().selectByKind(Constant.LOGO_KIND);
        if(!logos.isEmpty()) logo = logos.get(0).url;
        session.setAttribute("logo", logo);
        String action = req.getParameter("a");
        if(action == null || action.isEmpty()) {

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "SIGNUP": {
                    System.out.println("check signup");
                    String email = req.getParameter("email");
//                    kiem tra xem email da duoc dang ky chua
                    if(UserDAO.getInstance().checkEmail(email)) {// da duoc dang ky
                        System.out.println("email da ton tai");
                        String html = Constant.callFunction("existedEmail('#signup-form','"+ MessageValues.EXIST_EMAIL+"');");
                        resp.getWriter().write(html);
                    } else { // email co the dang ky
                        System.out.println("insert user into database");
                        String name = req.getParameter("name");
                        String password = req.getParameter("password");
                        String hashPassword = Constant.hashPassword(password);

                        User user = new User(Constant.NONE,name,email,hashPassword,null,null,0,null,null,Constant.UNACTIVE);
                        int re=UserDAO.getInstance().insert(user);
                        if(re!=0) { // insert thanh cong
                            System.out.println("chuyen huong");
//                            String html = Constant.callFunction("window.location.href='verify?fa=signup&email="+user.email+"';");

                            String html = Constant.callFunction("submitVerifyAccountForm();");
                            resp.getWriter().write(html);
                        } else {
                            System.out.println("insert failed, do database");
                            String html = renderHtml(email); // de tam, mot nho sua thanh truong hop khong thanh cong
                            resp.getWriter().write(html);
                        }
                    }


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

    public String renderHtml(String req) {
        String html = "";
        if(req.equals("USEDEMAIL")) {
            html = "   <script>\n" +
                    "      tellUsedEmail();\n" +
                    " </script>";
        } else if(req.equals("SUCCESS")) {
            html = "   <script>\n" +
                    "      forward(\"verify?email=\");\n" +
                    " </script>";
        } else {
            html = "   <script>\n" +
                    "      forward(\"verify?action=signup&&email="+req+"\");\n" +
                    " </script>";
        }
        return html;
    }
}
