/**
 * copyright(C) 2022 Luvina JSC
 * BaseDao.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author nguyenbahuy
 * thực hiện thao tác đóng mở kết nối
 */
public interface BaseDao {
	/**
	 * mở kết nối
	 */
	public Connection getConnection();
	/**
	 * đóng kết nối
	 */
	public void close();
	/**
	 * thực hiện rollback
	 * @return true nếu k có lỗi kết nối ,false nếu có lỗi kết nối và rollback
	 * @throws SQLException
	 */
	public boolean rollback() throws SQLException;
	/**
	 * lấy các thuộc tính theo tên bảng truyền vào
	 * @param tblName tên bảng cần lấy các thuộc tính
	 * @return danh sách các thuộc tính trong bảng
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	List<String> getColumnSort(String tblName) throws ClassNotFoundException, SQLException;
}
