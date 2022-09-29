/**
 * copyright(C) 2022 Luvina JSC
 * MstGroupLogic.java May 3, 2022 nguyenbahuy
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;

/**
 * @author nguyenbahuy
 * tạo các hàm kiểm tra logic
 */
public interface MstGroupLogic {
	/**
	 * hàm lấy danh sach MstGroup
	 * @return
	 * @throws SQLException
	 */
	public List<MstGroup> getAllMstGroup() throws SQLException;
	/**
	 * lấy mstGroup theo groupId
	 * @param groupId
	 * @return
	 * @throws SQLException
	 */
	public MstGroup getMstGroupByGroupId(int groupId) throws SQLException;
}
