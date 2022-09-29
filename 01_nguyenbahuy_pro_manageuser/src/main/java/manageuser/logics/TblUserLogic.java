/**
 * copyright(C) 2022 Luvina JSC
 * TblUserLogic.java May 3, 2022 nguyenbahuy
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * @author nguyenbahuy
 * tạo các hàm kiểm tra logic
 */
public interface TblUserLogic {
	/**
	 * hàm kiểm tra tài khoản có tồn tại
	 * @param loginId
	 * @param password
	 * @return true khi thỏa mãn các điều kiện login,false khi không thỏa mãn
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 */
	public boolean existAccount(String loginId,String password) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException;
	/**
	 * hàm lấy danh sách UserInfor theo các tham số
	 * @Param offset vị trí data cần lấy nào
	 * @Param limit số lượng lấy
	 * @Param groupId mã nhóm tìm kiếm
	 * @Param fullName Tên tìm kiếm
	 * @Param sortType: Nhận biết xem cột nào được ưu tiên sắp xếp 
	 * @Param sortByFullName,sortByCodeLevel,sortByEndDate: Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * trả về danh sách userInfor
	 * @Exception SQLException
	 */
	public List<UserInfor> getListUsers(int offset,int limit,int groupID,String fullName,String sortType,String sortByFullName,String sortByCodeLevel,String sortByEndDate) throws SQLException, ClassNotFoundException;
	/**
	 * hàm lấy tổng số user
	 * @Param groupId mã nhóm tìm kiếm
	 * @Param fullName Tên tìm kiếm
	 * @return trả về tổng số user
	 */
	public int getTotalUsers(int groupId,String fullName) throws SQLException, ClassNotFoundException;
	/**
	 * hàm check tài khoản đã tồn tại chưa
	 * @Param truyền vào loginName
	 * @return đúng khi loginName chưa tồn tại,sai khi loginName đã tồn tại
	 */
	public boolean checkExistLoginName(String loginName) throws ClassNotFoundException, SQLException;
	/**
	 * check email đã tồn tại chưa
	 * @Param truyền vào email
	 * @return đúng khi email tồn tại ,sai khi email chưa tồn tại
	 */
	public boolean checkExistEmail(String email) throws ClassNotFoundException, SQLException;
	/**
	 * check tạo user
	 * @param userInfor
	 * @return trả về true nếu thực hiện insert thành công vào DB và false khi không thoả mãn điều kiện check
	 * @throws NoSuchAlgorithmException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean createUser(UserInfor userInfor) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException;
	/**
	 * hàm lấy UserInfor theo userId
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public UserInfor getUserInforByUserId(int userId) throws SQLException;
	/**
	 * kiểm tra xem có tồn tại userId
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkExistUserId(int userId) throws SQLException;
	/**
	 * xóa UserInfor theo userId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteUser(int userId) throws SQLException;
	/**
	 * kiểm tra xem có email
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkEmailByUserId(int userId,String emailNew) throws SQLException;
	/**
	 * thực hiện update
	 * @param userInfor
	 * @return đúng nếu update thành công,sai khi update thất bại
	 * @throws SQLException 
	 */
	public boolean updateUser(UserInfor userInfor) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;
	/**
	 * lấy rule theo userId
	 * @param userId
	 * @return rule 
	 * @throws SQLException 
	 */
	public int getRuleByUserId(int userId) throws SQLException;
	/**
	 * lấy rule theo loginName
	 * @param loginName
	 * @return
	 * @throws SQLException
	 */
	public int getRuleByLoginName(String loginName) throws SQLException;
}
