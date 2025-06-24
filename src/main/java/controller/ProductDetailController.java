package controller;

import DAO.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Comment;
import model.Image;
import model.ProductUnit;
import model.SaleProgram;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/detail")
public class ProductDetailController  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
//        HttpSession session = req.getSession();
        int productID = Integer.parseInt(req.getParameter("productID"));
        String version = req.getParameter("version");

        String action  = req.getParameter("action");
        if(action==null || action.isEmpty()) {
//            ProductUnit pu = ProductUnitDAO.getInstance().selectDetailOf(productID,version);
//            ArrayList<Image> images = ImageDAO.getInstance().selectByProductDetail(productID,version);
//            ArrayList<Integer> puIDs = pu.getIDs();
//            SaleProgram saleProgram = SaleProgramDAO.getInstance().selectById(puIDs.get(0));
//            ArrayList<Comment> comments = CommentDAO.getInstance().selectObjectIDs(puIDs);

//            req.setAttribute("pu",pu);
//            req.setAttribute("images",images);
//            req.setAttribute("saleProgram",saleProgram);
//            req.setAttribute("comments",comments);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/product.jsp");
            rd.forward(req, resp);
        }



    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
