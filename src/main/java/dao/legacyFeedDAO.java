package dao;

import java.sql.*;
import java.util.*;

import javax.naming.NamingException;
import util.ConnectionPool;

public class legacyFeedDAO {
	public boolean insert(String uid, String ucon, String uimages) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			String sql = "INSERT INTO feed(id, content, images) VALUES(?, ?, ?)";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, uid);
			stmt.setString(2, ucon);
			stmt.setString(3, uimages);
			
			int count = stmt.executeUpdate();
			
			return (count > 0) ? true : false;
		} finally {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
		}
	}
	
	public ArrayList<FeedObj> getList() throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM feed ORDER BY ts DESC";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			ArrayList<FeedObj> feeds = new ArrayList<FeedObj>();
			
			while(rs.next()) {
				feeds.add(new FeedObj(rs.getString("id"), rs.getString("content"), rs.getString("ts"), rs.getString("images")));
			}
			return feeds;
			
		} finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}

}
