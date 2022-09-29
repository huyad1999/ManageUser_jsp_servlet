/**
 * copyright(C) 2022 Luvina JSC
 * MstJapanDao.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapan;

/**
 * @author nguyenbahuy
 *lớp giao diện thực hiện thao tác của bảng mst_japan
 */
public interface MstJapanDao extends BaseDao {
		/**
		 * lấy tất cả các MstJapan
		 */
		public List<MstJapan> getAllMstJapan() throws SQLException;
		/**
		 * lấy MstJapan theo codeLevel
		 * tham số truyền vào codelevel
		 */
		public MstJapan getMstJapanByCodeLevel(String codeLevel) throws SQLException;
}
