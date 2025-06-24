package controller;

import DAO.UserDAO;
import DAO.VerifyCodeDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Constant;
import model.VerifyCode;
import service.EmailService;
import values.MessageValues;

import java.io.IOException;
import java.util.Random;

@WebServlet("/verify")
public class VerifyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("a");
        System.out.println("verify");
        if(action==null) {
            String email = req.getParameter("email");
            System.out.println("email:" + email);
            String forAction = req.getParameter("fa");
            System.out.println("fa:" + forAction);
            Random rand = new Random();
            String otp ="";
            for(int i=0;i<5; i++) {
                otp+= String.valueOf(rand.nextInt(9)+1);
            }
            VerifyCode verifyCode = new VerifyCode(otp, email);
            VerifyCodeDAO.getInstance().insert(verifyCode);
            EmailService emailService = new EmailService();
            String mailContent = MessageValues.getOTP_VERIFY_ACCOUNT_MESSAGE(otp);
            emailService.sendHTML(email,MessageValues.WEB_NAME, mailContent);
            req.setAttribute("email", email);
            req.setAttribute("action", forAction);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
            rd.forward(req, resp);
            return;
        }

        action = action.toUpperCase();
        switch (action) {
            case "VERIFY": {
                System.out.println("verify");
                String email = req.getParameter("email");
                String forAction = req.getParameter("action");
                String otp = req.getParameter("otp");
                VerifyCode code = VerifyCodeDAO.getInstance().selectTheLast(email);
                boolean re=true;
                if(code.getIsVerify()==Constant.UNACTIVE) re=false;
                if(!otp.equalsIgnoreCase(code.getCode())) re=false;
                if(!code.isWithinFiveMinutes()) re=false;
                if(re) {
                    VerifyCodeDAO.getInstance().disableCode(otp);
                    if(forAction.equalsIgnoreCase("SIGNUP")) {
                        UserDAO.getInstance().verifyUser(email);
                        String html = Constant.callFunction("openModal2('.verify-success-modal');");
                        resp.getWriter().write(html);
                    }

                } else {
                    String html = Constant.callFunction("wrongOTP('#otp-form','"+ MessageValues.WRONG_OTP+"');");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "RESEND": {
                String email = req.getParameter("email");
                String forAction = req.getParameter("action");
                Random rand = new Random();
                String otp ="";
                for(int i=0;i<5; i++) {
                    otp+= String.valueOf(rand.nextInt(9)+1);
                }
                //                otp="12345";
                VerifyCode verifyCode = new VerifyCode(otp, email);
                VerifyCodeDAO.getInstance().insert(verifyCode);
                EmailService emailService = new EmailService();
                String mailContent = MessageValues.getOTP_VERIFY_ACCOUNT_MESSAGE(otp);
                emailService.sendHTML(email,MessageValues.WEB_NAME, mailContent);

                req.setAttribute("email", email);
                req.setAttribute("action", forAction);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
                rd.forward(req, resp);
                break;
            }
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
