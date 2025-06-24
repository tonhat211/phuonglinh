package controller;

import DAO.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import partial.ProductAdmin;
import partial.ProductInfoAdmin;
import values.MessageValues;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@MultipartConfig
@WebServlet("/aproduct")
public class AdminProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        System.out.println("admin product");
        if(userLogging==null || !userLogging.hasRole("product")) {
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
            ArrayList<ProductAdmin> productAdmins = ProductDAO.getInstance().selectByCategoryAdmin(Constant.ALL_String,0);
            int total = ProductDAO.getInstance().countByCategory(Constant.ALL_String);
            int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
            if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                totalPage++;
            }

            req.setAttribute("productAdmins", productAdmins);
            req.setAttribute("totalPage", totalPage);
            session.setAttribute("amenu", "product");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminProduct.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "SEARCH": {
                System.out.println("search");
                String searchInput = req.getParameter("search");
                String filterSearch = req.getParameter("filterSearch");
                ArrayList<ProductAdmin> productAdmins = new ArrayList<>();
                if(Constant.ID_FILTER.equals(filterSearch)) {
                    productAdmins = ProductDAO.getInstance().searchAdminByID(searchInput);
                } else if(Constant.INGREDIENT_FILTER.equals(filterSearch)) {
                    productAdmins = ProductDAO.getInstance().searchAdminByIngredient(searchInput);
                } else {
                    filterSearch = Constant.NAME_FILTER;
                    productAdmins = ProductDAO.getInstance().searchAdminByName(searchInput);
                }
                req.setAttribute("productAdmins", productAdmins);
                String script = Constant.callFunction("setSelectedOption('filterSẻarch','"+filterSearch+"');");

                req.setAttribute("script", script);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminProduct.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "DETAIL": {
                System.out.println("detail");
                int id = Integer.parseInt(req.getParameter("id"));
                ArrayList<ProductDetail> productDetails = ProductDetailDAO.getInstance().selectByProduct(new Product(id));
                Gson gson = new Gson();
                String json = gson.toJson(productDetails);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "INFO": {
                System.out.println("info");
                int id = Integer.parseInt(req.getParameter("id"));
                Product product = ProductDAO.getInstance().selectById(id);
                ArrayList<Image> images = ImageDAO.getInstance().selectByProduct(product);
                ProductInfoAdmin productInfoAdmin = new ProductInfoAdmin(product,images);
                Gson gson = new Gson();
                String json = gson.toJson(productInfoAdmin);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "QUERY": {
                System.out.println("query");
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                System.out.println("page: " +page);
                String category = req.getParameter("category");
                ArrayList<ProductAdmin> productAdmins = new ArrayList<>();
                int total = 0;
                String script="";
                if(category!=null) {
                    productAdmins = ProductDAO.getInstance().selectByCategoryAdmin(category,offset);
                    total = ProductDAO.getInstance().countByCategory(category);
                    script = Constant.callFunction("setSelectedOption('byCategory','"+category+"');");
                } else {
                    productAdmins = ProductDAO.getInstance().selectByCategoryAdmin(Constant.ALL_String,offset);
                    total = ProductDAO.getInstance().countByCategory(Constant.ALL_String);
                    script = Constant.callFunction("setSelectedOption('byCategory','"+Constant.ALL_String+"');");

                }
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                script += Constant.callFunction("updatePagination('#product-pagination',"+page+");");
                String fa = req.getParameter("fa");
                if("ADD".equalsIgnoreCase(fa)) {
                    System.out.println("fa: " + fa);
                    script += Constant.callFunction("scrollToBottom();" +
                            "showSuccessToast2('"+MessageValues.ADD_SUCCESS+"','none');");

                }
                req.setAttribute("script", script);
                req.setAttribute("productAdmins", productAdmins);
                req.setAttribute("totalPage", totalPage);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminProduct.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "UPDATE": {
                System.out.println("update info");
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                String category = req.getParameter("category");
                String otherCategory = req.getParameter("other-category");
                String ingredient = req.getParameter("ingredient");
                int prominence = Integer.parseInt(req.getParameter("prominence"));
                String description = req.getParameter("description");
                String direction = req.getParameter("direction");
                String thumbnailInput = req.getParameter("thumbnailInput");
                if(!"".equalsIgnoreCase(otherCategory)) {
                    category = otherCategory;
                }
                Product product = new Product(id, name, ingredient, category, description, direction, thumbnailInput, prominence, Constant.NONE);
                // cap nhat thong tin truoc
                System.out.println("update info");
                ProductDAO.getInstance().update(product);
                // cap nhat anh
                //kiem tra su cap nhat anh
                String currentThumbnail = ProductDAO.getInstance().selectThumbnail(id);
                if(!currentThumbnail.equalsIgnoreCase(thumbnailInput)) { // co cap nhat thumbnail
                    if("".equalsIgnoreCase(thumbnailInput)) { // xoa anh
                        ProductDAO.getInstance().updateThumbnail(id,Constant.DEFAULT_IMG);
                    } else { // thay doi thumbnail
                        String uploadThumbnailDir = getServletContext().getRealPath("/") + Constant.THUMBNAIL_DIR;
                        File thumbnailDir = new File(uploadThumbnailDir);
                        if (!thumbnailDir.exists()) {
                            thumbnailDir.mkdirs();  // Create the thumbnails directory
                        }
                        Part thumbnailPart = req.getPart("thumbnail"); // For the thumbnail (single file)
                        String thumbnailFileName="";
                        if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
                            thumbnailFileName = getFileName(thumbnailPart);
                            String thumbnailPath = uploadThumbnailDir + File.separator + thumbnailFileName;
                            File file = new File(thumbnailPath);
                            if (!file.exists()) thumbnailPart.write(thumbnailPath);
                            // Write the file to the server
                            System.out.println("update thumbnail");
                            ProductDAO.getInstance().updateThumbnail(id,thumbnailFileName);
                        }
                    }

                }

                String imgUrls = req.getParameter("imgUrls");
                ArrayList<String> imgUrlList = new ArrayList<>();
                if(!"".equalsIgnoreCase(imgUrls)) {
                    String temps[] = imgUrls.split("==");
                    for(String s : temps) {
                        imgUrlList.add(s);
                    }
                }
                ArrayList<Image> images = ImageDAO.getInstance().selectByProduct(product);
                ArrayList<String> currentImgsUrl = new ArrayList();
                for(Image image : images) {
                    currentImgsUrl.add(image.getUrl());
                }
                Set<String> set1 = new HashSet<>(currentImgsUrl);
                Set<String> set2 = new HashSet<>(imgUrlList);
                // So sánh hai Set
                if (!set1.equals(set2)) { // co cap nhat
                    if(imgUrlList.isEmpty()) { // xoa het anh
                        imgUrlList.add(Constant.DEFAULT_IMG);
                        ImageDAO.getInstance().updateImgs(id,imgUrlList);
                    } else { // co thay doi
                        String uploadImagesDir = getServletContext().getRealPath("/") + Constant.PRODUCT_DETAIL_IMG_DIR;
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
                        ImageDAO.getInstance().updateImgs(id,imgUrlList); // cap nhat database voi urls tu input
                    }
                }
                Product productReponse = ProductDAO.getInstance().selectById(id);
                Gson gson = new Gson();
                String json = gson.toJson(productReponse);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;
            }
            case "LOCK": {
                System.out.println("lock product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductDAO.getInstance().updateAvai(id,Constant.BLOCKED);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.LOCK_SUCCESS+"','none');" +
                            "refreshProduct("+id+");");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.LOCK_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVE": {
                System.out.println("active product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductDAO.getInstance().updateAvai(id,Constant.ACTIVE);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.ACTIVE_SUCCESS+"','none');" +
                            "refreshProduct("+id+");");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.ACTIVE_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETE": {
                System.out.println("delete product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductDAO.getInstance().updateAvai(id,Constant.DELETED);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("refresh();" +
                            "showSuccessToast2('"+MessageValues.DELETE_SUCCESS+"','none');");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.ACTIVE_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ADDDETAIL": {
                System.out.println("add detail");
                int productID = Integer.parseInt(req.getParameter("productID"));
                String classify = req.getParameter("classify");
                double prePrice = Double.parseDouble(req.getParameter("prePrice"));
                double price = Double.parseDouble(req.getParameter("price"));
                int qty = Integer.parseInt(req.getParameter("qty"));
                ProductDetail detail = new ProductDetail(Constant.NONE, productID, classify, price, prePrice, qty, Constant.ACTIVE);
                int re = ProductDetailDAO.getInstance().insert(detail);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.ADD_SUCCESS+"','none');" +
                            "refreshDetail("+productID+");");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.ADD_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "UPDATEDETAIL": {
                System.out.println("update detail");
                int id = Integer.parseInt(req.getParameter("id"));
                int productID = Integer.parseInt(req.getParameter("productID"));
                String classify = req.getParameter("classify");
                double prePrice = Double.parseDouble(req.getParameter("prePrice"));
                double price = Double.parseDouble(req.getParameter("price"));
                int qty = Integer.parseInt(req.getParameter("qty"));
                ProductDetail detail = new ProductDetail(id, productID, classify, price, prePrice, qty, Constant.NONE);
                int re = ProductDetailDAO.getInstance().update(detail);
                String html="";
                if(re==1) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.UPDATE_SUCCESS+"','none');" +
                            "refreshDetail("+productID+");");
                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.UPDATE_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "LOCKDETAIL": {
                System.out.println("lock detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                int productID = ProductDetailDAO.getInstance().updateAvai(id,Constant.BLOCKED);
                String html="";
                if(productID!=0) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.LOCK_SUCCESS+"','none');" +
                            "refreshDetail("+productID+");");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.LOCK_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVEDETAIL": {
                System.out.println("active detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                int productID = ProductDetailDAO.getInstance().updateAvai(id,Constant.ACTIVE);
                String html="";
                if(productID!=0) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.ACTIVE_SUCCESS+"','none');" +
                            "refreshDetail("+productID+");");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.ACTIVE_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETEDETAIL": {
                System.out.println("delete detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                int productID = ProductDetailDAO.getInstance().updateAvai(id,Constant.DELETED);
                String html="";
                if(productID!=0) {
                    html = Constant.callFunction("showSuccessToast2('"+MessageValues.DELETE_SUCCESS+"','none');" +
                            "refreshDetail("+productID+");");

                } else {
                    html = Constant.callFunction("showErrorToast2('"+MessageValues.DELETE_FAIL+": "+MessageValues.DATABASE_ERROR+"!','none');");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ADD": {
                System.out.println("add product");
                String name = req.getParameter("name");
                String category = req.getParameter("category");
                String otherCategory = req.getParameter("other-category");
                String ingredient = req.getParameter("ingredient");
                int prominence = Integer.parseInt(req.getParameter("prominence"));
                String description = req.getParameter("description");
                String direction = req.getParameter("direction");
                String thumbnailInput = req.getParameter("thumbnailInput");
                if(!"".equalsIgnoreCase(otherCategory)) {
                    category = otherCategory;
                }
                System.out.println("Category:" + category);
                if("".equalsIgnoreCase(thumbnailInput)) thumbnailInput = Constant.DEFAULT_IMG;
                Product product = new Product(Constant.NONE, name, ingredient, category, description, direction, thumbnailInput, prominence, Constant.NONE);
                // them thong tin truoc
                System.out.println("add info");
                int id = ProductDAO.getInstance().insert(product);
                // cap nhat anh
                if(!Constant.DEFAULT_IMG.equalsIgnoreCase(thumbnailInput)) {
                    String uploadThumbnailDir = getServletContext().getRealPath("/") + Constant.THUMBNAIL_DIR;
                    File thumbnailDir = new File(uploadThumbnailDir);
                    if (!thumbnailDir.exists()) {
                        thumbnailDir.mkdirs();  // Create the thumbnails directory
                    }
                    Part thumbnailPart = req.getPart("thumbnail"); // For the thumbnail (single file)
                    String thumbnailFileName="";
                    if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
                        thumbnailFileName = getFileName(thumbnailPart);
                        String thumbnailPath = uploadThumbnailDir + File.separator + thumbnailFileName;
                        File file = new File(thumbnailPath);
                        if (!file.exists()) thumbnailPart.write(thumbnailPath);
                        // Write the file to the server
//                        System.out.println("update thumbnail");
//                        ProductDAO.getInstance().updateThumbnail(id,thumbnailFileName);
                    }
                }

                String imgUrls = req.getParameter("imgUrls");
                ArrayList<String> imgUrlList = new ArrayList<>();
                if(!"".equalsIgnoreCase(imgUrls)) {
                    String temps[] = imgUrls.split("==");
                    for(String s : temps) {
                        imgUrlList.add(s);
                    }
                } else {
                    imgUrlList.add(Constant.DEFAULT_IMG);
                }
                String uploadImagesDir = getServletContext().getRealPath("/") + Constant.PRODUCT_DETAIL_IMG_DIR;
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
                ImageDAO.getInstance().updateImgs(id,imgUrlList); // cap nhat database voi urls t
                // add classify: productDetail
                String classifiesJson = req.getParameter("classifies");
                Gson gson = new Gson();

                // Xác định kiểu dữ liệu cho danh sách
                Type listType = new TypeToken<ArrayList<ProductDetail>>() {}.getType();

                // Chuyển đổi JSON sang danh sách ProductDetail
                ArrayList<ProductDetail> productDetails = gson.fromJson(classifiesJson, listType);

                // Thêm ID, productID, avai (tuỳ chỉnh nếu cần)
                for (ProductDetail productDetail : productDetails) {
                    productDetail.id=Constant.NONE;
                    productDetail.productID=id;
                    productDetail.avai=Constant.NONE;
                    System.out.println(productDetail.toString());
                }
                ProductDetailDAO.getInstance().insert(productDetails);

                //finish adding, forward to the last page of product
                int total = ProductDAO.getInstance().countByCategory(Constant.ALL_String);
                int totalPage = total/Constant.NUM_OF_ITEMS_A_PAGE;
                if(total%Constant.NUM_OF_ITEMS_A_PAGE!=0) {
                    totalPage++;
                }
                String html = Constant.callFunction("window.location.href='aproduct?a=query&fa=add&page="+totalPage+"';");
                resp.getWriter().write(html);
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

    public String getImgs(ArrayList<Image> images) {
        String re="";
        for(Image image : images) {
            re+="<div class=\"upload__img-box\">\n" +
                    "<div style=\"background-image: url('./assets/img/product/"+image.getUrl()+"')\"  data-file=\""+image.getUrl()+"\" class=\"img-bg\">\n" +
                        "<div class=\"upload__img-close\" onclick=\"removeImg(event)\"></div>\n" +
                    "</div>\n" +
               "</div>";
        }
        return  re;

    }

    public String getImgUrls(ArrayList<Image> images) {
        StringBuilder temp = new StringBuilder();

        for(Image image : images) {
            temp.append(image.getUrl()+"==");
        }
        String re="";
        if(temp.toString().length()>4) {
            re= temp.toString();
            re=re.substring(0, re.length()-2);
        }
        return  re;

    }

    public String htmlSuccessToast(String message) {
        return "<script>showSuccessToast('"+message+"');</script>";
    }

    public String htmlErrorToast(String message) {
        return "<script>showErrorToast('"+message+"');</script>";
    }





}
