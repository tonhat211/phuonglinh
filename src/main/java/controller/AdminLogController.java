package controller;

import DAO.EmployeeDAO;
import DAO.LogDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;
import model.Employee;
import model.Log;
import model.User;
import service.EmailService;
import service.LocalDateTypeAdapter;
import values.MessageValues;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/alog")
public class AdminLogController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        System.out.println("admin log");
        User userLogging = (User) session.getAttribute("userLogging");
        if(userLogging==null || !userLogging.hasAnyRole()) {
            String script = Constant.callFunction("changeToProductUrl();" +
                    "showErrorToast2('"+ MessageValues.NOT_ROLE+"','none');");

            req.setAttribute("script", script);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product");
            dispatcher.forward(req, resp);
            return;
        }
        System.out.println("admin log");
        String action = req.getParameter("a");
        if(action==null) {
            System.out.println("a null: -> init");
            String role = userLogging.checkRole();
            if(role != null) {
                String area = role;
                req.setAttribute("area", area);
                ArrayList<Log> logs = LogDAO.getInstance().selectByAreaAndLevel(area,Constant.NONE_CONDITION,0);
                int total = LogDAO.getInstance().countByLevel(area,Constant.NONE_CONDITION);
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                req.setAttribute("logs", logs);
                req.setAttribute("totalPage", totalPage);
                session.setAttribute("amenu", "log");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminLog.jsp");
                dispatcher.forward(req, resp);
            }
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "QUERY": {
                System.out.println("query");
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                System.out.println("page: " +page);
                String area = req.getParameter("area");
                int level = Integer.parseInt(req.getParameter("level"));
                ArrayList<Log> logs = LogDAO.getInstance().selectByAreaAndLevel(area,level,offset);
                int total = LogDAO.getInstance().countByLevel(area,level);
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                String script = Constant.callFunction("updatePagination('#log-pagination',"+page+");");
                req.setAttribute("script", script);
                req.setAttribute("logs", logs);
                req.setAttribute("totalPage", totalPage);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminLog.jsp");
                dispatcher.forward(req, resp);
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
