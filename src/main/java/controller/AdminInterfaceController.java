package controller;

import DAO.ImageDAO;
import DAO.InterfaceImageDAO;
import DAO.OrderDAO;
import DAO.OrderUnitDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.LocalDateTimeAdapter;
import values.MessageValues;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@MultipartConfig
@WebServlet("/ainterface")
public class AdminInterfaceController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        System.out.println("admin interface");
        if(userLogging==null || !userLogging.hasRole("interface")) {
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
            ArrayList<InterfaceImage> interfaceImages = InterfaceImageDAO.getInstance().selectByKind(Constant.BANNER_KIND);
            req.setAttribute("interfaceImages", interfaceImages);
            session.setAttribute("amenu", "interface");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminInterface.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "UPDATEBANNER": {
                String imgUrls = req.getParameter("imgUrls");
                ArrayList<String> imgUrlList = new ArrayList<>();
                if(!"".equalsIgnoreCase(imgUrls)) {
                    String temps[] = imgUrls.split("==");
                    for(String s : temps) {
                        imgUrlList.add(s);
                    }
                }
                ArrayList<InterfaceImage> interfaceImages = InterfaceImageDAO.getInstance().selectByKind(Constant.BANNER_KIND);
                ArrayList<String> currentImgsUrl = new ArrayList();
                for(InterfaceImage image : interfaceImages) {
                    currentImgsUrl.add(image.url);
                }
                Set<String> set1 = new HashSet<>(currentImgsUrl);
                Set<String> set2 = new HashSet<>(imgUrlList);
                if (!set1.equals(set2)) { // co cap nhat
                    if(imgUrlList.isEmpty()) { // xoa het anh
//                        imgUrlList.add(Constant.DEFAULT_IMG);
                        InterfaceImageDAO.getInstance().deleteAllByKind(Constant.BANNER_KIND);
                    } else { // co thay doi
                        String uploadImagesDir = getServletContext().getRealPath("/") + Constant.BANNER_IMG_DIR;
                        File imagesDir = new File(uploadImagesDir);
                        if (!imagesDir.exists()) {
                            imagesDir.mkdirs();  // Create the images directory
                        }
                        Collection<Part> parts = req.getParts();
                        for (Part part : parts) {
                            if (part.getName().equals("imgs") && part.getSize() > 0) {
                                String imageFileName = getFileName(part);
                                if(imgUrlList.contains(imageFileName)) {
                                    String imagePath = uploadImagesDir + File.separator + imageFileName;
                                    File file = new File(imagePath);
                                    if (!file.exists()) part.write(imagePath);   // them vao server nhung anh trong input file
                                }
                            }
                        }
                        System.out.println("update imgs");
                        InterfaceImageDAO.getInstance().deleteAllByKind(Constant.BANNER_KIND);
                        List<InterfaceImage> newImgs = new ArrayList<>();
                        for(String temp :imgUrlList) {
                            InterfaceImage tempImg = new InterfaceImage(0,temp,temp,Constant.BANNER_KIND);
                            newImgs.add(tempImg);
                        }
                        InterfaceImageDAO.getInstance().insertList(newImgs);

                    }
                }

                resp.getWriter().write("success");
                break;

            }
            case "UPDATELOGO": {
                String thumbnailInput = req.getParameter("thumbnailInput");
                if(!Constant.DEFAULT_IMG.equalsIgnoreCase(thumbnailInput)) {
                    String uploadThumbnailDir = getServletContext().getRealPath("/") + Constant.LOGO_IMG_DIR;
                    File thumbnailDir = new File(uploadThumbnailDir);
                    if (!thumbnailDir.exists()) {
                        thumbnailDir.mkdirs();
                    }
                    Part thumbnailPart = req.getPart("thumbnail");
                    String thumbnailFileName="";
                    if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
                        thumbnailFileName = getFileName(thumbnailPart);
                        String thumbnailPath = uploadThumbnailDir + File.separator + thumbnailFileName;
                        File file = new File(thumbnailPath);
                        if (!file.exists()) thumbnailPart.write(thumbnailPath);

                    }
                    InterfaceImageDAO.getInstance().deleteAllByKind(Constant.LOGO_KIND);
                    InterfaceImage image = new InterfaceImage(0,thumbnailInput,thumbnailInput,Constant.LOGO_KIND);
                    InterfaceImageDAO.getInstance().insert(image);
                }
                session.setAttribute("logo", thumbnailInput);

                resp.getWriter().write("success");

                break;

            }
            case "CLEARIMGSTORAGE": {
                System.out.println("CLEARIMGSTORAGE");
                // clear imgs in banner
                ArrayList<String> databaseImgUrls = new ArrayList();
                ArrayList<InterfaceImage> images = InterfaceImageDAO.getInstance().selectByKind(Constant.BANNER_KIND);
                for(InterfaceImage image : images) {
                    databaseImgUrls.add(image.url);
                }
                System.out.println("delete in banner");
                String dirPath = getServletContext().getRealPath("/") + Constant.BANNER_IMG_DIR;
                File imgDir = new File(dirPath);
                if (imgDir.exists()) {
                   File[] files = imgDir.listFiles();
                   for (File file : files) {
                       if(!databaseImgUrls.contains(file.getName())) {
                           System.out.println("Deleting " +file.getName()+"...");
                           file.delete();
                       }
                   }
                }
                // clear imgs in detail
                ArrayList<Image> imagesDetail = ImageDAO.getInstance().selectAll();
                databaseImgUrls.clear();
                for(Image image : imagesDetail) {
                    databaseImgUrls.add(image.url);
                }
                System.out.println("delete in detail");
                dirPath = getServletContext().getRealPath("/") + Constant.PRODUCT_DETAIL_IMG_DIR;
                imgDir = new File(dirPath);
                if (imgDir.exists()) {
                    File[] files = imgDir.listFiles();
                    for (File file : files) {
                        if(!databaseImgUrls.contains(file.getName())) {
                            System.out.println("Deleting " +file.getName()+"...");
                            file.delete();
                        }
                    }
                }
                // clear imgs in logo
                images = InterfaceImageDAO.getInstance().selectByKind(Constant.LOGO_KIND);
                for(InterfaceImage image : images) {
                    databaseImgUrls.add(image.url);
                }
                System.out.println("delete in logo");
                dirPath = getServletContext().getRealPath("/") + Constant.LOGO_IMG_DIR;
                imgDir = new File(dirPath);
                if (imgDir.exists()) {
                    File[] files = imgDir.listFiles();
                    for (File file : files) {
                        if(!databaseImgUrls.contains(file.getName())) {
                            System.out.println("Deleting " +file.getName()+"...");
                            file.delete();
                        }
                    }
                }


                resp.getWriter().write("success");

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

    public String getFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }


}
