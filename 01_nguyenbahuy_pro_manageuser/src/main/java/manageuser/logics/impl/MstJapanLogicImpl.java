/**
 * copyright(C) 2022 Luvina JSC
 * MstJapanLogicImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapan;
import manageuser.logics.MstJapanLogic;

/**
 * @author nguyenbahuy
 * Class MstJapanLogicImpl xử lý logic từ tầng MstJapanDaoImpl
 */
public class MstJapanLogicImpl implements MstJapanLogic {
	/*
	 * lấy tất cả các đối tượng MstJapan
	 * trả về danh sách các đối tượng MstJapan
	 */
	@Override
	public List<MstJapan> getAllMstJapan() throws SQLException {
		try {
			MstJapanDao mstJapanDao = new MstJapanDaoImpl();
			List<MstJapan> listMstJapan = null;
			
			listMstJapan = mstJapanDao.getAllMstJapan();
				return listMstJapan;
			} catch (SQLException e) {
				//ghi log
				System.out.println("MstJapanLogicImpl getAllMstJapan()"+e.getMessage());
				throw e;
			}
	}
	/*
	 * lấy đối tượng MstJapan theo codelevel
	 * trả về  đối tượng MstJapan
	 */
	@Override
	public MstJapan getMstJapanByCodeLevel(String codeLevel) throws SQLException {
		try {
			MstJapanDao mstJapanDao = new MstJapanDaoImpl();
			MstJapan mstJapan = new MstJapan();
			
			mstJapan = mstJapanDao.getMstJapanByCodeLevel(codeLevel);
				return mstJapan;
			} catch (SQLException e) {
				//ghi log
				System.out.println("MstJapanLogicImpl getMstJapanByCodeLevel()"+e.getMessage());
				throw e;
			}
	}

}
