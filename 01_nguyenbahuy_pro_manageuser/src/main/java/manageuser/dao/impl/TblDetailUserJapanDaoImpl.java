/**
 * copyright(C) 2022 Luvina JSC
 * TblDetailUserJapanDaoImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;

/**
 * @author nguyenbahuy
 *	 xử lý thoa tác với bảng tbl_detail_user_japan
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/**
	 * hàm insert dữ liệu vào bảng detailUserJapan
	 * @Param truyền vào detailUserJapan
	 */
	@Override
	public void insertDetailUserJapan(TblDetailUserJapan detailUserJapan,Connection conn) throws SQLException {
		try {
			
			//tao cau truy van
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO tbl_detail_user_japan (`USER_ID`, `CODE_LEVEL`, `START_DATE`, `END_DATE`, `TOTAL`) ");
			sql.append(" VALUES (?,?,?,?,?);");
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql.toString());
			int i = 1;
			ps.setInt(i++, detailUserJapan.getUserId());
			ps.setString(i++, detailUserJapan.getCodeLevel());
			ps.setString(i++, detailUserJapan.getStartDate());
			ps.setString(i++, detailUserJapan.getEndDate());
			ps.setInt(i++, detailUserJapan.getTotal());

			//chạy câu truy vấn
			ps.executeUpdate();

		} catch (SQLException e) {
			//ghi log
			System.out.println("TblDetailUserJapanDaoImpl insertDetailUserJapan" + e.getMessage());
			throw e;
		}
		
	}
	/**
	 * xóa detailJapan theo userId
	 * @param userId
	 * @param conn
	 * @throws SQLException
	 */
	@Override
	public void deleteDetailJapanByUserId(int userId,Connection conn) throws SQLException {
		try {
			
			//tao cau truy van
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM `tbl_detail_user_japan` WHERE (`user_id` = ?); ");
				
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1,userId);
			
			//chạy câu truy vấn
			ps.executeUpdate();

		} catch (SQLException e) {
			//ghi log
			System.out.println("TblDetailUserJapanDaoImpl deleteDetailJapanByUserId()" + e.getMessage());
			throw e;
		} 
		
	}
	/**
	 * update bảng detailUserJapan 
	 * @param detailUserJapan
	 * @param conn tham số 
	 * @throws SQLException
	 */
	@Override
	public void updateDetailJapan(TblDetailUserJapan detailUserJapan, Connection conn) throws SQLException {
		try {
			
			//tao cau truy van
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE `tbl_detail_user_japan` SET `CODE_LEVEL` = ?, `START_DATE` = ?, `END_DATE` = ?, `TOTAL` = ? WHERE (`user_id` = ? );");
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql.toString());
			int i = 1;
			
			ps.setString(i++, detailUserJapan.getCodeLevel());
			ps.setString(i++, detailUserJapan.getStartDate());
			ps.setString(i++, detailUserJapan.getEndDate());
			ps.setInt(i++, detailUserJapan.getTotal());
			ps.setInt(i++, detailUserJapan.getUserId());
			//chạy câu truy vấn
			ps.executeUpdate();

		} catch (SQLException e) {
			//ghi log
			System.out.println("TblDetailUserJapanDaoImpl updateDetailJapan() " + e.getMessage());
			throw e;
		}
		
	}
	/**
	 * hàm lấy codelevel theo userId
	 * @param userId
	 * @return codelevl
	 */
	@Override
	public String getCodeLevelDetailJapanByUserId(int userId) throws SQLException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT CODE_LEVEL FROM tbl_detail_user_japan where USER_ID = ? ;";
			PreparedStatement ps = null;
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn
			ps.setInt(1,userId);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			String codeLevel = "";
			while (rs.next()) {
				codeLevel = rs.getString("CODE_LEVEL");
			}
			return codeLevel;
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getTblUserByUsername " + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
	}

}
