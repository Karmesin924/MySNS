package dao;

import java.sql.*;
import java.util.*;

import javax.naming.NamingException;

import org.json.simple.*;

import util.*;

public class legacyUserDAO {
	public int login(String uid, String upass) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT id, password FROM user WHERE id = ?";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			
			rs = stmt.executeQuery();
			if(!rs.next()) return 1;
			if(!upass.equals(rs.getString("password"))) return 2;
			
			return 0;
			
		} finally {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
			if(rs != null) rs.close();
		}
	}
	
	public boolean insert(String uid, String upass, String uname) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			String sql = "INSERT INTO user(id, password, name) VALUES (?, ?, ?)";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, uid);
			stmt.setString(2, upass);
			stmt.setString(3, uname);
			
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
		} finally {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
		}
	}
	
	public boolean exists(String uid) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT id FROM user WHERE id = ?";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			
			rs = stmt.executeQuery();
			return rs.next();
			
		} finally {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
			if(rs != null) rs.close();
		}
	}
	
	public boolean delete(String uid) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			String sql = "DELETE FROM user WHERE id = ?";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			
			int count = stmt.executeUpdate();
			return (count > 0) ? true : false;
			
		} finally {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
		}
	}
	
	// public ArrayList<UserObj> getList() throws NamingException, SQLException {
	public String getList() throws NamingException, SQLException {	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM user ORDER BY ts DESC";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			// ArrayList<UserObj> users = new ArrayList<UserObj>();
			JSONArray users = new JSONArray();
			
			while(rs.next()) {
				// users.add(new UserObj(rs.getString("id"), rs.getString("name"), rs.getString("ts")));
				JSONObject obj = new JSONObject();
				obj.put("id", rs.getString("id"));
				obj.put("name", rs.getString("name"));
				obj.put("ts", rs.getString("ts"));
				users.add(obj);
			}
			
			// return users;
			return users.toJSONString();
			
		} finally {
			if (rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
}
