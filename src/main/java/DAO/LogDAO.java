package DAO;

import model.Constant;
import model.Log;
import service.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class LogDAO implements IDAO<Log>{
	public static LogDAO getInstance() {
		return new LogDAO();
	}
	
	@Override
	public int insert(Log log) {
		int re= -1;
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "insert into logs (ip, info, action, preValue, afterValue, level,area) values (?,?,?,?,?,?,?); ";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,log.getIp());
			pst.setString(2,log.getInfo());
			pst.setString(3,log.getAction());
			pst.setString(4,log.getPreValue());
			pst.setString(5,log.getAfterValue());
			pst.setInt(6,log.getLevel());
			pst.setString(7,log.getArea());
			re = pst.executeUpdate();
			JDBCUtil.closeConnection(conn);
			return re;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int selectCountTotal() {
		int re=0;
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "select count(*) from logs;";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				re = rs.getInt("count(*)");

			}
			JDBCUtil.closeConnection(conn);
			return re;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public int selectTheMaxID(){
		int res=-1;
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "select max(id) from areas;";
			PreparedStatement pst = conn.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				res = rs.getInt("max(id)");
			}
			JDBCUtil.closeConnection(conn);
			return res;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public ArrayList<Log> selectByAreaAndLevel(String areain, int levelin, int offset) {
		String levelCondition = " and level =" + levelin;
		if(Constant.NONE_CONDITION == levelin) levelCondition ="";
		ArrayList<Log> res = new ArrayList<>();
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "select id, ip, info, action, preValue, afterValue, level, time from logs where area = ? limit ?, " +Constant.NUM_OF_ITEMS_A_PAGE;
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,areain);
			pst.setInt(2,offset);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String ip = rs.getString("ip");
				String info = rs.getString("info");
				String action = rs.getString("action");
				String preValue = rs.getString("preValue");
				String afterValue = rs.getString("afterValue");
				String area = rs.getString("area");
				int level = rs.getInt("level");
				Timestamp time = rs.getTimestamp("time");
				res.add(new Log(id,ip,info,action,preValue,afterValue,area,level,time));
			}
			JDBCUtil.closeConnection(conn);
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int deleteAlert() {
		int re=-1;
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "delete from logs where level = 1";
			PreparedStatement pst = conn.prepareStatement(sql);
			re= pst.executeUpdate();

			JDBCUtil.closeConnection(conn);
			return re;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}


	@Override
	public int update(Log t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Log t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Log> selectAll() {
		ArrayList<Log> res = new ArrayList<>();
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "select id, ip, info, action, preValue, afterValue, level, time from logs";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String ip = rs.getString("ip");
				String info = rs.getString("info");
				String action = rs.getString("action");
				String preValue = rs.getString("preValue");
				String afterValue = rs.getString("afterValue");
				String area = rs.getString("area");
				int level = rs.getInt("level");
				Timestamp time = rs.getTimestamp("time");
				res.add(new Log(id,ip,info,action,preValue,afterValue,area,level,time));

			}
			JDBCUtil.closeConnection(conn);
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countByLevel(String area, int level) {
		int re=0;
		String levelCondition= " and level = '" + level;
		if(Constant.NONE_CONDITION == level) levelCondition="";
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "SELECT count(*) as count from logs where area = ? "+levelCondition+";";
			PreparedStatement pst = conn.prepareStatement(sql);
			System.out.println(pst);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				re = rs.getInt("count");
			}
			JDBCUtil.closeConnection(conn);
			return re;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Log selectById(int id) {
		Log res = null;
		try {
			Connection conn = JDBCUtil.getConnection();

			String sql = "select * from areas where id=?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			while(rs.next()){
				int idd = rs.getInt("id");
				String name = rs.getString("name");
				int available = rs.getInt("available");
//				res = new Area(idd, name, available);
			}

//			System.out.println(re + " dong da duoc them vao");
			JDBCUtil.closeConnection(conn);
			return res;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
//		Log t = new Log("123","uc",3031,"Awfqi@gmail.com",0,"dang nhap");
//		System.out.println(LogDAO.getInstance().insert(t));


	}

}
