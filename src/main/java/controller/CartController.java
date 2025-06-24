package controller;

import DAO.CartItemDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.CartUnit;
import model.Constant;
import model.User;
import values.MessageValues;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        System.out.println("Cart controller");
        String action = req.getParameter("a");
        if(action == null || action.isEmpty()) {
            ArrayList<CartUnit> carts = CartItemDAO.getInstance().selectByUserID(userLogging.id);
            req.setAttribute("carts", carts);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/cart.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "ADD": {
                    System.out.println("add");
                    if(userLogging==null) {
                        System.out.println("chua dang nhap");
                        String html = Constant.callFunction("showInfoToast2('"+ MessageValues.NOT_LOGIN+"','none')");
                        resp.getWriter().write(html);
                        return;
                    }
                    int id = Integer.parseInt(req.getParameter("id"));
                    CartItem cartItem = new CartItem(Constant.NONE,id,1,userLogging.id);
                    int existedCartItemID = CartItemDAO.getInstance().checkExistCart(cartItem);
                    if(existedCartItemID != 0) {
                        System.out.println("update cart item");
                        int newQty = CartItemDAO.getInstance().updateQty(existedCartItemID,Constant.PLUS);
                        if (newQty>0) {
                            String html = Constant.callFunction("showSuccessToast2('"+ MessageValues.UPDATE_QTY_SUCCESS+"','none')");
                            resp.getWriter().write(html);
                        }

                    } else {
                        int re = CartItemDAO.getInstance().insert(cartItem);
                        System.out.println("insert cart item");
                        if(re==1) {
                            String html = Constant.callFunction("showSuccessToast2('"+ MessageValues.ADD_TO_CART_SUCCESS+"','none')");
                            resp.getWriter().write(html);
                        }

                    }

                    break;
                }
                case "UPDATEQTY": {
                    int cartID = Integer.parseInt(req.getParameter("cartID"));
                    String operation = req.getParameter("operation");
                    int newQty = CartItemDAO.getInstance().updateQty(cartID, operation);
                    if(newQty==0) {
                        CartItem cart = new CartItem(cartID);
                        CartItemDAO.getInstance().delete(cart);
                    }
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.print(newQty);
                    out.flush();
                    break;
                }
                case "REMOVE": {
                    int cartID = Integer.parseInt(req.getParameter("cartID"));
                    CartItem cartItem = new CartItem(cartID);
                    int re = CartItemDAO.getInstance().delete(cartItem);
                    if(re==1) {
                        String html = Constant.callFunction("showSuccessToast2('"+ MessageValues.DELETE_FROM_CART_SUCCESS+"','none')");
                        resp.getWriter().write(html);
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

    public String renderHtml(String req, String page) {
        String html = "";
        if(req.equals("WRONGPASSWORD")) {
            html = "   <script>\n" +
                    "      tellWrongPassword();\n" +
                    " </script>";
        } else if(req.equals("SUCCESS")) {
            if(page.equalsIgnoreCase("cart")) {
                html = "   <script>\n" +
                        "      forward(\"cart?action=init\");\n" +
                        " </script>";
            } else {
                html = "   <script>\n" +
                        "      forward(\"product?action=init&&category=smartphone\");\n" +
                        " </script>";
            }

        }
        return html;
    }

    public String callFunction(String function) {
        return "<script>" + function + "</script>";
    }
}
