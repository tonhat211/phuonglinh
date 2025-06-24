package controller;

import DAO.CommentDAO;
import DAO.ImageDAO;
import DAO.ProductUnitDAO;
import DAO.SaleProgramDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet("/index")
public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        ArrayList<ProductUnit> saledOnlineProductUnits = ProductUnitDAO.getInstance().selectBySaleProgram(Constant.ONLINE,0,20);
//        ArrayList<ProductUnit> suggestedProductUnits = ProductUnitDAO.getInstance().selectByCategory(Constant.ALL,0,20);
//        LocalTime remaningTime = null;
//        if(saledOnlineProductUnits.size()>0) {
//            remaningTime = SaleProgram.getRemainingTime();
//        }
//        req.setAttribute("saledOnlineProductUnits",saledOnlineProductUnits);
//        req.setAttribute("suggestedProductUnits",suggestedProductUnits);
//        req.setAttribute("remaningTime",remaningTime);
//        RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");
//        rd.forward(req, resp);
        resp.sendRedirect("/product");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }

}
