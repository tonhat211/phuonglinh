package DAO;

import model.*;
import service.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class ProductUnitDAO implements IDAO<ProductUnit>{
    public static ProductUnitDAO getInstance(){
        return new ProductUnitDAO();
    }
    @Override
    public int insert(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public int update(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public int delete(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public ArrayList<ProductUnit> selectAll() {
        return null;
    }

    @Override
    public ProductUnit selectById(int id) {
        Product product = ProductDAO.getInstance().selectById(id);
        if (product == null) return null;
        ArrayList<ProductDetail> details = ProductDetailDAO.getInstance().selectByProduct(product);
        ProductUnit productUnit = new ProductUnit(product,details);
        return productUnit;
    }

    public ArrayList<ProductUnit> selectByCategory(String categoryin,int offset) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String categoryCondition ="p.category = ?";
        if(categoryin.equalsIgnoreCase(Constant.OTHER_CATEGORY)) categoryCondition="p.category not in ('"+Constant.NAM_CATEGORY+"','"+Constant.DONG_TRUNG_CATEGORY+"','"+Constant.RUOU_CATEGORY+"')";

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.thumbnail,\n" +
                    "        min(d.price) as price,\n" +
                    "        min(d.prePrice) as prePrice,\n" +
                    "        count(DISTINCT c.id) as totalComment,\n" +
                    "        avg(c.star) as rate, \n" +
                    "        s.id as saleprogramID, \n" +
                    "        s.title as saleprogram \n" +
                    "        from products p left join productdetails d on p.id = d.productID\n" +
                    "        left join comments c on c.productID = p.id\n" +
                    "            left join saleprograms s on JSON_CONTAINS(s.productIDs, JSON_QUOTE(p.id)) and s.kind = " + Constant.MAIN +
                    "        where "+categoryCondition+" and p.avai != " + Constant.DELETED +
                    "        group by p.id\n" +
                    "        order by p.prominence desc " +
                    "        limit ?," + Constant.NUM_OF_ITEMS_A_PAGE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            if(categoryin.equalsIgnoreCase(Constant.OTHER_CATEGORY)) pst.setInt(1, offset);
            else {
                pst.setString(1, categoryin);
                pst.setInt(2, offset);
            }
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String thumbnail=rs.getString("thumbnail");
                double price=rs.getDouble("price");
                double prePrice=rs.getDouble("prePrice");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgramTitle=rs.getString("saleprogram");
                star = Math.round(star * 10.0) / 10.0;

                Product product = new Product(id,name,null,categoryin,null,null,thumbnail,Constant.NONE,Constant.NONE);
                ProductDetail detail = new ProductDetail(Constant.NONE,id,null,price,prePrice,Constant.NONE,Constant.NONE);
                SaleProgram saleProgram = new SaleProgram(saleProgramID,null,saleProgramTitle,null,Constant.NONE,Constant.NONE,null,null,Constant.NONE);
                res.add(new ProductUnit(product,detail,star,totalComment,saleProgram));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByCategoryForAdmin(String categoryin,int offset) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.thumbnail,\n" +
                    "        min(d.price) as price,\n" +
                    "        min(d.prePrice) as prePrice,\n" +
                    "        count(DISTINCT c.id) as totalComment,\n" +
                    "        avg(c.star) as rate, \n" +
                    "        s.id as saleprogramID, \n" +
                    "        s.title as saleprogram \n" +
                    "        from products p join productdetails d on p.id = d.productID\n" +
                    "        left join comments c on c.productID = p.id\n" +
                    "            left join saleprograms s on JSON_CONTAINS(s.productIDs, JSON_QUOTE(p.id)) and s.kind = " + Constant.MAIN +
                    "        where p.category = ? and p.avai != " + Constant.DELETED +
                    "        group by p.id\n" +
                    "        order by p.prominence desc " +
                    "        limit ?," + Constant.NUM_OF_ITEMS_A_PAGE+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, categoryin);
            pst.setInt(2, offset);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String thumbnail=rs.getString("thumbnail");
                double price=rs.getDouble("price");
                double prePrice=rs.getDouble("prePrice");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgramTitle=rs.getString("saleprogram");

                Product product = new Product(id,name,null,categoryin,null,null,thumbnail,Constant.NONE,Constant.NONE);
                ProductDetail detail = new ProductDetail(Constant.NONE,id,null,price,prePrice,Constant.NONE,Constant.NONE);
                SaleProgram saleProgram = new SaleProgram(saleProgramID,null,saleProgramTitle,null,Constant.NONE,Constant.NONE,null,null,Constant.NONE);
                res.add(new ProductUnit(product,detail,star,totalComment,saleProgram));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> search(String searchInput) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String[] tokens = searchInput.split(" ");
        String condition=" where ";
        condition += "(p.id like '%"+searchInput+"%' or (";
        for(String token : tokens) {
            condition+= "p.name like '%"+token+"%' and ";
        }
        condition=condition.substring(0, condition.length()-5);
        condition+="))";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id as pid, p.name, p.thumbnail,\n" +
                    "        min(d.price) as price,\n" +
                    "        min(d.prePrice) as prePrice,\n" +
                    "        count(DISTINCT c.id) as totalComment,\n" +
                    "        avg(c.star) as rate, \n" +
                    "        s.id as saleprogramID, \n" +
                    "        s.title as saleprogram \n" +
                    "        from products p join productdetails d on p.id = d.productID\n" +
                    "        left join comments c on c.productID = p.id\n" +
                    "            left join saleprograms s on JSON_CONTAINS(s.productIDs, JSON_QUOTE(p.id)) and s.kind = " + Constant.MAIN + " and s.avai = " + Constant.ACTIVE +
                    "        " +condition +" and p.avai != " + Constant.DELETED +
                    "        group by p.id\n" +
                    "        order by p.prominence desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                String thumbnail=rs.getString("thumbnail");
                double price=rs.getDouble("price");
                double prePrice=rs.getDouble("prePrice");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgramTitle=rs.getString("saleprogram");

                Product product = new Product(id,name,null,null,null,null,thumbnail,Constant.NONE,Constant.NONE);
                ProductDetail detail = new ProductDetail(Constant.NONE,id,null,price,prePrice,Constant.NONE,Constant.NONE);
                SaleProgram saleProgram = new SaleProgram(saleProgramID,null,saleProgramTitle,null,Constant.NONE,Constant.NONE,null,null,Constant.NONE);
                res.add(new ProductUnit(product,detail,star,totalComment,saleProgram));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        ArrayList<ProductUnit> p = ProductUnitDAO.getInstance().search("nam linh");

    }
}
