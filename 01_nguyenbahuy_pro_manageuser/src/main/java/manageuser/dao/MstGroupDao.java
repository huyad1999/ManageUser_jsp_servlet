/**
 * copyright(C) 2022 Luvina JSC
 * MstGroupDao.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroup;

/**
 * @author nguyenbahuy
 * thực hiện thao tác của bảng mst_group
 */
public interface MstGroupDao extends BaseDao{
	/**
	 * lấy tất cả các MstGroup
	 * @return
	 * @throws SQLException
	 */
	public List<MstGroup> getAllMstGroup() throws SQLException;
	/**
	 * lâý mstGroup theo groupId
	 * @param groupId
	 * @return
	 * @throws SQLException
	 */
	public MstGroup getMstGroupById(int groupId) throws SQLException;
}
