/**
 * copyright(C) 2022 Luvina JSC
 * MstGroupDaoImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao.impl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroup;
import manageuser.entities.TblUser;

/**
 * @author nguyenbahuy
 *	 xử lý thoa tác với bảng mst_group
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	/**
	 * Phương thức lấy tất cả các MstGroup
	 * trả về danh sách MstGroup
	 */
		@Override
		public List<MstGroup> getAllMstGroup() throws SQLException {
			List<MstGroup> listMstGroup = new ArrayList<MstGroup>();
			try {
				//mở kết nối
				connection = super.getConnection();
				//tao cau truy van
				String sql = "SELECT * FROM mst_group gr order by gr.group_name";	
				PreparedStatement ps = null;
				ps = connection.prepareStatement(sql);
				
				//chạy câu lệnh truy vấn
				ResultSet rs = ps.executeQuery();
				//chạy từng phần tử trong rs
				while (rs.next()) {
					//tạo đối tượng mstGroup
					MstGroup mstGroup = new MstGroup();
					mstGroup.setID(rs.getInt("group_id"));
					mstGroup.setGroupName(rs.getString("group_name"));
					//add đối tượng mstGroup vào listMstGroup
					listMstGroup.add(mstGroup);
				}
				
				return listMstGroup;
			} catch (SQLException e) {
				//ghi log
				System.out.println("MstGroupDaoImpl MstGroupDaoImpl" + e.getMessage());
				throw e;
			} finally {
				//đóng kết nối
				 super.close();
			}
		}
		/**
		 * lâý mstGroup theo groupId
		 * @param groupId
		 * @return
		 * @throws SQLException
		 */
		@Override
		public MstGroup getMstGroupById(int groupId) throws SQLException {
			try {
				
				//mở kết nối
				connection = super.getConnection();
				MstGroup mstGroup = new MstGroup();
				//tao cau truy van
				String sql = "SELECT * FROM mst_group gr where gr.group_id = ?";	
				PreparedStatement ps = null;
				ps = connection.prepareStatement(sql);
				ps.setInt(1,groupId);
				//chạy câu lệnh truy vấn
				ResultSet rs = ps.executeQuery();
				//chạy từng phần tử trong rs
				while (rs.next()) {
					//tạo đối tượng mstGroup
					
					mstGroup.setID(rs.getInt("group_id"));
					mstGroup.setGroupName(rs.getString("group_name"));
					//add đối tượng mstGroup vào listMstGroup
					return mstGroup;
				}
				
				
			} catch (SQLException e) {
				//ghi log
				System.out.println("MstGroupDaoImpl getMstGroupById" + e.getMessage());
				throw e;
			} finally {
				//đóng kết nối
				 super.close();
			}
			return null;
		}

}
