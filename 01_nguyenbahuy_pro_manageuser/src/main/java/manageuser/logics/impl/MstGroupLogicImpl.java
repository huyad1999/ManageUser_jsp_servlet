/**
 * copyright(C) 2022 Luvina JSC
 * MstGroupLogicImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.logics.MstGroupLogic;

/**
 * @author nguyenbahuy
 * Class MstGroupLogicImpl xử lý logic từ tầng MstGroupDaoImpl
 */
public class MstGroupLogicImpl implements MstGroupLogic {
	/*
	 * lấy tất cả các đối tượng MstGroup
	 * trả về danh sách các đối tượng MstGroup
	 */
	@Override
	public List<MstGroup> getAllMstGroup() throws SQLException {
		try {
		MstGroupDao mstGroupDao = new MstGroupDaoImpl();
		List<MstGroup> listMstGroup = null;
		
			listMstGroup = mstGroupDao.getAllMstGroup();
			return listMstGroup;
		} catch (SQLException e) {
			//ghi log
			System.out.println("MstGroupLogicImpl MstGroupLogicImpl() "+e.getMessage());
			throw e;
		}
		
	}
/*
 * lấy đối tượng mstGroup theo groupId
 * trả về mstGroup
 */
	@Override
	public MstGroup getMstGroupByGroupId(int groupId) throws SQLException {
		try {
		MstGroupDao mstGroupDao = new MstGroupDaoImpl();
		MstGroup mstGroup = new MstGroup();
		mstGroup = mstGroupDao.getMstGroupById(groupId);
			return mstGroup;
		} catch (SQLException e) {
			//ghi log
			System.out.println("MstGroupLogicImpl getMstGroupByGroupId()"+e.getMessage());
			throw e;
		}
	}
}
