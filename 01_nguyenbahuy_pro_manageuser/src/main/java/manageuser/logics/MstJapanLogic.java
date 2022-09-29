/**
 * copyright(C) 2022 Luvina JSC
 * MstJapanLogic.java May 3, 2022 nguyenbahuy
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapan;

/**
 * @author nguyenbahuy
 * tạo các hàm kiểm tra logic
 */
public interface MstJapanLogic {
	/**
	 * hàm lấy danh sach MstJapan
	 * @return
	 * @throws SQLException
	 */
	public List<MstJapan> getAllMstJapan() throws SQLException;
	/**
	 * lấy mstJapan theo codeLevel
	 * @param codeLevel
	 * @return
	 * @throws SQLException
	 */
	public MstJapan getMstJapanByCodeLevel(String codeLevel) throws SQLException;
}
