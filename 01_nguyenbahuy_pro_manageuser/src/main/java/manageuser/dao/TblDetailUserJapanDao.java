/**
 * copyright(C) 2022 Luvina JSC
 * TblDetailUserJapanDao.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.SQLException;

import manageuser.entities.TblDetailUserJapan;

/**
 * @author nguyenbahuy
 * thực hiện thao tác bảng tbl_detail_user_japan
 */
public interface TblDetailUserJapanDao extends BaseDao{
	/**
	 * insert dữ liệu vào bảng detailUserJapan
	 * @param detailUserJapan
	 * @param conn
	 * @throws SQLException
	 */
	public void insertDetailUserJapan(TblDetailUserJapan detailUserJapan,Connection conn) throws SQLException;
	/**
	 * xóa detailJapan theo userId
	 * @param userId
	 * @param conn
	 * @throws SQLException
	 */
	public void deleteDetailJapanByUserId(int userId,Connection conn) throws SQLException;
	/**
	 * update detaiJapan
	 * @param detailUserJapan
	 * @param con
	 * @throws SQLException 
	 */
	public void updateDetailJapan(TblDetailUserJapan detailUserJapan, Connection con) throws SQLException;
	/**
	 * lấy codeLevel theo userId
	 * @param userId
	 * @return CodeLevel theo userId
	 * @throws SQLException 
	 */
	public String getCodeLevelDetailJapanByUserId(int userId) throws SQLException;
	
}
