package controller;

import DAO.*;
import com.google.gson.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import partial.ProductAdmin;
import service.LocalDateTimeAdapter;
import values.MessageValues;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("product controller");
        HttpSession session = req.getSession();
        String action = req.getParameter("a");

        String logo = Constant.DEFAULT_IMG;
        ArrayList<InterfaceImage> logos = InterfaceImageDAO.getInstance().selectByKind(Constant.LOGO_KIND);
        if(!logos.isEmpty()) logo = logos.get(0).url;
        session.setAttribute("logo", logo);
        ArrayList<InterfaceImage> banners = InterfaceImageDAO.getInstance().selectByKind(Constant.BANNER_KIND);
        session.setAttribute("banners", banners);


        if(action==null) {
            System.out.println("product");


            ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategory(Constant.NAM_CATEGORY,0);
            req.setAttribute("productUnits", productUnits);
            session.setAttribute("logo", logo);
            session.setAttribute("category", Constant.NAM_CATEGORY);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/product.jsp");
            rd.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch(action) {
            case "QUERY": {
                System.out.println("query: " + req.getParameter("c"));
                String category = req.getParameter("c");
                if(category==null) category = Constant.NAM_CATEGORY;
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategory(category,0);
                req.setAttribute("productUnits", productUnits);
                session.setAttribute("category", category);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/product.jsp");
                rd.forward(req, resp);
                break;
            }
            case "DETAIL": {
                System.out.println("detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                ProductUnit productUnit = ProductUnitDAO.getInstance().selectById(id);
                ArrayList<Image> images = ImageDAO.getInstance().selectByProduct(new Product(id));
                SaleProgram saleProgram = SaleProgramDAO.getInstance().selectByProduct(id);
                ArrayList<Comment> comments = CommentDAO.getInstance().selectByProductID(id);
                double avgRating = 0;
                for(Comment comment : comments) {
                    avgRating+=comment.star;
                }
                avgRating = avgRating/comments.size();
                avgRating = Math.round(avgRating * 10.0) / 10.0;
                if(comments.isEmpty()) avgRating = 0;

                req.setAttribute("productUnit",productUnit);
                req.setAttribute("images",images);
                req.setAttribute("saleProgram",saleProgram);
                req.setAttribute("comments",comments);
                req.setAttribute("avgRating",avgRating);

                // tang prominence+1
                ProductDAO.getInstance().updateProminence(id);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/productDetail.jsp");
                rd.forward(req, resp);
                break;
            }
            case "CLASSIFY": {
                int id = Integer.parseInt(req.getParameter("id"));
                double[] priceInfo = ProductDetailDAO.getInstance().selectPriceInfo(id);
                String price = Constant.formatPrice(priceInfo[0]);
                String prePrice = Constant.formatPrice(priceInfo[1]);
                if(Double.compare(priceInfo[0],0)==0) price = Constant.UNKNOWN;
                if(Double.compare(priceInfo[1],0)==0) prePrice = Constant.UNKNOWN;
                String html = callFunction("updatePrice('"+price+"','"+prePrice+"');");
                resp.getWriter().write(html);
                break;
            }
            case "MORE": {
                System.out.println("more");
                String category = req.getParameter("c");
                if(category==null) category = Constant.NAM_CATEGORY;
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):1;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategory(category,offset);
                int newPage = page+1;
                if(productUnits.isEmpty()) newPage = page;
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .create();
                List<JsonObject> productJsonList = new ArrayList<>();
                for (ProductUnit productUnit : productUnits) {
                    JsonElement productJson = gson.toJsonTree(productUnit);
                    JsonObject productJsonObject = productJson.getAsJsonObject();
                    productJsonObject.addProperty("formatPrice", productUnit.getPriceFormat());
                    productJsonObject.addProperty("formatPrePrice", productUnit.getPrePriceFormat());
                    productJsonList.add(productJsonObject);
                }
                JsonObject finalJsonObject = new JsonObject();
                finalJsonObject.add("productUnits", gson.toJsonTree(productJsonList));
                finalJsonObject.addProperty("script", Constant.callFunction("updateMorePage("+newPage+");"));
                String json = gson.toJson(finalJsonObject);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "SEARCH": {
                System.out.println("search");
                String searchInput = req.getParameter("search-input");
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().search(searchInput);
                req.setAttribute("productUnits", productUnits);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product.jsp");
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

    public String renderNotify(String notify) {
        String re="";
        re= "<script>" +
                "showSuccessToast('"+notify+"');" +
            "</script>";

        return re;
    }

    public String callFunction(String function) {
        return "<script>" + function + "</script>";
    }

}
