package controller;

import DAO.AddressItemDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AddressItem;
import model.Constant;
import model.User;
import service.LocalDateTypeAdapter;
import values.MessageValues;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        String action = req.getParameter("a");
        action = action.toUpperCase();
        switch (action) {
            case "ADDRESS" : {
                ArrayList<AddressItem> addresses = AddressItemDAO.getInstance().selectByUserID(userLogging.id);
//                String html = renderAddressList(addresses);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(new Gson().toJson(addresses));
                out.flush();
                break;
            }
            case "UPDATEADDRESS" : {
                int id = Integer.parseInt(req.getParameter("id"));
                String receiver = req.getParameter("receiver");
                String phone = req.getParameter("phone");
                String detail = req.getParameter("detail");
                String village = req.getParameter("village");
                String district = req.getParameter("district");
                String province = req.getParameter("province");
                AddressItem a = new AddressItem(id, userLogging.id, receiver, phone, province, district, village, detail);
                int re = AddressItemDAO.getInstance().update(a);
                if(re==1) {
                    ArrayList<AddressItem> addresses = AddressItemDAO.getInstance().selectByUserID(userLogging.id);
//                String html = renderAddressList(addresses);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.print(new Gson().toJson(addresses));
                    out.flush();
                }
                break;
            }
            case "DELETEADDRESS" : {
                int id = Integer.parseInt(req.getParameter("id"));
                AddressItem a =new AddressItem(id);
                int re = AddressItemDAO.getInstance().delete(a);
                if(re==1) {
                    ArrayList<AddressItem> addresses = AddressItemDAO.getInstance().selectByUserID(userLogging.id);
//                String html = renderAddressList(addresses);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.print(new Gson().toJson(addresses));
                    out.flush();
                }
                break;
            }
            case "ADDADDRESS" : {
                System.out.println("Add address");
                int id = userLogging.id;
                String receiver = req.getParameter("receiver");
                String phone = req.getParameter("phone");
                String detail = req.getParameter("detail");
                String village = req.getParameter("village");
                String district = req.getParameter("district");
                String province = req.getParameter("province");
                AddressItem a = new AddressItem(id, userLogging.id, receiver, phone, province, district, village, detail);
                int re = AddressItemDAO.getInstance().insert(a);
                if(re==1) {
                    ArrayList<AddressItem> addresses = AddressItemDAO.getInstance().selectByUserID(userLogging.id);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.print(new Gson().toJson(addresses));
                    out.flush();

                }
                break;
            }
            case "INFO" : {
                User user = UserDAO.getInstance().selectById(userLogging.id);
                System.out.println("info: user name:" + user.name);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())  // Đăng ký TypeAdapter
                        .create();
                String userJson = gson.toJson(user);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(userJson);
                break;
            }
            case "UPDATEINFO" : {
                System.out.println("update info");
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String gender = req.getParameter("gender");
                String phone = req.getParameter("phone");
                String birthday = req.getParameter("birthday");
                LocalDate dob = LocalDate.parse(birthday);
                User user = new User(userLogging.id, name, email, null,  phone, dob, Integer.parseInt(gender),null , null, Constant.NONE);
                int re = UserDAO.getInstance().update(user);
                user = UserDAO.getInstance().selectById(userLogging.id);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())  // Đăng ký TypeAdapter
                        .create();
                String json = gson.toJson(user);
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                jsonObject.addProperty("script",
                        Constant.callFunction("showSuccessToast2('"+MessageValues.UPDATE_SUCCESS+"!','none');" +
                                "cancelEditInfo('#editInfoForm');"));
                json = gson.toJson(jsonObject);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "UPDATEPASSWORD" : {
                String currentPassword = req.getParameter("currentPassword");
                String password = req.getParameter("password");
                password = Constant.hashPassword(password);
                User user = UserDAO.getInstance().checkLogin(userLogging.email,Constant.hashPassword(currentPassword));
                if(user!=null && user.id== userLogging.id) { // dung mat khau
                    int re = UserDAO.getInstance().updatePassword(user.id, password);
                    if(re==1) {
                        String html = "";
                        html+= callFunction("removeModal('#modal-container');");
                        html+= callFunction("clearForm('#editPasswordForm');");
                        html+= callFunction("showSuccessToast2('"+ MessageValues.CHANGE_PWD_SUCCESS+"','none');");
                        resp.setContentType("text/html");
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().write(html);
                    }
                } else { // sai mat khau
                    String html = callFunction("wrongPwd('#editPasswordForm');");
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
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

    public String callFunction(String method) {
        String re ="  <script>\n" +
                    method +
                "     </script>";
        return re;
    }
}
