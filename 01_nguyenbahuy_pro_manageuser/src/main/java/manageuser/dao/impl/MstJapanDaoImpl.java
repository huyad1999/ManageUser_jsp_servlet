/**
 * copyright(C) 2022 Luvina JSC
 * MstJapanDaoImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapan;

/**
 * @author nguyenbahuy
 *	 xử lý thoa tác với bảng mst_japan
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {
	/**
	 * Phương thức lấy tất cả các MstJapan
	 * trả về danh sách MstJapan
	 */
	@Override
	public List<MstJapan> getAllMstJapan() throws SQLException {
		List<MstJapan> listMstJapan = new ArrayList<MstJapan>();
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT * FROM mst_japan jp order by jp.code_level";	
			PreparedStatement ps = null;
			ps = connection.prepareStatement(sql);
			
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				//tạo đối tượng mstJapan
				MstJapan mstJapan = new MstJapan();
				mstJapan.setCodeLevel(rs.getString("CODE_LEVEL"));
				mstJapan.setNameLevel(rs.getString("NAME_LEVEL"));
				//add đối tượng mstJapan vào listMstJapan
				listMstJapan.add(mstJapan);
			}
			
			return listMstJapan;
		} catch (SQLException e) {
			//ghi log
			System.out.println("MstJapanDaoImpl getAllMstJapan()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
	}
	/**
	 * lấy MstJapan theo codeLevel
	 * tham số truyền vào codelevel
	 */
	@Override
	public MstJapan getMstJapanByCodeLevel(String codeLevel) throws SQLException {
		try {
			connection = super.getConnection();
			//tạo đối tượng mstJapan
			MstJapan mstJapan = new MstJapan();
			String sql = "SELECT * FROM mst_japan jp where jp.code_level = ? ";	
			PreparedStatement ps = null;
			ps = connection.prepareStatement(sql);
			ps.setString(1,codeLevel);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				mstJapan.setCodeLevel(rs.getString("CODE_LEVEL"));
				mstJapan.setNameLevel(rs.getString("NAME_LEVEL"));
				return mstJapan;
			}
			
			
		}  catch (SQLException e) {
			//ghi log
			System.out.println("MstJapanDaoImpl getMstJapanByCodeLevel()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return null;
	}

}
