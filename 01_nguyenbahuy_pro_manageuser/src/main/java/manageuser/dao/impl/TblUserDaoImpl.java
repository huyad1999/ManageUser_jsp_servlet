/**
 * copyright(C) 2022 Luvina JSC
 * TblUserDaoImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.utils.Constant;

/**
 @author nguyenbahuy
 *	 xử lý thoa tác với bảng tbl_user
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	Connection connection =null;
	PreparedStatement ps = null;
	/**
	 * xử lý lấy tblUser từ DB khi truyền vào loginId
	 * @Param truyền vào loginId
	 * @return tblUser tìm theo loginId
	 */
	@Override
	public TblUser getTblUserByUsername(String loginId,int rule) throws SQLException,ClassNotFoundException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT u.LOGIN_NAME,u.PASSWORD,u.SALT FROM tbl_user u "
					+ "WHERE login_name = ? and u.RULE = ?";
			
			ps = connection.prepareStatement(sql.toString());
			int index = 1;
			//set dữ liệu cho câu truy vấn
			ps.setString(index++,loginId);
			ps.setInt(index++,rule);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				TblUser tblUser = new TblUser();
				tblUser.setLoginName(rs.getString("LOGIN_NAME"));
				tblUser.setPassword(rs.getString("PASSWORD"));
				tblUser.setSalt(rs.getString("SALT"));
				
				return tblUser;
			}
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getTblUserByUsername " + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return null;
	}
	/**
	 * hàm xử lý lấy danh sách các user theo điều kiện tìm kiếm
	 * @Param offset vị trí data cần lấy nào
	 * @Param limit số lượng lấy
	 * @Param groupId mã nhóm tìm kiếm
	 * @Param fullName Tên tìm kiếm
	 * @Param sortType: Nhận biết xem cột nào được ưu tiên sắp xếp 
	 * @Param sortByFullName,sortByCodeLevel,sortByEndDate: Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @return trả về danh sách userInfor
	 * @Exception SQLException
	 */
	@Override
	public List<UserInfor> getListUsers(int offset,int limit,int groupID,String fullName,String sortType,String sortByFullName,String sortByCodeLevel,String sortByEndDate) throws SQLException,ClassNotFoundException {
		try {
			
			//danh sách lưu các userinfor
			List<UserInfor> listUser = new ArrayList<UserInfor>();
			List<String> columnSort = new ArrayList<String>();
			
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			StringBuilder sql = new StringBuilder("SELECT u.user_id, u.full_name, u.email, u.tel, u.birthday,  g.group_name,j.name_level , d.end_date,d.total ");
					sql.append(" FROM tbl_user u ");
					sql.append(" INNER JOIN mst_group g ON u.group_id = g.group_id ");
					sql.append(" LEFT JOIN (tbl_detail_user_japan d INNER JOIN mst_japan j ON d.code_level = j.code_level ) ");
					sql.append(" ON u.user_id = d.user_id ");
					sql.append(" WHERE u.RULE = 1 ");
				
		
			int i = 1;
			//check tham số groupID khác 0
			if(groupID != 0) {
				//tìm kiếm theo group_id
				sql.append(" and u.group_id = ? ");
			
			}
			//check tham số fullName khác rỗng
			if(!"".equals(fullName)) {
				//tìm kiếm theo tên
				sql.append(" AND u.full_name LIKE ? ");
			}
			//thêm các thuộc tính của bảng tbl_user và tbl_detail_user_japan vào mảng columnsort
			columnSort.addAll(getColumnSort("tbl_user"));
			columnSort.addAll(getColumnSort("tbl_detail_user_japan"));
			//kiểm tra xem trường sắp xếp nào được ưu tiên
			if(Constant.ENDDATE.equals(sortType) && columnSort.contains(sortType.toUpperCase()))  {//kiểm tra xem có phải trường end_date không
				sql.append(" order by d.end_date ");
				sql.append(sortByEndDate);
				sql.append(", u.full_name ASC, j.code_level ASC ");
			} else if(Constant.CODELEVEL.equals(sortType) && columnSort.contains(sortType.toUpperCase())) {//kiểm tra xem có phải trường code_level không
				sql.append(" order by j.code_level ");
				sql.append(sortByCodeLevel);
				sql.append(", u.full_name ASC, d.end_date DESC ");
				
			} else if(Constant.FULLNAME.equals(sortType) && columnSort.contains(sortType.toUpperCase())) {//kiểm tra xem có phải trường full_name không
				sql.append(" order by u.full_name ");
				sql.append(sortByFullName);
				sql.append(", j.code_level ASC, d.end_date DESC ");

			} 
			//set giá trị limit
			sql.append(" limit ");
			sql.append(limit);
			//set giá trị offset
			sql.append(" offset ");
			sql.append(offset);
			
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn	
				//kiểm tra groupId khác không
				if (groupID != 0) {
					//set giá trị cho group_id
					ps.setInt(i++, groupID );
				}
				//kiểm tra fullname khác null
				if (!"".equals(fullName)) {
					//set giá trị cho full_name
					ps.setString(i++, "%" +fullName+ "%" );
				}
		
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				//tạo 1 đối tượng userInfor mới
				UserInfor userInfor = new UserInfor();
				//set giá trị cho các thuộc tính của userInfor
				userInfor.setUserId(rs.getInt("user_id"));
				userInfor.setFullName(rs.getString("full_name"));
				userInfor.setBirthday(rs.getString("birthday"));
				userInfor.setGroupName(rs.getString("group_name"));
				userInfor.setEmail(rs.getString("email"));
				userInfor.setTel(rs.getString("tel"));
				userInfor.setNameLevel(rs.getString("name_level"));
				userInfor.setEndDate(rs.getString("end_date"));
				userInfor.setTotal(String.valueOf(rs.getInt("total")));
				//add userInfor vào listUser
				listUser.add(userInfor);
			}
			
			return listUser;
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getListUsers " + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}

	}
	

	/**
	 * lấy tổng số bản ghi trong DB
	 * @Param groupId mã nhóm tìm kiếm
	 * @Param fullName Tên tìm kiếm
	 * @return trả về tổng số lượng user
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException,ClassNotFoundException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tạo câu truy vấn
			StringBuilder sql = new StringBuilder("SELECT count(u.USER_ID) as 'total' ");
			sql.append(" FROM tbl_user u INNER JOIN mst_group g USING (group_id) ");
			sql.append(" WHERE u.rule = 1 ");
			
			int i = 1;
			//check tham số groupID khác 0
			if(groupId != 0) {
				//tìm kiếm theo group_id
				sql.append(" and u.group_id = ? ");
			
			}
			//check tham số fullName khác rỗng
			if(!"".equals(fullName)) {
				//tìm kiếm theo tên
				sql.append(" AND u.full_name LIKE ? ");
			}

			ps = connection.prepareStatement(sql.toString());
			
			//set dữ liệu
				//kiểm tra groupId khác không
			if (groupId != 0) {
				//set giá trị cho group_id
				ps.setInt(i++,groupId);
			}
			
			//kiểm tra fullname khác null
			if (!"".equals(fullName)) {
				//set giá trị cho full_name
				ps.setString(i++, "%" +fullName+ "%" );
				
			}
		
			//thực thi câu truy vấn
			ResultSet rs = ps.executeQuery();
			//gán dữ liệu cho tổng số user
			int total = 0;
			while (rs.next()) {
				//lấy giá trị của cột total
				total = rs.getInt("total");
				return total;
			}
			
			
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getTotalUser " + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return 0;
	}
	/**
	 * lấy tbl user theo email 
	 * @param email
	 * @return TblUser theo email
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public TblUser getTblUserByEmail(String email) throws SQLException, ClassNotFoundException {
		try {
			//mở kết nối
			connection = super.getConnection();
			
			//tao cau truy van
			String sql = "SELECT u.email,u.group_id,u.login_name,u.full_name,u.full_name_kana,u.tel,u.birthday FROM tbl_user u "
					+ "WHERE email = ? and rule = 1";
			
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn
			ps.setString(1,email);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				TblUser tblUser = new TblUser();
				tblUser.setEmail(rs.getString("EMAIL"));
				tblUser.setGroupId(rs.getInt("GROUP_ID"));
				tblUser.setLoginName(rs.getString("LOGIN_NAME"));
				tblUser.setFullName(rs.getString("FULL_NAME"));
				tblUser.setFullNameKana(rs.getString("FULL_NAME_KANA"));
				tblUser.setTel(rs.getString("TEL"));
				tblUser.setBirthday(rs.getString("BIRTHDAY"));
				return tblUser;
			}
			
		
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getTblUserEmail" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return null;
	}
	/**
	 * hàm insertUser dữ liệu vào tbl_user và lấy ra số thứ tự bản ghi mới nhất
	 * @Param truyền vào tbl_user
	 * @return user_id
	 */
	public int insertUser(TblUser tblUser,Connection conn) throws SQLException {
		try {
			
			//tao cau truy van
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO tbl_user (`group_id`, `login_name`, `password`, `full_name`, `full_name_kana`, `email`, `tel`, `birthday`, `rule`, `salt`) ");
			sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?); ");

			ps = conn.prepareStatement(sql.toString());
			
			int i = 1;
	            // set parameters for statement
			ps.setInt(i++, tblUser.getGroupId());
			ps.setString(i++, tblUser.getLoginName());
			ps.setString(i++, tblUser.getPassword());
			ps.setString(i++, tblUser.getFullName());
			ps.setString(i++, tblUser.getFullNameKana());
			ps.setString(i++, tblUser.getEmail());
			ps.setString(i++, tblUser.getTel());
			ps.setString(i++, tblUser.getBirthday());
			ps.setInt(i++, tblUser.getRule());
			ps.setString(i++, tblUser.getSalt());
			int insertOk = ps.executeUpdate();
			if(insertOk > 0) {
				StringBuilder sql1 = new StringBuilder();
				sql1.append(" SELECT LAST_INSERT_ID(); ");
				ps = conn.prepareStatement(sql1.toString());
				ResultSet rs = ps.executeQuery();
				int user_id = 0;
				while (rs.next()) {
					user_id = rs.getInt("LAST_INSERT_ID()");
				}
				return user_id;
			}
			//chạy câu truy vấn
			
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl insertTblUser" + e.getMessage());
			throw e;
		} 
		return -1;
	}
	/**
	 * update TblUser 
	 * @param TblUser
	 * @throws SQLException 
	 */
	@Override
	public void updateTblUser(TblUser tblUser, Connection conn) throws SQLException {
		try {
			
			//tao cau truy van
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `tbl_user` SET `GROUP_ID` = ?, `FULL_NAME` = ?, `FULL_NAME_KANA` = ?, `EMAIL` = ?, `TEL` = ?, `BIRTHDAY` = ? WHERE `USER_ID` = ?");

			ps = conn.prepareStatement(sql.toString());
			
			int i = 1;
	            // set parameters for statement
			ps.setInt(i++, tblUser.getGroupId());
			ps.setString(i++, tblUser.getFullName());
			ps.setString(i++, tblUser.getFullNameKana());
			ps.setString(i++, tblUser.getEmail());
			ps.setString(i++, tblUser.getTel());
			ps.setString(i++, tblUser.getBirthday());
			ps.setInt(i++, tblUser.getUserId());
			 ps.executeUpdate();
			
			//chạy câu truy vấn
			
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl insertTblUser" + e.getMessage());
			throw e;
		} 
	}
	/**
	 * lấy userInfor theo Id
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public UserInfor getUserInforByUserId(int userId) throws SQLException {
		try {
			connection = super.getConnection();

			StringBuilder sql = new StringBuilder("SELECT u.password,g.group_id,u.user_id,u.login_name, u.full_name,u.full_name_kana, u.email, u.tel, u.birthday,d.code_level, g.group_name,j.name_level ,d.start_date, d.end_date,d.total ");
			sql.append(" FROM tbl_user u ");
			sql.append(" INNER JOIN mst_group g ON u.group_id = g.group_id ");
			sql.append(" LEFT JOIN (tbl_detail_user_japan d INNER JOIN mst_japan j ON d.code_level = j.code_level ) ");
			sql.append(" ON u.user_id = d.user_id ");
			sql.append(" WHERE u.RULE = 1 and u.user_id = ?");
			ps = connection.prepareStatement(sql.toString());
			
            // set parameters for statement
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			UserInfor userInfor = new UserInfor();
			while (rs.next()) {
				userInfor.setPassword(rs.getString("password"));
				userInfor.setGroupId(rs.getInt("group_id"));
				userInfor.setUserId(rs.getInt("user_id"));
				userInfor.setLoginName(rs.getString("login_name"));
				userInfor.setFullName(rs.getString("full_name"));
				userInfor.setFullNameKana(rs.getString("full_name_kana"));
				userInfor.setEmail(rs.getString("email"));
				userInfor.setTel(rs.getString("tel"));
				userInfor.setBirthday(rs.getString("birthday"));
				userInfor.setCodeLevel(rs.getString("code_level"));
				userInfor.setGroupName(rs.getString("group_name"));
				userInfor.setNameLevel(rs.getString("name_level"));
				userInfor.setStartDate(rs.getString("start_date"));
				userInfor.setEndDate(rs.getString("end_date"));
				userInfor.setTotal(String.valueOf(rs.getInt("total")));
				return userInfor;
			}
		
			
		} catch (SQLException e) {
			System.out.println("TblUserDaoImpl getUserInforById()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return null;
	}
	
	/**
	 * check userId có tồn tại không
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public TblUser getTblUserById(int userId) throws SQLException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT u.user_id FROM tbl_user u "
					+ "WHERE u.RULE = 1 and u.user_id = ? ";
			
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn
			ps.setInt(1,userId);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				TblUser tblUser = new TblUser();
				tblUser.setUserId(rs.getInt("user_id"));
				return tblUser;
			}
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getTblUserById()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return null;
	}
	/**
	 * xóa tblUser theo userId
	 * @param userId 
	 * @param con 
	 * @throws SQLException
	 */
	@Override
	public void deleteUserByUserId(int userId,Connection con) throws SQLException {
		try {
			//tao cau truy van
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM `tbl_user` WHERE (`user_id` = ?); ");
				
			PreparedStatement ps = null;
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1,userId);
			
			//chạy câu truy vấn
			ps.executeUpdate();

		} catch (SQLException e) {
			//ghi log
			System.out.println("TblDetailUserJapanDaoImpl deleteDetailJapanByUserId()" + e.getMessage());
			throw e;
		} 
		
	}
	/**
	 * hàm lấy email theo user Id
	 * @param userId 
	 */
	@Override
	public TblUser getEmailByUserId(int userId) throws SQLException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT tbl_user.email FROM tbl_user where tbl_user.user_id = ?; ";
			
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn
			ps.setInt(1,userId);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs
			while (rs.next()) {
				TblUser tblUser = new TblUser();
				tblUser.setEmail(rs.getString("email"));
				return tblUser;
			}
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getEmailByUserId()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
		return null;
	}
	/**
	 * lấy rule theo UserId
	 * @param userId
	 * @return rule
	 * @throws SQLException
	 */
	@Override
	public int getRuleByUserId(int userId) throws SQLException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT tbl_user.rule FROM tbl_user where tbl_user.user_id = ?; ";
			
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn
			ps.setInt(1,userId);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs\
			int rule = -1;
			while (rs.next()) {
				rule = rs.getInt("rule");
			}
			return rule;
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getRuleByUserId()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}

	}
	/**
	 * hàm lấy rule theo loginname 
	 * @param truyền vào loginName
	 * @return trả về rule
	 */

	@Override
	public int getRuleByLoginName(String loginName) throws SQLException {
		try {
			//mở kết nối
			connection = super.getConnection();
			//tao cau truy van
			String sql = "SELECT tbl_user.rule FROM tbl_user where tbl_user.login_name = ?; ";
			
			ps = connection.prepareStatement(sql.toString());
			//set dữ liệu cho câu truy vấn
			ps.setString(1,loginName);
			//chạy câu lệnh truy vấn
			ResultSet rs = ps.executeQuery();
			//chạy từng phần tử trong rs\
			int rule = -1;
			while (rs.next()) {
				rule = rs.getInt("rule");
			}
			return rule;
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl getRuleByUserId()" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			 super.close();
		}
	}
	
}
