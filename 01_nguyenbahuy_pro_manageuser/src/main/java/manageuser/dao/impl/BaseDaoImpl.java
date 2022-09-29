/**
 * copyright(C) 2022 Luvina JSC
 * BaseDaoImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.BaseDao;
import manageuser.utils.DatabaseProperties;

/**
 * @author nguyenbahuy
 * xử lý đóng mở kết nối DB
 */
public class BaseDaoImpl implements BaseDao {
	private static final String url = DatabaseProperties.getValueByKey("url");
	private static final String username = DatabaseProperties.getValueByKey("username");
	private static final String password = DatabaseProperties.getValueByKey("password");
	protected Connection connection = null;
	/**
	 * phương thức kết nối với cơ sở dữ liệu
	 * @return kết nối
	 */
	@Override
	public Connection getConnection() {
		
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url, username,password);
		} catch (SQLException e) {
				System.out.println("BaseDaoImpl getConnection"+e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("BaseDaoImpl getConnection "+e.getMessage());
		}
		return connection;
	}
	/**
	 * phương thức đóng kết nối DB
	 */
	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl close"+e.getMessage());
		}
	}
	/**
	 * thực hiện rollback
	 * @return true nếu k có lỗi kết nối ,false nếu có lỗi kết nối và rollback
	 * @throws SQLException
	 */
	@Override
	public boolean rollback() throws SQLException {
		try {
			if (connection != null) {
				connection.rollback();
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl rollback " + e.getMessage());
			throw e;
		}

		
	}
	/**
	 * lấy ra danh sách các thuộc tính trong bảng
	 * @param tblName nhập tên bảng cần lấy các thuộc tính
	 */
	@Override
	public List<String> getColumnSort(String tblName) throws ClassNotFoundException, SQLException {
		try {
			List<String> listColums = new ArrayList<>();
			// má»Ÿ káº¿t ná»‘i
			connection = this.getConnection();
			// táº¡o cĂ¢u truy váº¥n
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COLUMN_NAME ");
			sql.append("FROM INFORMATION_SCHEMA.COLUMNS ");
			sql.append("WHERE TABLE_NAME = ? ");
			PreparedStatement ps = connection.prepareStatement(sql.toString());
			// gĂ¡n dá»¯ liá»‡u cho cĂ¢u truy váº¥n
			ps.setString(1, tblName);
			// thá»±c thi cĂ¢u truy váº¥n
			ResultSet rs = ps.executeQuery();
			// gĂ¡n dá»¯ liá»‡u vĂ o máº£n
			while (rs.next()) {
				listColums.add(rs.getString("COLUMN_NAME"));
			}
			return listColums;
		} catch ( SQLException e) {
			System.out.println("CommonModel getColumnSort " + e.getMessage());
			throw e;
		}
	}
	

}
