/**
 * copyright(C) 2022 Luvina JSC
 * TblUserDao.java May 3, 2022 nguyenbahuy
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * @author nguyenbahuy
 * thực hiện thao tác bảng tbl_User
 */
public interface TblUserDao extends BaseDao {
	/**
	 * hàm lấy tbl_user theo loginId
	 * @param loginId,rule
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblUser getTblUserByUsername(String loginId,int rule) throws SQLException, ClassNotFoundException;
	/**
	 * hàm lấy danh sách userInfor theo điều kiện
	 * @param offset
	 * @param limit
	 * @param groupID
	 * @param fullName
	 * @param sortType
	 * @param sortByFullName
	 * @param sortByCodeLevel
	 * @param sortByEndDate
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<UserInfor> getListUsers(int offset,int limit,int groupID,String fullName,String sortType,String sortByFullName,String sortByCodeLevel,String sortByEndDate) throws SQLException, ClassNotFoundException;
	/**
	 * hàm lấy tổng số user theo điều kiện
	 * @param groupId
	 * @param fullName
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getTotalUsers(int groupId,String fullName) throws SQLException, ClassNotFoundException;
	/**
	 * lấy tbl user theo email 
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblUser getTblUserByEmail(String email) throws SQLException, ClassNotFoundException;
	/**
	 * insert tbl_user vào db
	 * @param tblUser
	 * @param con
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int insertUser(TblUser tblUser,Connection con) throws SQLException,ClassNotFoundException;
	/**
	 * lấy userInfor theo Id
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public UserInfor getUserInforByUserId(int userId) throws SQLException;
	/**
	 * check userId có tồn tại không
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public TblUser getTblUserById(int userId) throws SQLException;
	/**
	 * xóa tblUser theo userId
	 * @param userId 
	 * @param con 
	 * @throws SQLException
	 */
	public void deleteUserByUserId(int userId, Connection con) throws SQLException;
	/**
	 * check email từ userId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public TblUser getEmailByUserId(int userId) throws SQLException;
	/**
	 * hàm thực hiện update TblUser
	 * @param tblUser
	 * @param con
	 * @throws SQLException
	 */
	public void updateTblUser(TblUser tblUser, Connection con) throws SQLException;
	/**
	 * lấy rule theo UserId
	 * @param userId
	 * @return rule
	 * @throws SQLException
	 */
	public int getRuleByUserId(int userId) throws SQLException;
	/**
	 * lấy rule theo loginName
	 * @param loginName
	 * @return rule
	 * @throws SQLException
	 */
	public int getRuleByLoginName(String loginName) throws SQLException;
}
